package com.ecommerce.catalog.product.application;

import java.util.UUID;

public record ProductViewStat(
        UUID productId,
        int views
) {
}