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
import com.covid_19.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import static android.widget.Toast.LENGTH_SHORT;

public class VerifyCodeActivity extends AppCompatActivity {
    //  EditText et1, et2, et3, et4, et5, et6;

    OtpView otpView;
    Button verifyButton;
    FirebaseAuth mAuth;
    String codeSent;
    String userName;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        mAuth = FirebaseAuth.getInstance();

        otpView = findViewById(R.id.otp_view);
        verifyButton = findViewById(R.id.verify_btn);

        createVerifyButton();


    }

    private void createVerifyButton() {

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (otpView.getText().length()==6) {
                Bundle bundle = getIntent().getExtras();
                if (bundle != null)
                    codeSent = bundle.getString(Constants.SEND_CODE);
                userName = bundle.getString(Constants.INTENT_USER_NAME);

                verifySignInCode(codeSent, userName);
                }else {
                     Toast.makeText(getApplicationContext(),"fill all the fields please",LENGTH_SHORT).show();
                }

            }
        });
    }

    private void verifySignInCode(String codeSent, String userName) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent,
                otpView.getText().toString());
        ;
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "waiting ..", Toast.LENGTH_SHORT).show();

                            reference = FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

                            User user = new User(userName, FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                            user.setStatus("offline");
                            user.setImageUrl("https://firebasestorage.googleapis.com/v0/b/my-project-covid-9e1c7.appspot.com/o/user.png?alt=media&token=6d0e40e1-338b-4217-8a47-dd145ddd0ed1");
                            reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> tasks) {

                                    if (tasks.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "sucess add user ..", Toast.LENGTH_SHORT).show();

                                          startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                         finish();
                                    } else {
                                        Log.d("ttt", ":failure add user");
                                        Toast.makeText(getApplicationContext(), " failure add user..", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.d("ttt", "signInWithCredential:failure");

                            otpView.setText("");


                        }
                    }
                });

    }



/*
  TODO IF YOU WANT MAKE VERFICATOIN EDIT TEXT  MANUAL

    public class GenericTextWatcher implements TextWatcher {
        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch (view.getId()) {

                case R.id.et1:
                    if (text.length() == 1)
                        et2.requestFocus();
                    break;
                case R.id.et2:
                    if (text.length() == 1)
                        et3.requestFocus();
                    else if (text.length() == 0)
                        et1.requestFocus();
                    break;
                case R.id.et3:
                    if (text.length() == 1)
                        et4.requestFocus();
                    else if (text.length() == 0)
                        et2.requestFocus();
                    break;
                case R.id.et4:
                    if (text.length() == 1)
                        et5.requestFocus();
                    else if (text.length() == 0)
                        et3.requestFocus();
                    break;

                case R.id.et5:
                    if (text.length() == 1)
                        et6.requestFocus();
                    else if (text.length() == 0)
                        et4.requestFocus();
                    break;


                case R.id.et6:
                    if (text.length() == 1)
                        et6.clearFocus();
                    else if (text.length() == 0)
                        et5.requestFocus();
                    break;


            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }
*/

}
