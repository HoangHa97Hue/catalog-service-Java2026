package com.ecommerce.catalog.product.application;

import com.ecommerce.catalog.product.api.ProductImageResponse;
import com.ecommerce.catalog.product.application.port.ImageStorage;
import com.ecommerce.catalog.product.domain.Product;
import com.ecommerce.catalog.product.domain.ProductImage;
import com.ecommerce.catalog.product.infrastructure.persistence.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UploadProductImageService {

    private final ProductRepository productRepository;
    private final ImageStorage imageStorage;

    public UploadProductImageService(
            ProductRepository productRepository,
            ImageStorage imageStorage
    ) {
        this.productRepository = productRepository;
        this.imageStorage = imageStorage;
    }

    @Transactional
    public ProductImageResponse upload(
            UUID productId,
            String originalFileName,
            String contentType,
            byte[] content
    ) {

        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new ProductNotFoundException(productId)
                );

        String objectKey = generateObjectKey(
                productId,
                originalFileName
        );

        imageStorage.upload(
                objectKey,
                contentType,
                content
        );

        ProductImage image = new ProductImage(
                objectKey,
                originalFileName,
                contentType,
                (long) content.length
        );

        product.changeImage(image);

        productRepository.save(product);

        return ProductImageResponse.from(image);
    }

    private String generateObjectKey(
            UUID productId,
            String originalFileName
    ) {

        return "products/"
                + productId
                + "/"
                + UUID.randomUUID()
                + "-"
                + originalFileName;
    }
}