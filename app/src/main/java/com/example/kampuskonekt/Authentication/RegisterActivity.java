package com.example.kampuskonekt.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kampuskonekt.Constants.constants;
import com.example.kampuskonekt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
 private Button createaccountbtn;
 private EditText enteremail,enterpassword;
 private TextView alreadyhaveanaccount;
 private FirebaseAuth firebaseAuth;
 private ProgressDialog progressDialog;
 private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeeverything();
        //when alreadyhaveanaccountbtnisclicked
        alreadyhaveanaccountbtnclicked();
        //createaccountbtnclicked;
        createaccountbtnclicked();
    }

    private void createaccountbtnclicked() {

    createaccountbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createaccount();
        }
    });
    }

    private void createaccount() {
       String email=enteremail.getText().toString().toLowerCase();
       String password=enterpassword.getText().toString();
       if (TextUtils.isEmpty(email)){
           enteremail.setError("Email field is required");
       }
       if(TextUtils.isEmpty(password)){
           enterpassword.setError("Password field is required");
       }
       if (password.length()<6){
           enterpassword.setError("Password mus be atleast six characters");
       }
       else{
           progressDialog.setTitle("Registering user");
           progressDialog.setMessage("Hold on while we create your account");
           progressDialog.setCanceledOnTouchOutside(false);
           progressDialog.show();
           firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                   if (task.isSuccessful()){
                      // String currentuserid=task.getResult().getUser().getUid();
                       String curretuser=firebaseAuth.getCurrentUser().getUid();
                       databaseReference.child(constants.USERS_PATH).child(curretuser).setValue("");
                       sendthemtomainactivity();
                       Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                       progressDialog.dismiss();
                   }else{
                       progressDialog.dismiss();
                       Toast.makeText(RegisterActivity.this, "failed "+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                   }

               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull @NotNull Exception e) {
                   progressDialog.dismiss();
                   Toast.makeText(RegisterActivity.this, "failed to register user with email "+email+"  "+e.getMessage(), Toast.LENGTH_SHORT).show();
               }
           });
       }


    }

    private void alreadyhaveanaccountbtnclicked() {
    alreadyhaveanaccount.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendthemtologinactivity();
        }
    });
    }

    private void sendthemtologinactivity() {
        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    private void sendthemtomainactivity() {
        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    private void initializeeverything() {
          firebaseAuth=FirebaseAuth.getInstance();
          databaseReference= FirebaseDatabase.getInstance().getReference();
          progressDialog=new ProgressDialog(this);
         createaccountbtn=findViewById(R.id.register_button);
         enteremail=findViewById(R.id.register_email);
         enterpassword=findViewById(R.id.register_password);
         alreadyhaveanaccount=findViewById(R.id.already_have_an_account);
    }
}