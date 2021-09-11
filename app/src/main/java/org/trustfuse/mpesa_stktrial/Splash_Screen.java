package org.trustfuse.mpesa_stktrial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash_Screen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2500;
    Animation bouncy;
    TextView logo,services;
    ImageView splash_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash__screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),Main_Menu.class));
            }
        },SPLASH_TIME_OUT);
        splash_logo = findViewById(R.id.splash_logo);
        services = findViewById(R.id.splash_services);
        logo = findViewById(R.id.splash_welcome);

        bouncy = AnimationUtils.loadAnimation(this,R.anim.bounce);
        splash_logo.setAnimation(bouncy);
        services.setAnimation(bouncy);
        logo.setAnimation(bouncy);
    }
}