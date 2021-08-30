package org.trustfuse.mpesa_stktrial.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;

import org.trustfuse.mpesa_stktrial.R;

public class Cart_frag extends Fragment {

    EditText editTextPhoneNumber;
    Button sendButton;
    Daraja daraja;
    String phoneNumber;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.cart_fragment,container,false);
        View view = inflater.inflate(R.layout.activity_main,container,false);
        sendButton = view.findViewById(R.id.sendButton);
        editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber);
        //start
        //Init Daraja
        //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO
        daraja = Daraja.with("EXvAiaPIofJG2SLwcNXd0y9TqcyAp6lr", "3lX8TIAXzCUurwip", new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {
                Log.i(getActivity().getClass().getSimpleName(), accessToken.getAccess_token());
                Toast.makeText(getActivity(), "TOKEN : " + accessToken.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Log.e(getActivity().getClass().getSimpleName(), error);
            }
        });
        ///my own
        sendButton.setOnClickListener(v -> {


            //Get Phone Number from User Input
            phoneNumber = editTextPhoneNumber.getText().toString().trim();

            if (TextUtils.isEmpty(phoneNumber)) {
                editTextPhoneNumber.setError("Please Provide a Phone Number");
                return;
            }
//
//            //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO
            LNMExpress lnmExpress = new LNMExpress(
                    "174379",
                    "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
                    TransactionType.CustomerBuyGoodsOnline, // TransactionType.CustomerPayBillOnline  <- Apply any of these two
                    "10",
                    "254708374149",
                    "174379",
                    phoneNumber,
                    "http://mycallbackurl.com/checkout.php",
                    "001ABC",
                    "Goods Payment"
            );

            //This is the
            daraja.requestMPESAExpress(lnmExpress,
                    new DarajaListener<LNMResult>() {
                        @Override
                        public void onResult(@NonNull LNMResult lnmResult) {
                            Log.i(getActivity().getClass().getSimpleName(), lnmResult.ResponseDescription);
                        }

                        @Override
                        public void onError(String error) {
                            Log.i(getActivity().getClass().getSimpleName(), error);
                        }
                    }
            );
        });
        //end of my own


//////end

        return view;
    }
}
