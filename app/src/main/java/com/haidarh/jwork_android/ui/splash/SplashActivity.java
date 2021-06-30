package com.haidarh.jwork_android.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.haidarh.jwork_android.R;
import com.haidarh.jwork_android.ui.login.LoginActivity;

/**
 * Activity Splash Screen, duh.
 *
 * @author Haidar Hanif
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * The Handler. Melakukan delay 3 detik lalu intent ke Login Activity.
     */
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}