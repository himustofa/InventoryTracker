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
import com.appsit.inventorytracker.models.Sale;
import com.appsit.inventorytracker.utils.Utility;
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

    private RecyclerView mRecyclerView;
    private EditText E1, E2, E3, E4, E5, E6, E7, E8, E9;
    private Spinner S1, S2;
    private TextView T1, T2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        mRecyclerView = (RecyclerView) findViewById(R.id.customer_recycler_view);
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

        ((FloatingActionButton) findViewById(R.id.customer_add_fab)).setOnClickListener(new View.OnClickListener() {
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
        ObjectDialog obj = showObjectDialog("Add Customer");

        E3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getDate(SaleActivity.this, E3);
            }
        });

        ((Button) obj.getView().findViewById(R.id.sale_save_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!E1.getText().toString().trim().isEmpty() && !E4.getText().toString().trim().isEmpty()) {
                    Sale model = new Sale(
                            UUID.randomUUID().toString(),
                            T1.getText().toString(),
                            S1.getSelectedItem().toString(),
                            Integer.parseInt(E1.getText().toString()),
                            Integer.parseInt(E2.getText().toString()),
                            T2.getText().toString(),
                            S2.getSelectedItem().toString(),
                            E3.getText().toString(),
                            Integer.parseInt(E4.getText().toString()),
                            Double.parseDouble(E5.getText().toString()),
                            Double.parseDouble(E6.getText().toString()),
                            Double.parseDouble(E7.getText().toString()),
                            Double.parseDouble(E8.getText().toString()),
                            E9.getText().toString()
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
        ObjectDialog obj = showObjectDialog("Edit Customer");

        E1.setText("" + model.getProductQuantity());
        E2.setText("" + model.getPurchaseProductQuantity());
        E3.setText(model.getSalesDate());
        E4.setText("" + model.getSalesDiscount());
        E5.setText("" + model.getSalesVat());
        E6.setText("" + model.getSalesAmount());
        E7.setText("" + model.getSalesPayment());
        E8.setText("" + model.getSalesBalance());
        E9.setText(model.getSalesDescription());

        E3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getDate(SaleActivity.this, E3);
            }
        });

        ((Button) obj.getView().findViewById(R.id.sale_save_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!E1.getText().toString().trim().isEmpty() && !E4.getText().toString().trim().isEmpty()) {
                    Sale sale = new Sale(
                            model.getSalesId(),
                            T1.getText().toString(),
                            S1.getSelectedItem().toString(),
                            Integer.parseInt(E1.getText().toString()),
                            Integer.parseInt(E2.getText().toString()),
                            T2.getText().toString(),
                            S2.getSelectedItem().toString(),
                            E3.getText().toString(),
                            Integer.parseInt(E4.getText().toString()),
                            Double.parseDouble(E5.getText().toString()),
                            Double.parseDouble(E6.getText().toString()),
                            Double.parseDouble(E7.getText().toString()),
                            Double.parseDouble(E8.getText().toString()),
                            E9.getText().toString()
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
        S1 = (Spinner) view.findViewById(R.id.sl_product_name);
        T1 = (TextView) view.findViewById(R.id.s1_product_id);
        E1 = (EditText) view.findViewById(R.id.sl_product_quantity);
        E2 = (EditText) view.findViewById(R.id.sl_purchase_product_quantity);
        S2 = (Spinner) view.findViewById(R.id.sl_customer_name);
        T2 = (TextView) view.findViewById(R.id.sl_customer_id);
        E3 = (EditText) view.findViewById(R.id.sl_sales_date);
        E4 = (EditText) view.findViewById(R.id.sl_sales_discount);
        E5 = (EditText) view.findViewById(R.id.sl_sales_vat);
        E6 = (EditText) view.findViewById(R.id.sl_sales_amount);
        E7 = (EditText) view.findViewById(R.id.sl_sales_payment);
        E8 = (EditText) view.findViewById(R.id.sl_sales_balance);
        E9 = (EditText) view.findViewById(R.id.sl_sales_description);
        return new ObjectDialog(view, dialog);
    }
}
