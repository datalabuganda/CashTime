package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.adapters.GoalListAdapter;
import com.example.eq62roket.CashTime.helper.GoalCrud;
import com.example.eq62roket.CashTime.models.Goal;

import java.util.ArrayList;

public class GoalsListActivity extends AppCompatActivity {

    private static final String TAG = "GoalActivity";

    private GoalCrud goalCrud;

    SharedPreferences preferences;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listView);
        goalCrud = new GoalCrud(this);

        ArrayList<Goal> goalArrayList = new ArrayList<>();
        goalArrayList = goalCrud.getAllGoals();

        GoalListAdapter goalListAdapter = new GoalListAdapter(this, R.layout.goal_list_adapter, goalArrayList);
        listView.setAdapter(goalListAdapter);

        preferences = getSharedPreferences(GoalDetailActivity.PREF_NAME, 0);
        final boolean goalOngoing = preferences.getBoolean("goalOngoing", false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goalOngoing){
                    Toast.makeText(GoalsListActivity.this,
                            "You must complete the current goal to set another goal.",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(GoalsListActivity.this, AddGoalActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
