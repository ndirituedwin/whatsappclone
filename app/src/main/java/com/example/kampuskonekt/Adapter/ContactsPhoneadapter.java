package com.example.kampuskonekt.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.databinding.DataBindingUtil;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.kampuskonekt.R;
//import com.example.kampuskonekt.UserModel;
//import com.example.kampuskonekt.databinding.ContactItemLayoutBinding;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//
//public class ContactsPhoneadapter extends RecyclerView.Adapter<ContactsPhoneadapter.ViehHolder> {
//
////    private Context  context;
////    private ArrayList<UserModel> arrayList;
////    private ContactItemLayoutBinding  binding;
////
////    public ContactsPhoneadapter(Context context, ArrayList<UserModel> arrayList) {
////        this.context = context;
////        this.arrayList = arrayList;
////    }
////
////    @NonNull
////    @NotNull
////    @Override
////    public ViehHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
////    binding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.contact_item_layout,parent,false);
////
////        return new ViehHolder(binding);
////    }
////
////    @Override
////    public void onBindViewHolder(@NonNull @NotNull ContactsPhoneadapter.ViehHolder holder, int position) {
////   UserModel user= arrayList.get(position);
////   holder.layoutBinding.setUserModel(user);
////    }
////
////    @Override
////    public int getItemCount() {
////        return arrayList.size();
////    }
////
////    public class ViehHolder extends RecyclerView.ViewHolder {
////        ContactItemLayoutBinding layoutBinding;
////
////        public ViehHolder(ContactItemLayoutBinding layoutBinding) {
////            super(layoutBinding.getRoot());
////            this.layoutBinding=layoutBinding;
////        }
////    }
//}
