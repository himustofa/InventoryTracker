package com.appsit.inventorytracker.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CustomerViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private IDaoAccess mDaoAccess;

    public CustomerViewModel(Application application) {
        super(application);

        AppDatabase mAppDatabase = AppDatabase.getDatabase(application);
        mDaoAccess = mAppDatabase.daoAccess();
    }

    public LiveData<Customer> getById(String mId) {
        return mDaoAccess.get(mId);
    }

    public LiveData<List<Customer>> getAllData() {
        return mDaoAccess.getAll();
    }

    @SuppressLint("StaticFieldLeak")
    public long saveData(Customer customer) {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                return mDaoAccess.insert(customer);
            }
        }.execute();
        return 1;
    }

    @SuppressLint("StaticFieldLeak")
    public int updateData(Customer customer) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                int result = mDaoAccess.update(customer);
                Log.d(TAG, ""+result);
                return result;
            }
        }.execute();
        return 1;
    }

    @SuppressLint("StaticFieldLeak")
    public int deleteData(Customer customer) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                int result = mDaoAccess.delete(customer);
                Log.d(TAG, ""+result);
                return result;
            }
        }.execute();
        return 1;
    }
