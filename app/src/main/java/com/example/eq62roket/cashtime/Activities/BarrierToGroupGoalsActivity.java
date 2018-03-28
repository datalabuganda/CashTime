package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupGoalsAdapter;

import java.util.List;

public class BarrierToGroupGoalsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GroupGoalsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_saving_to_goals);

        recyclerView = (RecyclerView) findViewById(R.id.group_recycler_view);

        new ParseHelper(BarrierToGroupGoalsActivity.this).getGroupGoalsFromParseDb(new ParseHelper.OnReturnedGroupGoalsListener() {
            @Override
            public void onResponse(List<GroupGoals> groupGoalsList) {
                mAdapter = new GroupGoalsAdapter(groupGoalsList, new GroupGoalsAdapter.OnGoalClickListener() {
                    @Override
                    public void onGoalClick(GroupGoals groupGoals) {
                        Intent addBarrierIntent = new Intent(BarrierToGroupGoalsActivity.this, AddBarrierActivity.class);
                        addBarrierIntent.putExtra("goalName", groupGoals.getName());
                        startActivity(addBarrierIntent);
                        finish();
                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(BarrierToGroupGoalsActivity.this);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(String error) {

            }
        });

    }
}
