package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.TextView;

import com.example.eq62roket.cashtime.Activities.EditGroupSavingActivity;
import com.example.eq62roket.cashtime.Activities.GroupSavingToGoalsActivity;
import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupSavingsListener;
import com.example.eq62roket.cashtime.Models.GroupSavings;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupSavingsAdapter;

import java.util.ArrayList;
import java.util.List;


public class GroupSavingsFragment extends Fragment implements SearchView.OnQueryTextListener{

    private static final String TAG = "GroupSavingsFragment";

    private RecyclerView mRecyclerView;
    private TextView emptyView;
    private GroupSavingsAdapter mGroupSavingsAdapter;
    private List<GroupSavings> mGroupSavings = null;

    private FloatingActionButton mFloatingActionButton;


    // TODO: 3/3/18 - limit group name to 30 characters.

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View rootView = inflater.inflate(R.layout.fragment_group_savings, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);

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
                if (groupSavingsList.isEmpty()){
                    mRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }else {
                    mGroupSavings = groupSavingsList;
                    emptyView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    mGroupSavingsAdapter = new GroupSavingsAdapter(groupSavingsList, new GroupSavingsAdapter.OnGroupSavingClickListener() {
                        @Override
                        public void onGroupSavingClick(GroupSavings groupSavings) {
                            Intent intent = new Intent(getActivity(), EditGroupSavingActivity.class);
                            intent.putExtra("groupGoalName", groupSavings.getGoalName());
                            intent.putExtra("groupSavingAmount", groupSavings.getAmount());
                            intent.putExtra("groupSavingNote", groupSavings.getNotes());
                            intent.putExtra("groupSavingLocalUniqueID", groupSavings.getLocalUniqueID());
                            startActivity(intent);
                        }
                    });
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setAdapter(mGroupSavingsAdapter);
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.group_savings, menu);

        MenuItem menuItem = menu.findItem(R.id.group_savings_search);

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
        ArrayList<GroupSavings> newList = new ArrayList<>();
        if (mGroupSavings != null){
            for (GroupSavings groupSavings : mGroupSavings){
                String source = groupSavings.getIncomeSource().toLowerCase();
                String notes = groupSavings.getNotes().toLowerCase();
                String goalName = groupSavings.getGoalName().toLowerCase();
                String period = groupSavings.getPeriod().toLowerCase();
                if (source.contains(newText)){
                    newList.add(groupSavings);
                }else if (notes.contains(newText)){
                    newList.add(groupSavings);
                }else if (goalName.contains(newText)){
                    newList.add(groupSavings);
                }else if (period.contains(newText)){
                    newList.add(groupSavings);
                }
            }
            mGroupSavingsAdapter.setFilter(newList);
        }
        return true;
    }
}
