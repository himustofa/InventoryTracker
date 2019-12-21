package com.appsit.inventorytracker.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MyTextWatcher implements TextWatcher {

    private EditText mEditText, first, second;
    private boolean isPay;

    public MyTextWatcher(EditText editText, EditText first, EditText second, boolean isPay) {
        this.mEditText = editText;
        this.first = first;
        this.second = second;
        this.isPay = isPay;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isPay) {
            //
        } else {
            if (first.getText().toString().isEmpty()) {
                first.setText("0");
            }
            if (second.getText().toString().isEmpty()) {
                second.setText("0.0");
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
            if (isPay) {
                mEditText.setText(String.valueOf(Double.parseDouble(first.getText().toString()) - Double.parseDouble(second.getText().toString())));
            } else {
                mEditText.setText(String.valueOf(Double.parseDouble(first.getText().toString()) * Double.parseDouble(second.getText().toString())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
