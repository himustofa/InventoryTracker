package com.appsit.inventorytracker.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class AdjustmentTextWatcher implements TextWatcher {

    private EditText mEditText, first, second;

    public AdjustmentTextWatcher(EditText editText, EditText first, EditText second) {
        this.mEditText = editText;
        this.first = first;
        this.second = second;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String qty = first.getText().toString();
        String amount = second.getText().toString();
        if (qty.isEmpty()) {
            first.setText("0");
        }
        if (amount.isEmpty()) {
            second.setText("0.0");
        } else {
            second.setText(amount);
        }

        try {
            mEditText.setText(String.valueOf(Double.parseDouble(qty) * Double.parseDouble(amount)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
