# Catalog Service - API manual test flow

Base URL:

```text
http://localhost:8080
```

## 0. Preconditions

1. PostgreSQL đang chạy và có database `catalog_db`.
2. `src/main/resources/application.yml` có đúng username/password PostgreSQL.
3. Nếu test upload ảnh S3:
   - AWS CLI đã login thành công.
   - `aws sts get-caller-identity` chạy được.
   - `aws s3 ls` nhìn thấy bucket cấu hình trong `application.yml`.
4. Start `CatalogServiceApplication` trên port `8080`.

---

## 1. Create product

```http
POST /api/products
Content-Type: application/json
```

Postman -> Body -> raw -> JSON:

```json
{
  "name": "IPad10",
  "description": "Description for IPad10",
  "price": 455,
  "categories": [" Tablet ", "Apple", "Tablet"]
}
```

Expected: `201 Created`.

Categories trong response phải được normalize thành:

```json
["Tablet", "Apple"]
```

Copy `id` trả về để dùng cho các bước sau.

---

## 2. Get products with pagination

```http
GET /api/products?page=0&size=10
```

Expected response có:

```json
{
  "items": [],
  "page": 0,
  "size": 10,
  "totalElements": 0,
  "totalPages": 0
}
```

Sau bước create, `items` phải chứa Product vừa tạo.

---

## 3. Get product by id + increment view count

```http
GET /api/products/{productId}

```

Ví dụ gọi endpoint này 3 lần.

Mỗi lần GET thành công sẽ gọi `ProductViewCounterService.increment(productId)`.

---

## 4. Check view count

```http
GET /api/products/{productId}/views
```

Nếu bước 3 gọi 3 lần thì expected body:

```text
3
```

---

## 5. Disable / enable view tracking (`volatile` demo)

Check status:

```http
GET /api/product-view-tracking
```

Disable:

```http
POST /api/product-view-tracking/disable
```

Sau đó gọi lại:

```http
GET /api/products/{productId}
```

nhiều lần rồi check:

```http
GET /api/products/{productId}/views
```

Count phải giữ nguyên.

Enable lại:

```http
POST /api/product-view-tracking/enable
```

Sau đó GET product sẽ tăng count trở lại.

---

## 6. Top viewed products (`Stream + Comparator`)

```http
GET /api/products/top-viewed?limit=5
```

Expected dạng:

```json
[
  {
    "productId": "<uuid>",
    "views": 3
  }
]
```

---

## 7. Upload product image to AWS S3

```http
POST /api/products/{productId}/image
Content-Type: multipart/form-data
```

Postman:

```text
Body -> form-data
KEY: file
TYPE: File
VALUE: chọn file .png / .jpg / .webp nhỏ hơn 5 MB
```

Expected response:

```json
{
  "originalFileName": "ipad.png",
  "contentType": "image/png",
  "size": 12345
}
```

Sau đó kiểm tra:

1. AWS S3 Console: object nằm dưới `products/{productId}/...`.
2. PostgreSQL table `products`: các column image metadata đã có dữ liệu.

Negative test:
- upload file > 5 MB hoặc content type không thuộc jpeg/png/webp;
- expected: `400 Bad Request` với field `error`.

---

## 8. Update product price

```http
PATCH /api/products/{productId}/price?newPrice=499.99
```

Expected: `204 No Content`.

Verify:

```http
GET /api/products/{productId}
```

price phải là `499.99`.

Negative test:

```http
PATCH /api/products/{productId}/price?newPrice=0
```

Expected: `400 Bad Request`.

---

## 9. StringBuilder text report

```http
GET /api/products/report
```

Expected `text/plain`, ví dụ:

```text
Product: IPad10
Price: 499.99
Categories: [Tablet, Apple]
----------------
```

---

## 10. Error handling

Product không tồn tại:

```http
GET /api/products/00000000-0000-0000-0000-000000000001
```

Expected:

```http
404 Not Found
```

```json
{
  "error": "Product not found with id: ..."
}
```

---

## 11. Spring Actuator - JVM / GC / Threads

```http
GET /actuator/health
GET /actuator/metrics
GET /actuator/metrics/jvm.memory.used
GET /actuator/metrics/jvm.memory.max
GET /actuator/metrics/jvm.gc.pause
GET /actuator/metrics/jvm.threads.live
```

Actuator chỉ dùng để **observe** JVM. Không có endpoint nào ở đây "gọi GC" thủ công.

---

## 12. Automated tests

IntelliJ:

```text
Right click src/test/java -> Run 'All Tests'
```

Hoặc terminal:

```text
mvn test
```

Các test đại diện:

```text
CreateProductServiceTest
ProductImageTest
ProductViewCounterServiceTest
ProductRepositoryIntegrationTest
```
