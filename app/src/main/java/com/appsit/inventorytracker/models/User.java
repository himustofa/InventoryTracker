package com.appsit.inventorytracker.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.appsit.inventorytracker.utils.Utility;

import java.sql.Timestamp;
import java.util.UUID;

@Entity(tableName = "users")
public class User {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String userId;
    @NonNull
    private String fullName;
    private String designation;
    private String email;
    private String phoneNumber;
    @NonNull
    private String username;
    @NonNull
    private String password;
    private String role = String.valueOf(Role.NORMAL_USER);
    private String photoName;
    private String photoPath;
    private String createdAt = String.valueOf(new Timestamp(System.currentTimeMillis()));
    //private Timestamp createdAt;

    public User() {
    }

    @Ignore
    public User(@NonNull String userId, @NonNull String fullName, String designation, String email, String phoneNumber, @NonNull String username, @NonNull String password, String role, String photoName, String photoPath) {
        this.userId = userId;
        this.fullName = fullName;
        this.designation = designation;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.role = role;
        this.photoName = photoName;
        this.photoPath = photoPath;
    }

    public static User[] populateData() {
        return new User[] {
                new User(UUID.randomUUID().toString(), "Admin User", "Admin", "admin@gamil.com", "01914141707", "admin", Utility.encode("admin"), String.valueOf(Role.ADMIN_USER), "", "")
        };
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    @NonNull
    public String getFullName() {
        return fullName;
    }

    public void setFullName(@NonNull String fullName) {
        this.fullName = fullName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
