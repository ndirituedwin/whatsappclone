package com.example.kampuskonekt.Authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kampuskonekt.Constants.constants;
import com.example.kampuskonekt.MainActivity;
import com.example.kampuskonekt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
  private CircleImageView userprofilephoto;
  private ImageView pickimage;
  private Button updateprofilebtn;
  private StorageReference storagereference;
  private DatabaseReference databasereference;
  private EditText enterusername, enterprofilestatus,entermobile;
  private FirebaseAuth firebaseAuth;
    private Uri imageuri;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initializeall();
        pickimagebtnclicked();
        updateprofilebtnclicked();
        Retrieveuserinfo();
    }

    private void Retrieveuserinfo() {
        databasereference.child(constants.USERS_PATH)
                .child(firebaseAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists() && snapshot.hasChild(constants.USERNAME)&&snapshot.hasChild(constants.USERPROFILESTATUS)&& snapshot.hasChild(constants.USERPROFILEPHOTO) && snapshot.hasChild(constants.MOBILE) ){
                            String userphoto=snapshot.child(constants.USERPROFILEPHOTO).getValue().toString();
                            String username=snapshot.child(constants.USERNAME).getValue().toString();
                            String userstatus=snapshot.child(constants.USERPROFILESTATUS).getValue().toString();
                            String mobile=snapshot.child(constants.MOBILE).getValue().toString();

                            entermobile.setText(mobile);
                              enterusername.setText(username);
                              enterprofilestatus.setText(userstatus);
                              Picasso.get().load(userphoto).placeholder(R.drawable.ic_person).into(userprofilephoto);
                        }else if(snapshot.exists() && snapshot.hasChild(constants.USERNAME) && snapshot.hasChild(constants.USERPROFILESTATUS)) {
                            String username = snapshot.child(constants.USERNAME).getValue().toString();
                            String userstatus = snapshot.child(constants.USERPROFILESTATUS).getValue().toString();
                            enterusername.setText(username);
                            enterprofilestatus.setText(userstatus);
                        }else if(snapshot.exists() && snapshot.hasChild(constants.USERNAME) && snapshot.hasChild(constants.USERPROFILESTATUS)&& snapshot.hasChild(constants.MOBILE)){
                            String username = snapshot.child(constants.USERNAME).getValue().toString();
                            String userstatus = snapshot.child(constants.USERPROFILESTATUS).getValue().toString();
                            String mobile = snapshot.child(constants.MOBILE).getValue().toString();
                            enterusername.setText(username);
                            enterprofilestatus.setText(userstatus);
                            entermobile.setText(mobile);
                        }else if(snapshot.exists()&& snapshot.hasChild(constants.USERPROFILEPHOTO)){
                            String userphoto=snapshot.child(constants.USERPROFILEPHOTO).getValue().toString();
                            Picasso.get().load(userphoto).placeholder(R.drawable.ic_person).into(userprofilephoto);

                        }else{

                            Toast.makeText(SettingsActivity.this, "Update your profile info", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }

    private void pickimagebtnclicked() {
      pickimage.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
           if (isstoragepermissionok()){
               pickimagefromphone();
           }
          }
      });
    }

    private void updateprofilebtnclicked() {

    updateprofilebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (checkName() && checkprofilestatus()){
               // UploadData();
                upload();
            }
        }
    });
    }
    private boolean checkName() {
        String username=enterusername.getText().toString().trim();
        if (username.isEmpty()) {
            enterusername.setError(" user name Filed is required");
            return false;
        } else {
            enterusername.setError(null);
            return true;
        }
    }
    private boolean checkprofilestatus() {
        String userstatus=enterprofilestatus.getText().toString().trim();
        if (userstatus.isEmpty()) {
            enterprofilestatus.setError(" Profile status Filed is required");
            return false;
        } else {
            enterusername.setError(null);
            return true;
        }
    }
    private boolean checkimage(){
        if (imageuri == null) {
         //imageuri=Uri.parse(String.valueOf(R.drawable.ic_person));
            Toast.makeText(this, "Image is required", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }


    private void upload(){

        String username=enterusername.getText().toString();
        String status=enterprofilestatus.getText().toString();
        if (TextUtils.isEmpty(username)){
            enterusername.setError("username field cannot be blank");
        }else if(TextUtils.isEmpty(status)) {
            enterprofilestatus.setError("status cannot be empty");
        }else{


           if (entermobile.getText().toString().isEmpty()) {
               HashMap<String, Object> profilemap = new HashMap<>();
               profilemap.put(constants.USERNAME, username);
               profilemap.put(constants.USERID, firebaseAuth.getCurrentUser().getUid());
//            profilemap.put("userimage",imageuri);
               profilemap.put(constants.USERPROFILESTATUS, status);
               databasereference.child(constants.USERS_PATH).child(firebaseAuth.getUid()).updateChildren(profilemap).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull @NotNull Task<Void> task) {
                       if (task.isSuccessful()) {
                           sendthemtomainactivity();
                           progressDialog.dismiss();
                           Toast.makeText(SettingsActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                       } else {
                           progressDialog.dismiss();
                           Toast.makeText(SettingsActivity.this, "failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }else{
               if (entermobile.getText().toString().length() >13 || entermobile.getText().toString().length()<10){
                   entermobile.setError("Mobile number must be of the required length");
               }else {
                   HashMap<String, Object> profilemap = new HashMap<>();
                   profilemap.put(constants.USERNAME, username);
                   profilemap.put(constants.USERID, firebaseAuth.getCurrentUser().getUid());
                   profilemap.put(constants.MOBILE, entermobile.getText().toString());
                   profilemap.put(constants.USERPROFILESTATUS, status);
                   databasereference.child(constants.USERS_PATH).child(firebaseAuth.getUid()).updateChildren(profilemap).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull @NotNull Task<Void> task) {
                           if (task.isSuccessful()) {
                               sendthemtomainactivity();
                               progressDialog.dismiss();
                               Toast.makeText(SettingsActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                           } else {
                               progressDialog.dismiss();
                               Toast.makeText(SettingsActivity.this, "failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }
           }
        }
    }
    private void sendthemtomainactivity() {
        Intent mainactivityintent=new Intent(SettingsActivity.this, MainActivity.class);
        mainactivityintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainactivityintent);
        finish();

    }
    private void pickimagefromphone() {
        CropImage.activity()
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(this);
    }

    private boolean isstoragepermissionok(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            requeststoragepath();
            return false;
        }
    }

    private void requeststoragepath() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 100:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    pickimagefromphone();
                else
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (data != null) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    imageuri = result.getUri();
                    Log.d("imageuri",imageuri.toString());
                    userprofilephoto.setImageURI(imageuri);

                    progressDialog.setTitle("Profile Photo update");
                    progressDialog.setMessage("Hold on while we update your profile info...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    StorageReference  filepath=storagereference.child(constants.STORAGEREFERENCE).child(firebaseAuth.getUid()+".jpg");
                    filepath.putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SettingsActivity.this, "Profile image  uploaded", Toast.LENGTH_SHORT).show();

                                task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageuri=uri.toString();
                                        String curretuser=firebaseAuth.getCurrentUser().getUid();
                                        databasereference.child(constants.USERS_PATH).child(firebaseAuth.getUid()).child(constants.USERPROFILEPHOTO).setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                  Toast.makeText(SettingsActivity.this, "Image successfully saved to database", Toast.LENGTH_SHORT).show();
                                                //upload();

                                                progressDialog.dismiss();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                Toast.makeText(SettingsActivity.this,"failed to save image to database"+e,Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {
                                        Toast.makeText(SettingsActivity.this, "failed to upload image "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });



                                progressDialog.dismiss();
                            }else{

                                Toast.makeText(SettingsActivity.this, "failed:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    });
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }else if(data ==null){
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void initializeall() {
        firebaseAuth=FirebaseAuth.getInstance();
      userprofilephoto=findViewById(R.id.userprofileImage);
       pickimage=findViewById(R.id.pickimage);
       updateprofilebtn=findViewById(R.id.updateuserprofilebtn);
       enterusername=findViewById(R.id.enterusername);
       entermobile=findViewById(R.id.entermobile);
       progressDialog=new ProgressDialog(this);
       enterprofilestatus=findViewById(R.id.enterprofilestatus);
       databasereference= FirebaseDatabase.getInstance().getReference();
       storagereference= FirebaseStorage.getInstance().getReference();
    }
}