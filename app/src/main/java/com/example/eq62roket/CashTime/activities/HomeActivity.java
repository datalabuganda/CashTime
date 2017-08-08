package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.eq62roket.CashTime.R;

public class HomeActivity extends AppCompatActivity {

    ImageView imgGoal, imgIncome, imgExpenditure, imgAnalytics, imgReports, imgTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        imgGoal = (ImageView) findViewById(R.id.imgGoals);
        imgIncome = (ImageView) findViewById(R.id.imgIncome);
        imgExpenditure = (ImageView) findViewById(R.id.imgExpenditure);
        imgAnalytics = (ImageView) findViewById(R.id.imgAnalytics);
        imgTips = (ImageView) findViewById(R.id.imgTips);
        imgReports = (ImageView) findViewById(R.id.imgReport);
        //final Button btnGoal = (Button) findViewById(R.id.btnAdd);
        //final ListView listView = (ListView)findViewById(R.id.listView);

        imgGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeGoalintent = new Intent(HomeActivity.this, GoalsListActivity.class);
                HomeActivity.this.startActivity(HomeGoalintent);
            }
        });

        imgIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeIncomeintent = new Intent(HomeActivity.this, IncomeActivity.class);
                HomeActivity.this.startActivity(HomeIncomeintent);
            }
        });

        imgExpenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeExpenditureintent = new Intent(HomeActivity.this, ExpenditureActivity.class);
                HomeActivity.this.startActivity(HomeExpenditureintent);
            }
        });

        imgAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeAnalyticsintent = new Intent(HomeActivity.this, AnalysisActivity.class);
                HomeActivity.this.startActivity(HomeAnalyticsintent);
            }
        });

        imgReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeReportsintent = new Intent(HomeActivity.this, ReportActivity.class);
                HomeActivity.this.startActivity(HomeReportsintent);
            }
        });

        imgTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeTipsintent = new Intent(HomeActivity.this, TipsActivity.class);
                HomeActivity.this.startActivity(HomeTipsintent);
            }
        });
    }
}
