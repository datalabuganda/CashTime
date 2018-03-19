package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eq62roket.cashtime.Activities.AddGroupSavingsActivity;
import com.example.eq62roket.cashtime.Models.GroupSavings;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupSavingsAdapter;

import java.util.ArrayList;
import java.util.List;


public class GroupSavingsFragment extends Fragment {

    private static final String TAG = "GroupSavingsFragment";

    List<GroupSavings> mGroupSavings = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private GroupSavingsAdapter mGroupSavingsAdapter;
    private FloatingActionButton mFloatingActionButton;

    // TODO: 3/3/18 - limit group name to 30 characters.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_group_savings, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);

        // add saving
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddGroupSavingsActivity.class);
                startActivity(intent);
            }
        });


        mGroupSavingsAdapter = new GroupSavingsAdapter(mGroupSavings);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mGroupSavingsAdapter);

        // Add data to arrayList
        prepareSavingData();


        return rootView;
    }

    private void prepareSavingData(){

        GroupSavings groupSavings = new GroupSavings("Leon Bryn", 20000);
        mGroupSavings.add(groupSavings);

        groupSavings = new GroupSavings("etwin himself", 5000);
        mGroupSavings.add(groupSavings);

        groupSavings = new GroupSavings("Moses Ali", 5000);
        mGroupSavings.add(groupSavings);

        groupSavings = new GroupSavings("Hostin Dundey", 3990);
        mGroupSavings.add(groupSavings);

        mGroupSavingsAdapter.notifyDataSetChanged();

    }
}
