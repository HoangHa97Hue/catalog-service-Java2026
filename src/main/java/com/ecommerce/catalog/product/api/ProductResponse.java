package com.ecommerce.catalog.product.api;

import com.ecommerce.catalog.product.domain.Product;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        List<String> categories,
        ProductImageResponse image
) {
    public static ProductResponse from(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategories(),
                ProductImageResponse.from(product.getImage())
        );
    }
}