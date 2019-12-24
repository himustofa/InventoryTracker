package com.appsit.inventorytracker.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.Stock;

import java.util.ArrayList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.MyViewModel>{

    private String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private ArrayList<Stock> mArrayList;
    private int lastPosition = -1;

    public StockAdapter(Context context, ArrayList<Stock> arrayList) {
        this.mContext = context;
        this.mArrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_stock, parent, false);
        return new MyViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewModel holder, int position) {
        Stock model = mArrayList.get(position);

        holder.product.setText(model.getProductName());
        holder.quantity.setText(model.getStockQuantity() + " Pieces");
        holder.amount.setText(model.getStockAmount() + " tk");

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_stock, null, false);
                builder.setView(view);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("Stock");
                builder.setCancelable(true);
                builder.create();
                AlertDialog dialog = builder.show();
            }
        });

        // Here you apply the animation when the view is bound
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class MyViewModel extends RecyclerView.ViewHolder {

        private LinearLayout layout;
        private TextView product, quantity, amount;

        public MyViewModel(@NonNull View itemView) {
            super(itemView);

            layout = (LinearLayout) itemView.findViewById(R.id.stock_list_item_id);
            product = (TextView) itemView.findViewById(R.id.stock_product_name);
            quantity = (TextView) itemView.findViewById(R.id.stock_quantity);
            amount = (TextView) itemView.findViewById(R.id.stock_amount);
        }
    }

    // Here is the key method to apply the animation
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
