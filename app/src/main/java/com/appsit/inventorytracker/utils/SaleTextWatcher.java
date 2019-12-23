package com.appsit.inventorytracker.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class SaleTextWatcher implements TextWatcher {

    private String TAG = this.getClass().getSimpleName();
    private EditText mEditText, productQty, discount, vat;
    private double perProductPrice;

    public SaleTextWatcher(EditText editText, EditText productQty, double price, EditText discount, EditText vat) {
        this.mEditText = editText;
        this.productQty = productQty;
        this.perProductPrice = price;
        this.discount = discount;
        this.vat = vat;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        /*
        total = (productQty * productPrice);
        vatAmount = (total * vat)/100;
        totalAmount = (total + vatAmount) - discount;
        */

        String qty = productQty.getText().toString();
        String dis = discount.getText().toString();
        String v = vat.getText().toString();
        if (qty.isEmpty()) {
            mEditText.setText("0.0");
        } else if(dis.isEmpty()) {
            discount.setText("0.0");
        } else if(v.isEmpty()) {
            vat.setText("0.0");
        } else {
            double total = (Double.parseDouble(qty) * perProductPrice);
            double vatAmount = (total * Double.parseDouble(v)) / 100;
            double d = (total * Double.parseDouble(dis)) / 100;
            double totalAmount = (total + vatAmount) - d;
            mEditText.setText("" + totalAmount);
        }
    }
}
