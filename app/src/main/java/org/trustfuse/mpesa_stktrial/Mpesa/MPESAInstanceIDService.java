//package org.trustfuse.mpesa_stktrial.Mpesa;
//
//
//import android.content.SharedPreferences;
//
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceIdService;
//
//import org.trustfuse.mpesa_stktrial.MainActivity;
//import org.trustfuse.mpesa_stktrial.Mpesa_Test;
//
//import static android.content.Context.MODE_PRIVATE;
//
///**
// * Created by miles on 20/11/2017.
// */
//
//public class MPESAInstanceIDService extends FirebaseInstanceIdService {
//
//
//    @Override
//    public void onTokenRefresh() {
//        // Get updated InstanceID token.
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        SharedPreferences sharedPreferences = getSharedPreferences(Mpesa_Test.SHARED_PREFERENCES, MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("InstanceID", refreshedToken);
//        editor.commit();
//
//    }
//
//}