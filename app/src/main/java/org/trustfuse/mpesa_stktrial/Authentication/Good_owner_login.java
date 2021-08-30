package org.trustfuse.mpesa_stktrial.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.trustfuse.mpesa_stktrial.Good_Owner.Good_owner_post;
import org.trustfuse.mpesa_stktrial.R;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Good_owner_login extends AppCompatActivity {
    Button login;
    EditText otp;
    TextView resend_otp,info;
    ProgressBar progressbar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    String verificationId;
    PhoneAuthProvider.ForceResendingToken token;
    Boolean verificationInProgress = false;
    String Userid;
    String p_number;
    public static String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_owner_login);

        login = findViewById(R.id.OTP_button_good_owner);
        info = findViewById(R.id.inform_good_owner);
        otp = findViewById(R.id.OTP_good_owner);
        resend_otp = findViewById(R.id.resend_otp_good_owner);
        progressbar = findViewById(R.id.progressbar_good_owner);
        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        String resend = "OTP not received? Send Again.";
        SpannableString spannableString = new SpannableString(resend);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(getApplicationContext(),"CLICKED",Toast.LENGTH_LONG).show();
            }
        };
        spannableString.setSpan(clickableSpan, 18,28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        resend_otp.setText(spannableString);
        resend_otp.setMovementMethod(LinkMovementMethod.getInstance());
        /////resend option
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!otp.getText().toString().isEmpty() && otp.getText().toString().length() > 5) {
                    if(!verificationInProgress){
                        otp.setEnabled(false);
                        phoneNum = otp.getText().toString().trim();
                        p_number = otp.getText().toString();

                        progressbar.setVisibility(View.VISIBLE);
                        requestOTP(phoneNum);
                    }else {
                        login.setEnabled(false);
                        otp.setVisibility(View.GONE);
                        progressbar.setVisibility(View.VISIBLE);
//                        state.setText("Logging in");
//                        state.setVisibility(View.VISIBLE);
                        //getting OTP from user
                        String userOTP = otp.getText().toString();

                        ///checking if OTP is entered
                        if (userOTP.length() == 6){
                            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,userOTP);
                            verifyAuth(credential);
                        }else{
                            otp.setError("OTP is required");
                        }
                    }

                }else {
                    otp.setError("Valid Phone Required");
                }
            }
        });

    }
    private void requestOTP(String phoneNum) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNum, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                progressbar.setVisibility(View.GONE);
//                state.setVisibility(View.GONE);
                otp.setVisibility(View.VISIBLE);
                verificationId = s;
                token = forceResendingToken;
                login.setText("VERIFY");
                info.setText("Enter OTP sent to Number");
                login.setEnabled(true);
                verificationInProgress = true;
                otp.setText("");
                otp.setEnabled(true);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Toast.makeText(Good_owner_login.this, "OTP has expired *CHECK YOUR NETWORK PLEASE*", Toast.LENGTH_SHORT).show();
//                resend.setVisibility(View.VISIBLE);
//                state.setVisibility(View.GONE);
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                verifyAuth(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Good_owner_login.this, "Phone number not verified"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.GONE);
                otp.setEnabled(true);
//                state.setVisibility(View.INVISIBLE);
                login.setEnabled(true);
            }
        });
    }                    ///////END OF REQUEST OTP

    //////////VERIFY AUTH
    private void verifyAuth(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Good_owner_login.this, "Authentication is successful", Toast.LENGTH_SHORT).show();
                    otp.setText("");
                    //// check if user exists in the db
                    checkUserProfile();

                }else {
                    progressbar.setVisibility(View.GONE);
//                    state.setVisibility(View.GONE);
                    Toast.makeText(Good_owner_login.this, "Phone Number Not Verified *Invalid OTP*", Toast.LENGTH_SHORT).show();
                    otp.setVisibility(View.VISIBLE);
                    login.setText("REENTER OTP");
                    login.setEnabled(true);
                }
            }
        });
    }            ///END OF VERIFY AUTH

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null){
            progressbar.setVisibility(View.VISIBLE);
//            state.setText("CHECKING .....");
//            state.setVisibility(View.VISIBLE);
            checkUserProfile();
        }
    }


    //////////////////////// CHECKING IF USER HAS ALREADY RECEIVED THE OTP,,IF SO THE USER WILL GO DIRECTLY TO MAIN PAGE
    private void checkUserProfile() {

        DocumentReference docref = fstore.collection("Good Owner").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    Toast.makeText(getApplicationContext(),"SUCCCCCCCCCCESSSSSS",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Good_owner_post.class));
                    finish();

                }else {
                    Intent intent1 = new Intent(getApplicationContext(), Goodowner_sign_in.class);
//                    Intent intent1 = new Intent(getApplicationContext(),Good_owner_post.class);
                            startActivity(intent1);
                            progressbar.setVisibility(View.GONE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Good_owner_login.this, "Profile does not exist" + e.toString() , Toast.LENGTH_LONG).show();
//                state.setVisibility(View.GONE);
                progressbar.setVisibility(View.GONE);

            }
        });
    }
}