package com.appsit.inventorytracker.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.Role;
import com.appsit.inventorytracker.models.StockSale;
import com.appsit.inventorytracker.models.User;
import com.appsit.inventorytracker.session.SharedPrefManager;
import com.appsit.inventorytracker.utils.Utility;
import com.appsit.inventorytracker.viewmodels.HomeViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = this.getClass().getSimpleName();
    private ActionBarDrawerToggle mToggle;

    private HomeViewModel mHomeViewModel;
    private User mUser;
    private BarChart barChart;

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
        //Utility.getAnimationCounter(((TextView) findViewById(R.id.counter)), 100);

        //==========================================| ViewModel
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mHomeViewModel.getPurchaseTotal().observe(this, new Observer<StockSale>() {
            @Override
            public void onChanged(StockSale myModel) {
                Log.d(TAG, "Purchase " + new Gson().toJson(myModel));
                Utility.getAnimationCounter(((TextView) findViewById(R.id.purchase_all_items)), myModel.getQuantity());
                ((TextView) findViewById(R.id.purchase_all_items_amount)).setText(myModel.getAmount() + "");
            }
        });

        mHomeViewModel.getSaleTotal().observe(this, new Observer<StockSale>() {
            @Override
            public void onChanged(StockSale myModel) {
                Log.d(TAG, "Sale " + new Gson().toJson(myModel));
                Utility.getAnimationCounter(((TextView) findViewById(R.id.sale_all_items)), myModel.getQuantity());
                ((TextView) findViewById(R.id.sale_all_items_amount)).setText(myModel.getAmount() + "");
            }
        });

        mHomeViewModel.getAdjustmentTotal().observe(this, new Observer<StockSale>() {
            @Override
            public void onChanged(StockSale myModel) {
                Log.d(TAG, "Wastage " + new Gson().toJson(myModel));
                Utility.getAnimationCounter(((TextView) findViewById(R.id.wastage_all_items)), myModel.getQuantity());
                ((TextView) findViewById(R.id.wastage_all_items_amount)).setText(myModel.getAmount() + "");
            }
        });

        mHomeViewModel.getSaleByDate(Utility.getCurrentDatPicker(HomeActivity.this)).observe(this, new Observer<StockSale>() {
            @Override
            public void onChanged(StockSale myModel) {
                Log.d(TAG, "Today " + new Gson().toJson(myModel));
                //Utility.getAnimationCounter(((TextView) findViewById(R.id.remaining_all_items)), myModel.getQuantity());
                //((TextView) findViewById(R.id.remaining_all_items_amount)).setText(myModel.getAmount() + " tk");
                ((TextView) findViewById(R.id.today_total_sale_amount)).setText(myModel.getAmount() + "");
            }
        });

        barChart();
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
            case R.id.users_id:
                if (mUser.getRole().equals(String.valueOf(Role.ADMIN_USER))) {
                    startActivity(new Intent(HomeActivity.this, UserActivity.class));
                } else {
                    Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.msg_admin_user), Snackbar.LENGTH_LONG).show();
                }
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

    //=====================================================| Bar Chart
    public void barChart() {
        barChart = (BarChart) findViewById(R.id.bar_chart); //Vertical chart
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(150);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        //barChart.getXAxis().setTextSize(35f);
        //barChart.getAxisLeft().setTextSize(35f);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        //barChart.getXAxis().setEnabled(false);
        barChart.getAxisLeft().setDrawAxisLine(false);

        ArrayList<BarEntry> barList = new ArrayList<>();

        String[] arr = new String[] {Utility.getDatePlus(this, -4), Utility.getDatePlus(this, -3), Utility.getDatePlus(this, -2), Utility.getDatePlus(this, -1), Utility.getCurrentDatPicker(this)};
        for (int i=0; i<arr.length; i++) {
            getData(arr[i], barList, i);
        }

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                //return arr[(int)value];
                return arr[(int)value].substring(0, arr[(int)value].length() - 5);
            }
        });
    }

    private void getData(String date, ArrayList<BarEntry> barList, int i) {
        mHomeViewModel.getSaleByDate(date).observe(this, new Observer<StockSale>() {
            @Override
            public void onChanged(StockSale myModel) {
                barList.add(new BarEntry(i, (float) myModel.getAmount()));
                BarDataSet barDataSet = new BarDataSet(barList, "Five days sales amount in BDT");
                barDataSet.setColors(Color.parseColor("#FFFFC107"));

                //------------------------------For single bar
                BarData bData = new BarData(barDataSet);
                bData.setBarWidth(0.5f);
                barChart.setData(bData);
                barChart.getBarData().setValueTextColor(Color.parseColor("#444444"));
                barChart.getBarData().setValueTextSize(25f);
                //---------------------------------------------

            }
        });
    }
}
