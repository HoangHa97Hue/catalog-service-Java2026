package com.ecommerce.catalog.product.api;

import com.ecommerce.catalog.product.domain.Product;
import com.ecommerce.catalog.product.domain.ProductImage;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CreateProductRequest(

        @NotBlank
        String name,

        String description,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal price,

//        ProductImage image,

        List<@NotBlank String> categories

) {
}