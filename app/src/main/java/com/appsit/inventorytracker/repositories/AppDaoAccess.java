package com.appsit.inventorytracker.repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.appsit.inventorytracker.models.Adjustment;
import com.appsit.inventorytracker.models.Customer;
import com.appsit.inventorytracker.models.Product;
import com.appsit.inventorytracker.models.Purchase;
import com.appsit.inventorytracker.models.Sale;
import com.appsit.inventorytracker.models.StockSale;
import com.appsit.inventorytracker.models.Supplier;
import com.appsit.inventorytracker.models.User;
import com.appsit.inventorytracker.viewmodels.HomeViewModel;

import java.util.List;

@Dao
public interface AppDaoAccess {

    //===============================================| User
    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUser();

    @Query("SELECT * FROM users WHERE id=:userId")
    LiveData<User> getUser(String userId);

    @Query("SELECT * FROM users WHERE username=:userName") //"SELECT * FROM users WHERE username IN (:userName)"
    LiveData<User> getUserByUserName(String userName);

    @Query("SELECT * FROM users WHERE username LIKE :username AND password LIKE :password")
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

    @Insert
    long[] insertAllSupplier(Supplier... suppliers);

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

    @Insert
    long[] insertAllProduct(Product... products);

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

    @Insert
    long[] insertAllCustomer(Customer... customers);

    @Update
    int updateCustomer(Customer model);

    @Delete
    int deleteCustomer(Customer model);

    //===============================================| Sale
    @Query("SELECT * FROM sales")
    LiveData<List<Sale>> getAllSale();

    @Query("SELECT * FROM sales WHERE id=:id")
    LiveData<Sale> getSaleById(String id);

    //https://stackoverflow.com/questions/50801617/return-sum-and-average-using-room
    @Query("SELECT SUM(productQuantity) as quantity, SUM(salesAmount) as amount FROM sales WHERE productId=:productId")
    LiveData<StockSale> getSaleByProductId(String productId);

    @Insert
    long insertSale(Sale model);

    @Update
    int updateSale(Sale model);

    @Delete
    int deleteSale(Sale model);

    //===============================================| Adjustment
    @Query("SELECT * FROM adjustments")
    LiveData<List<Adjustment>> getAllAdjustment();

    @Query("SELECT * FROM adjustments WHERE id=:id")
    LiveData<Adjustment> getAdjustmentById(String id);

    @Insert
    long insertAdjustment(Adjustment model);

    @Update
    int updateAdjustment(Adjustment model);

    @Delete
    int deleteAdjustment(Adjustment model);

    //===============================================| Home
    @Query("SELECT SUM(productQuantity) as quantity, SUM(salesAmount) as amount FROM sales")
    LiveData<StockSale> getSaleTotal();

    @Query("SELECT SUM(purchaseProductQuantity) as quantity, SUM(purchaseAmount) as amount FROM purchases")
    LiveData<StockSale> getPurchaseTotal();

    @Query("SELECT SUM(productQuantity) as quantity, SUM(productAmount) as amount FROM adjustments")
    LiveData<StockSale> getAdjustmentTotal();

    @Query("SELECT SUM(productQuantity) as quantity, SUM(salesAmount) as amount FROM sales WHERE salesDate=:date")
    LiveData<StockSale> getSaleByDate(String date);




}
