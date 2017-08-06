package com.cashtime.cashtimefinal;

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
        String goal_amount = intent.getExtras().getString("GOAL_AMOUNT");
        String goal_endDate = intent.getExtras().getString("GOAL_ENDDATE");

        // Amount saved on goal so far
        String goal_savings = String.valueOf(formatter.format(1000000));
//        String goal_amount_remaining = String.valueOf(
//                Integer.parseInt(goal_amount) - Integer.parseInt(goal_savings)
//        );

        tvGoalName.setText(goal_name);
        tvEndDate.setText("By: " + goal_endDate);
        tvGoalAmount.setText("Goal Cost:  " + goal_amount);
        tvAmountSaved.setText("Amount Saved So far: " + goal_savings);
//        tvAmountRemaining.setText("Amount remaining: " + goal_amount_remaining);
    }
}
