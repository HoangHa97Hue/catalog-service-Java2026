package com.ecommerce.catalog.product.infrastructure.persistence;

import com.ecommerce.catalog.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository
        extends JpaRepository<Product, UUID> {
}