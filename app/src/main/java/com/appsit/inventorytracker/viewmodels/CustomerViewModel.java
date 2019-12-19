package com.appsit.inventorytracker.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.appsit.inventorytracker.models.Customer;
import com.appsit.inventorytracker.repositories.AppDaoAccess;
import com.appsit.inventorytracker.repositories.AppDatabase;

import java.util.List;

public class CustomerViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private AppDaoAccess mDaoAccess;

    public CustomerViewModel(Application application) {
        super(application);
        mDaoAccess = AppDatabase.getDatabase(application).getDaoAccess();
    }

    public LiveData<Customer> getById(String mId) {
        return mDaoAccess.getCustomerById(mId);
    }

    public LiveData<List<Customer>> getAllData() {
        return mDaoAccess.getAllCustomer();
    }

    @SuppressLint("StaticFieldLeak")
    public long save(Customer model) {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                return mDaoAccess.insertCustomer(model);
            }
        }.execute();
        return 1;
    }

    @SuppressLint("StaticFieldLeak")
    public int update(Customer model) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                int result = mDaoAccess.updateCustomer(model);
                Log.d(TAG, "" + result);
                return result;
            }
        }.execute();
        return 1;
    }

    @SuppressLint("StaticFieldLeak")
    public int delete(Customer model) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                int result = mDaoAccess.deleteCustomer(model);
                Log.d(TAG, "" + result);
                return result;
            }
        }.execute();
        return 1;
    }
}