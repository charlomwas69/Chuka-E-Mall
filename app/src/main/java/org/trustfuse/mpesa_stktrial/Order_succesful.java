package org.trustfuse.mpesa_stktrial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.trustfuse.mpesa_stktrial.Authentication.Login;
import org.trustfuse.mpesa_stktrial.Fragments.Account_frag;
import org.trustfuse.mpesa_stktrial.Fragments.Home_frag;

public class Order_succesful extends AppCompatActivity {

    ImageView close;
    TextView myorders;
    View ellipse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_succesful);

        close = findViewById(R.id.close);
        myorders = findViewById(R.id.my_orders);
        ellipse = findViewById(R.id.rectangle_2);

        ellipse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Home_frag();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
        });
        myorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Account_frag();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home_frag.class);
                startActivity(intent);
            }
        });
    }
}