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
import android.widget.Button;
import android.widget.EditText;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.Customer;
import com.appsit.inventorytracker.models.ObjectDialog;
import com.appsit.inventorytracker.viewmodels.CustomerViewModel;
import com.appsit.inventorytracker.views.adapters.CustomerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerActivity extends AppCompatActivity implements CustomerAdapter.RecyclerItemListener {

    private String TAG = this.getClass().getSimpleName();
    private ArrayList<Customer> mArrayList = new ArrayList<>();
    private CustomerAdapter mAdapter;
    private CustomerViewModel mViewModel;
    private boolean isValue = true;

    private RecyclerView mRecyclerView;
    private EditText E1, E2, E3, E4, E5, E6, E7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        mRecyclerView = (RecyclerView) findViewById(R.id.customer_recycler_view);
        mViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        mViewModel.getAllData().observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                if (isValue) {
                    mArrayList.addAll(customers);
                    isValue = false;
                    initRecyclerView();
                }
            }
        });

        ((FloatingActionButton) findViewById(R.id.customer_add_fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
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

    private void initRecyclerView() {
        mAdapter = new CustomerAdapter(CustomerActivity.this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(CustomerActivity.this));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter.notifyDataSetChanged();
    }

    //https://stackoverflow.com/questions/31367599/how-to-update-recyclerview-adapter-data
    @Override
    public void removeItem(int position, Customer model) {
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

        ((Button) obj.getView().findViewById(R.id.customer_save_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!E1.getText().toString().trim().isEmpty() && !E2.getText().toString().trim().isEmpty()) {
                    Customer model = new Customer(
                            UUID.randomUUID().toString(),
                            E1.getText().toString(),
                            E2.getText().toString(),
                            E3.getText().toString(),
                            E4.getText().toString(),
                            Double.parseDouble(E5.getText().toString()),
                            E6.getText().toString(),
                            E7.getText().toString()
                    );
                    Log.d(TAG, new Gson().toJson(model));
                    long result = mViewModel.save(model);
                    if (result > 0) {
                        mArrayList.add(model);
                        mAdapter.notifyItemInserted(mArrayList.size());
                        obj.getDialog().dismiss();
                    }
                } else {
                    ((TextInputLayout) obj.getView().findViewById(R.id.layout_customer_name)).setError("required!");
                    ((TextInputLayout) obj.getView().findViewById(R.id.layout_customer_phone_number)).setError("required!");
                    //Snackbar.make(findViewById(android.R.id.content), "Please insert the values in your mandatory fields.", Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        });
    }

    @Override
    public void updateItem(int position, Customer model) {
        ObjectDialog obj = showObjectDialog("Edit");

        E1.setText(model.getCustomerName());
        E2.setText(model.getCustomerPhoneNumber());
        E3.setText(model.getCustomerEmail());
        E4.setText(model.getCustomerContactPerson());
        E5.setText("" + model.getCustomerDiscount());
        E6.setText(model.getCustomerAddress());
        E7.setText(model.getCustomerDescription());

        ((Button) obj.getView().findViewById(R.id.customer_save_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!E1.getText().toString().trim().isEmpty() && !E4.getText().toString().trim().isEmpty()) {
                    Customer customer = new Customer(
                            model.getCustomerId(),
                            E1.getText().toString(),
                            E2.getText().toString(),
                            E3.getText().toString(),
                            E4.getText().toString(),
                            Double.parseDouble(E5.getText().toString()),
                            E6.getText().toString(),
                            E7.getText().toString()
                    );
                    long result = mViewModel.update(customer);
                    if (result > 0) {
                        //mArrayList.clear();
                        //mArrayList.addAll(viewModels);
                        mArrayList.set(position, customer);
                        mAdapter.notifyItemChanged(position, customer);
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
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_customer, null, false);
        builder.setView(view);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.create();
        AlertDialog dialog = builder.show();
        E1 = (EditText) view.findViewById(R.id.customer_name);
        E2 = (EditText) view.findViewById(R.id.customer_phone_number);
        E3 = (EditText) view.findViewById(R.id.customer_email);
        E4 = (EditText) view.findViewById(R.id.customer_contact_person);
        E5 = (EditText) view.findViewById(R.id.customer_discount);
        E6 = (EditText) view.findViewById(R.id.customer_address);
        E7 = (EditText) view.findViewById(R.id.customer_description);
        return new ObjectDialog(view, dialog);
    }
}
