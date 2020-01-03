package com.appsit.inventorytracker.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.views.adapters.StockPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class Stock2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock2);

        //====================================================| TabLayout
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Product"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Supplier"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        final StockPagerAdapter adapter = new StockPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    }
}
