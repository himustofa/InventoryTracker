package com.appsit.inventorytracker.repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.appsit.inventorytracker.models.Customer;
import com.appsit.inventorytracker.models.Product;
import com.appsit.inventorytracker.models.Purchase;
import com.appsit.inventorytracker.models.Sale;
import com.appsit.inventorytracker.models.Supplier;
import com.appsit.inventorytracker.models.User;

import java.util.List;

@Dao
public interface AppDaoAccess {

    //===============================================| User
    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUser();

    @Query("SELECT * FROM users WHERE id=:userId")
    LiveData<User> getUser(String userId);

    @Query("SELECT * FROM users WHERE username like :username and password like :password")
    LiveData<User> getUserByUserAndPass(String username, String password);

    @Insert
    long insertUser(User user);

    @Insert
    long[] insertAllUser(User... users);

    @Update
    int updateUser(User user);

    @Delete
    int deleteUser(User user);

    //===============================================| Supplier
    @Query("SELECT * FROM suppliers")
    LiveData<List<Supplier>> getAllSupplier();

    @Query("SELECT * FROM suppliers WHERE id=:id")
    LiveData<Supplier> getSupplierById(String id);

    @Insert
    long insertSupplier(Supplier supplier);

    @Update
    int updateSupplier(Supplier supplier);

    @Delete
    int deleteSupplier(Supplier supplier);

    //===============================================| Purchase
    @Query("SELECT * FROM purchases")
    LiveData<List<Purchase>> getAllPurchase();

    @Query("SELECT * FROM purchases WHERE id=:id")
    LiveData<Purchase> getPurchaseById(String id);

    @Insert
    long insertPurchase(Purchase model);

    @Update
    int updatePurchase(Purchase model);

    @Delete
    int deletePurchase(Purchase model);

    //===============================================| Product
    @Query("SELECT * FROM products")
    LiveData<List<Product>> getAllProduct();

    @Query("SELECT * FROM products WHERE id=:id")
    LiveData<Product> getProductById(String id);

    @Insert
    long insertProduct(Product model);

    @Update
    int updateProduct(Product model);

    @Delete
    int deleteProduct(Product model);

    //===============================================| Customer
    @Query("SELECT * FROM customers")
    LiveData<List<Customer>> getAllCustomer();

    @Query("SELECT * FROM customers WHERE id=:id")
    LiveData<Customer> getCustomerById(String id);

    @Insert
    long insertCustomer(Customer model);

    @Update
    int updateCustomer(Customer model);

    @Delete
    int deleteCustomer(Customer model);

    //===============================================| Sale
    @Query("SELECT * FROM sales")
    LiveData<List<Sale>> getAllSale();

    @Query("SELECT * FROM sales WHERE id=:id")
    LiveData<Sale> getSaleById(String id);

    @Insert
    long insertSale(Sale model);

    @Update
    int updateSale(Sale model);

    @Delete
    int deleteSale(Sale model);
}
