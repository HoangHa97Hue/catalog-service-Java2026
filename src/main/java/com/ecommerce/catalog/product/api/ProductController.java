package com.ecommerce.catalog.product.api;

import com.ecommerce.catalog.product.application.CreateProductService;
import com.ecommerce.catalog.product.application.ProductViewCounterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final CreateProductService createProductService;
    private final ProductViewCounterService productViewCounterService;

    public ProductController(
            CreateProductService createProductService, ProductViewCounterService productViewCounterService
    ) {
        this.createProductService = createProductService;
        this.productViewCounterService = productViewCounterService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(
            @Valid @RequestBody CreateProductRequest request
    ) {
        return createProductService.create(request);
    }

    @GetMapping("/{id}/views")
    public int getProductViews(@PathVariable UUID id) {
        return productViewCounterService.getCount(id);
    }
}