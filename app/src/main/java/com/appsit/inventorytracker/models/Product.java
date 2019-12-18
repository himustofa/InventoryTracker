package com.appsit.inventorytracker.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "products")
public class Product {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String productId;
    @NonNull
    private String productName;
    @NonNull
    private String productCode;
    @NonNull
    private int productQuantity;
    @NonNull
    private double productPrice;
    @NonNull
    private String productExpireDate;
    private String productDescription;
    private String createdAt = String.valueOf(new Timestamp(System.currentTimeMillis()));

    public Product(@NonNull String productId, @NonNull String productName, @NonNull String productCode, int productQuantity, double productPrice, @NonNull String productExpireDate, String productDescription) {
        this.productId = productId;
        this.productName = productName;
        this.productCode = productCode;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.productExpireDate = productExpireDate;
        this.productDescription = productDescription;
    }

    @NonNull
    public String getProductId() {
        return productId;
    }

    public void setProductId(@NonNull String productId) {
        this.productId = productId;
    }

    @NonNull
    public String getProductName() {
        return productName;
    }

    public void setProductName(@NonNull String productName) {
        this.productName = productName;
    }

    @NonNull
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(@NonNull String productCode) {
        this.productCode = productCode;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @NonNull
    public String getProductExpireDate() {
        return productExpireDate;
    }

    public void setProductExpireDate(@NonNull String productExpireDate) {
        this.productExpireDate = productExpireDate;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
