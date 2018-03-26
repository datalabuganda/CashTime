package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupGoalsAdapter;

import java.util.ArrayList;
import java.util.List;

public class TipsToGroupGoalsActivity extends AppCompatActivity {

    private List<GroupGoals> groupGoalsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GroupGoalsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_saving_to_goals);

        recyclerView = (RecyclerView) findViewById(R.id.group_recycler_view);

        mAdapter = new GroupGoalsAdapter(groupGoalsList, new GroupGoalsAdapter.OnGoalClickListener() {
            @Override
            public void onGoalClick(GroupGoals groupGoals) {
                Intent addTipsIntent = new Intent(TipsToGroupGoalsActivity.this, AddTipsActivity.class);
                addTipsIntent.putExtra("goalName", groupGoals.getName());
                startActivity(addTipsIntent);
                finish();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

//        prepareGroupGoalsData();

    }

//    private void prepareGroupGoalsData() {
//        GroupGoals groupGoals = new GroupGoals(
//                "Buy 5 Hives", "23/5/2018", "500000", "this is optional", "incomplete");
//        groupGoalsList.add(groupGoals);
//
//        groupGoals = new GroupGoals("Buy seeds", "23/5/2018", "703000", "notes", "incomplete");
//        groupGoalsList.add(groupGoals);
//
//        groupGoals = new GroupGoals("Buy seeds", "14/3/1890", "23000000", "notessssss", "incomplete");
//        groupGoalsList.add(groupGoals);
//
//        groupGoals = new GroupGoals("Buy seeds", "23/5/2018", "703000", "notedsss", "incomplete");
//        groupGoalsList.add(groupGoals);
//
//        groupGoals = new GroupGoals("Buy seeds", "23/5/2018", "703000", "noruuueue", "incomplete");
//        groupGoalsList.add(groupGoals);
//
//        mAdapter.notifyDataSetChanged();
//    }
}
