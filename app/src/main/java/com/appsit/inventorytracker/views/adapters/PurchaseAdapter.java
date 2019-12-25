package com.appsit.inventorytracker.views.adapters;

import android.app.Activity;
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
import com.appsit.inventorytracker.models.Purchase;
import com.appsit.inventorytracker.models.Role;
import com.appsit.inventorytracker.models.User;
import com.appsit.inventorytracker.session.SharedPrefManager;
import com.appsit.inventorytracker.utils.Utility;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.MyViewModel>{

    private String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private ArrayList<Purchase> mArrayList;
    private int lastPosition = -1;
    private RecyclerItemListener mListener;
    private User mUser;

    public interface RecyclerItemListener {
        void removeItem(int position, Purchase model);
        void addItem();
        void updateItem(int position, Purchase model);
    }

    public PurchaseAdapter(Context context, ArrayList<Purchase> arrayList) {
        this.mContext = context;
        this.mArrayList = arrayList;
        this.mListener = (RecyclerItemListener) context;
        this.mUser = SharedPrefManager.getInstance(context).getUser();
    }

    @NonNull
    @Override
    public MyViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_purchase, parent, false);
        return new MyViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewModel holder, int position) {
        Purchase model = mArrayList.get(position);

        holder.productName.setText(model.getProductName());
        holder.supplierName.setText(model.getSupplierName());
        holder.date.setText(model.getPurchaseDate());
        holder.quantity.setText("" + model.getPurchaseProductQuantity());
        holder.amount.setText("" + model.getPurchaseAmount());
        holder.payment.setText("" + model.getPurchasePayment());
        holder.balance.setText("" + model.getPurchaseBalance());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUser.getRole().equals(String.valueOf(Role.ADMIN_USER))) {
                    mListener.updateItem(position, model);
                } else {
                    Snackbar.make(((Activity)mContext).findViewById(android.R.id.content), mContext.getString(R.string.msg_admin_user), Snackbar.LENGTH_INDEFINITE);
                }
            }
        });

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Utility.deleteDialog(mContext).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mUser.getRole().equals(String.valueOf(Role.ADMIN_USER))) {
                            mListener.removeItem(position, model);
                        } else {
                            Snackbar.make(((Activity)mContext).findViewById(android.R.id.content), mContext.getString(R.string.msg_admin_user), Snackbar.LENGTH_INDEFINITE);
                        }
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

    class MyViewModel extends RecyclerView.ViewHolder{
        private LinearLayout layout;
        //private CircleImageView imageView;
        private TextView productName, supplierName, date, quantity, amount, payment, balance;

        MyViewModel(@NonNull View itemView) {
            super(itemView);

            layout = (LinearLayout) itemView.findViewById(R.id.purchases_list_item_id);
            //imageView = (CircleImageView) itemView.findViewById(R.id.supplier_item_name);
            productName = (TextView) itemView.findViewById(R.id.product_name_pur);
            supplierName = (TextView) itemView.findViewById(R.id.supplier_name_pur);
            date = (TextView) itemView.findViewById(R.id.purchase_date_pur);
            quantity = (TextView) itemView.findViewById(R.id.purchase_product_quantity_pur);
            amount = (TextView) itemView.findViewById(R.id.purchase_amount_pur);
            payment = (TextView) itemView.findViewById(R.id.purchase_payment_pur);
            balance = (TextView) itemView.findViewById(R.id.purchase_balance_pur);
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
