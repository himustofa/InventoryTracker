package com.appsit.inventorytracker.views.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.Supplier;
import com.appsit.inventorytracker.viewmodels.SupplierViewModel;
import com.appsit.inventorytracker.views.adapters.SupplierAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SupplierActivity extends AppCompatActivity implements SupplierAdapter.RecyclerItemListener {

    private String TAG = this.getClass().getSimpleName();
    private ArrayList<Supplier> mArrayList = new ArrayList<>();
    private SupplierAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SupplierViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);

        mRecyclerView = (RecyclerView) findViewById(R.id.supplier_recycler_view);
        mViewModel = ViewModelProviders.of(this).get(SupplierViewModel.class);
        mViewModel.getAll().observe(this, new Observer<List<Supplier>>() {
            @Override
            public void onChanged(List<Supplier> suppliers) {
                mArrayList.addAll(suppliers);
                mAdapter = new SupplierAdapter(SupplierActivity.this, mArrayList);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(SupplierActivity.this));
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void removeItem(int position, Supplier model) {
        mArrayList.remove(position);
        mRecyclerView.removeViewAt(position);
        mAdapter.notifyItemRemoved(position);
        mAdapter.notifyItemRangeChanged(position, mArrayList.size());
    }

    @Override
    public void addItem(Supplier model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.recycler_item_supplier, null, false);
        builder.setView(view);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("Add Supplier");
        builder.setCancelable(false);
        builder.create();

        AlertDialog dialog = builder.show();

        EditText E1 = (EditText) view.findViewById(R.id.supplierName);
        EditText E2 = (EditText) view.findViewById(R.id.supplierCompanyName);
        EditText E3 = (EditText) view.findViewById(R.id.supplierContactPerson);
        EditText E4 = (EditText) view.findViewById(R.id.supplierPhoneNumber);
        EditText E5 = (EditText) view.findViewById(R.id.supplierAddress);
        EditText E6 = (EditText) view.findViewById(R.id.supplierBankName);
        EditText E7 = (EditText) view.findViewById(R.id.supplierBankAccount);
        EditText E8 = (EditText) view.findViewById(R.id.supplierEmail);
        EditText E9 = (EditText) view.findViewById(R.id.supplierWebsite);
        ((Button) view.findViewById(R.id.supplier_save_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!E1.getText().toString().trim().isEmpty() && !E4.getText().toString().trim().isEmpty()) {
                    long result = mViewModel.save(new Supplier(
                            UUID.randomUUID().toString(),
                            E1.getText().toString(),
                            E2.getText().toString(),
                            E3.getText().toString(),
                            E4.getText().toString(),
                            E5.getText().toString(),
                            E6.getText().toString(),
                            E7.getText().toString(),
                            E8.getText().toString(),
                            E9.getText().toString()
                    ));
                    mArrayList.add(model);
                    mAdapter.notifyItemInserted(mArrayList.size());
                    dialog.dismiss();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Please insert the values in your mandatory fields.", Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        });
    }

    @Override
    public void updateItem(int position, Supplier model) {
        /*mArrayList.clear();
        mArrayList.addAll(viewModels);
        mAdapter.notifyItemChanged(position, model);
        mAdapter.notifyDataSetChanged();*/
    }
}
