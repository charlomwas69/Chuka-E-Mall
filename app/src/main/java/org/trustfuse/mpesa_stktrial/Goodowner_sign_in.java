package org.trustfuse.mpesa_stktrial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Goodowner_sign_in extends AppCompatActivity {

    EditText name,location;
    Button sign_In;
    ProgressBar progressBarr;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    TextView number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodowner_sign_in);

        name = findViewById(R.id.good_owner_name);
        location = findViewById(R.id.good_owner_location);
        sign_In = findViewById(R.id.consumer_sign_in);
        progressBarr = findViewById(R.id.goodowner_progressBar);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        number = findViewById(R.id.phone_number);

        number.setText(Good_owner_login.phoneNum);

        sign_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!isuseronline()){
                    Toast.makeText(getApplicationContext(),"Check your internet connection",Toast.LENGTH_LONG).show();
                }
                CreateGoodOwner();

            }
        });
    }

    private boolean isuseronline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void CreateGoodOwner() {

        String g_name = name.getText().toString().trim();
        String g_location = location.getText().toString().trim();
        String p_number = number.getText().toString().trim();

        if (TextUtils.isEmpty(g_name)){
            name.setError("Name cant be empty");
        }
        if (TextUtils.isEmpty(g_location)){
            location.setError("Location cant be empty");
        }
        else
        progressBarr.setVisibility(View.VISIBLE);
        //add data
        Map<String , Object> dataa = new HashMap<>();
        dataa.put("Consumer name",g_name);
        dataa.put("Good owner location",g_location);
        dataa.put("Good owner phone number",p_number);
        firebaseFirestore.collection("Good owner").document(firebaseAuth.getCurrentUser().getUid())
                .set(dataa, SetOptions.merge());
        Intent intent = new Intent(getApplicationContext(),Good_owner_post.class);
        startActivity(intent);
    }
}