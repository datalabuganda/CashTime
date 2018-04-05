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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.eq62roket.cashtime.Activities.AddGroupIncomeActivity;
import com.example.eq62roket.cashtime.Activities.EditGroupIncomeActivity;
import com.example.eq62roket.cashtime.Activities.IncomeToGroupActivity;
import com.example.eq62roket.cashtime.Helper.ParseIncomeHelper;
import com.example.eq62roket.cashtime.Models.GroupIncome;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupIncomeAdapter;

import java.util.ArrayList;
import java.util.List;


public class GroupIncomeFragment extends Fragment implements SearchView.OnQueryTextListener{
    private static final String TAG = "GroupIncomeFragment";
    FloatingActionButton fabGroupIncome;

    List<GroupIncome> groupIncome = null;
    private RecyclerView recyclerView;
    private GroupIncomeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View rootView = inflater.inflate(R.layout.fragment_group_income, container, false);
        fabGroupIncome = (FloatingActionButton) rootView.findViewById(R.id.fabGroupIncome);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.group_income_recycler_view);


        fabGroupIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupIncomeFragment.this.getContext(),IncomeToGroupActivity.class);
                startActivity(intent);
            }
        });

        new ParseIncomeHelper(getActivity()).getGroupIncomeFromParseDb(new ParseIncomeHelper.OnReturnedGroupIncomeListener() {
            @Override
            public void onResponse(List<GroupIncome> groupIncomeList) {
                groupIncome = groupIncomeList;

                mAdapter = new GroupIncomeAdapter(groupIncomeList, new GroupIncomeAdapter.OnGroupClickListener() {
                    @Override
                    public void onGroupClick(GroupIncome groupIncome) {
                        Intent editGroupIncomeIntent = new Intent(getActivity(), EditGroupIncomeActivity.class);
                        editGroupIncomeIntent.putExtra("groupIncomeSource", groupIncome.getSource());
                        editGroupIncomeIntent.putExtra("groupIncomeAmount", groupIncome.getAmount());
                        editGroupIncomeIntent.putExtra("groupIncomePeriod",groupIncome.getPeriod());
                        editGroupIncomeIntent.putExtra("groupIncomeNotes", groupIncome.getNotes());
                        editGroupIncomeIntent.putExtra("groupIncomeParseId", groupIncome.getParseId());
                        startActivity(editGroupIncomeIntent);
                        getActivity().finish();
                    }

                });

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);

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
        inflater.inflate(R.menu.group_income, menu);

        MenuItem menuItem = menu.findItem(R.id.group_income_search);

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
        ArrayList<GroupIncome> newList = new ArrayList<>();
        for (GroupIncome groupIncome : groupIncome){
            String source = groupIncome.getSource().toLowerCase();
            String notes = groupIncome.getNotes().toLowerCase();
            String groupName = groupIncome.getGroupName().toLowerCase();
            String period = groupIncome.getPeriod().toLowerCase();
            if (source.contains(newText)){
                newList.add(groupIncome);
            }else if (notes.contains(newText)){
                newList.add(groupIncome);
            }else if (groupName.contains(newText)){
                newList.add(groupIncome);
            }else if (period.contains(newText)){
                newList.add(groupIncome);
            }
        }
        mAdapter.setFilter(newList);
        return true;
    }
}
