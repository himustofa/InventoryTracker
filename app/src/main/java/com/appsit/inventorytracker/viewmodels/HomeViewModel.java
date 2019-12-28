package com.appsit.inventorytracker.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.appsit.inventorytracker.models.Sale;
import com.appsit.inventorytracker.models.Stock;
import com.appsit.inventorytracker.models.StockSale;
import com.appsit.inventorytracker.repositories.AppDaoAccess;
import com.appsit.inventorytracker.repositories.AppDatabase;

public class HomeViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private AppDaoAccess mDaoAccess;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mDaoAccess = AppDatabase.getDatabase(application).getDaoAccess();
    }

    public LiveData<StockSale> getPurchaseTotal() {
        return mDaoAccess.getPurchaseTotal();
    }

    public LiveData<StockSale> getSaleTotal() {
        return mDaoAccess.getSaleTotal();
    }

    public LiveData<StockSale> getAdjustmentTotal() {
        return mDaoAccess.getAdjustmentTotal();
    }

    public LiveData<StockSale> getSaleByDate(String date) {
        return mDaoAccess.getSaleByDate(date);
    }

    public LiveData<Stock> getTotalStock() {
        return mDaoAccess.getTotalStock();
    }
}
