package com.appsit.inventorytracker.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.Stock;
import com.appsit.inventorytracker.viewmodels.StockViewModel;
import com.appsit.inventorytracker.views.adapters.StockAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class StockActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private ArrayList<Stock> mArrayList = new ArrayList<>();
    private StockAdapter mAdapter;
    private StockViewModel mViewModel;
    private boolean isValue = true;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        mRecyclerView = (RecyclerView) findViewById(R.id.stock_recycler_view);
        mViewModel = ViewModelProviders.of(this).get(StockViewModel.class);
        mViewModel.getAll().observe(this, new Observer<List<Stock>>() {
            @Override
            public void onChanged(List<Stock> stocks) {
                if (isValue) {
                    mArrayList.addAll(stocks);
                    isValue = false;
                    Log.d(TAG, new Gson().toJson(stocks));
                }
            }
        });

        ((FloatingActionButton) findViewById(R.id.stock_add_fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addItem();
            }
        });

        //initRecyclerView();
    }
}
