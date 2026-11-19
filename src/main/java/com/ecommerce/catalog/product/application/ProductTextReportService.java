package com.ecommerce.catalog.product.application;

import com.ecommerce.catalog.product.infrastructure.persistence.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductTextReportService {

    private final ProductRepository productRepository;

    public ProductTextReportService(
            ProductRepository productRepository
    ) {
        this.productRepository = productRepository;
    }

    public String generate() {

        StringBuilder builder = new StringBuilder();

        productRepository.findAll()
                .forEach(product -> {
                    builder.append("Product: ")
                            .append(product.getName())
                            .append(System.lineSeparator());

                    builder.append("Price: ")
                            .append(product.getPrice())
                            .append(System.lineSeparator());

                    builder.append("Categories: ")
                            .append(product.getCategories())
                            .append(System.lineSeparator());

                    builder.append("----------------")
                            .append(System.lineSeparator());
                });

        return builder.toString();
    }
}
