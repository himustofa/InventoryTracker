package com.appsit.inventorytracker;

import android.app.Application;
import android.content.Context;

import com.appsit.inventorytracker.utils.language.LocaleHelper;

public class MainApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
