package com.ecommerce.basket.domain;

import java.util.List;
import java.util.UUID;

public class ShoppingCart {
    private UUID userId;
    private List<ShoppingCartItem> items ; // ???
    private double total;

    public ShoppingCart(UUID userId, List<ShoppingCartItem> items, double total) {
        this.userId = userId;
        this.items = items;
        this.total = total;
    }
}
