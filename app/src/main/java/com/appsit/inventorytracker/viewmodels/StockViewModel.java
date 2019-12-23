package com.appsit.inventorytracker.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.appsit.inventorytracker.models.Purchase;
import com.appsit.inventorytracker.models.Sale;
import com.appsit.inventorytracker.models.Stock;
import com.appsit.inventorytracker.repositories.AppDaoAccess;
import com.appsit.inventorytracker.repositories.AppDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class StockViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private AppDaoAccess mDaoAccess;
    private Application application;

    private ArrayList<Stock> purchaseList = new ArrayList<>();

    public StockViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        mDaoAccess = AppDatabase.getDatabase(application).getDaoAccess();
    }

    public  MutableLiveData<ArrayList<Stock>> getAll() {
        MutableLiveData<ArrayList<Stock>> data = new MutableLiveData<>();

        mDaoAccess.getAllPurchase().observe((LifecycleOwner) application, new Observer<List<Purchase>>() {
            @Override
            public void onChanged(List<Purchase> purchases) {
                if (purchases.size() > 0) {
                    for(Purchase p : purchases) {
                        mDaoAccess.getSaleByProductId(p.getProductId()).observe((LifecycleOwner) application, new Observer<Sale>() {
                            @Override
                            public void onChanged(Sale sale) {
                                Log.d(TAG, new Gson().toJson(sale));
                                //purchaseList.add(new Stock( p.getProductId(), p.getProductName(), (p.getPurchaseProductQuantity()-sale.getProductQuantity()), (p.getPurchaseAmount()-sale.getSalesAmount()) ));
                            }
                        });
                    }
                    data.setValue(purchaseList);
                }
            }
        });

        return data;
    }
}
