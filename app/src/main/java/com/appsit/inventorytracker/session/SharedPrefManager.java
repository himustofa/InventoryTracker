package com.appsit.inventorytracker.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.appsit.inventorytracker.models.User;
import com.google.gson.Gson;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "shared_pref";
    private static final String IS_LOGGED = "login_key";
    private static final String TAG_TOKEN = "token_key";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mCtx = context;
            mInstance = new SharedPrefManager();
        }
        return mInstance;
    }

    //===============================================| Token
    public void saveDeviceToken(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_TOKEN, token);
        editor.apply();
        editor.commit();
    }

    public String getDeviceToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_TOKEN, null);
    }

    //===============================================| Save
    public void saveUser(User model){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserModel", new Gson().toJson(model));
        editor.apply();
        editor.commit(); //for old version
    }

    public void saveLogInStatus(boolean isLogged){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGGED, isLogged);
        editor.apply();
        editor.commit(); //for old version
    }

    //===============================================| Fetch/Get
    public User getUser(){
        SharedPreferences pref = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Gson().fromJson(pref.getString("UserModel", null), User.class);
    }

    public boolean getLogInStatus(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean(IS_LOGGED, false);
    }

    //===============================================| Delete
    public void deleteCurrentSession(){
        SharedPreferences pre = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.clear(); //Remove from login.xml file
        editor.apply();
        editor.commit(); //for old version
    }
}
