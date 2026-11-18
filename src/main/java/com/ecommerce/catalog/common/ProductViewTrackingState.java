package com.ecommerce.catalog.common;

import org.springframework.stereotype.Component;

@Component
public class ProductViewTrackingState {

    private volatile boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }
}