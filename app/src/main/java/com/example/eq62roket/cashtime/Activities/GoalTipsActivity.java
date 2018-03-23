package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.eq62roket.cashtime.Models.Tip;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GoalTipAdapter;

import java.util.ArrayList;
import java.util.List;

public class GoalTipsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private GoalTipAdapter mGoalTipAdapter;
    private List<Tip> tipList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_tips);

        Intent goalTipsIntent = getIntent();
        String nameOfGoal = goalTipsIntent.getStringExtra("goalName");

        mGoalTipAdapter = new GoalTipAdapter(tipList, new GoalTipAdapter.OnTipClickListener() {
            @Override
            public void onTipSelected(Tip tip) {
//                Intent editTipIntent = new Intent(GoalTipsActivity.this, EditTipActivity.class);
//                startActivity(editTipIntent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tips for " + nameOfGoal);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mGoalTipAdapter);

        prepareGoalTipData(nameOfGoal);

    }

    private void prepareGoalTipData(String nameOfGoal){
        Tip tip = null;
        List<Tip> tips = new ArrayList<>();

        tip = new Tip(
                "Buy BeeHive",
                "It is advisable to buy new beehives....this is because new beehives are new.",
                "23/11/2020"
        );
        tips.add(tip);

        tip = new Tip(
                "Buy a goat",
                "Female goats are good goats because...female goats have breasts that give sweet milk.",
                "12/01/2026"
        );
        tips.add(tip);

        tip = new Tip(
                "Increase Honey Sales",
                "Bees make more honey if the conditions are favorable...bees need water...flowers",
                "02/04/2020"
        );
        tips.add(tip);

        tip = new Tip(
                "Buy a goat",
                "Female goats are good goats because...female goats have breasts that give sweet milk.",
                "23/12/2021"
        );
        tips.add(tip);


        tip = new Tip(
                "Buy BeeHive",
                "It is advisable to buy new beehives....this is because new beehives are new.",
                "01/01/2070"
        );
        tips.add(tip);

        tip = new Tip(
                "Buy a goat",
                "Female goats are good goats because...female goats have breasts that give sweet milk.",
                "23/01/2103"
        );
        tips.add(tip);

        tip = new Tip(
                "Buy BeeHive",
                "It is advisable to buy new beehives....this is because new beehives are new.",
                "23/07/2020"
        );
        tips.add(tip);

        tip = new Tip(
                "Increase Honey Sales",
                "Bees make more honey if the conditions are favorable...bees need water...flowers",
                "23/06/2020"
        );
        tips.add(tip);

        for (Tip singleTip : tips){
            if (singleTip.getGoalName().equals(nameOfGoal)){
                tipList.add(singleTip);
            }
        }

        mGoalTipAdapter.notifyDataSetChanged();

    }
}
