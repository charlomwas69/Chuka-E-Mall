package org.trustfuse.mpesa_stktrial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.trustfuse.mpesa_stktrial.Fragments.Account_frag;
import org.trustfuse.mpesa_stktrial.Fragments.Cart_frag;
import org.trustfuse.mpesa_stktrial.Fragments.Categories_frag;
import org.trustfuse.mpesa_stktrial.Fragments.Home_frag;

public class Main_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("TARGET_FRAGMENT");
//            Toast.makeText(this, "VALUE: " + value, Toast.LENGTH_LONG).show();
            if (value.equals("ACCOUNT_FRAGMENT")){
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Account_frag()).commit();
                return ;
            }
//            if (value.equals("ACCOUNT_FRAGMENT")){
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Account_frag()).commit();
//                return ;
//            }
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Home_frag()).commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selected_fragment = null;

                    switch (item.getItemId()){
                        case R.id.Home_nav:
                            selected_fragment = new Home_frag();
                            break;
                        case R.id.Categories_nav:
                            selected_fragment = new Categories_frag();
                            break;
                        case R.id.Cart_nav:
                            selected_fragment = new Cart_frag();
                            break;
                        case R.id.Account_nav:
                            selected_fragment = new Account_frag();
                            break;
                    }
                    assert selected_fragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,selected_fragment).commit();

                    return true;
                }
            };
}