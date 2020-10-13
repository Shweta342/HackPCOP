package com.example.hack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class Intro extends AppCompatActivity {

ImageView logo,bg;
LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        logo = findViewById(R.id.logo);
        bg = findViewById(R.id.img);
        lottieAnimationView = findViewById(R.id.Animation);
        logo.animate().translationX(1400).setDuration(1000).setStartDelay(4000);
        bg.animate().translationX(-1600).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationX(1400).setDuration(1000).setStartDelay(4000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent =  new Intent(Intro.this, LoginPage.class);
                Intro.this.startActivity(mainIntent);
                Intro.this.finish();
            }
        },3000);

    }
}
