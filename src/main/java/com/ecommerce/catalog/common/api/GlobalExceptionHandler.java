package com.ecommerce.catalog.common.api;

import com.ecommerce.catalog.product.application.InvalidProductImageException;
import com.ecommerce.catalog.product.application.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(
            ProductNotFoundException ex
    ) {
        return Map.of(
                "error",
                ex.getMessage()
        );
    }

    @ExceptionHandler(InvalidProductImageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidImage(
            InvalidProductImageException ex
    ) {
        return Map.of(
                "error",
                ex.getMessage()
        );
    }
}