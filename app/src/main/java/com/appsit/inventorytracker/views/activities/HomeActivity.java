package com.appsit.inventorytracker.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.Role;
import com.appsit.inventorytracker.models.User;
import com.appsit.inventorytracker.session.SharedPrefManager;
import com.appsit.inventorytracker.utils.Utility;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = this.getClass().getSimpleName();
    private ActionBarDrawerToggle mToggle;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mUser = SharedPrefManager.getInstance(this).getUser();

        //==========================================| findViewById
        ((TextView) findViewById(R.id.log_out)).setOnClickListener(new ActionHandler());
        //((TextView) findViewById(R.id.log_out)).setOnLongClickListener(new ActionHandler());
        ((TextView) findViewById(R.id.about_id)).setOnClickListener(new ActionHandler());

        //====================================================| To Display Navigation Bar Icon and Back
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false); //Remove title
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            //toolbar.getBackground().setAlpha(200);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(mToggle);
        mToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        if (mUser != null) {
            if (mUser.getRole().equals(String.valueOf(Role.ADMIN_USER))) {
                ((CircleImageView) hView.findViewById(R.id.nav_header_photo)).setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ic_log_mk));
            } else {
                ((CircleImageView) hView.findViewById(R.id.nav_header_photo)).setImageBitmap(Utility.loadFromInternalStorage(mUser.getPhotoPath(), mUser.getPhotoName()));
            }
            ((TextView) hView.findViewById(R.id.user_full_name)).setText(mUser.getFullName());
            ((TextView) hView.findViewById(R.id.user_email)).setText(mUser.getEmail());
        }

        //==========================================| ValueAnimator
        int count = 100;
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, count);// here you set the range, from 0 to "count" value
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                ((TextView) findViewById(R.id.counter)).setText(String.valueOf(animation.getAnimatedValue()));
            }
        });
        animator.setDuration(1000); // here you set the duration of the anim
        animator.start();
    }

    //====================================================| Button events
    private class ActionHandler implements View.OnClickListener, View.OnLongClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.about_id) {
                Utility.aboutMe(HomeActivity.this);
                //startActivity(new Intent(getApplicationContext(), PostsListActivity.class));
            }
            if (v.getId() == R.id.log_out) {
                signOut();
            }
        }
        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }

    private void signOut() {
        new AlertDialog.Builder(HomeActivity.this)
                .setTitle(R.string.about_title)
                .setMessage(R.string.msg_sign_out)
                .setPositiveButton(R.string.sign_out, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        SharedPrefManager.getInstance(HomeActivity.this).saveLogInStatus(false);
                        Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //For login to clear this screen for that did not back this screen
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.msg_neg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                }).show();
    }

    //====================================================| onBackPressed in Background and OptionsMenu
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()) {
            case R.id.settings_id:
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                break;
            case R.id.about_id:
                Utility.aboutMe(HomeActivity.this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.suppliers_id:
                startActivity(new Intent(HomeActivity.this, SupplierActivity.class));
                break;
            case R.id.products_id:
                startActivity(new Intent(HomeActivity.this, ProductActivity.class));
                break;
            case R.id.purchases_id:
                startActivity(new Intent(HomeActivity.this, PurchaseActivity.class));
                break;
            case R.id.customers_id:
                startActivity(new Intent(HomeActivity.this, CustomerActivity.class));
                break;
            case R.id.sales_id:
                startActivity(new Intent(HomeActivity.this, SaleActivity.class));
                break;
            case R.id.stocks_id:
                startActivity(new Intent(HomeActivity.this, StockActivity.class));
                break;
            case R.id.adjustments_id:
                startActivity(new Intent(HomeActivity.this, AdjustmentActivity.class));
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
