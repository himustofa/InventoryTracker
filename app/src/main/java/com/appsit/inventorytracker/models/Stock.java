package com.appsit.inventorytracker.models;

public class Stock {

    private String productId;
    private String productName;
    private int stockQuantity;
    private double stockAmount;

    public Stock(String productId, String productName, int stockQuantity, double stockAmount) {
        this.productId = productId;
        this.productName = productName;
        this.stockQuantity = stockQuantity;
        this.stockAmount = stockAmount;
    }
}
