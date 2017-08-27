package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.adapters.GoalListAdapter;
import com.example.eq62roket.CashTime.helper.GoalCrud;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Expenditure;
import com.example.eq62roket.CashTime.models.Goal;
import com.example.eq62roket.CashTime.models.User;

import java.util.ArrayList;

public class GoalsListActivity extends AppCompatActivity {

    private static final String TAG = "GoalActivity";

    private GoalCrud goalCrud;
    private UserCrud userCrud;
    private Expenditure expenditure;
    private Goal goal;
    Button btnPoints;

    SQLiteHelper sqliteHelper;


    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listView);
        btnPoints = (Button) findViewById(R.id.btnSavings);
        goalCrud = new GoalCrud(this);
        userCrud = new UserCrud(this);
        sqliteHelper = new SQLiteHelper(this);
        expenditure = new Expenditure();
        final User user = userCrud.getLastUserInserted();

        // get amount saved on goal and goal amount.
        /*final int goal_amount = goalCrud.getLastInsertedGoal().getAmount();
        final int goal_saving = sqliteHelper.addAllSavings();
        final int extraSavings = goal_saving - goal_amount;*/


        ArrayList<Goal> goalArrayList = new ArrayList<>();
        goalArrayList = goalCrud.getAllGoals();

        /*for(int i=0; i < goalArrayList.size(); i++){
            Log.d(TAG, "goal status  " + goalArrayList.get(i).getCompleteStatus());
            Log.d(TAG, "goal id  " + goalArrayList.get(i).getId());
            Log.d(TAG, "goal start date " + goalCrud.getLastInsertedGoal().getStartDate());
        }*/

        GoalListAdapter goalListAdapter = new GoalListAdapter(this, R.layout.goal_list_adapter, goalArrayList);
        listView.setAdapter(goalListAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goalCrud.getLastInsertedGoal().getCompleteStatus() != 1){
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
