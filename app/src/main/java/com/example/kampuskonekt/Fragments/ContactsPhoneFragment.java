//package com.example.kampuskonekt.Fragments;
//
//import android.app.Fragment;
//import android.content.ContentResolver;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.os.Build;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import android.provider.ContactsContract;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import com.example.kampuskonekt.Constants.constants;
//import com.example.kampuskonekt.Permissions.permissions;
//import com.example.kampuskonekt.UserModel;
//import com.example.kampuskonekt.databinding.FragmentContactsPhoneBinding;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
////
/////**
//// * A simple {@link Fragment} subclass.
//// * Use the {@link ContactsPhoneFragment#newInstance} factory method to
//// * create an instance of this fragment.
//// */
//public class ContactsPhoneFragment extends Fragment {
//    private FragmentContactsPhoneBinding binding;
//    private DatabaseReference databaseReference;
//    private permissions permissions;
//    private ArrayList<UserModel> usercontacts,appcontacts;
////    // TODO: Rename parameter arguments, choose names that match
////    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
////    private static final String ARG_PARAM1 = "param1";
////    private static final String ARG_PARAM2 = "param2";
////
////    // TODO: Rename and change types of parameters
////    private String mParam1;
////    private String mParam2;
//
//    public ContactsPhoneFragment() {
//        // Required empty public constructor
//    }
//
////    /**
////     * Use this factory method to create a new instance of
////     * this fragment using the provided parameters.
////     *
////     * @param param1 Parameter 1.
////     * @param param2 Parameter 2.
////     * @return A new instance of fragment ContactsPhoneFragment.
////     */
////    // TODO: Rename and change types and number of parameters
////    public static ContactsPhoneFragment newInstance(String param1, String param2) {
////        ContactsPhoneFragment fragment = new ContactsPhoneFragment();
////        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
////        fragment.setArguments(args);
////        return fragment;
////    }
////
////    @Override
////    public void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        if (getArguments() != null) {
////            mParam1 = getArguments().getString(ARG_PARAM1);
////            mParam2 = getArguments().getString(ARG_PARAM2);
////        }
////    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//      //  return inflater.inflate(R.layout.fragment_contacts_phone, container, false);
//        binding=FragmentContactsPhoneBinding.inflate(inflater, container, false);
//        View view=binding.getRoot();
//        permissions=new permissions();
//        usercontacts=new ArrayList<>();
//        appcontacts=new ArrayList<>();
//        binding.recyclerViewContact.setLayoutManager(new LinearLayoutManager(getContext()));
//        binding.recyclerViewContact.setHasFixedSize(true);
//        return view;
//    }
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void getUserContacts(){
//           if (permissions.iscontactok(getContext())){
//                String[] projection=new String[]{
//                        ContactsContract.Contacts.DISPLAY_NAME,
//                        ContactsContract.CommonDataKinds.Phone.NUMBER
//                };
//               ContentResolver cr=getActivity().getContentResolver();
//               Cursor cursor=cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,projection,null,null,null);
//               if (cursor !=null){
//                   while (cursor.moveToFirst()){
//                       String name= cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                       String  number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//                       number=number.replace("\\s","");
//                       String num=String.valueOf(number.charAt(0));
//                       if (num.equals("0"))
//                           num=number.replace("(?:0)+","+254");
//
//                          UserModel userModel=new UserModel();
//                          userModel.setUsername(name);
//                          userModel.setUsermobile(number);
//                          usercontacts.add(userModel);
//                   }
//                   cursor.close();
//                   getAppcontacts(usercontacts);
//               }
//           }else{
//               permissions.requestContact(getActivity());
//           }
//    }
//
//    private void getAppcontacts(ArrayList<UserModel> usercontacts) {
//       if (usercontacts.size()>0){
//           DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(constants.USERS_PATH);
//           Query query=databaseReference.orderByChild(constants.MOBILE);
//           query.addValueEventListener(new ValueEventListener() {
//               @RequiresApi(api = Build.VERSION_CODES.M)
//               @Override
//               public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                   if (snapshot.exists()){
//                       for (DataSnapshot ds:snapshot.getChildren()){
//                           String mobilenumber=ds.child(constants.MOBILE).getValue().toString();
//                           for (UserModel userModel:usercontacts){
//                               if (userModel.getUsermobile().equals(mobilenumber)){
//                                 //  String name=ds.child(constants.USERNAME).getValue().toString();
//                                   String status=ds.child(constants.USERPROFILESTATUS).getValue().toString();
//                                //   String mobilenumbe=ds.child(constants.MOBILE).getValue().toString();
//                                  String uid=ds.child(constants.USERID).getValue().toString();
//                                  String image=ds.child(constants.USERPROFILEPHOTO).getValue().toString();
//                                  UserModel user=new UserModel();
//                                  user.setUsername(userModel.getUsername());
//                                  user.setUserprofilestatus(status);
//                                  user.setUserprofilephoto(image);
//                                  user.setUserId(uid);
//                                  user.setUserprofilephoto(image);
//                                   appcontacts.add(user);
//
//                                   break;
//                               }
//                           }
//                       }
//                       ContactsPhoneadapter contactsPhoneadapter=new ContactsPhoneadapter(getContext(),appcontacts);
//                       binding.recyclerViewContact.setAdapter(contactsPhoneadapter);
//                   }
//               }
//
//               @Override
//               public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//               }
//           });
//       }
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode){
//            case constants.CONTACTSREQUESTCODE:
//                if (grantResults[0]== PackageManager.PERMISSION_GRANTED)
//                    getUserContacts();
//                    else
//                    Toast.makeText(getContext(), "Contact permission denied", Toast.LENGTH_SHORT).show();
//
//        }
//    }
//}