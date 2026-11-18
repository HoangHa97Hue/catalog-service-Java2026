package com.ecommerce.catalog.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

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
}
