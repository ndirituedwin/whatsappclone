package com.example.kampuskonekt.Authentication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kampuskonekt.Constants.constants;
import com.example.kampuskonekt.R;
import com.gmail.samehadar.iosdialog.CamomileSpinner;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class GetNumber extends AppCompatActivity {
private EditText enterphone;
private String number;
private Button generateotp;
private FirebaseAuth firebaseAuth;
private DatabaseReference databaseReference;
private CountryCodePicker ccpicker;
private TextView verifytv,enterphonetv;
private CamomileSpinner spinnerr;
ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_number);
        initializefields();
        generateotpbtnclicked();
    }

    private void generateotpbtnclicked() {
        generateotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNumber();
                if(checkNumber()){
                    String phonenumber=ccpicker.getSelectedCountryCodeWithPlus()+number;
                    Toast.makeText(GetNumber.this, "verification code to be sent to "+phonenumber, Toast.LENGTH_SHORT).show();
                    Log.d("ddd",phonenumber);
                    enterphonetv.setVisibility(View.GONE);
                    verifytv.setVisibility(View.GONE);
                    sendOtp(phonenumber);
                }
            }
        });


    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks verifyphonenumber=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
            if (e instanceof FirebaseAuthInvalidCredentialsException){

                Toast.makeText(GetNumber.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

//            binding.progressbarlayout.setVisibility(View.VISIBLE);
//            binding.progressbar.stop();

            else if(e instanceof FirebaseTooManyRequestsException) {
                spinnerr.stop();
                findViewById(R.id.progressbarlayout).setVisibility(View.GONE);
                spinnerr.setVisibility(View.GONE);
                enterphonetv.setVisibility(View.VISIBLE);
                verifytv.setVisibility(View.VISIBLE);
                Toast.makeText(GetNumber.this, "The sms quota for the project has been exceeded", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(GetNumber.this, "failed to send code " + e.getMessage(), Toast.LENGTH_LONG).show();
                spinnerr.stop();
                findViewById(R.id.progressbarlayout).setVisibility(View.GONE);
                spinnerr.setVisibility(View.GONE);
                enterphonetv.setVisibility(View.VISIBLE);
                verifytv.setVisibility(View.VISIBLE);
            }

        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            Intent intent=new Intent(GetNumber.this,VerifyNumber.class);
            intent.putExtra(constants.VERIFICATION_CODE,s);
            startActivity(intent);
            //progressDialog.dismiss();
            spinnerr.stop();
            Log.d("sentcode",s);
            Toast.makeText(GetNumber.this, "OTP code sent to phone "+number, Toast.LENGTH_SHORT).show();

        }
    };
    private void sendOtp(String phonenumber) {
        findViewById(R.id.progressbarlayout).setVisibility(View.VISIBLE);
       spinnerr.setVisibility(View.VISIBLE);
        spinnerr.start();
        spinnerr.setTag("Sending OTP to "+phonenumber);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setTitle("Sending OTP code to "+phonenumber);
//        progressDialog.setMessage("Please wait, sending otp...");
//        progressDialog.setIndeterminate(true);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,
                60,
                TimeUnit.SECONDS,
                this,
                verifyphonenumber
        );
    }

    private boolean checkNumber(){
         number=enterphone.getText().toString();
        if(number.isEmpty()){
            enterphone.setError("Mobile number field is required");
            return false;
        }else if(number.length() !=9){
            enterphone.setError("Enter the required amount of digits");
            return false;
        }else{
            enterphone.setError(null);
            return true;

        }
    }
    private void initializefields() {
      generateotp=findViewById(R.id.generateotpbtn);
      enterphone=findViewById(R.id.txtenterphone);
      ccpicker=findViewById(R.id.countrycodepicker);
      verifytv=findViewById(R.id.verifyphone);
      spinnerr=findViewById(R.id.progressbar);
  //    spinner=findViewById(R.id.progressbar);
        enterphonetv=findViewById(R.id.enterphonenumbertoverifytextview);
      firebaseAuth=FirebaseAuth.getInstance();
      databaseReference= FirebaseDatabase.getInstance().getReference();
        progressDialog=new ProgressDialog(this);


    }
}