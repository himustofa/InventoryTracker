package com.appsit.inventorytracker.views.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.ObjectDialog;
import com.appsit.inventorytracker.models.Product;
import com.appsit.inventorytracker.models.Supplier;
import com.appsit.inventorytracker.utils.Utility;
import com.appsit.inventorytracker.viewmodels.ProductViewModel;
import com.appsit.inventorytracker.views.adapters.ProductAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

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

    private List<Supplier> mSupplierList;
    private List<String> sList = new ArrayList<>();

    private Spinner suppName;
    private TextView suppId;
    private EditText proName, proCode, proQty, proPrice, proExpDate, proDesc;

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
                    initRecyclerView();
                }
            }
        });

        ((FloatingActionButton) findViewById(R.id.product_add_fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });


        ((SearchView) findViewById(R.id.search_view)).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (mAdapter != null){
                    mAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
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
        ObjectDialog obj = showObjectDialog("Add");

        proExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getDate(ProductActivity.this, proExpDate);
            }
        });

        ((Button) obj.getView().findViewById(R.id.product_save_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!suppId.getText().toString().isEmpty() && !proName.getText().toString().trim().isEmpty() && !proCode.getText().toString().trim().isEmpty() && !proQty.getText().toString().trim().isEmpty() && !proPrice.getText().toString().trim().isEmpty() && !proExpDate.getText().toString().trim().isEmpty()) {
                    Product model = new Product(
                            UUID.randomUUID().toString(),
                            proName.getText().toString(),
                            proCode.getText().toString(),
                            Integer.parseInt(proQty.getText().toString()),
                            Double.parseDouble(proPrice.getText().toString()),
                            proExpDate.getText().toString(),
                            proDesc.getText().toString()
                    );
                    long result = mViewModel.save(model);
                    if (result > 0) {
                        mArrayList.add(model);
                        mAdapter.notifyItemInserted(mArrayList.size());
                        obj.getDialog().dismiss();
                    }
                } else {
                    ((TextInputLayout) obj.getView().findViewById(R.id.layout_product_name)).setError("required!");
                    ((TextInputLayout) obj.getView().findViewById(R.id.layout_product_code)).setError("required!");
                    ((TextInputLayout) obj.getView().findViewById(R.id.layout_product_quantity)).setError("required!");
                    ((TextInputLayout) obj.getView().findViewById(R.id.layout_product_price)).setError("required!");
                    //Snackbar.make(findViewById(android.R.id.content), "Please insert the values in your mandatory fields.", Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        });
    }

    @Override
    public void updateItem(int position, Product model) {
        ObjectDialog obj = showObjectDialog("Edit");
        proName.setText(model.getProductName());
        proCode.setText(model.getProductCode());
        proQty.setText("" + model.getProductQuantity());
        proPrice.setText("" + model.getProductPrice());
        proExpDate.setText("" + model.getProductExpireDate());
        proDesc.setText(model.getProductDescription());

        proExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getDate(ProductActivity.this, proExpDate);
            }
        });

        ((Button) obj.getView().findViewById(R.id.product_save_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!suppId.getText().toString().isEmpty() && !proName.getText().toString().trim().isEmpty() && !proCode.getText().toString().trim().isEmpty() && !proQty.getText().toString().trim().isEmpty() && !proPrice.getText().toString().trim().isEmpty() && !proExpDate.getText().toString().trim().isEmpty()) {
                    Product mModel = new Product(
                            UUID.randomUUID().toString(),
                            proName.getText().toString(),
                            proCode.getText().toString(),
                            Integer.parseInt(proQty.getText().toString()),
                            Double.parseDouble(proPrice.getText().toString()),
                            proExpDate.getText().toString(),
                            proDesc.getText().toString()
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
        suppName = (Spinner) view.findViewById(R.id.p_supplier_name);
        suppId = (TextView) view.findViewById(R.id.p_supplier_id);
        proName = (EditText) view.findViewById(R.id.product_name);
        proCode = (EditText) view.findViewById(R.id.product_code);
        proQty = (EditText) view.findViewById(R.id.product_quantity);
        proPrice = (EditText) view.findViewById(R.id.product_price);
        proExpDate = (EditText) view.findViewById(R.id.product_expire_date);
        proDesc = (EditText) view.findViewById(R.id.product_description);
        return new ObjectDialog(view, dialog);
    }
}
