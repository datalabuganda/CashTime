package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.eq62roket.cashtime.R;

public class GroupGoalsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_goals);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);

        // switch to GroupSavingToGoalsActivity
        Intent intent = new Intent(GroupGoalsActivity.this, GroupSavingToGoalsActivity.class);
        startActivity(intent);

    }
}
