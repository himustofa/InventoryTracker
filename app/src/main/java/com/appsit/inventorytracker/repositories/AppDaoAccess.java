package com.appsit.inventorytracker.repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.appsit.inventorytracker.models.User;

import java.util.List;

@Dao
public interface AppDaoAccess {

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUser();

    @Query("SELECT * FROM users WHERE id=:userId")
    LiveData<User> getUser(String userId);

    @Insert
    long insertUser(User user);

    @Insert
    long[] insertAllUser(User... users);

    @Update
    int updateUser(User user);

    @Delete
    int deleteUser(User user);

}
