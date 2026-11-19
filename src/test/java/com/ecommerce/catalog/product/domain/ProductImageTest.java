package com.ecommerce.catalog.product.domain;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ProductImageTest {

    @Test
    void equalValueObjects_shouldHaveSameEqualsAndHashCode() {
        ProductImage image1 = new ProductImage(
                "products/1/ipad.png",
                "ipad.png",
                "image/png",
                100L
        );

        ProductImage image2 = new ProductImage(
                "products/1/ipad.png",
                "ipad.png",
                "image/png",
                100L
        );

        assertThat(image1).isEqualTo(image2);
        assertThat(image1.hashCode()).isEqualTo(image2.hashCode());

        Set<ProductImage> images = new HashSet<>();
        images.add(image1);
        images.add(image2);

        assertThat(images).hasSize(1);
    }
}
