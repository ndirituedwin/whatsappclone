package com.example.kampuskonekt.Permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.kampuskonekt.Constants.constants;

public class permissions {

    public boolean  iscontactok(Context context){
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED;
    }

    public void requestContact(Activity activity){
        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_CONTACTS}, constants.CONTACTSREQUESTCODE);
    }
//    private boolean RequestPermissions() {
//          return new String[]{Manifest.permission.WRITE_CONTACTS,Manifest.permission.READ_CONTACTS},1
//        }
    }


