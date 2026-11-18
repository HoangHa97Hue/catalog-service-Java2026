package com.ecommerce.catalog.product.domain;

import java.util.Set;

public final class ProductImagePolicy {

    public static final long MAX_IMAGE_SIZE =
            5 * 1024 * 1024;

    public static final Set<String> ALLOWED_CONTENT_TYPES =
            Set.of(
                    "image/jpeg",
                    "image/png",
                    "image/webp"
            );

    private ProductImagePolicy() {
    }
}
