package com.appsit.inventorytracker.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class SalePayTextWatcher implements TextWatcher {

    private String TAG = this.getClass().getSimpleName();
    private EditText total, payment, balance;

    public SalePayTextWatcher(EditText total, EditText payment, EditText balance) {
        this.total = total;
        this.payment = payment;
        this.balance = balance;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String tol = total.getText().toString();
        String pay = payment.getText().toString();
        String bal = balance.getText().toString();
        if (tol.isEmpty()) {
            payment.setText("0.0");
            balance.setText("0.0");
        } else if (pay.isEmpty()) {
            payment.setText("0.0");
            balance.setText("" + tol);
        } else {
            balance.setText("" + (Double.parseDouble(tol) - Double.parseDouble(pay)));
        }
    }
}
