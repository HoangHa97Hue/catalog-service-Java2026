package com.ecommerce.catalog.product.application;

import com.ecommerce.catalog.product.api.CreateProductRequest;
import com.ecommerce.catalog.product.api.ProductResponse;
import com.ecommerce.catalog.product.domain.Product;
import com.ecommerce.catalog.product.infrastructure.persistence.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateProductService {

    private final ProductRepository productRepository;

    public CreateProductService(
            ProductRepository productRepository
    ) {
        this.productRepository = productRepository;
    }

    @Transactional //want to add save product with collection categories in same transaction
    public ProductResponse create(
            CreateProductRequest request
    ) {
        Product product = new Product(
                UUID.randomUUID(),
                request.name(),
                request.description(),
                request.price(),
                null,
                request.categories()
        );

        Product saved = productRepository.save(product);

        return  ProductResponse.from(saved);
    }
}