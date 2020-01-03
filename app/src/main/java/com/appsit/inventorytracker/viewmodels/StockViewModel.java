package com.appsit.inventorytracker.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.appsit.inventorytracker.models.Purchase;
import com.appsit.inventorytracker.models.Stock;
import com.appsit.inventorytracker.models.StockSale;
import com.appsit.inventorytracker.repositories.AppDaoAccess;
import com.appsit.inventorytracker.repositories.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class StockViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private AppDaoAccess mDaoAccess;
    private Application application;

    public StockViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        mDaoAccess = AppDatabase.getDatabase(application).getDaoAccess();
    }

    /*public  MutableLiveData<ArrayList<Stock>> getAll() {
        MutableLiveData<ArrayList<Stock>> data = new MutableLiveData<>();

        mDaoAccess.getAllPurchase().observe((LifecycleOwner) application, new Observer<List<Purchase>>() {
            @Override
            public void onChanged(List<Purchase> purchases) {
                if (purchases.size() > 0) {
                    for(Purchase p : purchases) {
                        mDaoAccess.getSaleByProductId(p.getProductId()).observe((LifecycleOwner) application, new Observer<StockSale>() {
                            @Override
                            public void onChanged(StockSale sale) {
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
    }*/

    public LiveData<StockSale> getStockByProductId(String productId) {
        return mDaoAccess.getSaleByProductIdForStock(productId);
    }

    public LiveData<StockSale> getPurchaseByProductId(String productId) {
        return mDaoAccess.getPurchaseByProductIdForStock(productId);
    }

    public LiveData<StockSale> getAdjustmentByProductId(String productId) {
        return mDaoAccess.getAdjustmentByProductIdForStock(productId);
    }

    public LiveData<StockSale> getSaleBySupplierId(String supplierId) {
        return mDaoAccess.getSaleBySupplierId(supplierId);
    }
}
