package com.appsit.inventorytracker.views.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsit.inventorytracker.R;
import com.appsit.inventorytracker.models.Role;
import com.appsit.inventorytracker.models.User;
import com.appsit.inventorytracker.session.SharedPrefManager;
import com.appsit.inventorytracker.utils.Utility;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewModel>{

    private String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private ArrayList<User> mArrayList;
    private User mUser;

    private RecyclerItemListener mListener;

    public interface RecyclerItemListener {
        void removeItem(int position, User model);
        void updateItem(int position, User model);
    }

    public UserAdapter(Context context, ArrayList<User> arrayList) {
        this.mContext = context;
        this.mArrayList = arrayList;
        this.mListener = (RecyclerItemListener) context;
        this.mUser = SharedPrefManager.getInstance(context).getUser();
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
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_log_mk, mContext.getApplicationContext().getTheme()));
            } else {
                holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_log_mk));
            }
        }
        holder.userFullName.setText(user.getFullName());
        holder.userName.setText(user.getUsername());
        holder.designation.setText(user.getDesignation());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUser.getRole().equals(String.valueOf(Role.ADMIN_USER))) {
                    mListener.updateItem(position, user);
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
                            mListener.removeItem(position, user);
                        }
                    }
                }).show();
                return true;
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
