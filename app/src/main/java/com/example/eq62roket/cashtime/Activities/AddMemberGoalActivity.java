package com.example.eq62roket.cashtime.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.eq62roket.cashtime.R;

public class AddMemberGoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
    }
}
