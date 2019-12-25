package com.appsit.inventorytracker.models;

public class Dashboard {

    private int purchaseItems;
    private int saleItems;
    private int wastageItems;
    private int remainingItems;
    private double purchaseAmount;
    private double saleAmount;
    private double wastageAmount;
    private double remainingAmount;

    public Dashboard(int purchaseItems, int saleItems, int wastageItems, int remainingItems, double purchaseAmount, double saleAmount, double wastageAmount, double remainingAmount) {
        this.purchaseItems = purchaseItems;
        this.saleItems = saleItems;
        this.wastageItems = wastageItems;
        this.remainingItems = remainingItems;
        this.purchaseAmount = purchaseAmount;
        this.saleAmount = saleAmount;
        this.wastageAmount = wastageAmount;
        this.remainingAmount = remainingAmount;
    }

    public int getPurchaseItems() {
        return purchaseItems;
    }

    public void setPurchaseItems(int purchaseItems) {
        this.purchaseItems = purchaseItems;
    }

    public int getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(int saleItems) {
        this.saleItems = saleItems;
    }

    public int getWastageItems() {
        return wastageItems;
    }

    public void setWastageItems(int wastageItems) {
        this.wastageItems = wastageItems;
    }

    public int getRemainingItems() {
        return remainingItems;
    }

    public void setRemainingItems(int remainingItems) {
        this.remainingItems = remainingItems;
    }

    public double getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(double purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public double getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(double saleAmount) {
        this.saleAmount = saleAmount;
    }

    public double getWastageAmount() {
        return wastageAmount;
    }

    public void setWastageAmount(double wastageAmount) {
        this.wastageAmount = wastageAmount;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
}
