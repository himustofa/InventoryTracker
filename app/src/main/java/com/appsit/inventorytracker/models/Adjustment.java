package com.appsit.inventorytracker.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "adjustments")
public class Adjustment {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String adjustmentId;
    @NonNull
    private String productName;
    private String productId;
    @NonNull
    private int productQuantity;
    @NonNull
    private double productAmount;
    private String description;
    private String createdAt = String.valueOf(new Timestamp(System.currentTimeMillis()));

    public Adjustment(@NonNull String adjustmentId, @NonNull String productName, String productId, int productQuantity, double productAmount, String description) {
        this.adjustmentId = adjustmentId;
        this.productName = productName;
        this.productId = productId;
        this.productQuantity = productQuantity;
        this.productAmount = productAmount;
        this.description = description;
    }

    @NonNull
    public String getAdjustmentId() {
        return adjustmentId;
    }

    public void setAdjustmentId(@NonNull String adjustmentId) {
        this.adjustmentId = adjustmentId;
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

    public double getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(double productAmount) {
        this.productAmount = productAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
