package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Activities.EditGroupGoalActivity;
import com.example.eq62roket.cashtime.Activities.GoalsToGroupActivity;
import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.AllGroupGoalsAdapter;

import java.util.ArrayList;
import java.util.List;


public class GroupGoalsFragment extends Fragment implements SearchView.OnQueryTextListener{
    private static final String TAG = "GroupGoalsFragment";

    List<GroupGoals> groupGoals = null;
    private RecyclerView recyclerView;
    private TextView emptyView;
    private AllGroupGoalsAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_group_goals, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.group_recycler_view);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addGroupGoalsIntent = new Intent(GroupGoalsFragment.this.getActivity(), GoalsToGroupActivity.class);
                startActivity(addGroupGoalsIntent);
            }
        });

        new ParseHelper(getActivity()).getGroupGoalsFromParseDb(new ParseHelper.OnReturnedGroupGoalsListener() {
            @Override
            public void onResponse(List<GroupGoals> groupGoalsList) {
                if (groupGoalsList.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }else {
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    groupGoals = groupGoalsList;

                    mAdapter = new AllGroupGoalsAdapter(getActivity(), groupGoalsList, new AllGroupGoalsAdapter.OnGoalClickListener() {
                        @Override
                        public void onGoalClick(GroupGoals groupGoals) {
                            Intent editGroupGoalIntent = new Intent(getActivity(), EditGroupGoalActivity.class);
                            editGroupGoalIntent.putExtra("groupGoalName", groupGoals.getName());
                            editGroupGoalIntent.putExtra("groupGoalAmount", groupGoals.getAmount());
                            editGroupGoalIntent.putExtra("groupGoalDeadline",groupGoals.getDueDate());
                            editGroupGoalIntent.putExtra("groupGoalNotes", groupGoals.getNotes());
                            editGroupGoalIntent.putExtra("groupGoalLocalUniqueID", groupGoals.getLocalUniqueID());
                            editGroupGoalIntent.putExtra("groupLocalUniqueID", groupGoals.getGroupLocalUniqueID());
                            startActivity(editGroupGoalIntent);
                        }
                    });

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
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


        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.group_goals, menu);

        MenuItem menuItem = menu.findItem(R.id.goals_search);

        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setBackgroundColor(Color.WHITE);
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setTextColor(Color.BLACK);
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(Color.GRAY);

        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        ArrayList<GroupGoals> newList = new ArrayList<>();
        for (GroupGoals groupGoals : groupGoals){
            String name = groupGoals.getName().toLowerCase();
            if (name.contains(newText)){
                newList.add(groupGoals);
            }
        }
        mAdapter.setFilter(newList);
        return true;
    }


}
