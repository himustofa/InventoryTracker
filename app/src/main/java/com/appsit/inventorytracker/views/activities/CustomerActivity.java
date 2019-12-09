package com.appsit.inventorytracker.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.appsit.inventorytracker.R;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        /*
        ((Spinner) findViewById(R.id.customer_id)).setOnItemSelectedListener(new ActionHandler());
        ((Button) findViewById(R.id.submit_button)).setOnClickListener(new ActionHandler());
        ((Button) findViewById(R.id.display_button)).setOnClickListener(new ActionHandler());
        ((Button) findViewById(R.id.update_button)).setOnClickListener(new ActionHandler());
        ((Button) findViewById(R.id.delete_button)).setOnClickListener(new ActionHandler());
        */
    }

    /*
    //===============================================| Click Events
    private class ActionHandler implements View.OnClickListener, AdapterView.OnItemSelectedListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.submit_button:
                    String name = customerName.getText().toString().trim();
                    String phone = customerPhoneNumber.getText().toString().trim();
                    String desc = customerDescription.getText().toString().trim();
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(desc)) {
                        saveData(name, phone, desc);
                    }
                    break;
                case R.id.display_button:
                    String mId = customerId.getSelectedItem().toString();
                    if (!TextUtils.isEmpty(mId)) {
                        getData(mId);
                    }
                    break;
                case R.id.update_button:
                    String id = customerId.getSelectedItem().toString();
                    String nameD = customerNameDisplay.getText().toString().trim();
                    String phoneD = customerPhoneNumberDisplay.getText().toString().trim();
                    String descD = customerDescriptionDisplay.getText().toString().trim();
                    if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(nameD) && !TextUtils.isEmpty(phoneD) && !TextUtils.isEmpty(descD)) {
                        updateData(id, nameD, phoneD, descD);
                    }
                    break;
                case R.id.delete_button:
                    String i = customerId.getSelectedItem().toString();
                    String n = customerNameDisplay.getText().toString().trim();
                    String p = customerPhoneNumberDisplay.getText().toString().trim();
                    String d = customerDescriptionDisplay.getText().toString().trim();
                    if (!TextUtils.isEmpty(i) && !TextUtils.isEmpty(n) && !TextUtils.isEmpty(p) && !TextUtils.isEmpty(d)) {
                        deleteData(i, n, p, d);
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Object item = adapterView.getItemAtPosition(i);
            if (item != null) {
                getData(item.toString());
                //Toast.makeText(CustomerActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}
    }

    //===============================================| Save Data
    private void saveData(String name, String phone, String desc) {
        final String id = UUID.randomUUID().toString();
        Customer model = new Customer();
        model.setCustomerId(id);
        model.setCustomerName(name);
        model.setCustomerPhoneNumber(phone);
        model.setCustomerDescription(desc);
        long data = mViewModel.saveData(model);
        if (data > 0){
            clearInputFields();
            Toast.makeText(this,"Inserted successfully", Toast.LENGTH_SHORT).show();
        }
    }

    //===============================================| Get Data
    private void getData(String mId) {
        LiveData<Customer> model = mViewModel.getById(mId);
        model.observe(this, new Observer<Customer>() {
            @Override
            public void onChanged(Customer customer) {
                if (customer != null) {
                    Log.d(TAG, customer.getCustomerName() + " got the object successfully");
                    customerNameDisplay.setText(customer.getCustomerName());
                    customerPhoneNumberDisplay.setText(customer.getCustomerPhoneNumber());
                    customerDescriptionDisplay.setText(customer.getCustomerDescription());
                }
            }
        });
    }

    //===============================================| Get Data
    private void getAllData() {
        ArrayList<String> arrayList = new ArrayList<>();
        LiveData<List<Customer>> model = mViewModel.getAllData();
        model.observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                arrayList.clear();
                for(Customer obj : customers){
                    arrayList.add(obj.getCustomerId());
                    adapter.notifyDataSetChanged();
                }
                Log.d(TAG, new Gson().toJson(customers));
            }
        });

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerId.setAdapter(adapter);
    }

    //===============================================| Update Data
    private void updateData(String id, String name, String phone, String desc) {
        Customer model = new Customer();
        model.setCustomerId(id);
        model.setCustomerName(name);
        model.setCustomerPhoneNumber(phone);
        model.setCustomerDescription(desc);
        int data = mViewModel.updateData(model);
        if (data > 0){
            Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    //===============================================| Delete Data
    private void deleteData(String id, String name, String phone, String desc) {
        Customer model = new Customer();
        model.setCustomerId(id);
        model.setCustomerName(name);
        model.setCustomerPhoneNumber(phone);
        model.setCustomerDescription(desc);
        int data = mViewModel.deleteData(model); //delete row
        Log.d(TAG, ""+data);
        if(data > 0){
            Toast.makeText(this, "Delete row successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearInputFields() {
        customerName.getText().clear();
        customerPhoneNumber.getText().clear();
        customerDescription.getText().clear();
    }
    */
}
