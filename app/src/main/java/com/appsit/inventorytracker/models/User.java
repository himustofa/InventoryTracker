package com.appsit.inventorytracker.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

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
    private String photoName;
    private String photoPath;
    private String createdAt = String.valueOf(new Timestamp(System.currentTimeMillis()));
    //private Timestamp createdAt;


}
