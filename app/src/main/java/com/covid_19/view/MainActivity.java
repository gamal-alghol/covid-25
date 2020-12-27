package com.covid_19.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.covid_19.R;
import com.covid_19.model.Constants;
import com.covid_19.view.VerifyCodeActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText editTextLoginNumber;
    EditText userNameEditText;
    Button loginButton;
    //FirebaseAuth mAuth;
    String codeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLoginNumber = findViewById(R.id.edit_text_login_number);
        loginButton = findViewById(R.id.login_btn);
        userNameEditText = findViewById(R.id.edit_text_name);


        createEditTextLogin();
        createLoginButton();


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
    }

    private void createEditTextLogin() {
        editTextLoginNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 9) {
                    loginButton.setVisibility(View.VISIBLE);
                } else {
                    loginButton.setVisibility(View.GONE);

                }
            }
        });

    }

    private void createLoginButton() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode();


                //startActivity(new Intent(getApplicationContext(), VerifyCodeActivity.class));


            }
        });
    }

    private void sendVerificationCode() {
        String phoneNumber = editTextLoginNumber.getText().toString();
        if (!phoneNumber.startsWith("5")) {
            editTextLoginNumber.setError("Invalid Number");
            editTextLoginNumber.requestFocus();
            Log.d("ttt","error in 5");
            return;
        }
        phoneNumber = Constants.CODE_PALESTINE + phoneNumber;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Log.d("ttt", "complete");

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.d("ttt", "onVerificationFailed  " + e.getMessage());
            Toast.makeText(getApplicationContext(), "Enter vaild Number", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;

            Log.d("ttt", "code sent");
            Bundle bundle = new Bundle();
            bundle.putString(Constants.SEND_CODE, codeSent);
            bundle.putString(Constants.INTENT_USER_NAME, userNameEditText.getText().toString());
            startActivity(new Intent(getApplicationContext(), VerifyCodeActivity.class).putExtras(bundle));
            finish();
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
            super.onCodeAutoRetrievalTimeOut(s);
            Log.d("ttt", "onCodeAutoRetrievalTimeOut");

        }
    };

}
