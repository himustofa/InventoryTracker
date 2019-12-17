package com.appsit.inventorytracker.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.appsit.inventorytracker.models.Supplier;
import com.appsit.inventorytracker.repositories.AppDaoAccess;
import com.appsit.inventorytracker.repositories.AppDatabase;

import java.util.List;

public class SupplierViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private AppDaoAccess mDaoAccess;

    public SupplierViewModel(@NonNull Application application) {
        super(application);
        mDaoAccess = AppDatabase.getDatabase(application).getDaoAccess();
    }

    @SuppressLint("StaticFieldLeak")
    public long save(Supplier model) {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                return mDaoAccess.insertSupplier(model);
            }
        }.execute();
        return 1;
    }

    @SuppressLint("StaticFieldLeak")
    public int update(Supplier model) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                return mDaoAccess.updateSupplier(model);
            }
        }.execute();
        return 1;
    }

    @SuppressLint("StaticFieldLeak")
    public long delete(Supplier supplier) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                return mDaoAccess.deleteSupplier(supplier);
            }
        }.execute();
        return 1;
    }

    public LiveData<Supplier> getById(String id) {
        return mDaoAccess.getSupplierById(id);
    }

    public LiveData<List<Supplier>> getAll() {
        return mDaoAccess.getAllSupplier();
    }
}
