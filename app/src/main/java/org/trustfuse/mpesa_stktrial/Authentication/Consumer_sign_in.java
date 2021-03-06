package org.trustfuse.mpesa_stktrial.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.trustfuse.mpesa_stktrial.Authentication.Login;
import org.trustfuse.mpesa_stktrial.Main_Menu;
import org.trustfuse.mpesa_stktrial.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Consumer_sign_in extends AppCompatActivity {
    EditText name,username,location,email;
    Button login;
    ProgressBar progressBar;
    TextView p_no;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Toolbar toolbar;
    String phone_number;
//    String Userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_sign_in);

        name = findViewById(R.id.cons_name);
        username = findViewById(R.id.cons_username);
        location = findViewById(R.id.cons_location);
        email = findViewById(R.id.cons_email);
        login = findViewById(R.id.consumer_sign_in);
        progressBar = findViewById(R.id.cons_progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        p_no = findViewById(R.id.p_no);
//        Userid = FirebaseAuth.getInstance().getUid();
        p_no.setText(Login.phoneNum);

        toolbar = findViewById(R.id.toolbar_cons_sign_in);
        toolbar.setTitle("Consumer Sign In");
        toolbar.setEnabled(true);

        phone_number = firebaseAuth.getCurrentUser().getUid();
        Toast.makeText(this, phone_number, Toast.LENGTH_SHORT).show();


//        DocumentReference documentReferencee = firebaseFirestore.collection("Consumer").document(firebaseAuth.getCurrentUser().getUid());
//        documentReferencee.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//                phone_number = Objects.requireNonNull(documentSnapshot.getString("Phone Number")).substring(1);
//                Toast.makeText(Consumer_sign_in.this, phone_number, Toast.LENGTH_SHORT).show();
////                freeshippin.setText(p_number);
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(), "Failed to fetch data" + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });



        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isuseronline()){
                    Toast.makeText(getApplicationContext(),"Check your internet connection",Toast.LENGTH_LONG).show();
                }

                create_user();
            }
        });
    }

    private boolean isuseronline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void create_user() {

        String phone = p_no.getText().toString().trim();
        String nam = name.getText().toString().trim();
        String usernam = username.getText().toString().trim();
        String email_st = email.getText().toString().trim();
        String locat_st = location.getText().toString().trim();


        if (TextUtils.isEmpty(nam)){
            name.setError("Username is required");
            if (TextUtils.isEmpty(usernam)){
                username.setError("Name cant be empty");
                if (TextUtils.isEmpty(locat_st)){
                    location.setError("Location is required");
                }
            }

                if (TextUtils.isEmpty(email_st)){
                    email.setError("Email is required");
                }
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            //add data
            Map<String , Object> dataa = new HashMap<>();
            dataa.put("Consumer name",nam);
            dataa.put("Consumer username",usernam);
            dataa.put("Consumer email",email_st);
            dataa.put("Consumer location",locat_st);
            dataa.put("Phone Number",phone);
            dataa.put("User Id", firebaseAuth.getCurrentUser().getUid());
            firebaseFirestore.collection("Consumer").document(firebaseAuth.getCurrentUser().getUid())
                    .set(dataa, SetOptions.merge());

            Intent intent = new Intent(getApplicationContext(), Main_Menu.class);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
            //end of add data
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Main_Menu.class);
        startActivity(intent);
    }
}