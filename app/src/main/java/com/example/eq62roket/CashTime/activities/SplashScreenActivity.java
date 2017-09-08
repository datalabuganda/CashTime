package com.example.eq62roket.CashTime.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.GoalCrud;

import gr.net.maroulis.library.EasySplashScreen;



public class SplashScreenActivity extends Activity {

    SharedPreferences preferences;
    GoalCrud goalCrud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash_screen);
        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(RegisterActivity.class)
                .withSplashTimeOut(500)
                .withBackgroundColor(Color.parseColor("#074E72"))
                .withLogo(R.drawable.cashtime_logo)
                .withFooterText("Copyright CashTime 2017")
                .withBeforeLogoText("CashTime")
                .withAfterLogoText("Track Your Expenses Visually");

        preferences = getSharedPreferences(RegisterActivity.PREF_NAME, 0);
        boolean isRegistered = preferences.getBoolean("isRegistered", false);

        goalCrud = new GoalCrud(this);

        if (isRegistered) {
            if (goalCrud.getAllGoals().size() == 0){
                config.withTargetActivity(AddGoalActivity.class);
            }else {
                config.withTargetActivity(HomeDrawerActivity.class);
            }

        }


        // set Text Color
        config.getFooterTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextColor(Color.WHITE);
        config.getBeforeLogoTextView().setTextColor(Color.WHITE);

        // set to view
        View view = config.create();

        //set view to content view
        setContentView(view);

    }
}
