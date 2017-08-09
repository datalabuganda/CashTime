package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;

import java.text.DecimalFormat;

public class GoalDetailActivity extends AppCompatActivity {

    private static final String TAG = "GoalDetailActivity";

    TextView tvGoalName, tvEndDate, tvGoalAmount, tvAmountSaved, tvAmountRemaining, tvUserPoints;
    FloatingActionButton edit;

    DecimalFormat formatter;
    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_detail);

        tvGoalName = (TextView) findViewById(R.id.tvGoalName);
        tvEndDate = (TextView) findViewById(R.id.tvEndDate);
        tvGoalAmount = (TextView) findViewById(R.id.tvGoalAmount);
        tvAmountSaved = (TextView) findViewById(R.id.tvAmountSaved);
        tvAmountRemaining = (TextView) findViewById(R.id.tvAmountRemaining);
        tvUserPoints = (TextView) findViewById(R.id.tvUserPoints);

        edit = (FloatingActionButton) findViewById(R.id.edit);

        formatter = new DecimalFormat("#,###,###");
        sqLiteHelper = new SQLiteHelper(this);

        // Receive Data from Intent
        Intent intent = this.getIntent();
        final String goal_name = intent.getStringExtra("GOAL_NAME");
        final int goal_amount = intent.getIntExtra("GOAL_AMOUNT", 0);
        final String goal_endDate = intent.getStringExtra("GOAL_ENDDATE");
        final long goal_id = intent.getLongExtra("GOAL_ID", 0);
        final long user_points = intent.getLongExtra("USER_POINTS", 0);


        // Amount saved on goal so far
        int goal_savings = sqLiteHelper.addAllSavings();
        int goal_amount_remaining =  goal_amount - goal_savings;

        tvGoalName.setText(goal_name);
        tvEndDate.setText("By: " + goal_endDate);
        tvGoalAmount.setText("Goal Cost  Shs: " + formatter.format(goal_amount));
        tvAmountSaved.setText("Amount Saved So far Shs: " + formatter.format(goal_savings));
        tvAmountRemaining.setText("Amount remaining Shs: " + formatter.format(goal_amount_remaining));
        tvUserPoints.setText("Your Current points are: " + user_points);

        // starts edit goal activity
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(GoalDetailActivity.this, EditGoalActivity.class);
                intent1.putExtra("GOAL_ID", goal_id);
                intent1.putExtra("GOAL_NAME", goal_name);
                intent1.putExtra("GOAL_AMOUNT", goal_amount);
                intent1.putExtra("GOAL_ENDDATE", goal_endDate);
                startActivity(intent1);
            }
        });
    }


}
