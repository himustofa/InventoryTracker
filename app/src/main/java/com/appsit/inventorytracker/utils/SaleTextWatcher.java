package com.appsit.inventorytracker.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class SaleTextWatcher implements TextWatcher {

    private String TAG = this.getClass().getSimpleName();
    private EditText mEditText, first;
    private double perProductPrice;

    public SaleTextWatcher(EditText editText, EditText first, double price) {
        this.mEditText = editText;
        this.first = first;
        this.perProductPrice = price;
        Log.d(TAG, "" + perProductPrice);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.d(TAG, "" + perProductPrice);
        try {
            mEditText.setText( String.valueOf( Double.parseDouble(first.getText().toString()) * perProductPrice ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
