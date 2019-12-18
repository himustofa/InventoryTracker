package com.appsit.inventorytracker.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "purchases")
public class Purchase {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String purchaseId;
    @NonNull
    private String productName;
    private String productId;
    @NonNull
    private String supplierName;
    private String supplierId;
    @NonNull
    private int purchaseProductQuantity;
    @NonNull
    private double purchaseProductPrice;
    @NonNull
    private String purchaseDate;
    private double purchaseAmount;
    @NonNull
    private double purchasePayment;
    private double purchaseBalance;
    private String purchaseDescription;
    private String createdAt = String.valueOf(new Timestamp(System.currentTimeMillis()));

    public Purchase(@NonNull String purchaseId, @NonNull String productName, String productId, @NonNull String supplierName, String supplierId, int purchaseProductQuantity, double purchaseProductPrice, @NonNull String purchaseDate, double purchaseAmount, double purchasePayment, double purchaseBalance, String purchaseDescription) {
        this.purchaseId = purchaseId;
        this.productName = productName;
        this.productId = productId;
        this.supplierName = supplierName;
        this.supplierId = supplierId;
        this.purchaseProductQuantity = purchaseProductQuantity;
        this.purchaseProductPrice = purchaseProductPrice;
        this.purchaseDate = purchaseDate;
        this.purchaseAmount = purchaseAmount;
        this.purchasePayment = purchasePayment;
        this.purchaseBalance = purchaseBalance;
        this.purchaseDescription = purchaseDescription;
    }

    @NonNull
    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(@NonNull String purchaseId) {
        this.purchaseId = purchaseId;
    }

    @NonNull
    public String getProductName() {
        return productName;
    }

    public void setProductName(@NonNull String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @NonNull
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(@NonNull String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public int getPurchaseProductQuantity() {
        return purchaseProductQuantity;
    }

    public void setPurchaseProductQuantity(int purchaseProductQuantity) {
        this.purchaseProductQuantity = purchaseProductQuantity;
    }

    public double getPurchaseProductPrice() {
        return purchaseProductPrice;
    }

    public void setPurchaseProductPrice(double purchaseProductPrice) {
        this.purchaseProductPrice = purchaseProductPrice;
    }

    @NonNull
    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(@NonNull String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(double purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public double getPurchasePayment() {
        return purchasePayment;
    }

    public void setPurchasePayment(double purchasePayment) {
        this.purchasePayment = purchasePayment;
    }

    public double getPurchaseBalance() {
        return purchaseBalance;
    }

    public void setPurchaseBalance(double purchaseBalance) {
        this.purchaseBalance = purchaseBalance;
    }

    public String getPurchaseDescription() {
        return purchaseDescription;
    }

    public void setPurchaseDescription(String purchaseDescription) {
        this.purchaseDescription = purchaseDescription;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
