package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupGoalsAdapter;

import java.util.ArrayList;
import java.util.List;

public class GroupSavingToGoalsActivity extends AppCompatActivity {

    private List<GroupGoals> groupGoalsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GroupGoalsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_group_goals);

        recyclerView = (RecyclerView) findViewById(R.id.group_recycler_view);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addGroupGoalsIntent = new Intent(GroupSavingToGoalsActivity.this, AddGroupGoalsActivity.class);
                startActivity(addGroupGoalsIntent);
            }
        });

        mAdapter = new GroupGoalsAdapter(groupGoalsList, new GroupGoalsAdapter.OnGoalClickListener() {
            @Override
            public void onGoalClick(GroupGoals groupGoals) {
                Intent intent = new Intent(GroupSavingToGoalsActivity.this, AddGroupSavingsActivity.class);
                intent.putExtra("goalName", groupGoals.getName());
                startActivity(intent);
                finish();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        prepareGroupGoalsData();

    }

    private void prepareGroupGoalsData() {
        GroupGoals groupGoals = new GroupGoals(
                "Buy 5 Hives", "23/5/2018", "500000", "this is optional");
        groupGoalsList.add(groupGoals);

        groupGoals = new GroupGoals("Buy seeds", "23/5/2018", "703000", "notes");
        groupGoalsList.add(groupGoals);

        groupGoals = new GroupGoals("Buy seeds", "703000", "Salary", "notessssss");
        groupGoalsList.add(groupGoals);

        groupGoals = new GroupGoals("Buy seeds", "23/5/2018", "703000", "notedsss");
        groupGoalsList.add(groupGoals);

        groupGoals = new GroupGoals("Buy seeds", "23/5/2018", "703000", "noruuueue");
        groupGoalsList.add(groupGoals);

        mAdapter.notifyDataSetChanged();
    }
}
