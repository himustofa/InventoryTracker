package com.appsit.inventorytracker.views.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.ObjectDialog;
import com.appsit.inventorytracker.models.Product;
import com.appsit.inventorytracker.models.Purchase;
import com.appsit.inventorytracker.models.Role;
import com.appsit.inventorytracker.models.Supplier;
import com.appsit.inventorytracker.models.User;
import com.appsit.inventorytracker.session.SharedPrefManager;
import com.appsit.inventorytracker.utils.PurchaseTextWatcher;
import com.appsit.inventorytracker.utils.Utility;
import com.appsit.inventorytracker.viewmodels.ProductViewModel;
import com.appsit.inventorytracker.viewmodels.PurchaseViewModel;
import com.appsit.inventorytracker.viewmodels.SupplierViewModel;
import com.appsit.inventorytracker.views.adapters.PurchaseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class PurchaseActivity extends AppCompatActivity implements PurchaseAdapter.RecyclerItemListener {

    private String TAG = this.getClass().getSimpleName();

    private List<Supplier> mSupplierList;
    private List<Product> mProductList;
    private ArrayList<Purchase> mArrayList = new ArrayList<>();
    private PurchaseAdapter mAdapter;
    private PurchaseViewModel mViewModel;
    private boolean isValue = true;

    List<String> sList = new ArrayList<>();
    List<String> pList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private Spinner S1, S2;
    private TextView T1, T2;
    private EditText E3, E4, E5, E6, E7, E8, E9;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        this.mUser = SharedPrefManager.getInstance(this).getUser();

        mRecyclerView = (RecyclerView) findViewById(R.id.purchase_recycler_view);

        SupplierViewModel mSupplierViewModel = ViewModelProviders.of(this).get(SupplierViewModel.class);
        mSupplierViewModel.getAll().observe(this, new Observer<List<Supplier>>() {
            @Override
            public void onChanged(List<Supplier> suppliers) {
                if (suppliers != null) {
                    mSupplierList = suppliers;
                    if (suppliers.size() > 0) {
                        for(Supplier s : suppliers) {
                            sList.add(s.getSupplierName());
                        }
                    }
                }
            }
        });

        ProductViewModel mProductViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        mProductViewModel.getAll().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null) {
                    mProductList = products;
                    if (products.size() > 0) {
                        for(Product p : products) {
                            pList.add(p.getProductName());
                        }
                    }
                }
            }
        });

        mViewModel = ViewModelProviders.of(this).get(PurchaseViewModel.class);
        mViewModel.getAll().observe(this, new Observer<List<Purchase>>() {
            @Override
            public void onChanged(List<Purchase> list) {
                if (isValue) {
                    mArrayList.addAll(list);
                    isValue = false;
                    //sorting(mArrayList);
                    initRecyclerView();
                }
            }
        });

        ((FloatingActionButton) findViewById(R.id.purchase_add_fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProductList.size() > 0) {
                    if (mUser.getRole().equals(String.valueOf(Role.ADMIN_USER))) {
                        addItem();
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "You must be an admin user.", Snackbar.LENGTH_INDEFINITE).show();
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Products are not available.", Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        });

        ((EditText) findViewById(R.id.search_item)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mAdapter != null){
                    mAdapter.getFilter().filter(s);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void sorting(ArrayList<Purchase> list) {
        //https://stackoverflow.com/questions/9109890/android-java-how-to-sort-a-list-of-objects-by-a-certain-value-within-the-object
        Collections.sort(list, new Comparator<Purchase>(){
            public int compare(Purchase obj1, Purchase obj2) {
                return obj1.getSupplierName().compareToIgnoreCase(obj2.getSupplierName());
            }
        });
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
        ObjectDialog obj = showObjectDialog("Add");

        E3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getDate(PurchaseActivity.this, E3);
            }
        });
        Utility.getSpinnerData(new Utility.AdapterPosition() {
            @Override
            public void onPosition(int position) {
                T1.setText(mProductList.get(position).getProductId());
                E5.setText("" + mProductList.get(position).getProductPrice());
            }
        }, this, S1, pList);
        Utility.getSpinnerData(new Utility.AdapterPosition() {
            @Override
            public void onPosition(int position) {
                T2.setText(mSupplierList.get(position).getSupplierId());
            }
        }, this, S2, sList);
        E4.addTextChangedListener(new PurchaseTextWatcher(E6, E4, E5, false));
        E5.addTextChangedListener(new PurchaseTextWatcher(E6, E4, E5, false));
        E7.addTextChangedListener(new PurchaseTextWatcher(E8, E6, E7, true));
        E3.setText(Utility.getCurrentDatPicker(this));

        ((Button) obj.getView().findViewById(R.id.p_purchase_save_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!E3.getText().toString().trim().isEmpty() && !E4.getText().toString().trim().isEmpty() && !E5.getText().toString().trim().isEmpty() && !E7.getText().toString().trim().isEmpty()) {
                    Purchase model = new Purchase(
                            UUID.randomUUID().toString(),
                            S1.getSelectedItem().toString(),
                            T1.getText().toString(),
                            S2.getSelectedItem().toString(),
                            T2.getText().toString(),
                            Integer.parseInt(E4.getText().toString()),
                            Double.parseDouble(E5.getText().toString()),
                            E3.getText().toString(),
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
                    ((TextInputLayout) obj.getView().findViewById(R.id.layout_p_purchase_date)).setError("required!");
                    ((TextInputLayout) obj.getView().findViewById(R.id.layout_p_purchase_product_quantity)).setError("required!");
                    ((TextInputLayout) obj.getView().findViewById(R.id.layout_p_purchase_product_price)).setError("required!");
                    ((TextInputLayout) obj.getView().findViewById(R.id.layout_p_purchase_payment)).setError("required!");
                    //Snackbar.make(findViewById(android.R.id.content), "Please insert the values in your mandatory fields.", Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        });
    }

    @Override
    public void updateItem(int position, Purchase model) {
        ObjectDialog obj = showObjectDialog("Edit");

        E3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getDate(PurchaseActivity.this, E3);
            }
        });

        ArrayAdapter<String> productAdapter = Utility.getSpinnerData(new Utility.AdapterPosition() {
            @Override
            public void onPosition(int position) {
                T1.setText(mProductList.get(position).getProductId());
                E5.setText("" + mProductList.get(position).getProductPrice());
            }
        }, this, S1, pList);
        S1.setSelection(pList.indexOf(model.getProductName()));

        ArrayAdapter<String> supplierAdapter = Utility.getSpinnerData(new Utility.AdapterPosition() {
            @Override
            public void onPosition(int position) {
                T2.setText(mSupplierList.get(position).getSupplierId());
            }
        }, this, S2, sList);
        S2.setSelection(sList.indexOf(model.getSupplierName()));

        S1.setSelection(productAdapter.getPosition(model.getProductName()));
        S2.setSelection(supplierAdapter.getPosition(model.getSupplierName()));
        T1.setText(model.getProductId());
        T2.setText(model.getSupplierId());
        E3.setText(model.getPurchaseDate());
        E4.setText("" + model.getPurchaseProductQuantity());
        E5.setText("" + model.getPurchaseProductPrice());
        E6.setText("" + model.getPurchaseAmount());
        E7.setText("" + model.getPurchasePayment());
        E8.setText("" + model.getPurchaseBalance());
        E9.setText(model.getPurchaseDescription());

        ((Button) obj.getView().findViewById(R.id.p_purchase_save_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!S1.getSelectedItem().toString().trim().isEmpty() && !S2.getSelectedItem().toString().trim().isEmpty() && !E3.getText().toString().trim().isEmpty() && !E4.getText().toString().trim().isEmpty() && !E5.getText().toString().trim().isEmpty() && !E7.getText().toString().trim().isEmpty()) {
                    Purchase mModel = new Purchase(
                            model.getPurchaseId(),
                            S1.getSelectedItem().toString(),
                            T1.getText().toString(),
                            S2.getSelectedItem().toString(),
                            T2.getText().toString(),
                            Integer.parseInt(E4.getText().toString()),
                            Double.parseDouble(E5.getText().toString()),
                            E3.getText().toString(),
                            Double.parseDouble(E6.getText().toString()),
                            Double.parseDouble(E7.getText().toString()),
                            Double.parseDouble(E8.getText().toString()),
                            E9.getText().toString()
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
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_purchase, null, false);
        builder.setView(view);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.create();
        AlertDialog dialog = builder.show();
        S1 = (Spinner) view.findViewById(R.id.p_product_name);
        T1 = (TextView) view.findViewById(R.id.p_product_id);
        S2 = (Spinner) view.findViewById(R.id.p_supplier_name);
        T2 = (TextView) view.findViewById(R.id.p_supplier_id);
        E3 = (EditText) view.findViewById(R.id.p_purchase_date);
        E4 = (EditText) view.findViewById(R.id.p_purchase_product_quantity);
        E5 = (EditText) view.findViewById(R.id.p_purchase_product_price);
        E6 = (EditText) view.findViewById(R.id.p_purchase_amount);
        E7 = (EditText) view.findViewById(R.id.p_purchase_payment);
        E8 = (EditText) view.findViewById(R.id.p_purchase_balance);
        E9 = (EditText) view.findViewById(R.id.p_purchase_description);
        return new ObjectDialog(view, dialog);
    }
}
