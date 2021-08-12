package org.trustfuse.mpesa_stktrial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Consumer_sign_in extends AppCompatActivity {
    EditText name,username,location,email;
    Button login;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
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
//        Userid = FirebaseAuth.getInstance().getUid();

        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                create_user();
//                String phone_num = phone_number.getText().toString().trim();
//                Intent intent = new Intent(getApplicationContext(),Login.class);
//                intent.putExtra("number",phone_num);
//                startActivity(intent);
            }
        });
    }

    private void create_user() {

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
            firebaseFirestore.collection("Drivers").document(firebaseAuth.getCurrentUser().getUid())
                    .set(dataa, SetOptions.merge());
            //end of add date
//            DocumentReference documentReference = firebaseFirestore.collection("Consumer").document();
//            Map<String, Object> consumer = new HashMap<>();
//            consumer.put("name",nam);
//            consumer.put("username",usernam);
//            consumer.put("email",email_st);
//            consumer.put("location",locat_st);
//
//            documentReference.set(consumer).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//
//                    Intent intent = new Intent(getApplicationContext(),Login.class);
//                    startActivity(intent);
//                    progressBar.setVisibility(View.GONE);
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(getApplicationContext(),"Registration failed" + e.toString(),Toast.LENGTH_LONG).show();
//                }
//            });
        }

    }
}