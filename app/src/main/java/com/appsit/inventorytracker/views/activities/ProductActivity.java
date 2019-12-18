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
import com.appsit.inventorytracker.models.ObjectDialog;
import com.appsit.inventorytracker.models.Product;
import com.appsit.inventorytracker.utils.Utility;
import com.appsit.inventorytracker.viewmodels.ProductViewModel;
import com.appsit.inventorytracker.views.adapters.ProductAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductActivity extends AppCompatActivity implements ProductAdapter.RecyclerItemListener{

    private String TAG = this.getClass().getSimpleName();
    private ArrayList<Product> mArrayList = new ArrayList<>();
    private ProductAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ProductViewModel mViewModel;
    private boolean isValue = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        mRecyclerView = (RecyclerView) findViewById(R.id.product_recycler_view);
        mViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        mViewModel.getAll().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> list) {
                if (isValue) {
                    mArrayList.addAll(list);
                    isValue = false;
                }
            }
        });

        ((FloatingActionButton) findViewById(R.id.product_add_fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new ProductAdapter(ProductActivity.this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter.notifyDataSetChanged();
    }

    //https://stackoverflow.com/questions/31367599/how-to-update-recyclerview-adapter-data
    @Override
    public void removeItem(int position, Product model) {
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

        EditText E1 = (EditText) obj.getView().findViewById(R.id.product_name);
        EditText E2 = (EditText) obj.getView().findViewById(R.id.product_code);
        EditText E3 = (EditText) obj.getView().findViewById(R.id.product_quantity);
        EditText E4 = (EditText) obj.getView().findViewById(R.id.product_price);
        EditText E5 = (EditText) obj.getView().findViewById(R.id.product_expire_date);
        EditText E6 = (EditText) obj.getView().findViewById(R.id.product_description);

        E5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getDate(ProductActivity.this, E5);
            }
        });

        ((Button) obj.getView().findViewById(R.id.product_save_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!E1.getText().toString().trim().isEmpty() && !E2.getText().toString().trim().isEmpty() && !E3.getText().toString().trim().isEmpty() && !E4.getText().toString().trim().isEmpty() && !E5.getText().toString().trim().isEmpty()) {
                    Product model = new Product(
                            UUID.randomUUID().toString(),
                            E1.getText().toString(),
                            E2.getText().toString(),
                            Integer.parseInt(E3.getText().toString()),
                            Double.parseDouble(E4.getText().toString()),
                            E5.getText().toString(),
                            E6.getText().toString()
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
    public void updateItem(int position, Product model) {
        ObjectDialog obj = showObjectDialog("Edit Supplier");

        EditText E1 = (EditText) obj.getView().findViewById(R.id.product_name);
        EditText E2 = (EditText) obj.getView().findViewById(R.id.product_code);
        EditText E3 = (EditText) obj.getView().findViewById(R.id.product_quantity);
        EditText E4 = (EditText) obj.getView().findViewById(R.id.product_price);
        EditText E5 = (EditText) obj.getView().findViewById(R.id.product_expire_date);
        EditText E6 = (EditText) obj.getView().findViewById(R.id.product_description);
        E1.setText(model.getProductName());
        E2.setText(model.getProductCode());
        E3.setText("" + model.getProductQuantity());
        E4.setText("" + model.getProductPrice());
        E5.setText("" + model.getProductExpireDate());
        E6.setText(model.getProductDescription());

        E5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getDate(ProductActivity.this, E5);
            }
        });

        ((Button) obj.getView().findViewById(R.id.product_save_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!E1.getText().toString().trim().isEmpty() && !E2.getText().toString().trim().isEmpty() && !E3.getText().toString().trim().isEmpty() && !E4.getText().toString().trim().isEmpty() && !E5.getText().toString().trim().isEmpty()) {
                    Product mModel = new Product(
                            model.getProductId(),
                            E1.getText().toString(),
                            E2.getText().toString(),
                            Integer.parseInt(E3.getText().toString()),
                            Double.parseDouble(E4.getText().toString()),
                            E5.getText().toString(),
                            E6.getText().toString()
                    );
                    long result = mViewModel.update(mModel);
                    if (result > 0) {
                        //mArrayList.clear();
                        //mArrayList.addAll(viewModels);
                        mArrayList.set(position, mModel);
                        mAdapter.notifyItemChanged(position, mModel);
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
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_product, null, false);
        builder.setView(view);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.create();
        AlertDialog dialog = builder.show();
        return new ObjectDialog(view, dialog);
    }
}
