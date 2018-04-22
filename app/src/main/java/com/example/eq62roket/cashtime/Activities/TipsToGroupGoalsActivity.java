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

public class TipsToGroupGoalsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GroupGoalsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_saving_to_goals);

        recyclerView = (RecyclerView) findViewById(R.id.group_recycler_view);

        new ParseHelper(TipsToGroupGoalsActivity.this).getGroupGoalsFromParseDb(new ParseHelper.OnReturnedGroupGoalsListener() {
            @Override
            public void onResponse(List<GroupGoals> groupGoalsList) {
                mAdapter = new GroupGoalsAdapter(TipsToGroupGoalsActivity.this, groupGoalsList, new GroupGoalsAdapter.OnGoalClickListener() {
                    @Override
                    public void onGoalClick(GroupGoals groupGoals) {
                        Intent addTipsIntent = new Intent(TipsToGroupGoalsActivity.this, AddTipsActivity.class);
                        addTipsIntent.putExtra("goalName", groupGoals.getName());
                        addTipsIntent.putExtra("groupGoalLocalUniqueID", groupGoals.getLocalUniqueID());
                        addTipsIntent.putExtra("groupLocalUniqueID", groupGoals.getGroupLocalUniqueID());
                        startActivity(addTipsIntent);
                        finish();
                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TipsToGroupGoalsActivity.this);
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
