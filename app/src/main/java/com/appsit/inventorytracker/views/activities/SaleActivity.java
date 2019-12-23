package com.appsit.inventorytracker.views.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.Customer;
import com.appsit.inventorytracker.models.ObjectDialog;
import com.appsit.inventorytracker.models.Purchase;
import com.appsit.inventorytracker.models.Sale;
import com.appsit.inventorytracker.utils.SalePayTextWatcher;
import com.appsit.inventorytracker.utils.SaleTextWatcher;
import com.appsit.inventorytracker.utils.Utility;
import com.appsit.inventorytracker.viewmodels.CustomerViewModel;
import com.appsit.inventorytracker.viewmodels.ProductViewModel;
import com.appsit.inventorytracker.viewmodels.PurchaseViewModel;
import com.appsit.inventorytracker.viewmodels.SaleViewModel;
import com.appsit.inventorytracker.views.adapters.SaleAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SaleActivity extends AppCompatActivity implements SaleAdapter.RecyclerItemListener {

    private String TAG = this.getClass().getSimpleName();
    private ArrayList<Sale> mArrayList = new ArrayList<>();
    private SaleAdapter mAdapter;
    private SaleViewModel mViewModel;
    private boolean isValue = true;
    private List<Customer> mCustomerList;
    private List<Purchase> mPurchaseList;
    private double perProductPrice;

    private RecyclerView mRecyclerView;
    private EditText eQuantity, ePurchaseQuantity, eSaleDate, eSaleDiscount, eSaleVat, eSaleAmount, eSalePayment, eSaleBalance, eSaleDesc;
    private Spinner sProductName, sCustomerName;
    private TextView tProductId, tCustomerId;

    List<String> cList = new ArrayList<>();
    List<String> pList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        mRecyclerView = (RecyclerView) findViewById(R.id.sale_recycler_view);
        mViewModel = ViewModelProviders.of(this).get(SaleViewModel.class);
        mViewModel.getAll().observe(this, new Observer<List<Sale>>() {
            @Override
            public void onChanged(List<Sale> list) {
                if (isValue) {
                    mArrayList.addAll(list);
                    isValue = false;
                }
            }
        });

        PurchaseViewModel mPurchaseViewModel = ViewModelProviders.of(this).get(PurchaseViewModel.class);
        mPurchaseViewModel.getAll().observe(this, new Observer<List<Purchase>>() {
            @Override
            public void onChanged(List<Purchase> purchases) {
                if (purchases != null) {
                    mPurchaseList = purchases;
                    if (purchases.size() > 0) {
                        for(Purchase s : purchases) {
                            pList.add(s.getProductName());
                        }
                    }
                }
            }
        });

        CustomerViewModel mCustomerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        mCustomerViewModel.getAllData().observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                if (customers != null) {
                    mCustomerList = customers;
                    if (customers.size() > 0) {
                        for(Customer s : customers) {
                            cList.add(s.getCustomerName());
                        }
                    }
                }
            }
        });

        ((FloatingActionButton) findViewById(R.id.sale_add_fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new SaleAdapter(SaleActivity.this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SaleActivity.this));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter.notifyDataSetChanged();
    }

    //https://stackoverflow.com/questions/31367599/how-to-update-recyclerview-adapter-data
    @Override
    public void removeItem(int position, Sale model) {
        long result = mViewModel.delete(model);
        if (result > 0) {
            mArrayList.remove(position);
            mRecyclerView.removeViewAt(position);
            mAdapter.notifyItemRemoved(position);
            mAdapter.notifyItemRangeChanged(position, mArrayList.size());
        }
    }

    @Override
    public void addItem() {
        ObjectDialog obj = showObjectDialog("Add");

        eSaleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getDate(SaleActivity.this, eSaleDate);
            }
        });

        Utility.getSpinnerData(new Utility.AdapterPosition() {
            @Override
            public void onPosition(int position) {
                tProductId.setText(mPurchaseList.get(position).getProductId());
                ePurchaseQuantity.setText("" + mPurchaseList.get(position).getPurchaseProductQuantity());
                perProductPrice = mPurchaseList.get(position).getPurchaseProductPrice();
                //Calculation
                eQuantity.addTextChangedListener(new SaleTextWatcher(eSaleAmount, eQuantity, perProductPrice, eSaleDiscount, eSaleVat));
                eSaleDiscount.addTextChangedListener(new SaleTextWatcher(eSaleAmount, eQuantity, perProductPrice, eSaleDiscount, eSaleVat));
                eSaleVat.addTextChangedListener(new SaleTextWatcher(eSaleAmount, eQuantity, perProductPrice, eSaleDiscount, eSaleVat));
                eSalePayment.addTextChangedListener(new SalePayTextWatcher(eSaleAmount, eSalePayment, eSaleBalance));
            }
        }, this, sProductName, pList);

        Utility.getSpinnerData(new Utility.AdapterPosition() {
            @Override
            public void onPosition(int position) {
                tCustomerId.setText(mCustomerList.get(position).getCustomerId());
            }
        }, this, sCustomerName, cList);



        ((Button) obj.getView().findViewById(R.id.sale_save_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tProductId.getText().toString().trim().isEmpty() && !eQuantity.getText().toString().trim().isEmpty() && !eSaleDate.getText().toString().trim().isEmpty() && !eSaleAmount.getText().toString().trim().isEmpty() && !eSalePayment.getText().toString().trim().isEmpty()) {
                    Sale model = new Sale(
                            UUID.randomUUID().toString(),
                            sProductName.getSelectedItem().toString(),
                            tProductId.getText().toString(),
                            Integer.parseInt(eQuantity.getText().toString()),
                            Integer.parseInt(ePurchaseQuantity.getText().toString()),
                            sCustomerName.getSelectedItem().toString(),
                            tCustomerId.getText().toString(),
                            eSaleDate.getText().toString(),
                            Double.parseDouble(eSaleDiscount.getText().toString()),
                            Double.parseDouble(eSaleVat.getText().toString()),
                            Double.parseDouble(eSaleAmount.getText().toString()),
                            Double.parseDouble(eSalePayment.getText().toString()),
                            Double.parseDouble(eSaleBalance.getText().toString()),
                            eSaleDesc.getText().toString()
                    );
                    Log.d(TAG, new Gson().toJson(model));
                    long result = mViewModel.save(model);
                    if (result > 0) {
                        mArrayList.add(model);
                        mAdapter.notifyItemInserted(mArrayList.size());
                        obj.getDialog().dismiss();
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Please insert the values in your mandatory fields.", Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        });
    }

    @Override
    public void updateItem(int position, Sale model) {
        ObjectDialog obj = showObjectDialog("Edit");

        eQuantity.setText("" + model.getProductQuantity());
        ePurchaseQuantity.setText("" + model.getPurchaseProductQuantity());
        eSaleDate.setText(model.getSalesDate());
        eSaleDiscount.setText("" + model.getSalesDiscount());
        eSaleVat.setText("" + model.getSalesVat());
        eSaleAmount.setText("" + model.getSalesAmount());
        eSalePayment.setText("" + model.getSalesPayment());
        eSaleBalance.setText("" + model.getSalesBalance());
        eSaleDesc.setText(model.getSalesDescription());

        eSaleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getDate(SaleActivity.this, eSaleDate);
            }
        });

        ((Button) obj.getView().findViewById(R.id.sale_save_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tProductId.getText().toString().trim().isEmpty() && !eQuantity.getText().toString().trim().isEmpty() && !eSaleDate.getText().toString().trim().isEmpty() && !eSaleAmount.getText().toString().trim().isEmpty() && !eSalePayment.getText().toString().trim().isEmpty()) {
                    Sale sale = new Sale(
                            model.getSalesId(),
                            sProductName.getSelectedItem().toString(),
                            tProductId.getText().toString(),
                            Integer.parseInt(eQuantity.getText().toString()),
                            Integer.parseInt(ePurchaseQuantity.getText().toString()),
                            sCustomerName.getSelectedItem().toString(),
                            tCustomerId.getText().toString(),
                            eSaleDate.getText().toString(),
                            Double.parseDouble(eSaleDiscount.getText().toString()),
                            Double.parseDouble(eSaleVat.getText().toString()),
                            Double.parseDouble(eSaleAmount.getText().toString()),
                            Double.parseDouble(eSalePayment.getText().toString()),
                            Double.parseDouble(eSaleBalance.getText().toString()),
                            eSaleDesc.getText().toString()
                    );
                    long result = mViewModel.update(sale);
                    if (result > 0) {
                        //mArrayList.clear();
                        //mArrayList.addAll(viewModels);
                        mArrayList.set(position, sale);
                        mAdapter.notifyItemChanged(position, sale);
                        //mAdapter.notifyDataSetChanged(); //recyclerView.invalidate();
                        obj.getDialog().dismiss();
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Please insert the values in your mandatory fields.", Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        });
    }

    private ObjectDialog showObjectDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_sale, null, false);
        builder.setView(view);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.create();
        AlertDialog dialog = builder.show();

        sProductName = (Spinner) view.findViewById(R.id.sl_product_name);
        tProductId = (TextView) view.findViewById(R.id.s1_product_id);
        eQuantity = (EditText) view.findViewById(R.id.sl_product_quantity);
        ePurchaseQuantity = (EditText) view.findViewById(R.id.sl_purchase_product_quantity);
        sCustomerName = (Spinner) view.findViewById(R.id.sl_customer_name);
        tCustomerId = (TextView) view.findViewById(R.id.sl_customer_id);
        eSaleDate = (EditText) view.findViewById(R.id.sl_sales_date);
        eSaleDiscount = (EditText) view.findViewById(R.id.sl_sales_discount);
        eSaleVat = (EditText) view.findViewById(R.id.sl_sales_vat);
        eSaleAmount = (EditText) view.findViewById(R.id.sl_sales_amount);
        eSalePayment = (EditText) view.findViewById(R.id.sl_sales_payment);
        eSaleBalance = (EditText) view.findViewById(R.id.sl_sales_balance);
        eSaleDesc = (EditText) view.findViewById(R.id.sl_sales_description);
        return new ObjectDialog(view, dialog);
    }
}
