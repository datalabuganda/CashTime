package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.adapters.GoalListAdapter;
import com.example.eq62roket.CashTime.helper.ExpenditureCrud;
import com.example.eq62roket.CashTime.helper.GoalCrud;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Expenditure;
import com.example.eq62roket.CashTime.models.Goal;
import com.example.eq62roket.CashTime.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GoalsListActivity extends AppCompatActivity {

    private static final String TAG = "GoalActivity";

    private GoalCrud goalCrud;
    private UserCrud userCrud;
    private Expenditure expenditure;
    private Goal goal;

    private Date currentDate;
    private Date goalEndDate;

    Button btnPoints;

    ExpenditureCrud expenditureCrud;


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
        expenditureCrud = new ExpenditureCrud(this);
        expenditure = new Expenditure();
        final User user = userCrud.getLastUserInserted();
        goal = goalCrud.getLastInsertedGoal();


        ArrayList<Goal> goalArrayList = new ArrayList<>();
        goalArrayList = goalCrud.getAllGoals();


        GoalListAdapter goalListAdapter = new GoalListAdapter(this, R.layout.goal_list_adapter, goalArrayList);
        listView.setAdapter(goalListAdapter);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
        String formattedDate = df.format(c.getTime());

        try {
            currentDate = df.parse(formattedDate);
            goalEndDate = df.parse(goal.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( (goalCrud.getLastInsertedGoal().getCompleteStatus() == 0 && currentDate.before(goalEndDate))){
                    Toast.makeText(GoalsListActivity.this,
                            "You must complete the current goal to set another goal.",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(GoalsListActivity.this, AddGoalActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

}
