package com.example.kampuskonekt.Fragments;


import android.Manifest;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kampuskonekt.Constants.constants;
import com.example.kampuskonekt.Permissions.permissions;
import com.example.kampuskonekt.R;
import com.example.kampuskonekt.User.UserListAdapter;
import com.example.kampuskonekt.User.UserObject;
import com.example.kampuskonekt.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class PhoneContacts extends Fragment {

   private View Phonecontactsrecyclerview;
    List<UserObject> userlist=new ArrayList<UserObject>();
    List<UserObject> contactlist=new ArrayList<UserObject>();
    Set<UserObject> conts=new LinkedHashSet<UserObject>(userlist);

    private RecyclerView phonecontactslist;
    private RecyclerView.Adapter phonecontactlisadpter;
    private RecyclerView.LayoutManager phonecontactlistlayoutmanager;
    private permissions permissions;
    private CircleImageView userprofilephoto;
    public PhoneContacts() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Phonecontactsrecyclerview=inflater.inflate(R.layout.fragment_phone_contacts, container, false);
        initializefields();
        getContactslist();

        return Phonecontactsrecyclerview;
    }

    private void initializefields() {
      phonecontactslist=Phonecontactsrecyclerview.findViewById(R.id.recyclerViewContact);
      phonecontactslist.setNestedScrollingEnabled(false);
      phonecontactslist.setHasFixedSize(false);
      userprofilephoto=Phonecontactsrecyclerview.findViewById(R.id.imgContactUserInfo);

   //   phonecontactlistlayoutmanager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
     //phonecontactslist.setLayoutManager(phonecontactlistlayoutmanager);
    phonecontactlisadpter=new UserListAdapter((ArrayList<UserObject>) userlist);
    //    phonecontactlisadpter=new UserListAdapter(contactset);
     phonecontactslist.setAdapter(phonecontactlisadpter);
    }

      private void getContactslist(){
         // if (permissions.iscontactok(getContext())) {
         String ISOprefix=getcountryiso();
          Cursor phones = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
              if (phones != null) {
                  contactlist.clear();
                  //contactset.clear();
                  try {
                      while (phones.moveToNext()) {

                          String username = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                          String usermobile = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

//                          UserObject contactss=new UserObject(username,usermobile);
//                          contactlist.add(contactss);
//                          getuserdetails(contactss);
//                          phonecontactlisadpter.notifyDataSetChanged();

                          usermobile=usermobile.replace(" ","");
                          usermobile=usermobile.replace("-","");
                          usermobile=usermobile.replace("(","");
                          usermobile=usermobile.replace("(","");

                          if (!String.valueOf(usermobile.charAt(0)).equals("+")){
                              //removing the zero
                           usermobile=usermobile.substring(1);
                              usermobile=ISOprefix+usermobile;
                             UserObject contactss = new UserObject(username, usermobile, (CircleImageView) userprofilephoto);
                              contactlist.add(contactss);
                             getuserdetails(contactss);
                              phonecontactlisadpter.notifyDataSetChanged();

                          }else if (String.valueOf(usermobile.charAt(0)).equals("+")){
                              UserObject contactss=new UserObject(username,usermobile, (CircleImageView) userprofilephoto);
                              contactlist.add(contactss);
                              getuserdetails(contactss);
                              phonecontactlisadpter.notifyDataSetChanged();

                          }else{

                          }
                         // getuserdetails(contactss);


                      }
                  } catch (Exception exception) {
                      exception.printStackTrace();
                  }
              }else {
                  phones.close();
                  Toast.makeText(getContext(), "No contacts", Toast.LENGTH_SHORT).show();
              }
//          }else{
//              permissions.requestContact(getActivity());
//          }

    }

    private void getuserdetails(UserObject contactss) {
        DatabaseReference db= FirebaseDatabase.getInstance().getReference().child(constants.USERS_PATH);
        Query query=db.orderByChild(constants.MOBILE).equalTo(contactss.getUsermobile());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String usermobile="";
                    String username="";
                    for (DataSnapshot childsnapshot: snapshot.getChildren()){
                        if (childsnapshot.child(constants.MOBILE).getValue()!=null)
                            usermobile=childsnapshot.child(constants.MOBILE).getValue().toString();
                       // Toast.makeText(getContext(), "mobile "+usermobile, Toast.LENGTH_SHORT).show();
//                        if (childsnapshot.child(constants.USERPROFILEPHOTO).getValue()!=null)
//                              userprofilephoto= (CircleImageView) childsnapshot.child(constants.USERPROFILEPHOTO).getValue();
                        if (childsnapshot.child(constants.USERNAME).getValue()!=null)
                            username=childsnapshot.child(constants.USERNAME).getValue().toString();
                              UserObject muser=new UserObject(username,usermobile,userprofilephoto);
                              if (username.equals(usermobile)) {
                                  for (UserObject contactlistiterator : contactlist) {
                                      if (contactlistiterator.getUsermobile().equals(muser.getUsermobile())) {
                                          muser.setUsername(contactlistiterator.getUsername());
                                      }
                                  }
                              }

                       // UserObject muser=new UserObject(username,usermobile);
                        userlist.add(muser);
                        phonecontactlisadpter.notifyDataSetChanged();
                        return;

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private  String getcountryiso(){
        String iso=null;
        //TelephonyManager telephonyManager=(TelephonyManager) getActivity().getSystemService(getActivity().TELEPHONY_SERVICE);
        TelephonyManager telephonyManager=(TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
         if (telephonyManager.getNetworkCountryIso()!=null)
             if (!telephonyManager.getNetworkCountryIso().toString().equals(""))
                 iso=telephonyManager.getNetworkCountryIso().toString();

     return Countrytophoneprefix.getPhone(iso);
//        return iso;
    }


    private void requestcontactpermissions(){
        requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS,Manifest.permission.READ_CONTACTS},1);

    }

}