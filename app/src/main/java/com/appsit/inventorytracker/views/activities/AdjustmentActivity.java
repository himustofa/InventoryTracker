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
import com.appsit.inventorytracker.models.Adjustment;
import com.appsit.inventorytracker.models.ObjectDialog;
import com.appsit.inventorytracker.models.Purchase;
import com.appsit.inventorytracker.utils.Utility;
import com.appsit.inventorytracker.viewmodels.AdjustmentViewModel;
import com.appsit.inventorytracker.viewmodels.PurchaseViewModel;
import com.appsit.inventorytracker.views.adapters.AdjustmentAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdjustmentActivity extends AppCompatActivity implements AdjustmentAdapter.RecyclerItemListener {

    private String TAG = this.getClass().getSimpleName();
    private List<Purchase> mPurchaseList;
    List<String> pList = new ArrayList<>();
    private ArrayList<Adjustment> mArrayList = new ArrayList<>();
    private AdjustmentAdapter mAdapter;
    private AdjustmentViewModel mViewModel;
    private boolean isValue = true;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjustment);

        mRecyclerView = (RecyclerView) findViewById(R.id.adjustment_recycler_view);

        PurchaseViewModel mPurchaseViewModel = ViewModelProviders.of(this).get(PurchaseViewModel.class);
        mPurchaseViewModel.getAll().observe(this, new Observer<List<Purchase>>() {
            @Override
            public void onChanged(List<Purchase> purchases) {
                if (purchases != null) {
                    mPurchaseList = purchases;
                    Log.d(TAG, new Gson().toJson(purchases));
                    if (purchases.size() > 0) {
                        for(Purchase s : purchases) {
                            pList.add(s.getProductName());
                        }
                    }
                }
            }
        });

        mViewModel = ViewModelProviders.of(this).get(AdjustmentViewModel.class);
        mViewModel.getAllData().observe(this, new Observer<List<Adjustment>>() {
            @Override
            public void onChanged(List<Adjustment> customers) {
                if (isValue) {
                    mArrayList.addAll(customers);
                    isValue = false;
                }
            }
        });

        ((FloatingActionButton) findViewById(R.id.adjustment_add_fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPurchaseList.size() > 0) {
                    addItem();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Products are not available.", Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new AdjustmentAdapter(AdjustmentActivity.this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(AdjustmentActivity.this));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter.notifyDataSetChanged();
    }

    //https://stackoverflow.com/questions/31367599/how-to-update-recyclerview-adapter-data
    @Override
    public void removeItem(int position, Adjustment model) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_adjustment, null, false);
        builder.setView(view);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("Add");
        builder.setCancelable(true);
        builder.create();
        AlertDialog dialog = builder.show();
        Spinner productName = (Spinner) view.findViewById(R.id.adj_product_name);
        TextView productId = (TextView) view.findViewById(R.id.adj_product_id);
        EditText adjQuantity = (EditText) view.findViewById(R.id.adj_product_quantity);
        EditText adjAmount = (EditText) view.findViewById(R.id.adj_amount);
        EditText adjDesc = (EditText) view.findViewById(R.id.adj_description);

        Utility.getSpinnerData(new Utility.AdapterPosition() {
            @Override
            public void onPosition(int position) {
                productId.setText(mPurchaseList.get(position).getProductId());
            }
        }, this, productName, pList);

        ((Button) view.findViewById(R.id.adj_save_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!adjQuantity.getText().toString().trim().isEmpty() && !adjAmount.getText().toString().trim().isEmpty()) {
                    Adjustment model = new Adjustment(
                            UUID.randomUUID().toString(),
                            productName.getSelectedItem().toString(),
                            productId.getText().toString(),
                            Integer.parseInt(adjQuantity.getText().toString()),
                            Double.parseDouble(adjAmount.getText().toString()),
                            adjDesc.getText().toString()
                    );
                    long result = mViewModel.save(model);
                    if (result > 0) {
                        mArrayList.add(model);
                        mAdapter.notifyItemInserted(mArrayList.size());
                        dialog.dismiss();
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Please insert the values in your mandatory fields.", Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        });
    }

}
