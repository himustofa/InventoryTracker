package com.appsit.inventorytracker.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "sales")
public class Sale {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String salesId;
    @NonNull
    private String productName;
    private String productId;
    @NonNull
    private int productQuantity;
    @NonNull
    private int purchaseProductQuantity;
    private String customerName;
    private String customerId;
    @NonNull
    private String salesDate;
    private double salesDiscount;
    @NonNull
    private double salesVat;
    @NonNull
    private double salesAmount;
    @NonNull
    private double salesPayment;
    private double salesBalance;
    private String salesDescription;
    private String createdAt = String.valueOf(new Timestamp(System.currentTimeMillis()));

    public Sale(@NonNull String salesId, @NonNull String productName, String productId, int productQuantity, int purchaseProductQuantity, String customerName, String customerId, @NonNull String salesDate, double salesDiscount, double salesVat, double salesAmount, double salesPayment, double salesBalance, String salesDescription) {
        this.salesId = salesId;
        this.productName = productName;
        this.productId = productId;
        this.productQuantity = productQuantity;
        this.purchaseProductQuantity = purchaseProductQuantity;
        this.customerName = customerName;
        this.customerId = customerId;
        this.salesDate = salesDate;
        this.salesDiscount = salesDiscount;
        this.salesVat = salesVat;
        this.salesAmount = salesAmount;
        this.salesPayment = salesPayment;
        this.salesBalance = salesBalance;
        this.salesDescription = salesDescription;
    }

    @NonNull
    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(@NonNull String salesId) {
        this.salesId = salesId;
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

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getPurchaseProductQuantity() {
        return purchaseProductQuantity;
    }

    public void setPurchaseProductQuantity(int purchaseProductQuantity) {
        this.purchaseProductQuantity = purchaseProductQuantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @NonNull
    public String getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(@NonNull String salesDate) {
        this.salesDate = salesDate;
    }

    public double getSalesDiscount() {
        return salesDiscount;
    }

    public void setSalesDiscount(double salesDiscount) {
        this.salesDiscount = salesDiscount;
    }

    public double getSalesVat() {
        return salesVat;
    }

    public void setSalesVat(double salesVat) {
        this.salesVat = salesVat;
    }

    public double getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(double salesAmount) {
        this.salesAmount = salesAmount;
    }

    public double getSalesPayment() {
        return salesPayment;
    }

    public void setSalesPayment(double salesPayment) {
        this.salesPayment = salesPayment;
    }

    public double getSalesBalance() {
        return salesBalance;
    }

    public void setSalesBalance(double salesBalance) {
        this.salesBalance = salesBalance;
    }

    public String getSalesDescription() {
        return salesDescription;
    }

    public void setSalesDescription(String salesDescription) {
        this.salesDescription = salesDescription;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
