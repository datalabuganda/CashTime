package com.cashtime.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {

    ImageView imgIncome, imgExpenditure, imgGoal, imgAnalytics, imgReports, imgTips;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imgIncome = (ImageView) findViewById(R.id.imgIncome);
        imgExpenditure = (ImageView) findViewById(R.id.imgExpenditure);
        imgGoal = (ImageView) findViewById(R.id.imgGoal);
        imgAnalytics = (ImageView) findViewById(R.id.imgAnalytics);
        imgReports = (ImageView) findViewById(R.id.imgReports);
        imgTips = (ImageView) findViewById(R.id.imgTips);

        imgIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, IncomeActivity.class);
                startActivity(intent);
            }
        });

        imgExpenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ExpenditureActivity.class);
                startActivity(intent);
            }
        });

        imgGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, GoalListActivity.class);
                startActivity(intent);
            }
        });

        imgAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AnalyticsActivity.class);
                startActivity(intent);
            }
        });

        imgReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });

        imgTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TipsActivity.class);
                startActivity(intent);
            }
        });

    }
}
