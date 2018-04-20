package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedTipsListener;
import com.example.eq62roket.cashtime.Models.Tip;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GoalTipAdapter;

import java.util.List;

public class GoalTipsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private GoalTipAdapter mGoalTipAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_tips);

        Intent goalTipsIntent = getIntent();
        final String nameOfGoal = goalTipsIntent.getStringExtra("goalName");

        new ParseHelper(GoalTipsActivity.this).getTipsOfParticularGoalFromParseDb(nameOfGoal, new OnReturnedTipsListener() {
            @Override
            public void onResponse(List<Tip> tipsList) {
                mGoalTipAdapter = new GoalTipAdapter(tipsList, new GoalTipAdapter.OnTipClickListener() {
                    @Override
                    public void onTipSelected(Tip tip) {
                        Intent editTipIntent = new Intent(GoalTipsActivity.this, EditTipActivity.class);
                        editTipIntent.putExtra("nameOfGoal", tip.getGoalName());
                        editTipIntent.putExtra("tipText", tip.getIntroText());
                        editTipIntent.putExtra("tipAddDate", tip.getDateAdded());
                        editTipIntent.putExtra("tipLocalUniqueID", tip.getLocalUniqueID());
                        startActivity(editTipIntent);
                        finish();
                    }
                });

                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setTitle("Tips for " + nameOfGoal);
                setSupportActionBar(toolbar);

                mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(GoalTipsActivity.this);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(mGoalTipAdapter);

            }

            @Override
            public void onFailure(String error) {

            }
        });

    }
}
