package org.trustfuse.mpesa_stktrial.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import org.trustfuse.mpesa_stktrial.Main_Menu;
import org.trustfuse.mpesa_stktrial.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {

    Button login;
    EditText otp;
    TextView resend_otp,info;
    ProgressBar progressbar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    String verificationId;
    PhoneAuthProvider.ForceResendingToken token;
    Boolean verificationInProgress = false;
    Toolbar toolbar;
//    public static String cary_number;
    String p_number;
    public static String phoneNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.OTP_button);
        info = findViewById(R.id.inform);
        otp = findViewById(R.id.OTP);
        resend_otp = findViewById(R.id.resend_otp);
        progressbar = findViewById(R.id.progressbar);
        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        toolbar = findViewById(R.id.toolbar_login);
        toolbar.setTitle("Login");
        toolbar.setEnabled(true);

//        Userid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

        //resend option
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
//                        Intent intent = new Intent(getApplicationContext(),Consumer_sign_in.class);
//                        intent.putExtra("phoneNum", phoneNum);
//                        startActivity(intent);
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
                otp.setText("");
                info.setText("Enter OTP sent to Number");
                login.setEnabled(true);
                verificationInProgress = true;
                otp.setEnabled(true);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Toast.makeText(Login.this, "OTP has expired *CHECK YOUR NETWORK PLEASE*", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Login.this, "Phone number not verified"+ e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Login.this, "Authentication is successful", Toast.LENGTH_SHORT).show();
                    otp.setText("");
                    //// check if user exists in the db
                    checkUserProfile();

                }else {
                    progressbar.setVisibility(View.GONE);
//                    state.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Phone Number Not Verified *Invalid OTP*", Toast.LENGTH_SHORT).show();
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

        DocumentReference docref = fstore.collection("Consumer").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
//                    Toast.makeText(getApplicationContext(),"SUCCCCCCCCCCESSSSSS",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Main_Menu.class));
                    finish();

                }else {
                    DocumentReference documentReference = fstore.collection("Consumer").document();
                    Map<String, Object> consumer = new HashMap<>();
//                    Toast.makeText(getApplicationContext(),"SUCCESFULLY ADDED",Toast.LENGTH_LONG);
                    consumer.put("Phone number",p_number);

                    documentReference.set(consumer).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Intent intent1 = new Intent(getApplicationContext(),Consumer_sign_in.class);
                            startActivity(intent1);
                            progressbar.setVisibility(View.GONE);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Registration failed" + e.toString(),Toast.LENGTH_LONG).show();
                        }
                    });
//                    Intent intent1 = new Intent(getApplicationContext(), Consumer_sign_in.class);
//                            startActivity(intent1);
//                            progressbar.setVisibility(View.GONE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "Profile does not exist" + e.toString() , Toast.LENGTH_LONG).show();
//                state.setVisibility(View.GONE);
                progressbar.setVisibility(View.GONE);

            }
        });
    }

/////last tag
}