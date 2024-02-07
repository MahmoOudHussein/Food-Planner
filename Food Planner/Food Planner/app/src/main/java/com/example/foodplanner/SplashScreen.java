package com.example.foodplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        TextView appname = findViewById(R.id.food_planner);
        LottieAnimationView lottie = findViewById(R.id.lottie);

        // Animation for app name
        appname.animate().translationY(-1400).setDuration(2700).setStartDelay(0);

        // Animation for lottie
        lottie.animate().translationX(2000).setDuration(2000).setStartDelay(2900);

        // Delay for splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }

}