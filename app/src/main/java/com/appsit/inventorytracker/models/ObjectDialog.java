package com.appsit.inventorytracker.models;

import android.view.View;

import androidx.appcompat.app.AlertDialog;

public class ObjectDialog {

    private View view;
    private AlertDialog dialog;

    public ObjectDialog(View view, AlertDialog dialog) {
        this.view = view;
        this.dialog = dialog;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public AlertDialog getDialog() {
        return dialog;
    }

    public void setDialog(AlertDialog dialog) {
        this.dialog = dialog;
    }
}
