package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupGoalsAdapter;

import java.util.List;

public class GroupSavingToGoalsActivity extends AppCompatActivity {
    private static final String TAG = "GroupSavingToGoals";

    private RecyclerView recyclerView;
    private TextView emptyView;
    private GroupGoalsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_saving_to_goals);

        recyclerView = (RecyclerView) findViewById(R.id.group_recycler_view);
        emptyView = (TextView) findViewById(R.id.empty_view);

        new ParseHelper(this).getGroupGoalsFromParseDb(new ParseHelper.OnReturnedGroupGoalsListener() {
            @Override
            public void onResponse(List<GroupGoals> groupGoalsList) {
                if (groupGoalsList.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }else {
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    mAdapter = new GroupGoalsAdapter(GroupSavingToGoalsActivity.this, groupGoalsList, new GroupGoalsAdapter.OnGoalClickListener() {
                        @Override
                        public void onGoalClick(GroupGoals groupGoals) {
                            Intent intent = new Intent(GroupSavingToGoalsActivity.this, AddGroupSavingsActivity.class);
                            intent.putExtra("goalName", groupGoals.getName());
                            intent.putExtra("groupParseId", groupGoals.getGroupId());
                            intent.putExtra("groupGoalAmount", groupGoals.getAmount());
                            intent.putExtra("groupGoalParseId", groupGoals.getParseId());
                            intent.putExtra("groupGoalDueDate", groupGoals.getDueDate());
                            startActivity(intent);
                            finish();
                        }
                    });
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(GroupSavingToGoalsActivity.this);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    mAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG, "onFailure: " + error);
            }
        });
    }

}
