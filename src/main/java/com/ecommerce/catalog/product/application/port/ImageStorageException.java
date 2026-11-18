package com.ecommerce.catalog.product.application.port;

public class ImageStorageException extends RuntimeException {

    public ImageStorageException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }
}