package com.appsit.inventorytracker.repositories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.appsit.inventorytracker.models.Adjustment;
import com.appsit.inventorytracker.models.Customer;
import com.appsit.inventorytracker.models.Product;
import com.appsit.inventorytracker.models.Purchase;
import com.appsit.inventorytracker.models.Sale;
import com.appsit.inventorytracker.models.Supplier;
import com.appsit.inventorytracker.models.User;
import com.appsit.inventorytracker.utils.ConstantKey;

import java.util.concurrent.Executors;

@Database(entities = {User.class, Supplier.class, Purchase.class, Product.class, Customer.class, Sale.class, Adjustment.class}, version = ConstantKey.DATABASE_VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AppDaoAccess getDaoAccess();

    private static volatile AppDatabase mInstance;

    public static AppDatabase getDatabase(final Context context) {
        if (mInstance == null) {
            synchronized (AppDatabase.class) {
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, ConstantKey.DATABASE_NAME).addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    getDatabase(context).getDaoAccess().insertAllUser(User.populateData()); //Initial data insert
                                    getDatabase(context).getDaoAccess().insertAllSupplier(Supplier.dummyData());
                                    getDatabase(context).getDaoAccess().insertAllProduct(Product.dummyData());
                                    getDatabase(context).getDaoAccess().insertAllCustomer(Customer.dummyData());
                                }
                            });
                        }
                    }).build();
                }
            }
        }
        return mInstance;
    }

}
