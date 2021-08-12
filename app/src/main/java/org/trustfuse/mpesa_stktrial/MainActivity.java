package org.trustfuse.mpesa_stktrial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;
import com.androidstudy.daraja.model.AccessToken;


public class MainActivity extends AppCompatActivity {

    EditText editTextPhoneNumber;
    Button sendButton;
    Daraja daraja;
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendButton = findViewById(R.id.sendButton);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        //start
        //Init Daraja
        //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO
        daraja = Daraja.with("EXvAiaPIofJG2SLwcNXd0y9TqcyAp6lr", "3lX8TIAXzCUurwip", new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {
                Log.i(MainActivity.this.getClass().getSimpleName(), accessToken.getAccess_token());
                Toast.makeText(MainActivity.this, "TOKEN : " + accessToken.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Log.e(MainActivity.this.getClass().getSimpleName(), error);
            }
        });
        ///my own
        sendButton.setOnClickListener(v -> {

            Intent intent = new Intent(getApplicationContext(),First_Page.class);
            startActivity(intent);

//            //Get Phone Number from User Input
//            phoneNumber = editTextPhoneNumber.getText().toString().trim();
//
//            if (TextUtils.isEmpty(phoneNumber)) {
//                editTextPhoneNumber.setError("Please Provide a Phone Number");
//                return;
//            }
//
//            //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO
//            LNMExpress lnmExpress = new LNMExpress(
//                    "174379",
//                    "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
//                    TransactionType.CustomerBuyGoodsOnline, // TransactionType.CustomerPayBillOnline  <- Apply any of these two
//                    "10",
//                    "254708374149",
//                    "174379",
//                    phoneNumber,
//                    "http://mycallbackurl.com/checkout.php",
//                    "001ABC",
//                    "Goods Payment"
//            );
//
//            //This is the
//            daraja.requestMPESAExpress(lnmExpress,
//                    new DarajaListener<LNMResult>() {
//                        @Override
//                        public void onResult(@NonNull LNMResult lnmResult) {
//                            Log.i(MainActivity.this.getClass().getSimpleName(), lnmResult.ResponseDescription);
//                        }
//
//                        @Override
//                        public void onError(String error) {
//                            Log.i(MainActivity.this.getClass().getSimpleName(), error);
//                        }
//                    }
//            );
        });
        //end of my own


//////end
    }
}