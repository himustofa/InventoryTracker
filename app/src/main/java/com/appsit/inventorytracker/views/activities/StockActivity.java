package com.appsit.inventorytracker.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.Product;
import com.appsit.inventorytracker.models.Purchase;
import com.appsit.inventorytracker.models.Stock;
import com.appsit.inventorytracker.models.StockSale;
import com.appsit.inventorytracker.viewmodels.AdjustmentViewModel;
import com.appsit.inventorytracker.viewmodels.ProductViewModel;
import com.appsit.inventorytracker.viewmodels.PurchaseViewModel;
import com.appsit.inventorytracker.viewmodels.StockViewModel;
import com.appsit.inventorytracker.views.adapters.StockAdapterOne;
import com.appsit.inventorytracker.views.adapters.StockPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StockActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private RecyclerView mRecyclerView;
    private ArrayList<Stock> mArrayList = new ArrayList<>();
    private StockAdapterOne mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        mRecyclerView = (RecyclerView) findViewById(R.id.stock_recycler_view);

        //====================================================| ViewModel
        /*AdjustmentViewModel mAdjustmentViewModel = ViewModelProviders.of(this).get(AdjustmentViewModel.class);
        StockViewModel mViewModel = ViewModelProviders.of(this).get(StockViewModel.class);
        PurchaseViewModel mPurchaseViewModel = ViewModelProviders.of(this).get(PurchaseViewModel.class);
        mPurchaseViewModel.getAll().observe(this, new Observer<List<Purchase>>() {
            @Override
            public void onChanged(List<Purchase> list) {
                if (list.size() > 0) {
                    for(Purchase p : list) {
                        mViewModel.getStockByProductId(p).observe(StockActivity.this, new Observer<StockSale>() {
                            @Override
                            public void onChanged(StockSale sale) {
                                mArrayList.add(new Stock( p.getProductId(), p.getProductName(), (p.getPurchaseProductQuantity()-sale.getQuantity()), (p.getPurchaseAmount()-sale.getAmount()) ));
                                sorting(mArrayList);
                                initRecyclerView(mRecyclerView, mArrayList);
                            }
                        });
                    }
                }
            }
        });*/

        StockViewModel mViewModel = ViewModelProviders.of(this).get(StockViewModel.class);
        ProductViewModel mProductViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        mProductViewModel.getAll().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                for (Product p : products) {
                    mViewModel.getPurchaseByProductId(p.getProductId()).observe(StockActivity.this, new Observer<StockSale>() {
                        @Override
                        public void onChanged(StockSale stock1) {
                            mViewModel.getStockByProductId(p.getProductId()).observe(StockActivity.this, new Observer<StockSale>() {
                                @Override
                                public void onChanged(StockSale stock2) {
                                    mViewModel.getAdjustmentByProductId(p.getProductId()).observe(StockActivity.this, new Observer<StockSale>() {
                                        @Override
                                        public void onChanged(StockSale stock3) {
                                            if (stock3 != null) {
                                                mArrayList.add(new Stock(p.getProductId(), p.getProductName(), ((stock1.getQuantity() - stock2.getQuantity()) - stock3.getQuantity()), ((stock1.getAmount() - stock2.getAmount()) - stock3.getAmount())));
                                                sorting(mArrayList);
                                                initRecyclerView(mRecyclerView, mArrayList);
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    });
                    /*mViewModel.getPurchaseByProductId(p.getProductId()).observe(StockActivity.this, new Observer<StockSale>() {
                        @Override
                        public void onChanged(StockSale stock) {
                            purchase = stock;
                        }
                    });
                    mViewModel.getAdjustmentByProductId(p.getProductId()).observe(StockActivity.this, new Observer<StockSale>() {
                        @Override
                        public void onChanged(StockSale stock) {
                            adjustment = stock;
                        }
                    });*/


                }
            }
        });


        ((SearchView) findViewById(R.id.stock_search_view)).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    }

    private void sorting(ArrayList<Stock> list) {
        //https://stackoverflow.com/questions/9109890/android-java-how-to-sort-a-list-of-objects-by-a-certain-value-within-the-object
        Collections.sort(list, new Comparator<Stock>(){
            public int compare(Stock obj1, Stock obj2) {
                return obj1.getProductName().compareToIgnoreCase(obj2.getProductName());
            }
        });
    }

    private void initRecyclerView(RecyclerView mRecyclerView, ArrayList<Stock> mArrayList) {
        mAdapter = new StockAdapterOne(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter.notifyDataSetChanged();
    }

}
