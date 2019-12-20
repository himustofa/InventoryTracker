package com.appsit.inventorytracker.repositories;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.appsit.inventorytracker.models.Customer;
import com.appsit.inventorytracker.models.Product;
import com.appsit.inventorytracker.models.Purchase;
import com.appsit.inventorytracker.models.Sale;
import com.appsit.inventorytracker.models.Supplier;
import com.appsit.inventorytracker.models.User;
import com.appsit.inventorytracker.utils.ConstantKey;

@Database(entities = {User.class, Supplier.class, Purchase.class, Product.class, Customer.class, Sale.class}, version = ConstantKey.DATABASE_VERSION)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AppDaoAccess getDaoAccess();

    private static volatile AppDatabase mInstance;

    public static AppDatabase getDatabase(final Context context) {
        if (mInstance == null) {
            synchronized (AppDatabase.class) {
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, ConstantKey.DATABASE_NAME).build();
                }
            }
        }
        return mInstance;
    }

}
