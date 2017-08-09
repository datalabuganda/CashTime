package com.example.eq62roket.CashTime.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;



public class SplashScreenActivity extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash_screen);
        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(RegisterActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#074E72"))
//                .withLogo(R.mipmap.logo)
                .withHeaderText("Welcome")
                .withFooterText("Copyright CashTime 2017")
                .withBeforeLogoText("CashTime")
                .withAfterLogoText("Track Your Expenses");

        preferences = getSharedPreferences(RegisterActivity.PREF_NAME, 0);
        boolean isRegistered = preferences.getBoolean("isRegistered", false);

        if (isRegistered) {
            config.withTargetActivity(HomeActivity.class);
        }


        // set Text Color
        config.getHeaderTextView().setTextColor(Color.WHITE);
        config.getFooterTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextColor(Color.WHITE);
        config.getBeforeLogoTextView().setTextColor(Color.WHITE);

        // set to view
        View view = config.create();

        //set view to content view
        setContentView(view);

    }
}
