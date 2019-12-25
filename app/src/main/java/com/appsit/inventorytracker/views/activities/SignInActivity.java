package com.appsit.inventorytracker.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.User;
import com.appsit.inventorytracker.session.SharedPrefManager;
import com.appsit.inventorytracker.utils.Utility;
import com.appsit.inventorytracker.viewmodels.UserViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private ProgressDialog mProgress;
    private UserViewModel mUserViewModel;
    private EditText userName, userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        userName = (EditText) findViewById(R.id.login_username);
        userPass = (EditText) findViewById(R.id.login_password);

        ((Button) findViewById(R.id.login_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = userName.getText().toString();
                String p = userPass.getText().toString();
                if (!u.isEmpty() && !p.isEmpty()) {
                    mProgress = Utility.showProgressDialog(SignInActivity.this, getResources().getString(R.string.progress), false);
                    try {
                        getData(u, p);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        Utility.alertDialog(SignInActivity.this, e.getMessage());
                    }
                } else {
                    Utility.alertDialog(SignInActivity.this, "Please enter username and password");
                }
            }
        });

        ((TextView) findViewById(R.id.for_registration)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        isLoggedIn();
    }

    //===============================================| Get Data
    private void getData(String userName, String userPass) throws UnsupportedEncodingException {
        LiveData<User> model = mUserViewModel.getUserByUserAndPass(userName, Utility.encode(userPass));
        model.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.d(TAG, new Gson().toJson(user));
                if (user != null) {
                    SharedPrefManager.getInstance(SignInActivity.this).saveLogInStatus(true);
                    SharedPrefManager.getInstance(SignInActivity.this).saveUser(user);
                    Toast.makeText(SignInActivity.this, "Login successfully, " + user.getFullName(), Toast.LENGTH_SHORT).show();
                    Utility.dismissProgressDialog(mProgress);

                    Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //For login to clear this screen for that did not back this screen
                    startActivity(intent);
                    finish();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Please enter your valid username and password", Snackbar.LENGTH_INDEFINITE).show();
                    Utility.dismissProgressDialog(mProgress);
                }
            }
        });
    }

    //===============================================| Check the login session
    private void isLoggedIn() {
        boolean isLoggedIn = SharedPrefManager.getInstance(SignInActivity.this).getLogInStatus();
        if (isLoggedIn) {
            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //For login to clear this screen for that did not back this screen
            startActivity(intent);
            finish();
        }
    }
}
