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

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.ObjectDialog;
import com.appsit.inventorytracker.models.Supplier;
import com.appsit.inventorytracker.viewmodels.SupplierViewModel;
import com.appsit.inventorytracker.views.adapters.SupplierAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SupplierActivity extends AppCompatActivity implements SupplierAdapter.RecyclerItemListener {

    private String TAG = this.getClass().getSimpleName();
    private ArrayList<Supplier> mArrayList = new ArrayList<>();
    private SupplierAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SupplierViewModel mViewModel;
    private boolean isValue = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);

        mRecyclerView = (RecyclerView) findViewById(R.id.supplier_recycler_view);
        mViewModel = ViewModelProviders.of(this).get(SupplierViewModel.class);
        mViewModel.getAll().observe(this, new Observer<List<Supplier>>() {
            @Override
            public void onChanged(List<Supplier> suppliers) {
                if (isValue) {
                    mArrayList.addAll(suppliers);
                    isValue = false;
                }
            }
        });

        ((FloatingActionButton) findViewById(R.id.supplier_add_fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new SupplierAdapter(SupplierActivity.this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SupplierActivity.this));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter.notifyDataSetChanged();
    }

    //https://stackoverflow.com/questions/31367599/how-to-update-recyclerview-adapter-data
    @Override
    public void removeItem(int position, Supplier model) {
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
        ObjectDialog obj = showObjectDialog("Add Supplier");

        EditText E1 = (EditText) obj.getView().findViewById(R.id.supplierName);
        EditText E2 = (EditText) obj.getView().findViewById(R.id.supplierCompanyName);
        EditText E3 = (EditText) obj.getView().findViewById(R.id.supplierContactPerson);
        EditText E4 = (EditText) obj.getView().findViewById(R.id.supplierPhoneNumber);
        EditText E5 = (EditText) obj.getView().findViewById(R.id.supplierAddress);
        EditText E6 = (EditText) obj.getView().findViewById(R.id.supplierBankName);
        EditText E7 = (EditText) obj.getView().findViewById(R.id.supplierBankAccount);
        EditText E8 = (EditText) obj.getView().findViewById(R.id.supplierEmail);
        EditText E9 = (EditText) obj.getView().findViewById(R.id.supplierWebsite);
        ((Button) obj.getView().findViewById(R.id.supplier_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!E1.getText().toString().trim().isEmpty() && !E4.getText().toString().trim().isEmpty()) {
                    Supplier model = new Supplier(
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
                    );
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
    public void updateItem(int position, Supplier model) {
        ObjectDialog obj = showObjectDialog("Edit Supplier");

        EditText E1 = (EditText) obj.getView().findViewById(R.id.supplierName);
        EditText E2 = (EditText) obj.getView().findViewById(R.id.supplierCompanyName);
        EditText E3 = (EditText) obj.getView().findViewById(R.id.supplierContactPerson);
        EditText E4 = (EditText) obj.getView().findViewById(R.id.supplierPhoneNumber);
        EditText E5 = (EditText) obj.getView().findViewById(R.id.supplierAddress);
        EditText E6 = (EditText) obj.getView().findViewById(R.id.supplierBankName);
        EditText E7 = (EditText) obj.getView().findViewById(R.id.supplierBankAccount);
        EditText E8 = (EditText) obj.getView().findViewById(R.id.supplierEmail);
        EditText E9 = (EditText) obj.getView().findViewById(R.id.supplierWebsite);
        E1.setText(model.getSupplierName());
        E2.setText(model.getSupplierCompanyName());
        E3.setText(model.getSupplierContactPerson());
        E4.setText(model.getSupplierPhoneNumber());
        E5.setText(model.getSupplierAddress());
        E6.setText(model.getSupplierBankName());
        E7.setText(model.getSupplierBankAccount());
        E8.setText(model.getSupplierEmail());
        E9.setText(model.getSupplierWebsite());

        ((Button) obj.getView().findViewById(R.id.supplier_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!E1.getText().toString().trim().isEmpty() && !E4.getText().toString().trim().isEmpty()) {
                    Supplier supplier = new Supplier(
                            model.getSupplierId(),
                            E1.getText().toString(),
                            E2.getText().toString(),
                            E3.getText().toString(),
                            E4.getText().toString(),
                            E5.getText().toString(),
                            E6.getText().toString(),
                            E7.getText().toString(),
                            E8.getText().toString(),
                            E9.getText().toString()
                    );
                    long result = mViewModel.update(supplier);
                    if (result > 0) {
                        //mArrayList.clear();
                        //mArrayList.addAll(viewModels);
                        mArrayList.set(position, supplier);
                        mAdapter.notifyItemChanged(position, supplier);
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
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_supplier, null, false);
        builder.setView(view);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.create();
        AlertDialog dialog = builder.show();
        return new ObjectDialog(view, dialog);
    }
}
