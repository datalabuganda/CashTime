package com.cashtime.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class GoalDetailActivity extends AppCompatActivity {

    TextView tvGoalName, tvEndDate, tvGoalAmount, tvAmountSaved, tvAmountRemaining;

    DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_detail);

        tvGoalName = (TextView) findViewById(R.id.tvGoalName);
        tvEndDate = (TextView) findViewById(R.id.tvEndDate);
        tvGoalAmount = (TextView) findViewById(R.id.tvGoalAmount);
        tvAmountSaved = (TextView) findViewById(R.id.tvAmountSaved);
        tvAmountRemaining = (TextView) findViewById(R.id.tvAmountRemaining);

        formatter = new DecimalFormat("#,###,###");

        // Receive Data from Intent
        Intent intent = this.getIntent();
        String goal_name = intent.getExtras().getString("GOAL_NAME");
        int goal_amount = intent.getExtras().getInt("GOAL_AMOUNT");
        String goal_endDate = intent.getExtras().getString("GOAL_ENDDATE");


        // Amount saved on goal so far
        int goal_savings = 1000000;
        int goal_amount_remaining =  goal_amount - goal_savings;

        tvGoalName.setText(goal_name);
        tvEndDate.setText("By: " + goal_endDate);
        tvGoalAmount.setText("Goal Cost  Shs: " + formatter.format(goal_amount));
        tvAmountSaved.setText("Amount Saved So far Shs: " + formatter.format(goal_savings));
        tvAmountRemaining.setText("Amount remaining: Shs:" + formatter.format(goal_amount_remaining));
    }
}
