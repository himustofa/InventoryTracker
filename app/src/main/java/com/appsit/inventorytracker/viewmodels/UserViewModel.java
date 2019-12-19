package com.appsit.inventorytracker.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.appsit.inventorytracker.models.User;
import com.appsit.inventorytracker.repositories.AppDaoAccess;
import com.appsit.inventorytracker.repositories.AppDatabase;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private AppDaoAccess mDaoAccess;

    public UserViewModel(@NonNull Application application) {
        super(application);
        mDaoAccess = AppDatabase.getDatabase(application).getDaoAccess();
    }

    @SuppressLint("StaticFieldLeak")
    public long saveData(User user) {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                return mDaoAccess.insertUser(user);
            }
        }.execute();
        return 1;
    }

    public LiveData<User> getUserByUserAndPass(String userName, String userPass) {
        return mDaoAccess.getUserByUserAndPass(userName, userPass);
    }

    public LiveData<List<User>> getAllUser() {
        return mDaoAccess.getAllUser();
    }

    public LiveData<User> getUser(String id) {
        return mDaoAccess.getUser(id);
    }
}
