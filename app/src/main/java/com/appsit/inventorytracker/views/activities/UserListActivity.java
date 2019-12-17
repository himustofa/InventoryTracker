package com.appsit.inventorytracker.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.User;
import com.appsit.inventorytracker.viewmodels.UserViewModel;
import com.appsit.inventorytracker.views.adapters.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    private UserViewModel mViewModel;
    private ArrayList<User> mArrayList = new ArrayList<>();
    private UserAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.user_recycler_view);

        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                mArrayList.addAll(users);
                mAdapter = new UserAdapter(UserListActivity.this, mArrayList);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(UserListActivity.this));
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
