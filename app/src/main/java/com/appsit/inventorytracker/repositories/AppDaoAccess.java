package com.appsit.inventorytracker.repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.appsit.inventorytracker.models.Supplier;
import com.appsit.inventorytracker.models.User;

import java.util.List;

@Dao
public interface AppDaoAccess {

    //===============================================| User
    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUser();

    @Query("SELECT * FROM users WHERE id=:userId")
    LiveData<User> getUser(String userId);

    @Query("SELECT * FROM users WHERE username like :username and password like :password")
    LiveData<User> getUserByUserAndPass(String username, String password);

    @Insert
    long insertUser(User user);

    @Insert
    long[] insertAllUser(User... users);

    @Update
    int updateUser(User user);

    @Delete
    int deleteUser(User user);

    //===============================================| Supplier
    @Query("SELECT * FROM suppliers")
    LiveData<List<Supplier>> getAllSupplier();

    @Query("SELECT * FROM suppliers WHERE id=:id")
    LiveData<Supplier> getSupplierById(String id);

    @Insert
    long insertSupplier(Supplier supplier);

    @Delete
    int deleteSupplier(Supplier supplier);
}
