package com.appsit.inventorytracker.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.Purchase;
import com.appsit.inventorytracker.models.Stock;
import com.appsit.inventorytracker.models.StockSale;
import com.appsit.inventorytracker.viewmodels.AdjustmentViewModel;
import com.appsit.inventorytracker.viewmodels.PurchaseViewModel;
import com.appsit.inventorytracker.viewmodels.StockViewModel;
import com.appsit.inventorytracker.views.adapters.StockAdapter;

import java.util.ArrayList;
import java.util.List;

public class StockTabOne extends Fragment {

    private String TAG = this.getClass().getCanonicalName();
    private ArrayList<Stock> mArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private StockAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_one, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.stock_product_recycler_view);
        /*if (getArguments() != null) {
            ArrayList<Stock> mArrayList = new Gson().fromJson(getArguments().getString("TAB_ONE", null), new TypeToken<ArrayList<Stock>>() {}.getType());
            Log.d(TAG, new Gson().toJson(mArrayList));
            initRecyclerView(mRecyclerView, mArrayList);
        }*/

        //====================================================| ViewModel
        AdjustmentViewModel mAdjustmentViewModel = ViewModelProviders.of(getActivity()).get(AdjustmentViewModel.class);
        StockViewModel mViewModel = ViewModelProviders.of(getActivity()).get(StockViewModel.class);
        PurchaseViewModel mPurchaseViewModel = ViewModelProviders.of(getActivity()).get(PurchaseViewModel.class);
        mPurchaseViewModel.getAll().observe(getActivity(), new Observer<List<Purchase>>() {
            @Override
            public void onChanged(List<Purchase> list) {
                if (list.size() > 0) {
                    for(Purchase p : list) {
                        mViewModel.getStockByProductId(p).observe(getActivity(), new Observer<StockSale>() {
                            @Override
                            public void onChanged(StockSale sale) {
                                mArrayList.add(new Stock( p.getProductId(), p.getProductName(), (p.getPurchaseProductQuantity()-sale.getQuantity()), (p.getPurchaseAmount()-sale.getAmount()) ));
                                initRecyclerView(mRecyclerView, mArrayList);
                            }
                        });
                    }
                }
            }
        });

        /*StockViewModel mViewModel = ViewModelProviders.of(getActivity()).get(StockViewModel.class);
        mViewModel.getStockByProductId().observe(getActivity(), new Observer<List<Stock>>() {
            @Override
            public void onChanged(List<Stock> stockSales) {
                mArrayList.addAll(stockSales);
                initRecyclerView(mRecyclerView, mArrayList);
            }
        });*/

        ((SearchView) view.findViewById(R.id.stock_one_search_view)).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (mAdapter != null){
                    mAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });

        return view;
    }

    private void initRecyclerView(RecyclerView mRecyclerView, ArrayList<Stock> mArrayList) {
        mAdapter = new StockAdapter(getActivity(), mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter.notifyDataSetChanged();
    }
}
