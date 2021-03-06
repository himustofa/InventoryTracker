package com.appsit.inventorytracker.views.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.Customer;
import com.appsit.inventorytracker.utils.Utility;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewModel> implements Filterable {

    private String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private ArrayList<Customer> mArrayList;
    private ArrayList<Customer> mArrayList1;
    private int lastPosition = -1;
    private RecyclerItemListener mListener;

    public interface RecyclerItemListener {
        void removeItem(int position, Customer model);
        void addItem();
        void updateItem(int position, Customer model);
    }

    public CustomerAdapter(Context context, ArrayList<Customer> arrayList) {
        this.mContext = context;
        this.mArrayList = arrayList;
        this.mArrayList1 = arrayList;
        this.mListener = (RecyclerItemListener) context;
    }

    @NonNull
    @Override
    public MyViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_customer, parent, false);
        return new MyViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewModel holder, int position) {
        Customer model = mArrayList.get(position);

        holder.name.setText(model.getCustomerName());
        holder.phone.setText(model.getCustomerPhoneNumber());
        holder.email.setText(model.getCustomerEmail());
        holder.discount.setText("" + model.getCustomerDiscount());

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
        private TextView name, phone, email, discount;

        public MyViewModel(@NonNull View itemView) {
            super(itemView);

            layout = (LinearLayout) itemView.findViewById(R.id.customers_list_item_id);
            name = (TextView) itemView.findViewById(R.id.customer_name_cust);
            phone = (TextView) itemView.findViewById(R.id.customer_phone_number_cust);
            email = (TextView) itemView.findViewById(R.id.customer_email_cust);
            discount = (TextView) itemView.findViewById(R.id.customer_discount_cust);
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

    //http://programmingroot.com/android-recyclerview-search-filter-tutorial-with-example/
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()){
                    mArrayList = mArrayList1;
                } else {
                    ArrayList<Customer> filterList = new ArrayList<>();
                    for (Customer data : mArrayList1){
                        if (data.getCustomerName().toLowerCase().contains(charString)){
                            filterList.add(data);
                        }
                    }
                    mArrayList = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mArrayList = (ArrayList<Customer>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
