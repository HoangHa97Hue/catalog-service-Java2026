package com.ecommerce.catalog.product.api;

import com.ecommerce.catalog.common.ProductViewTrackingState;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product-view-tracking")
public class ProductViewTrackingController  {
    private final ProductViewTrackingState trackingState;

    public ProductViewTrackingController(ProductViewTrackingState trackingState) {
        this.trackingState = trackingState;
    }

    @PostMapping("/enable")
    public void enable(){
        trackingState.enable();
    }

    @PostMapping("/disable")
    public void disable() {
        trackingState.disable();
    }


    @GetMapping
    public boolean getStatus() {
        return trackingState.isEnabled();
    }
}
