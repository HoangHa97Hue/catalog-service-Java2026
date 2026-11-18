package com.ecommerce.catalog.product.application.port;

public interface ImageStorage {
    String upload(
      String objectKey,
      String contentType,
      byte[] content
    );
}
