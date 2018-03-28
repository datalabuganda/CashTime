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

import com.example.eq62roket.cashtime.Activities.EditGroupSavingActivity;
import com.example.eq62roket.cashtime.Activities.GroupSavingToGoalsActivity;
import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupSavingsListener;
import com.example.eq62roket.cashtime.Models.GroupSavings;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupSavingsAdapter;

import java.util.List;


public class GroupSavingsFragment extends Fragment {

    private static final String TAG = "GroupSavingsFragment";

    private RecyclerView mRecyclerView;
    private GroupSavingsAdapter mGroupSavingsAdapter;

    private FloatingActionButton mFloatingActionButton;


    // TODO: 3/3/18 - limit group name to 30 characters.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View rootView = inflater.inflate(R.layout.fragment_group_savings, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);

        // add saving
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GroupSavingToGoalsActivity.class);
                startActivity(intent);

            }
        });

        new ParseHelper(getActivity()).getGroupSavingsFromParseDb(new OnReturnedGroupSavingsListener() {
            @Override
            public void onResponse(List<GroupSavings> groupSavingsList) {
                mGroupSavingsAdapter = new GroupSavingsAdapter(groupSavingsList, new GroupSavingsAdapter.OnGroupSavingClickListener() {
                    @Override
                    public void onGroupSavingClick(GroupSavings groupSavings) {
                        Intent intent = new Intent(getActivity(), EditGroupSavingActivity.class);
                        intent.putExtra("groupGoalName", groupSavings.getGoalName());
                        intent.putExtra("groupSavingAmount", groupSavings.getAmount());
                        intent.putExtra("groupSavingNote", groupSavings.getNotes());
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(mGroupSavingsAdapter);
            }

            @Override
            public void onFailure(String error) {

            }
        });

        return rootView;
    }
}
