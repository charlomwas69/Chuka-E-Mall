package org.trustfuse.mpesa_stktrial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

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
                Intent intent = new Intent(getApplicationContext(),Good_owner_post.class);
//                Intent intent = new Intent(getApplicationContext(),Good_owner_login.class);
                startActivity(intent);
//                new SweetAlertDialog(First_Page.this, SweetAlertDialog.SUCCESS_TYPE)
//                        .setTitleText("SUCCESS!")
//                        .setContentText("Item was posted!")
//                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//                                sDialog.cancel();
//                            }
//                        })
//                        .show();
//                alertDialog= new LottieAlertDialog.Builder(First_Page.this, DialogTypes.TYPE_LOADING)
//                        .setTitle("Loading")
//                        .setDescription("Please Wait")
//                        .build();
//                alertDialog.setCancelable(true);
//                alertDialog.show();


            }
        });

    }
}