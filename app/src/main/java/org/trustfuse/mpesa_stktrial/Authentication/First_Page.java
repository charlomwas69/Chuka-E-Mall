package org.trustfuse.mpesa_stktrial.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import org.trustfuse.mpesa_stktrial.Good_Owner.Good_owner_post;
import org.trustfuse.mpesa_stktrial.MainActivity;
import org.trustfuse.mpesa_stktrial.Mpesa_Test;
import org.trustfuse.mpesa_stktrial.R;

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
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        good_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Good_owner_post.class);
//                Intent intent = new Intent(getApplicationContext(),Good_owner_login.class);
                startActivity(intent);


            }
        });

    }
}