package com.appsit.inventorytracker.views.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.User;
import com.appsit.inventorytracker.utils.Utility;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewModel>{

    private String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private ArrayList<User> mArrayList;

    public UserAdapter(Context context, ArrayList<User> arrayList) {
        this.mContext = context;
        this.mArrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_user, parent, false);
        return new MyViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewModel holder, int position) {
        User user = mArrayList.get(position);
        if (user.getPhotoName() != null) {
            holder.imageView.setImageBitmap(Utility.loadFromInternalStorage(user.getPhotoPath(), user.getPhotoName()));
        }
        holder.userFullName.setText(user.getFullName());
        holder.userName.setText(user.getUsername());
        holder.designation.setText(user.getDesignation());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, user.getPhotoPath()+user.getPhotoName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    class MyViewModel extends RecyclerView.ViewHolder{
        private RelativeLayout layout;
        private CircleImageView imageView;
        private TextView userFullName, userName, designation;

        MyViewModel(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.user_item_id);
            imageView = itemView.findViewById(R.id.user_item_image);
            userFullName = itemView.findViewById(R.id.user_item_full_name);
            userName = itemView.findViewById(R.id.user_item_username);
            designation = itemView.findViewById(R.id.user_item_designation);
        }
    }
}
