package com.example.kampuskonekt.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.kampuskonekt.Constants.constants;
import com.example.kampuskonekt.R;
import com.gmail.samehadar.iosdialog.CamomileSpinner;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

public class VerifyNumber extends AppCompatActivity {
    private String OTP,pin;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Button verifyotp;
    private PinView otppinvieww;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_number);
             OTP=getIntent().getExtras().get(constants.VERIFICATION_CODE).toString();
        Toast.makeText(this, "otp "+OTP, Toast.LENGTH_SHORT).show();
             initiliazefields();
             btnverifyotpclicked();
    }

    private void btnverifyotpclicked() {
     verifyotp.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if (checkotp()){
                 verifypin(pin);
             }else{
                 Toast.makeText(VerifyNumber.this, "failed", Toast.LENGTH_SHORT).show();
             }
         }
     });
    }
    private boolean checkotp(){
       pin=otppinvieww.getText().toString();
        if (pin.isEmpty()){
            otppinvieww.setError("Field is required");
            return false;
        }else if(pin.length()<6){
            otppinvieww.setError("number length not enough");
            return false;
        }else {
            otppinvieww.setError(null);
            return true;
        }
    }
    private void  verifypin(String pin){
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(OTP,pin);
        SignInWithCredential(credential);
    }
    private void SignInWithCredential(PhoneAuthCredential credential){
        progressDialog.setTitle("Verifying OTP");
       // progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading. Please wait verifynig otp...");
     //   progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getProgress();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {


                if (task.isSuccessful()){
                    Intent intent=new Intent(VerifyNumber.this, SettingsActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    Toast.makeText(VerifyNumber.this, "User successfully signed in ", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }else{

                    Toast.makeText(VerifyNumber.this, "Failed to sign in user "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void initiliazefields() {

     firebaseAuth=FirebaseAuth.getInstance();
     verifyotp=findViewById(R.id.btnverifyotp);
     otppinvieww=findViewById(R.id.otppinview);
     progressDialog=new ProgressDialog(this);
    }
}