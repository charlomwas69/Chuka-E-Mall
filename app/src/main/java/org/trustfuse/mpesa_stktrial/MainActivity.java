package org.trustfuse.mpesa_stktrial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;

import org.trustfuse.mpesa_stktrial.Authentication.First_Page;


public class MainActivity extends AppCompatActivity {

    EditText editTextPhoneNumber;
    Button sendButton;
    TextView textView;
    String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendButton = findViewById(R.id.sendButton);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        textView = findViewById(R.id.yohyoh);
    }
}