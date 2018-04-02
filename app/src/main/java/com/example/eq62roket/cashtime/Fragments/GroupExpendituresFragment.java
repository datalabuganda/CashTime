package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eq62roket.cashtime.Activities.AddGroupExpenditureActivity;
import com.example.eq62roket.cashtime.Activities.EditGroupExpenditureActivity;
import com.example.eq62roket.cashtime.Activities.EditGroupGoalActivity;
import com.example.eq62roket.cashtime.Helper.ParseExpenditureHelper;
import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Models.GroupExpenditure;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupExpenditureAdapter;
import com.example.eq62roket.cashtime.adapters.GroupGoalsAdapter;

import java.util.List;


public class GroupExpendituresFragment extends Fragment implements SearchView.OnQueryTextListener{
    private static String TAG = "GroupExpendituresFragment";
    FloatingActionButton fabGroupExpenditures;

    List<GroupExpenditure> groupExpenditures = null;
    private RecyclerView recyclerView;
    private GroupExpenditureAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View rootView = inflater.inflate(R.layout.fragment_group_expenditures, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.group_expenditures_recycler_view);

        fabGroupExpenditures = (FloatingActionButton) rootView.findViewById(R.id.fabGroupExpenditures);

        fabGroupExpenditures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupExpendituresFragment.this.getContext(),AddGroupExpenditureActivity.class);
                startActivity(intent);
            }
        });

        new ParseExpenditureHelper(getActivity()).getGroupeExpenditureFromParseDb(new ParseExpenditureHelper.OnReturnedGroupExpenditureListener() {
            @Override
            public void onResponse(List<GroupExpenditure> groupExpendituresList) {
                groupExpenditures = groupExpendituresList;

                mAdapter = new GroupExpenditureAdapter(groupExpendituresList, new GroupExpenditureAdapter.OnGroupExpenditureClickListener() {
                    @Override
                    public void onGroupExpenditureClick(GroupExpenditure groupExpenditure) {
                        Intent editGroupExpenditureIntent = new Intent(getActivity(), EditGroupExpenditureActivity.class);
                        editGroupExpenditureIntent.putExtra("groupExpenditureCategory", groupExpenditure.getCategory());
                        editGroupExpenditureIntent.putExtra("groupExpenditureAmount", groupExpenditure.getAmount());
                        editGroupExpenditureIntent.putExtra("groupExpenditureDueDate",groupExpenditure.getDueDate());
                        editGroupExpenditureIntent.putExtra("groupExpenditureNotes", groupExpenditure.getNotes());
                        editGroupExpenditureIntent.putExtra("groupExpenditureParseId", groupExpenditure.getParseId());
                        startActivity(editGroupExpenditureIntent);
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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
