package com.cashtime.cashtimefinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cashtime.cashtimefinal.AnalyticsActivity;
import com.cashtime.cashtimefinal.R;

public class GoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoalActivity.this, AddGoalActivity.class);
                startActivity(intent);
            }
        });
    }
}
