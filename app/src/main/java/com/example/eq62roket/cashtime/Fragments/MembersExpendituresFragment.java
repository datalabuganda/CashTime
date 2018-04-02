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

import com.example.eq62roket.cashtime.Activities.AddGroupMembersExpendituresActivity;
import com.example.eq62roket.cashtime.Activities.EditGroupGoalActivity;
import com.example.eq62roket.cashtime.Activities.EditGroupMemberExpenditureActivity;
import com.example.eq62roket.cashtime.Activities.GroupMembersExpenditureListActivity;
import com.example.eq62roket.cashtime.Helper.ParseExpenditureHelper;
import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.Models.GroupMemberExpenditure;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupGoalsAdapter;
import com.example.eq62roket.cashtime.adapters.GroupMemberExpenditureAdapter;
import com.example.eq62roket.cashtime.adapters.GroupMembersIncomeAdapter;

import java.util.List;


public class MembersExpendituresFragment extends Fragment {
    FloatingActionButton fabMembersExpenditures;
    private static final String TAG = "GroupGoalsFragment";

    List<GroupMemberExpenditure> groupMemberExpenditures = null;
    private RecyclerView recyclerView;
    private GroupMemberExpenditureAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_members_expenditures, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.members_expenditures_recycler_view);

        fabMembersExpenditures = (FloatingActionButton) rootView.findViewById(R.id.fabMemberExpenditures);

        fabMembersExpenditures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MembersExpendituresFragment.this.getContext(),GroupMembersExpenditureListActivity.class);
                startActivity(intent);
            }
        });

        new ParseExpenditureHelper(getActivity()).getGroupMembersExpenditureFromParseDb(new ParseExpenditureHelper.OnReturnedGroupMembersExpenditureListener() {
            @Override
            public void onResponse(List<GroupMemberExpenditure> groupMemberExpenditureList) {
                groupMemberExpenditures = groupMemberExpenditureList;

                mAdapter = new GroupMemberExpenditureAdapter(groupMemberExpenditureList, new GroupMemberExpenditureAdapter.OnGoalClickListener() {
                    @Override
                    public void onGoalClick(GroupMemberExpenditure groupMemberExpenditure) {
                        Intent editGroupMemberExpenditureIntent = new Intent(getActivity(), EditGroupMemberExpenditureActivity.class);
                        editGroupMemberExpenditureIntent.putExtra("groupMembersExpenditureCategory", groupMemberExpenditure.getCategory());
                        editGroupMemberExpenditureIntent.putExtra("groupMembersExpenditureAmount", groupMemberExpenditure.getAmount());
                        editGroupMemberExpenditureIntent.putExtra("groupMembersExpenditureNotes",groupMemberExpenditure.getDueDate());
                        editGroupMemberExpenditureIntent.putExtra("groupMembersExpenditureDate", groupMemberExpenditure.getNotes());
                        editGroupMemberExpenditureIntent.putExtra("groupMembersExpenditureParseId", groupMemberExpenditure.getParseId());
                        startActivity(editGroupMemberExpenditureIntent);
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

}
