package com.appsit.inventorytracker.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.Purchase;
import com.appsit.inventorytracker.models.Stock;
import com.appsit.inventorytracker.models.StockSale;
import com.appsit.inventorytracker.viewmodels.AdjustmentViewModel;
import com.appsit.inventorytracker.viewmodels.PurchaseViewModel;
import com.appsit.inventorytracker.viewmodels.StockViewModel;
import com.appsit.inventorytracker.views.adapters.StockAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class StockActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private ArrayList<Stock> mArrayList = new ArrayList<>();
    private StockAdapter mAdapter;
    private PurchaseViewModel mPurchaseViewModel;
    private StockViewModel mViewModel;
    private AdjustmentViewModel mAdjustmentViewModel;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        mRecyclerView = (RecyclerView) findViewById(R.id.stock_recycler_view);

        mViewModel = ViewModelProviders.of(this).get(StockViewModel.class);
        mAdjustmentViewModel = ViewModelProviders.of(this).get(AdjustmentViewModel.class);
        mPurchaseViewModel = ViewModelProviders.of(this).get(PurchaseViewModel.class);

        mPurchaseViewModel.getAll().observe(this, new Observer<List<Purchase>>() {
            @Override
            public void onChanged(List<Purchase> list) {
                if (list.size() > 0) {
                    for(Purchase p : list) {
                        mViewModel.getStockByProductId(p).observe(StockActivity.this, new Observer<StockSale>() {
                            @Override
                            public void onChanged(StockSale sale) {
                                Log.d(TAG, new Gson().toJson(sale));
                                mArrayList.add(new Stock( p.getProductId(), p.getProductName(), (p.getPurchaseProductQuantity()-sale.getQuantity()), (p.getPurchaseAmount()-sale.getAmount()) ));
                                Log.d(TAG, new Gson().toJson(mArrayList));
                                initRecyclerView();
                            }
                        });
                    }
                }
            }
        });

    }

    private void initRecyclerView() {
        mAdapter = new StockAdapter(StockActivity.this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(StockActivity.this));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter.notifyDataSetChanged();
    }

}
