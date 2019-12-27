package com.appsit.inventorytracker.views.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.appsit.inventorytracker.views.fragments.StockTabOne;
import com.appsit.inventorytracker.views.fragments.StockTabTwo;

public class StockPagerAdapter extends FragmentStatePagerAdapter {

    private String TAG = this.getClass().getCanonicalName();
    private int mNumOfTabs;
    private Bundle bundle = new Bundle();

    public StockPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.mNumOfTabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                StockTabOne one = new StockTabOne();
                //bundle.putString("TAB_ONE", new Gson().toJson(mArrayList));
                //one.setArguments(bundle);
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
