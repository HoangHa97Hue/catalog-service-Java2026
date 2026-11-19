package com.ecommerce.catalog.product.application;

public class InvalidProductImageException
        extends RuntimeException {

    public InvalidProductImageException(String message) {
        super(message);
    }
}