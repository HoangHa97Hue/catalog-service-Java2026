package com.ecommerce.catalog.product.application;

import com.ecommerce.catalog.common.api.PageResponse;
import com.ecommerce.catalog.product.api.CreateProductRequest;
import com.ecommerce.catalog.product.api.ProductResponse;
import com.ecommerce.catalog.product.domain.Product;
import com.ecommerce.catalog.product.infrastructure.persistence.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetProductsService {

    private final ProductRepository productRepository;
    private final ProductViewCounterService viewCounterService;

    public GetProductsService(
            ProductRepository productRepository, ProductViewCounterService viewCounterService
    ) {
        this.productRepository = productRepository;
        this.viewCounterService = viewCounterService;
    }


    public PageResponse<ProductResponse> getAll(
            int page,
            int size
    ) {

        Page<Product> products =
                productRepository.findAll(
                        PageRequest.of(page, size)
                );

        List<ProductResponse> responses =
                products.stream()
                        .map(ProductResponse::from)
                        .toList();

        return new PageResponse<>(
                responses,
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages()
        );
    }

    public ProductResponse getById(UUID id){
        ProductResponse response =  productRepository.findById(id)
                .map(ProductResponse::from)
                .orElseThrow(() -> new ProductNotFoundException(id));

        viewCounterService.increment(id);

        return response;
    }

//    @Transactional //want to add save product with collection categories in same transaction
//    public ProductResponse create(
//            CreateProductRequest request
//    ) {
//        Product product = new Product(
//                UUID.randomUUID(),
//                request.name(),
//                request.description(),
//                request.price(),
//                null,
//                request.categories()
//        );
//
//        Product saved = productRepository.save(product);
//
//        return  ProductResponse.from(product);
//    }
}
