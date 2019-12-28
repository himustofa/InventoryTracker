package com.appsit.inventorytracker.views.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.User;
import com.appsit.inventorytracker.session.SharedPrefManager;
import com.appsit.inventorytracker.utils.Utility;
import com.appsit.inventorytracker.viewmodels.UserViewModel;
import com.appsit.inventorytracker.views.adapters.UserAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity implements UserAdapter.RecyclerItemListener {

    private UserViewModel mViewModel;
    private ArrayList<User> mArrayList = new ArrayList<>();
    private UserAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private boolean isValue = true;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mRecyclerView = (RecyclerView) findViewById(R.id.user_recycler_view);
        mUser = SharedPrefManager.getInstance(this).getUser();

        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (isValue) {
                    mArrayList.addAll(users);
                    isValue = false;
                    mAdapter = new UserAdapter(UserActivity.this, mArrayList);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(UserActivity.this));
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void removeItem(int position, User model) {
        long result = mViewModel.delete(model);
        if (result > 0) {
            mArrayList.remove(position);
            mRecyclerView.removeViewAt(position);
            mAdapter.notifyItemRemoved(position);
            mAdapter.notifyItemRangeChanged(position, mArrayList.size());
        }
    }

    @Override
    public void updateItem(int position, User model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_user, null, false);
        builder.setView(view);
        builder.setCancelable(true);
        builder.create();
        AlertDialog dialog = builder.show();

        CircleImageView image = (CircleImageView) view.findViewById(R.id.user_image);
        Spinner role = (Spinner) view.findViewById(R.id.user_role);
        EditText name = (EditText) view.findViewById(R.id.full_name);
        EditText design = (EditText) view.findViewById(R.id.designation);
        EditText email = (EditText) view.findViewById(R.id.email);
        EditText phone = (EditText) view.findViewById(R.id.phone_number);
        EditText username = (EditText) view.findViewById(R.id.username);
        EditText pass = (EditText) view.findViewById(R.id.change_pass);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.roles));
        role.setAdapter(adapter);
        role.setSelection(adapter.getPosition(model.getRole()));

        if (model.getPhotoName() != null) {
            image.setImageBitmap(Utility.loadFromInternalStorage(model.getPhotoPath(), model.getPhotoName()));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                image.setImageDrawable(getResources().getDrawable(R.drawable.ic_log_mk, getApplicationContext().getTheme()));
            } else {
                image.setImageDrawable(getResources().getDrawable(R.drawable.ic_log_mk));
            }
        }

        name.setText(model.getFullName());
        design.setText(model.getDesignation());
        email.setText(model.getEmail());
        phone.setText(model.getPhoneNumber());
        username.setText(model.getUsername());
        pass.setText(model.getPassword());

        ((Button) view.findViewById(R.id.user_update)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().trim().isEmpty() && !username.getText().toString().trim().isEmpty() && !pass.getText().toString().trim().isEmpty()) {

                    User user = new User();
                    user.setUserId(model.getUserId());
                    user.setRole(role.getSelectedItem().toString());
                    user.setFullName(name.getText().toString());
                    user.setDesignation(design.getText().toString());
                    user.setEmail(email.getText().toString());
                    user.setPhoneNumber(phone.getText().toString());
                    user.setUsername(username.getText().toString());
                    user.setPassword(pass.getText().toString());
                    user.setPhotoName(model.getPhotoName());
                    user.setPhotoPath(model.getPhotoPath());

                    if (mUser.getUserId().equals(user.getUserId())) {
                        SharedPrefManager.getInstance(UserActivity.this).saveUser(user);
                    }
                    long result = mViewModel.update(user);
                    if (result > 0) {
                        //mArrayList.clear();
                        //mArrayList.addAll(viewModels);
                        mArrayList.set(position, user);
                        mAdapter.notifyItemChanged(position, user);
                        //mAdapter.notifyDataSetChanged(); //recyclerView.invalidate();
                        dialog.dismiss();
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Please insert the values in your mandatory fields (Name, Username, Password).", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
