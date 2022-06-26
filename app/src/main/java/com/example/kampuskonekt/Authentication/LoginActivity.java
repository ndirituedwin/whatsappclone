package com.example.kampuskonekt.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kampuskonekt.MainActivity;
import com.example.kampuskonekt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {
 private FirebaseAuth firebaseAuth;
 private FirebaseUser currentUser;
 private Button LoginButton,phoneloginbutton;
 private EditText email,password;
 private TextView needanewaccountlink,forgotpassword;
 private ProgressDialog progressDialog;
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializefields();
        createaccountbtnclicked();
        loginbtnclicked();
     phoneloginbuttonclicked();
    }

    private void phoneloginbuttonclicked() {
        phoneloginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, GetNumber.class);
                startActivity(intent);
            }
        });
 }

    private void loginbtnclicked() {
    LoginButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SignInUser();
        }
    });
    }

    private void SignInUser() {
    String emai=email.getText().toString().toLowerCase();
    String passwor=password.getText().toString();
    if (TextUtils.isEmpty(emai)){
        email.setError("Email field is required");
    }
    if(TextUtils.isEmpty(passwor)){
        password.setError("password field is required");
    }
    if (passwor.length()<6){
        password.setError("password must have atleast six characters");
    }else{
        progressDialog.setTitle("SignIning in "+emai);
        progressDialog.setMessage("Hold on while we sign you in");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(emai,passwor).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                   if (task.isSuccessful()){

                       sendthemtomainactivity();
                       progressDialog.dismiss();
                       Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                   }else{
                       progressDialog.dismiss();
                       Toast.makeText(LoginActivity.this, "failed "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                   }
               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull @NotNull Exception e) {
                   progressDialog.dismiss();
                   Toast.makeText(LoginActivity.this, "Signin failed for user "+emai +" due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
               }
           });
    }
    }

    //when create a new account  btn is clicked;
    private void createaccountbtnclicked() {
       needanewaccountlink.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                    sendthemtocreatenewaccountactivity();
           }
       });
    }

    private void sendthemtocreatenewaccountactivity() {
    Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
    startActivity(intent);

    }

    private void initializefields() {
        firebaseAuth=FirebaseAuth.getInstance();
        currentUser=firebaseAuth.getCurrentUser();
       LoginButton=findViewById(R.id.login_button);
       phoneloginbutton=findViewById(R.id.login_with_mobile);
       progressDialog=new ProgressDialog(this);
       email=findViewById(R.id.login_email);
       password=findViewById(R.id.login_password);
       forgotpassword=findViewById(R.id.forgot_password_link);
       needanewaccountlink=findViewById(R.id.dont_have_an_account);

    }
    //check if user is authenticated

    @Override
    protected void onStart() {
        super.onStart();
        //if user is not looged in
          if (currentUser !=null){
              sendthemtomainactivity();
          }
    }

    private void sendthemtomainactivity() {
        Intent mainactivityintent=new Intent(LoginActivity.this,MainActivity.class);
        mainactivityintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainactivityintent);
        finish();

    }
}