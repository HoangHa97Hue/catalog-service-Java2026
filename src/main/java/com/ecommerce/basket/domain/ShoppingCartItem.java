package com.ecommerce.basket.domain;

import java.util.List;
import java.util.UUID;

public class ShoppingCartItem {
    public ShoppingCartItem(UUID productId, String productName, String description, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    private UUID productId;
    private String productName;
    private String description;
    private int quantity;
    private double price;

}
