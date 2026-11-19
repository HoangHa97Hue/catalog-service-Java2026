package com.ecommerce.catalog.product.application;

import com.ecommerce.catalog.product.domain.Product;
import com.ecommerce.catalog.product.infrastructure.persistence.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class UpdateProductPriceService {

    private final ProductRepository productRepository;

    public UpdateProductPriceService(
            ProductRepository productRepository
    ) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void update(
            UUID id,
            BigDecimal newPrice
    ) {
        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new ProductNotFoundException(id)
                );

        product.changePrice(newPrice);
    }
}
