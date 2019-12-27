package com.appsit.inventorytracker.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.util.UUID;

@Entity(tableName = "customers")
public class Customer {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String customerId;
    @NonNull
    private String customerName;
    @NonNull
    private String customerPhoneNumber;
    private String customerEmail;
    private String customerContactPerson;
    private double customerDiscount;
    private String customerAddress;
    private String customerDescription;
    private String createdAt = String.valueOf(new Timestamp(System.currentTimeMillis()));

    public Customer(@NonNull String customerId, @NonNull String customerName, @NonNull String customerPhoneNumber, String customerEmail, String customerContactPerson, double customerDiscount, String customerAddress, String customerDescription) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerEmail = customerEmail;
        this.customerContactPerson = customerContactPerson;
        this.customerDiscount = customerDiscount;
        this.customerAddress = customerAddress;
        this.customerDescription = customerDescription;
    }

    public static Customer[] dummyData() {
        return new Customer[] {
                new Customer("none", "None", "none", "none", "none", 0.0, "none", "none"),
                new Customer(UUID.randomUUID().toString(), "Mr. Hasan", "01914161808", "hasan@gmail.com", "", 2.5, "Dhaka, Bangladesh", "Nothing..."),
                new Customer(UUID.randomUUID().toString(), "Rafiqul Islam", "01814161808", "rafiq@gmail.com", "", 0.0, "Dhaka, Bangladesh", "Nothing...")
        };
    }

    @NonNull
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(@NonNull String customerId) {
        this.customerId = customerId;
    }

    @NonNull
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(@NonNull String customerName) {
        this.customerName = customerName;
    }

    @NonNull
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(@NonNull String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerContactPerson() {
        return customerContactPerson;
    }

    public void setCustomerContactPerson(String customerContactPerson) {
        this.customerContactPerson = customerContactPerson;
    }

    public double getCustomerDiscount() {
        return customerDiscount;
    }

    public void setCustomerDiscount(double customerDiscount) {
        this.customerDiscount = customerDiscount;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerDescription() {
        return customerDescription;
    }

    public void setCustomerDescription(String customerDescription) {
        this.customerDescription = customerDescription;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
