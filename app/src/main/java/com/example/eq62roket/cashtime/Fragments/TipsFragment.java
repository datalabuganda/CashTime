package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Activities.GoalTipsActivity;
import com.example.eq62roket.cashtime.Activities.TipsToGroupGoalsActivity;
import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedTipsListener;
import com.example.eq62roket.cashtime.Models.Tip;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.TipsAdapter;

import java.util.List;


public class TipsFragment extends Fragment {

    private static final String TAG = "GroupSavingsFragment";

    private RecyclerView mRecyclerView;
    private TextView emptyView;
    private TipsAdapter mTipsAdapter;

    private FloatingActionButton mFloatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View rootView = inflater.inflate(R.layout.activity_tips, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TipsToGroupGoalsActivity.class);
                startActivity(intent);

            }
        });

        new ParseHelper(getActivity()).getAllTipsFromParseDb(new OnReturnedTipsListener() {
            @Override
            public void onResponse(List<Tip> tipsList) {
                if (tipsList.isEmpty()){
                    mRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    mTipsAdapter = new TipsAdapter(tipsList, new TipsAdapter.OnTipClickListener() {
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
                }
            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG, "onFailure: " + error);
            }
        });

        return rootView;
    }
}
