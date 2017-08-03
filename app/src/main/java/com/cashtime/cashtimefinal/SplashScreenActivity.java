package com.cashtime.cashtimefinal;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withSplashTimeOut(5000)
                .withTargetActivity(HomeActivity.class)
                .withBackgroundColor(Color.parseColor("#074E72"))
               // .withLogo(R.mipmap.ic_launcher_round)
                .withHeaderText("Welcome")
                .withFooterText("Copyright CashTime 2017")
                .withBeforeLogoText("CashTime")
                .withAfterLogoText("Track Your Expenses");

        preferences = getSharedPreferences(RegisterActivity.PREF_NAME, 0);
        boolean isRegistered = preferences.getBoolean("isRegistered", false);

        if (isRegistered){
            config.withTargetActivity(GoalActivity.class);
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
