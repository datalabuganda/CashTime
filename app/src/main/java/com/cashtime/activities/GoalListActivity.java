package com.cashtime.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.cashtime.helper.GoalCrud;
import com.cashtime.helper.GoalListAdapter;
import com.cashtime.models.Goal;

import java.util.ArrayList;

public class GoalListActivity extends AppCompatActivity {

    private static final String TAG = "GoalActivity";

    private GoalCrud goalCrud;


    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_list);

        listView = (ListView) findViewById(R.id.listView);
        goalCrud = new GoalCrud(this);

        ArrayList<Goal> goalArrayList = new ArrayList<>();
        goalArrayList = goalCrud.getAllGoals();

        GoalListAdapter goalListAdapter = new GoalListAdapter(this, R.layout.goal_list_adapter, goalArrayList);
        listView.setAdapter(goalListAdapter);

    }
}
