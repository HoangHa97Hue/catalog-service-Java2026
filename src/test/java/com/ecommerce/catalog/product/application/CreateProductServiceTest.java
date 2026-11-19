package com.ecommerce.catalog.product.application;

import com.ecommerce.catalog.product.api.CreateProductRequest;
import com.ecommerce.catalog.product.api.ProductResponse;
import com.ecommerce.catalog.product.domain.Product;
import com.ecommerce.catalog.product.infrastructure.persistence.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProductService createProductService;

    @Test
    void create_shouldSaveProductAndNormalizeCategories() {
        CreateProductRequest request = new CreateProductRequest(
                "IPad10",
                "Description for IPad10",
                new BigDecimal("455.00"),
                List.of(" Tablet ", "Apple", "Tablet", " ")
        );

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponse response = createProductService.create(request);

        assertThat(response.name()).isEqualTo("IPad10");
        assertThat(response.price()).isEqualByComparingTo("455.00");
        assertThat(response.categories())
                .containsExactly("Tablet", "Apple");
        assertThat(response.image()).isNull();

        verify(productRepository).save(any(Product.class));
    }
}
