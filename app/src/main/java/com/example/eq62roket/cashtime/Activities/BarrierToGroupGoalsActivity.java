package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupGoalsAdapter;

import java.util.List;

public class BarrierToGroupGoalsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView emptyView;
    private GroupGoalsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barrier_to_group_goals);

        recyclerView = (RecyclerView) findViewById(R.id.group_recycler_view);
        emptyView = (TextView) findViewById(R.id.empty_view);

        new ParseHelper(BarrierToGroupGoalsActivity.this).getAllFailedGroupGoalsFromParseDb(new ParseHelper.OnReturnedGroupGoalsListener() {
            @Override
            public void onResponse(List<GroupGoals> groupGoalsList) {
                if (groupGoalsList.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }else {
                    mAdapter = new GroupGoalsAdapter(BarrierToGroupGoalsActivity.this, groupGoalsList, new GroupGoalsAdapter.OnGoalClickListener() {
                        @Override
                        public void onGoalClick(GroupGoals groupGoals) {
                            Intent addBarrierIntent = new Intent(BarrierToGroupGoalsActivity.this, AddBarrierActivity.class);
                            addBarrierIntent.putExtra("goalName", groupGoals.getName());
                            addBarrierIntent.putExtra("groupLocalUniqueID", groupGoals.getGroupLocalUniqueID());
                            addBarrierIntent.putExtra("groupGoalLocalUniqueID", groupGoals.getLocalUniqueID());
                            startActivity(addBarrierIntent);
                            finish();
                        }
                    });
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(BarrierToGroupGoalsActivity.this);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });

    }
}
