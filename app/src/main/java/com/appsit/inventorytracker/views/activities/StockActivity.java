package com.appsit.inventorytracker.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.Purchase;
import com.appsit.inventorytracker.models.Stock;
import com.appsit.inventorytracker.models.StockSale;
import com.appsit.inventorytracker.viewmodels.AdjustmentViewModel;
import com.appsit.inventorytracker.viewmodels.PurchaseViewModel;
import com.appsit.inventorytracker.viewmodels.StockViewModel;
import com.appsit.inventorytracker.views.adapters.StockPagerAdapter;
import com.appsit.inventorytracker.views.adapters.StockAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class StockActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private ArrayList<Stock> mArrayList = new ArrayList<>();
    private PurchaseViewModel mPurchaseViewModel;
    private StockViewModel mViewModel;
    private AdjustmentViewModel mAdjustmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        //====================================================| ViewModel
        mViewModel = ViewModelProviders.of(this).get(StockViewModel.class);
        mAdjustmentViewModel = ViewModelProviders.of(this).get(AdjustmentViewModel.class);
        mPurchaseViewModel = ViewModelProviders.of(this).get(PurchaseViewModel.class);

        //====================================================| TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Product"));
        tabLayout.addTab(tabLayout.newTab().setText("Supplier"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        //final StockPagerAdapter adapter = new StockPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), mArrayList);
        //mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        //====================================================| ViewModel
        mPurchaseViewModel.getAll().observe(this, new Observer<List<Purchase>>() {
            @Override
            public void onChanged(List<Purchase> list) {
                if (list.size() > 0) {
                    for(Purchase p : list) {
                        mViewModel.getStockByProductId(p).observe(StockActivity.this, new Observer<StockSale>() {
                            @Override
                            public void onChanged(StockSale sale) {
                                mArrayList.add(new Stock( p.getProductId(), p.getProductName(), (p.getPurchaseProductQuantity()-sale.getQuantity()), (p.getPurchaseAmount()-sale.getAmount()) ));
                                final StockPagerAdapter adapter = new StockPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), mArrayList);
                                mViewPager.setAdapter(adapter);
                            }
                        });
                    }
                }
            }
        });

    }

}
