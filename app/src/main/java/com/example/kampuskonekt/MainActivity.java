package com.example.kampuskonekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kampuskonekt.Authentication.LoginActivity;
import com.example.kampuskonekt.Authentication.SettingsActivity;
import com.example.kampuskonekt.Constants.constants;
import com.example.kampuskonekt.Groups.GroupCreateActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabsAccessorAdapter tabsAccessorAdapter;
    private FirebaseUser currentuser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializefields();
        RequestPermissions();

    }

    private void RequestPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS,Manifest.permission.READ_CONTACTS},1);
        }
    }

    //when this page is starting
    @Override
    protected void onStart() {
        super.onStart();
        //if user is not logged in
        if (currentuser==null){
            sendthemtologinactivity();
        }else{
              verifyifhasusername();
        }
    }

    private void verifyifhasusername() {
       String currentuserid=firebaseAuth.getCurrentUser().getUid();
       databaseReference.child(constants.USERS_PATH).child(currentuserid).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
               if (snapshot.child(constants.USERNAME).exists()&& snapshot.child(constants.USERPROFILESTATUS).exists()&& snapshot.child(constants.USERPROFILEPHOTO).exists()){
                   Toast.makeText(MainActivity.this, "Welcome to campus Connect", Toast.LENGTH_SHORT).show();
               }else{

                   sendthemtosettingsactivity();
               }
           }

           @Override
           public void onCancelled(@NonNull @NotNull DatabaseError error) {

           }
       });
    }
    private void sendthemtologinactivity() {
        Intent loginintent=new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginintent);
    }
    private void sendthemtosettingsactivity() {
        Intent settings=new Intent(MainActivity.this, SettingsActivity.class);
        settings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(settings);
        finish();
    }
    private void initializefields() {
        firebaseAuth=FirebaseAuth.getInstance();
        currentuser=firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference();
         toolbar=findViewById(R.id.mainpagetoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Kampus Konekt");

        viewPager=findViewById(R.id.maintabspager);
         tabsAccessorAdapter=new TabsAccessorAdapter(getSupportFragmentManager());
         viewPager.setAdapter(tabsAccessorAdapter);

         tabLayout=findViewById(R.id.maintabs);
         tabLayout.setupWithViewPager(viewPager);


    }
    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
       getMenuInflater().inflate(R.menu.menu_options,menu);
       return true;
    }
    //when menu item is selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
     if (item.getItemId()==R.id.main_find_friends_option){

     }
     if (item.getItemId()==R.id.main_settings_option){
         sendthemtosettingsactivity();

     }if (item.getItemId()==R.id.main_logout_option){
         firebaseAuth.signOut();
            sendthemtologinactivity();
        }
    if (item.getItemId()==R.id.main_create_group_option){
             Intent groupintent=new Intent(MainActivity.this, GroupCreateActivity.class);
             startActivity(groupintent);

    }

     return true;
    }
    private void Createanewgroup() {
        AlertDialog.Builder createnewgroupdialog=new AlertDialog.Builder(MainActivity.this,R.style.AlertDialog);
        createnewgroupdialog.setTitle("Enter group name");
        final EditText groupnamefield=new EditText(MainActivity.this);
        groupnamefield.setHint("Kampus connect");
        createnewgroupdialog.setView(groupnamefield);
//        createnewgroupdialog.setIcon(R.drawable.grou);
        createnewgroupdialog.setCancelable(false);
        createnewgroupdialog.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String groupname=groupnamefield.getText().toString();
                if (TextUtils.isEmpty(groupname)){
                    Toast.makeText(MainActivity.this,"enter group name",Toast.LENGTH_SHORT).show();
                }else{
                    //save group to database
                    CREATEGROUP(groupname);
                }
            }
        });
        createnewgroupdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        createnewgroupdialog.show();

    }
    private void CREATEGROUP(String groupname) {

    }
    private void createnewgroup() {
//    databaseReference.child(constants.GROUPNAME).child(groupname).setValue("")
    }
}