package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Activities.AddGroupGoalsActivity;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupGoalsAdapter;

import java.util.ArrayList;
import java.util.List;


public class GroupGoalsFragment extends Fragment implements SearchView.OnQueryTextListener{
    private static final String TAG = "GroupGoalsFragment";

    private List<GroupGoals> groupGoalsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GroupGoalsAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_group_goals, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.group_recycler_view);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addGroupGoalsIntent = new Intent(GroupGoalsFragment.this.getActivity(), AddGroupGoalsActivity.class);
                startActivity(addGroupGoalsIntent);
            }
        });

        mAdapter = new GroupGoalsAdapter(groupGoalsList, new GroupGoalsAdapter.OnGoalClickListener() {
            @Override
            public void onGoalClick(GroupGoals groupGoals) {
                Toast.makeText(getContext(), "<*>" + groupGoals.getName() + " Clicked <*>", Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        prepareGroupGoalsData();

        return rootView;
    }

    private void prepareGroupGoalsData() {
        GroupGoals groupGoals = new GroupGoals(
                "Buy 5 Hives", "23/5/2018", "500000", "this is optional");
        groupGoalsList.add(groupGoals);

        groupGoals = new GroupGoals("Buy seeds", "23/5/2018", "703000", "notes");
        groupGoalsList.add(groupGoals);

        groupGoals = new GroupGoals("Buy seeds", "703000", "Salary", "notessssss");
        groupGoalsList.add(groupGoals);

        groupGoals = new GroupGoals("Buy seeds", "23/5/2018", "703000", "notedsss");
        groupGoalsList.add(groupGoals);

        groupGoals = new GroupGoals("Buy seeds", "23/5/2018", "703000", "noruuueue");
        groupGoalsList.add(groupGoals);

        mAdapter.notifyDataSetChanged();
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
        for (GroupGoals groupGoals : groupGoalsList){
            String name = groupGoals.getName().toLowerCase();
            if (name.contains(newText)){
                newList.add(groupGoals);
            }
        }
        mAdapter.setFilter(newList);
        return true;
    }


}
