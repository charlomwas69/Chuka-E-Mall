package org.trustfuse.mpesa_stktrial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Home_frag()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,selected_fragment).commit();

                    return true;
                }
            };
}