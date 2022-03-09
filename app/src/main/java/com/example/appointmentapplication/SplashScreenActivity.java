package com.example.appointmentapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.window.SplashScreen;

import com.example.appointmentapplication.Authentication.SignInActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.splash_screen_activity);

        Animation faeOut = new AlphaAnimation(1,0);
        faeOut.setInterpolator(new AccelerateInterpolator());
        faeOut.setStartOffset(500);
        faeOut.setDuration(1800);

        ImageView imageView = findViewById(R.id.iv_splash_screen);

        imageView.setAnimation(faeOut);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN_TIMEOUT);

    }
}