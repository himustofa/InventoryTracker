package com.appsit.inventorytracker.views.adapters;

import android.content.Context;
import android.util.Log;
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
import com.appsit.inventorytracker.models.Supplier;

import java.util.ArrayList;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.MyViewModel>{

    private String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private ArrayList<Supplier> mArrayList;
    private int lastPosition = -1;
    private RecyclerItemListener mListener;

    public interface RecyclerItemListener {
        void removeItem(int position, Supplier model);
        void addItem(Supplier model);
        void updateItem(int position, Supplier model);
    }

    public SupplierAdapter(Context context, ArrayList<Supplier> arrayList) {
        this.mContext = context;
        this.mArrayList = arrayList;
        this.mListener = (RecyclerItemListener) context;
    }

    @NonNull
    @Override
    public MyViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_supplier, parent, false);
        return new MyViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewModel holder, int position) {
        Supplier model = mArrayList.get(position);
        //holder.imageView.setImageBitmap(Utility.loadFromInternalStorage(supplier.getPhotoPath(),supplier.getPhotoName()));
        holder.name.setText(model.getSupplierName());
        holder.company.setText(model.getSupplierCompanyName());
        holder.phone.setText(model.getSupplierPhoneNumber());
        holder.email.setText(model.getSupplierEmail());
        holder.address.setText(model.getSupplierAddress());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, model.getSupplierName());
            }
        });

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mListener.removeItem(position, model);
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
        private TextView name, company, phone, email, address;

        MyViewModel(@NonNull View itemView) {
            super(itemView);

            layout = (LinearLayout) itemView.findViewById(R.id.suppliers_list_item_id);
            //imageView = (CircleImageView) itemView.findViewById(R.id.supplier_item_name);
            name = (TextView) itemView.findViewById(R.id.supplier_item_name);
            company = (TextView) itemView.findViewById(R.id.supplier_item_company);
            phone = (TextView) itemView.findViewById(R.id.supplier_item_phone);
            email = (TextView) itemView.findViewById(R.id.supplier_item_email);
            address = (TextView) itemView.findViewById(R.id.supplier_item_address);
        }
    }

    /*private void removeData(Supplier model, int position) {
        mArrayList.remove(position);
		notifyItemRemoved(position);
		notifyItemRangeChanged(position, mArrayList.size());
    }*/

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
