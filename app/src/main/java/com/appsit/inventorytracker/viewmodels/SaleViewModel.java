package com.appsit.inventorytracker.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.appsit.inventorytracker.models.Sale;
import com.appsit.inventorytracker.repositories.AppDaoAccess;
import com.appsit.inventorytracker.repositories.AppDatabase;

import java.util.List;

public class SaleViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private AppDaoAccess mDaoAccess;

    public SaleViewModel(Application application) {
        super(application);
        mDaoAccess = AppDatabase.getDatabase(application).getDaoAccess();
    }

    public LiveData<Sale> getById(String mId) {
        return mDaoAccess.getSaleById(mId);
    }

    public LiveData<List<Sale>> getAll() {
        return mDaoAccess.getAllSale();
    }

    public LiveData<Integer> getSaleTotalQtyByProductId(String productId) {
        return mDaoAccess.getSaleTotalQtyByProductId(productId);
    }

    @SuppressLint("StaticFieldLeak")
    public long save(Sale model) {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                return mDaoAccess.insertSale(model);
            }
        }.execute();
        return 1;
    }

    @SuppressLint("StaticFieldLeak")
    public int update(Sale model) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                int result = mDaoAccess.updateSale(model);
                Log.d(TAG, "" + result);
                return result;
            }
        }.execute();
        return 1;
    }

    @SuppressLint("StaticFieldLeak")
    public int delete(Sale model) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                int result = mDaoAccess.deleteSale(model);
                Log.d(TAG, "" + result);
                return result;
            }
        }.execute();
        return 1;
    }
}
