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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public double getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(double stockAmount) {
        this.stockAmount = stockAmount;
    }
}
