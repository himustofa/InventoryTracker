package com.appsit.inventorytracker.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.appsit.inventorytracker.models.Adjustment;
import com.appsit.inventorytracker.repositories.AppDaoAccess;
import com.appsit.inventorytracker.repositories.AppDatabase;

import java.util.List;

public class AdjustmentViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private AppDaoAccess mDaoAccess;

    public AdjustmentViewModel(Application application) {
        super(application);
        mDaoAccess = AppDatabase.getDatabase(application).getDaoAccess();
    }

    public LiveData<Adjustment> getById(String mId) {
        return mDaoAccess.getAdjustmentById(mId);
    }

    public LiveData<List<Adjustment>> getAllData() {
        return mDaoAccess.getAllAdjustment();
    }

    @SuppressLint("StaticFieldLeak")
    public long save(Adjustment model) {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                return mDaoAccess.insertAdjustment(model);
            }
        }.execute();
        return 1;
    }

    @SuppressLint("StaticFieldLeak")
    public int update(Adjustment model) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                int result = mDaoAccess.updateAdjustment(model);
                Log.d(TAG, "" + result);
                return result;
            }
        }.execute();
        return 1;
    }

    @SuppressLint("StaticFieldLeak")
    public int delete(Adjustment model) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                int result = mDaoAccess.deleteAdjustment(model);
                Log.d(TAG, "" + result);
                return result;
            }
        }.execute();
        return 1;
    }
}
