package com.appsit.inventorytracker.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.Stock;
import com.appsit.inventorytracker.views.activities.StockActivity;
import com.appsit.inventorytracker.views.adapters.StockAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class StockTabOne extends Fragment {

    private String TAG = this.getClass().getCanonicalName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_one, container, false);

        final RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.stock_product_recycler_view);
        if (getArguments() != null) {
            ArrayList<Stock> mArrayList = new Gson().fromJson(getArguments().getString("TAB_ONE", null), new TypeToken<ArrayList<Stock>>() {}.getType());
            Log.d(TAG, new Gson().toJson(mArrayList));
            initRecyclerView(mRecyclerView, mArrayList);
        }
        return view;
    }

    private void initRecyclerView(RecyclerView mRecyclerView, ArrayList<Stock> mArrayList) {
        StockAdapter mAdapter = new StockAdapter(getActivity(), mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter.notifyDataSetChanged();
    }
}
