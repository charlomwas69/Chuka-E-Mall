package org.trustfuse.mpesa_stktrial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.trustfuse.mpesa_stktrial.Authentication.Login;
import org.trustfuse.mpesa_stktrial.Fragments.Account_frag;
import org.trustfuse.mpesa_stktrial.Fragments.Cart_frag;
import org.trustfuse.mpesa_stktrial.Fragments.Home_frag;

public class Order_succesful extends AppCompatActivity {

    ImageView close;
    TextView myorders;
    View ellipse;
    RelativeLayout btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_succesful);
        close = findViewById(R.id.close);
        myorders = findViewById(R.id.my_orders);
        ellipse = findViewById(R.id.base);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Main_Menu.class);
                intent.putExtra("TARGET_FRAGMENT", "ACCOUNT_FRAGMENT");
                startActivity(intent);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Main_Menu.class);
                startActivity(intent);
            }
        });
    }
}