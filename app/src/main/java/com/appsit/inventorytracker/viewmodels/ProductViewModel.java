package com.appsit.inventorytracker.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.appsit.inventorytracker.models.Product;
import com.appsit.inventorytracker.repositories.AppDaoAccess;
import com.appsit.inventorytracker.repositories.AppDatabase;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private AppDaoAccess mDaoAccess;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        mDaoAccess = AppDatabase.getDatabase(application).getDaoAccess();
    }

    @SuppressLint("StaticFieldLeak")
    public long save(Product model) {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                return mDaoAccess.insertProduct(model);
            }
        }.execute();
        return 1;
    }

    @SuppressLint("StaticFieldLeak")
    public int update(Product model) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                return mDaoAccess.updateProduct(model);
            }
        }.execute();
        return 1;
    }

    @SuppressLint("StaticFieldLeak")
    public long delete(Product model) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                return mDaoAccess.deleteProduct(model);
            }
        }.execute();
        return 1;
    }

    public LiveData<Product> getById(String id) {
        return mDaoAccess.getProductById(id);
    }

    public LiveData<List<Product>> getAll() {
        return mDaoAccess.getAllProduct();
    }
}
