package com.appsit.inventorytracker.views.adapters;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.appsit.inventorytracker.models.Stock;
import com.appsit.inventorytracker.views.fragments.StockTabOne;
import com.appsit.inventorytracker.views.fragments.StockTabTwo;
import com.google.gson.Gson;

import java.util.ArrayList;

public class StockPagerAdapter extends FragmentStatePagerAdapter {

    private String TAG = this.getClass().getCanonicalName();

    private ArrayList<Stock> mArrayList;
    private int mNumOfTabs;
    private Bundle bundle = new Bundle();

    public StockPagerAdapter(@NonNull FragmentManager fm, int behavior,ArrayList<Stock> arrayList) {
        super(fm, behavior);
        this.mNumOfTabs = behavior;
        this.mArrayList = arrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                StockTabOne one = new StockTabOne();
                bundle.putString("TAB_ONE", new Gson().toJson(mArrayList));
                one.setArguments(bundle);
                Log.d(TAG, new Gson().toJson(mArrayList));
                return one;
            case 1:
                return new StockTabTwo();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
