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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.ObjectDialog;
import com.appsit.inventorytracker.models.Purchase;
import com.appsit.inventorytracker.models.Supplier;
import com.appsit.inventorytracker.utils.Utility;
import com.appsit.inventorytracker.viewmodels.PurchaseViewModel;
import com.appsit.inventorytracker.viewmodels.SupplierViewModel;
import com.appsit.inventorytracker.views.adapters.PurchaseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PurchaseActivity extends AppCompatActivity implements PurchaseAdapter.RecyclerItemListener {

    private String TAG = this.getClass().getSimpleName();
    private List<Supplier> mSupplierList;
    private ArrayList<Purchase> mArrayList = new ArrayList<>();
    private PurchaseAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SupplierViewModel mSupplierViewModel;
    private PurchaseViewModel mViewModel;
    private boolean isValue = true;

    List<String> sList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        mRecyclerView = (RecyclerView) findViewById(R.id.supplier_recycler_view);

        mSupplierViewModel = ViewModelProviders.of(this).get(SupplierViewModel.class);
        mSupplierViewModel.getAll().observe(this, new Observer<List<Supplier>>() {
            @Override
            public void onChanged(List<Supplier> suppliers) {
                mSupplierList = suppliers;
                sList.add(suppliers.get(0).getSupplierName());
            }
        });

        mViewModel = ViewModelProviders.of(this).get(PurchaseViewModel.class);
        mViewModel.getAll().observe(this, new Observer<List<Purchase>>() {
            @Override
            public void onChanged(List<Purchase> list) {
                if (isValue) {
                    mArrayList.addAll(list);
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
        mAdapter = new PurchaseAdapter(PurchaseActivity.this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(PurchaseActivity.this));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter.notifyDataSetChanged();
    }

    //https://stackoverflow.com/questions/31367599/how-to-update-recyclerview-adapter-data
    @Override
    public void removeItem(int position, Purchase model) {
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

        Spinner S1 = (Spinner) obj.getView().findViewById(R.id.product_name);
        TextView T1 = (TextView) obj.getView().findViewById(R.id.product_id);
        Spinner S2 = (Spinner) obj.getView().findViewById(R.id.supplier_name);
        TextView T2 = (TextView) obj.getView().findViewById(R.id.supplier_id);
        EditText E3 = (EditText) obj.getView().findViewById(R.id.purchase_date);
        EditText E4 = (EditText) obj.getView().findViewById(R.id.purchase_product_quantity);
        EditText E5 = (EditText) obj.getView().findViewById(R.id.purchase_product_price);
        EditText E6 = (EditText) obj.getView().findViewById(R.id.purchase_amount);
        EditText E7 = (EditText) obj.getView().findViewById(R.id.purchase_payment);
        EditText E8 = (EditText) obj.getView().findViewById(R.id.purchase_balance);
        EditText E9 = (EditText) obj.getView().findViewById(R.id.purchase_description);

        Utility.getSpinnerData(new Utility.AdapterPosition() {
            @Override
            public void onPosition(int position) {
                T1.setText(mSupplierList.get(position).getSupplierId());
            }
        }, this, S2, sList);

        ((Button) obj.getView().findViewById(R.id.purchase_save_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!S1.getSelectedItem().toString().trim().isEmpty() && !E4.getText().toString().trim().isEmpty()) {
                    Purchase model = new Purchase(
                            UUID.randomUUID().toString(),
                            S1.getSelectedItem().toString(),
                            T1.getText().toString(),
                            S2.getSelectedItem().toString(),
                            T2.getText().toString(),
                            Integer.parseInt(E3.getText().toString()),
                            Double.parseDouble(E4.getText().toString()),
                            E5.getText().toString(),
                            Double.parseDouble(E6.getText().toString()),
                            Double.parseDouble(E7.getText().toString()),
                            Double.parseDouble(E8.getText().toString()),
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
    public void updateItem(int position, Purchase model) {
        ObjectDialog obj = showObjectDialog("Edit Supplier");

        Spinner E1 = (Spinner) obj.getView().findViewById(R.id.product_name);
        Spinner E2 = (Spinner) obj.getView().findViewById(R.id.supplier_name);
        EditText E3 = (EditText) obj.getView().findViewById(R.id.purchase_date);
        EditText E4 = (EditText) obj.getView().findViewById(R.id.purchase_product_quantity);
        EditText E5 = (EditText) obj.getView().findViewById(R.id.purchase_product_price);
        EditText E6 = (EditText) obj.getView().findViewById(R.id.purchase_amount);
        EditText E7 = (EditText) obj.getView().findViewById(R.id.purchase_payment);
        EditText E8 = (EditText) obj.getView().findViewById(R.id.purchase_balance);
        EditText E9 = (EditText) obj.getView().findViewById(R.id.purchase_description);
        //E1.setText(model.getSupplierName());
        //E2.setText(model.getSupplierCompanyName());
        E3.setText(model.getPurchaseDate());
        E4.setText("" + model.getPurchaseProductQuantity());
        E5.setText("" + model.getPurchaseProductPrice());
        E6.setText("" + model.getPurchaseAmount());
        E7.setText("" + model.getPurchasePayment());
        E8.setText("" + model.getPurchaseBalance());
        E9.setText(model.getPurchaseDescription());

        ((Button) obj.getView().findViewById(R.id.supplier_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!E1.getSelectedItem().toString().trim().isEmpty() && !E4.getText().toString().trim().isEmpty()) {
                    Purchase model = new Purchase(
                            UUID.randomUUID().toString(),
                            E1.getSelectedItem().toString(),
                            "",
                            E2.getSelectedItem().toString(),
                            "",
                            Integer.parseInt(E3.getText().toString()),
                            Double.parseDouble(E4.getText().toString()),
                            E5.getText().toString(),
                            Double.parseDouble(E6.getText().toString()),
                            Double.parseDouble(E7.getText().toString()),
                            Double.parseDouble(E8.getText().toString()),
                            E9.getText().toString()
                    );
                    long result = mViewModel.update(model);
                    if (result > 0) {
                        //mArrayList.clear();
                        //mArrayList.addAll(viewModels);
                        mArrayList.set(position, model);
                        mAdapter.notifyItemChanged(position, model);
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
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_purchase, null, false);
        builder.setView(view);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.create();
        AlertDialog dialog = builder.show();
        return new ObjectDialog(view, dialog);
    }
}
