package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eq62roket.cashtime.Activities.GoalTipsActivity;
import com.example.eq62roket.cashtime.Activities.TipsToGroupGoalsActivity;
import com.example.eq62roket.cashtime.Models.Tip;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.TipsAdapter;

import java.util.ArrayList;
import java.util.List;


public class TipsFragment extends Fragment {

    private static final String TAG = "GroupSavingsFragment";

    List<Tip> tipList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TipsAdapter mTipsAdapter;

    private FloatingActionButton mFloatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_tips, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);


        mTipsAdapter = new TipsAdapter(tipList, new TipsAdapter.OnTipClickListener() {
            @Override
            public void onTipSelected(Tip tip) {
                Intent goalTipsIntent = new Intent(getActivity(), GoalTipsActivity.class);
                goalTipsIntent.putExtra("goalName", tip.getGoalName());
                startActivity(goalTipsIntent);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mTipsAdapter);


        // add saving
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch to goals fragment
                Intent intent = new Intent(getActivity(), TipsToGroupGoalsActivity.class);
                startActivity(intent);

            }
        });

        // Add data to arrayList
        prepareTipData();


        return rootView;
    }

    private void prepareTipData(){
        Tip tip = null;
        tip = new Tip(
                "Buy BeeHive",
                "It is advisable to buy new beehives....this is because new beehives are new.",
                "23/11/2020"
        );
        tipList.add(tip);

        tip = new Tip(
                "Buy a goat",
                "Female goats are good goats because...female goats have breasts that give sweet milk.",
                "12/01/2026"
        );
        tipList.add(tip);

        tip = new Tip(
                "Increase Honey Sales",
                "Bees make more honey if the conditions are favorable...bees need water...flowers",
                "02/04/2020"
        );
        tipList.add(tip);

        tip = new Tip(
                "Buy a goat",
                "Female goats are good goats because...female goats have breasts that give sweet milk.",
                "23/12/2021"
        );
        tipList.add(tip);


        tip = new Tip(
                "Buy BeeHive",
                "It is advisable to buy new beehives....this is because new beehives are new.",
                "01/01/2070"
        );
        tipList.add(tip);

        tip = new Tip(
                "Buy a goat",
                "Female goats are good goats because...female goats have breasts that give sweet milk.",
                "23/01/2103"
        );
        tipList.add(tip);

        tip = new Tip(
                "Buy BeeHive",
                "It is advisable to buy new beehives....this is because new beehives are new.",
                "23/07/2020"
        );
        tipList.add(tip);

        tip = new Tip(
                "Increase Honey Sales",
                "Bees make more honey if the conditions are favorable...bees need water...flowers",
                "23/06/2020"
        );
        tipList.add(tip);

        mTipsAdapter.notifyDataSetChanged();
    }
}
