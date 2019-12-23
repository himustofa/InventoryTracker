package com.appsit.inventorytracker.views.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.Sale;
import com.appsit.inventorytracker.utils.Utility;

import java.util.ArrayList;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.MyViewModel> {

    private String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private ArrayList<Sale> mArrayList;
    private int lastPosition = -1;
    private RecyclerItemListener mListener;

    public interface RecyclerItemListener {
        void removeItem(int position, Sale model);
        void addItem();
        void updateItem(int position, Sale model);
    }

    public SaleAdapter(Context context, ArrayList<Sale> arrayList) {
        this.mContext = context;
        this.mArrayList = arrayList;
        this.mListener = (RecyclerItemListener) context;
    }

    @NonNull
    @Override
    public MyViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_sale, parent, false);
        return new MyViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewModel holder, int position) {
        Sale model = mArrayList.get(position);

        holder.name.setText(model.getProductName());
        holder.quantity.setText("" + model.getProductQuantity());
        holder.date.setText(model.getSalesDate());
        holder.amount.setText("" + model.getSalesAmount());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.updateItem(position, model);
            }
        });

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Utility.deleteDialog(mContext).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.removeItem(position, model);
                    }
                }).show();
                return true;
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
        private TextView name, quantity, date, amount;

        public MyViewModel(@NonNull View itemView) {
            super(itemView);

            layout = (LinearLayout) itemView.findViewById(R.id.sales_list_item_id);
            name = (TextView) itemView.findViewById(R.id.sale_item_product_name);
            quantity = (TextView) itemView.findViewById(R.id.sale_item_product_quantity);
            date = (TextView) itemView.findViewById(R.id.sale_item_date);
            amount = (TextView) itemView.findViewById(R.id.sale_item_amount);
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
