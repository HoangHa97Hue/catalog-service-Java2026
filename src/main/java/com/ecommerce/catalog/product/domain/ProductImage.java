package com.ecommerce.catalog.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ProductImage {  //is value object of Product
    @Column(name = "image_object_key")
    private String objectKey;
    @Column(name = "image_original_file_name")
    private String originalFileName;
    @Column(name = "image_content_type")
    private String contentType; // k3 will use thís for display
    @Column(name = "image_size")
    private Long size;

    protected ProductImage() {
        // Required by JPA
    }

    public ProductImage(
            String objectKey,
            String originalFileName,
            String contentType,
            Long size
    ) {
        if (objectKey == null || objectKey.isBlank()) {
            throw new IllegalArgumentException(
                    "Image object key cannot be empty"
            );
        }

        if (size == null || size <= 0) {
            throw new IllegalArgumentException(
                    "Image size must be positive"
            );
        }

        this.objectKey = objectKey;
        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.size = size;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public String getContentType() {
        return contentType;
    }

    public Long getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ProductImage that)) {
            return false;
        }

        return Objects.equals(objectKey, that.objectKey)
                && Objects.equals(
                originalFileName,
                that.originalFileName
        )
                && Objects.equals(
                contentType,
                that.contentType
        )
                && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                objectKey,
                originalFileName,
                contentType,
                size
        );
    }

    /*
    image2.hashCode()
        ↓
    xác định bucket cần tìm
            ↓
    equals()
            ↓
    có đúng key mình đang tìm không?
     */
}
