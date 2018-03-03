package com.example.eq62roket.cashtime.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.eq62roket.cashtime.R;

public class MemberSavingsDetail extends AppCompatActivity {

    private static final String TAG = "MemberSavingsDetail";

    private TextView goalName, amountSaved, amountRemaining;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_savings_detail);

        String name = getIntent().getStringExtra("name");
        String amount = getIntent().getStringExtra("amount");
        Log.d(TAG, "onCreate: " + amount);

        amountSaved = (TextView) findViewById(R.id.amountSaved);
        amountRemaining = (TextView) findViewById(R.id.amountRemaining);
        goalName = (TextView) findViewById(R.id.goalName);

        goalName.setText("Use Cash Time App");
        amountSaved.setText(amount);
        int remaining = 100000 - Integer.parseInt(amount);
        amountRemaining.setText(String.valueOf(remaining));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(name);
    }
}
