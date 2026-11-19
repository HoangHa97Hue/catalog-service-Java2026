package com.ecommerce.catalog.product.persistence;

import com.ecommerce.catalog.product.domain.Product;
import com.ecommerce.catalog.product.domain.ProductImage;
import com.ecommerce.catalog.product.infrastructure.persistence.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void saveAndFindById_shouldPersistCategoriesAndImageMetadata() {
        UUID productId = UUID.randomUUID();

        ProductImage image = new ProductImage(
                "products/" + productId + "/ipad.png",
                "ipad.png",
                "image/png",
                1024L
        );

        Product product = new Product(
                productId,
                "IPad10",
                "Description for IPad10",
                new BigDecimal("455.00"),
                image,
                List.of("Tablet", "Apple")
        );

        productRepository.saveAndFlush(product);

        Product saved = productRepository.findById(productId)
                .orElseThrow();

        assertThat(saved.getName()).isEqualTo("IPad10");
        assertThat(saved.getCategories())
                .containsExactly("Tablet", "Apple");
        assertThat(saved.getImage()).isEqualTo(image);
    }
}
