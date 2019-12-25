package com.appsit.inventorytracker.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.util.UUID;

@Entity(tableName = "suppliers")
public class Supplier {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String supplierId;
    @NonNull
    private String supplierName;
    private String supplierCompanyName;
    private String supplierContactPerson;
    @NonNull
    private String supplierPhoneNumber;
    private String supplierAddress;
    private String supplierBankName;
    private String supplierBankAccount;
    private String supplierEmail;
    private String supplierWebsite;
    private String createdAt = String.valueOf(new Timestamp(System.currentTimeMillis()));

    public Supplier(@NonNull String supplierId, @NonNull String supplierName, String supplierCompanyName, String supplierContactPerson, @NonNull String supplierPhoneNumber, String supplierAddress, String supplierBankName, String supplierBankAccount, String supplierEmail, String supplierWebsite) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierCompanyName = supplierCompanyName;
        this.supplierContactPerson = supplierContactPerson;
        this.supplierPhoneNumber = supplierPhoneNumber;
        this.supplierAddress = supplierAddress;
        this.supplierBankName = supplierBankName;
        this.supplierBankAccount = supplierBankAccount;
        this.supplierEmail = supplierEmail;
        this.supplierWebsite = supplierWebsite;
    }

    public static Supplier[] dummyData() {
        return new Supplier[] {
                new Supplier(UUID.randomUUID().toString(), "Abdul Haque", "Haque Enterprise Ltd.", "Mamun", "01714141707", "Dhaka, Bangladesh", "City Bank", "CB4886648", "abdulhaque@abh.com.bd", "www.haque.com.bd"),
                new Supplier(UUID.randomUUID().toString(), "Munir Uddin", "Rahman Enterprise Ltd.", "Mamun", "01714141666", "Dhaka, Bangladesh", "DBBL Bank", "DBBL554584886648", "rahman@rh.com.bd", "www.rahman.com.bd")
        };
    }

    @NonNull
    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(@NonNull String supplierId) {
        this.supplierId = supplierId;
    }

    @NonNull
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(@NonNull String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierCompanyName() {
        return supplierCompanyName;
    }

    public void setSupplierCompanyName(String supplierCompanyName) {
        this.supplierCompanyName = supplierCompanyName;
    }

    public String getSupplierContactPerson() {
        return supplierContactPerson;
    }

    public void setSupplierContactPerson(String supplierContactPerson) {
        this.supplierContactPerson = supplierContactPerson;
    }

    @NonNull
    public String getSupplierPhoneNumber() {
        return supplierPhoneNumber;
    }

    public void setSupplierPhoneNumber(@NonNull String supplierPhoneNumber) {
        this.supplierPhoneNumber = supplierPhoneNumber;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierBankName() {
        return supplierBankName;
    }

    public void setSupplierBankName(String supplierBankName) {
        this.supplierBankName = supplierBankName;
    }

    public String getSupplierBankAccount() {
        return supplierBankAccount;
    }

    public void setSupplierBankAccount(String supplierBankAccount) {
        this.supplierBankAccount = supplierBankAccount;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public String getSupplierWebsite() {
        return supplierWebsite;
    }

    public void setSupplierWebsite(String supplierWebsite) {
        this.supplierWebsite = supplierWebsite;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
