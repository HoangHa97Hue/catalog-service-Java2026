# Catalog Service - Java knowledge actually applied

## 1. Project structure

```text
src/main/java/com/ecommerce/catalog/
├── common/
│   ├── ProductViewTrackingState.java
│   └── api/
│       ├── GlobalExceptionHandler.java
│       └── PageResponse.java
└── product/
    ├── api/
    │   ├── CreateProductRequest.java
    │   ├── ProductController.java
    │   ├── ProductImageResponse.java
    │   ├── ProductResponse.java
    │   └── ProductViewTrackingController.java
    ├── application/
    │   ├── CreateProductService.java
    │   ├── GetProductsService.java
    │   ├── InvalidProductImageException.java
    │   ├── ProductNotFoundException.java
    │   ├── ProductTextReportService.java
    │   ├── ProductViewCounterService.java
    │   ├── ProductViewStat.java
    │   ├── UpdateProductPriceService.java
    │   ├── UploadProductImageService.java
    │   └── port/
    │       ├── ImageStorage.java
    │       └── ImageStorageException.java
    ├── domain/
    │   ├── Product.java
    │   ├── ProductImage.java
    │   └── ProductImagePolicy.java
    └── infrastructure/
        ├── persistence/
        │   └── ProductRepository.java
        └── storage/
            ├── S3Config.java
            └── S3ImageStorage.java
```

## 2. Java / Spring topics currently applied

| Kiến thức | Chỗ đang apply thật |
|---|---|
| OOP / Encapsulation | `Product`, `ProductImage`, các method `changeImage`, `changePrice` |
| Record | `CreateProductRequest`, `ProductResponse`, `ProductImageResponse`, `PageResponse<T>`, `ProductViewStat` |
| Generics | `PageResponse<T>`, Spring Data `Page<Product>` / `Page<ProductResponse>` |
| Bean Validation | `CreateProductRequest` với `@NotBlank`, `@NotNull`, `@DecimalMin` |
| JPA / PostgreSQL | `Product`, `ProductRepository extends JpaRepository` |
| `@ElementCollection` | `Product.categories` lưu trong bảng `product_categories` |
| Functional Programming / Stream | normalize categories: `map`, `filter`, `distinct`, `toList` |
| Lambda / Method reference | `String::trim`, `ProductResponse::from`, `computeIfAbsent` |
| Optional / Supplier | `findById(...).orElseThrow(() -> new ProductNotFoundException(...))` |
| List / Set / Map | categories, `ProductImagePolicy.ALLOWED_CONTENT_TYPES`, view counter |
| `equals` / `hashCode` | `ProductImage` value object; có test với `HashSet` |
| `ConcurrentHashMap` | lưu view counter theo `productId` |
| `AtomicInteger` | tăng view count atomically |
| `volatile` | `ProductViewTrackingState.enabled` để đảm bảo visibility giữa các thread |
| Comparator | sort danh sách top viewed products |
| StringBuilder | `ProductTextReportService` tạo text report |
| Exception handling | custom exception + `GlobalExceptionHandler` |
| Transaction | Create product, upload image, update price |
| Interface / DIP | `ImageStorage` port -> `S3ImageStorage` implementation |
| AWS S3 SDK v2 | `S3Client`, `RequestBody`, `DefaultCredentialsProvider` |
| Spring Actuator | expose health/info/metrics để xem JVM memory, GC, threads |
| JUnit / Mockito | unit test `CreateProductServiceTest` |
| Concurrency testing | `ProductViewCounterServiceTest` chạy nhiều thread cùng increment |
| JPA integration test | `ProductRepositoryIntegrationTest` dùng `@DataJpaTest` + H2 |

## 3. Representative tests

```text
src/test/java/com/ecommerce/catalog/product/
├── application/
│   ├── CreateProductServiceTest.java
│   └── ProductViewCounterServiceTest.java
├── domain/
│   └── ProductImageTest.java
└── infrastructure/persistence/
    └── ProductRepositoryIntegrationTest.java
```

### CreateProductServiceTest
- Mockito mock `ProductRepository`.
- Verify service gọi `save`.
- Verify categories được trim, bỏ blank và distinct.

### ProductImageTest
- Hai value object có cùng dữ liệu phải `equals == true`.
- Khi add cả hai vào `HashSet`, set chỉ giữ một phần tử.
- Demonstrate contract giữa `equals()` và `hashCode()`.

### ProductViewCounterServiceTest
- Nhiều thread increment cùng một product.
- Kết quả cuối phải chính xác nhờ `ConcurrentHashMap + AtomicInteger`.
- Test tracking disabled để demonstrate `volatile` flag được dùng trong flow.

### ProductRepositoryIntegrationTest
- Dùng `@DataJpaTest` và H2 test database.
- Save/read Product qua JPA thật.
- Verify `categories` và embedded `ProductImage` được persist/map đúng.

## 4. AWS image flow currently implemented

```text
POST /api/products/{id}/image
        ↓
ProductController
        ↓
UploadProductImageService
        ↓
ImageStorage (interface)
        ↓
S3ImageStorage
        ↓
Amazon S3

ProductImage metadata
        ↓
Product
        ↓
PostgreSQL
```

`ProductImagePolicy` hiện validate:
- file không rỗng;
- tối đa 5 MB;
- content type chỉ nhận `image/jpeg`, `image/png`, `image/webp`.

## 5. Topics intentionally NOT claimed as implemented

Các chủ đề dưới đây đã học/thảo luận nhưng **chưa được đưa vào source code hiện tại**, nên không claim khi present:

- JDBC reporting / `JdbcTemplate`
- File I/O CSV export / try-with-resources
- `StringBuffer`
- manual `synchronized` production implementation
- deadlock demo
- virtual threads
- JPA `@Version` optimistic locking
- AOP
- Testcontainers
