package org.trustfuse.mpesa_stktrial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class First_Page extends AppCompatActivity {

    Button consumer,good_owner;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first__page);

        consumer = findViewById(R.id.btn_consumer);
        good_owner = findViewById(R.id.btn_goodowner);
        firebaseAuth= FirebaseAuth.getInstance();

        consumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });
        good_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),Goodowner_sign_in.class);
                Intent intent = new Intent(getApplicationContext(),Main_Menu.class);
                startActivity(intent);
            }
        });
    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//    }
}