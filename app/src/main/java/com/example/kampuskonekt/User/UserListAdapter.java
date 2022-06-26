package com.example.kampuskonekt.User;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kampuskonekt.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {
    ArrayList<UserObject> userlist;

    public UserListAdapter(ArrayList<UserObject> userlist) {
      this.userlist=userlist;
    }

    @NonNull
    @NotNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_layout,null,false);
        //RecyclerView.LayoutParams lp=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT);
       // view.setLayoutParams(lp);
        UserListViewHolder userlistview=new UserListViewHolder(view);
        return userlistview;
    }

    @Override
    public long getItemId(int position) {
        //return super.getItemId(position);
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
    return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull @NotNull UserListAdapter.UserListViewHolder holder, int position) {
       //  String ima=userlist.get(position).getUserprofilephoto().toString();
       // Picasso.get().load(userlist.get(position).getUserprofilephoto().toString()).placeholder(R.drawable.ic_person).into(holder.userprofileimage);
        //Log.d("imageuriii",ima);

        holder.tvcntactname.setText(userlist.get(position).getUsername());
         holder.tvcontactphone.setText(userlist.get(position).getUsermobile());
        Picasso.get().load(String.valueOf((CircleImageView) userlist.get(position).getUserprofilephoto())).placeholder(R.drawable.ic_person).into(holder.userprofileimage);
//        Picasso.get().load(String.valueOf((CircleImageView) userlist.get(position).getUserprofilephoto())).placeholder(R.drawable.ic_person).into(holder.userprofileimage);
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class UserListViewHolder extends RecyclerView.ViewHolder {
        public TextView tvcntactname,tvcontactphone;
        public CircleImageView  userprofileimage;

        public UserListViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvcontactphone=itemView.findViewById(R.id.txtContactphone);
            tvcntactname=itemView.findViewById(R.id.txtContactName);
            userprofileimage=itemView.findViewById(R.id.imgContactUserInfo);

        }
    }
}
