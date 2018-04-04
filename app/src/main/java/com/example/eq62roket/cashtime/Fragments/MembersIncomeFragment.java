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

import com.example.eq62roket.cashtime.Activities.EditGroupMemberIncomeActivity;
import com.example.eq62roket.cashtime.Activities.GroupMembersIncomeListActivity;
import com.example.eq62roket.cashtime.Helper.ParseIncomeHelper;
import com.example.eq62roket.cashtime.Models.MembersIncome;
import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupMembersIncomeAdapter;

import java.util.List;


public class MembersIncomeFragment extends Fragment {
    private static final String TAG = "GroupMembersActivity";

    private List<User> mGroupMemberUsers = null;
    private RecyclerView mRecyclerView;
    FloatingActionButton fabMembersIncome;

    List<MembersIncome> membersIncomes = null;
    private GroupMembersIncomeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_members_income, container, false);
        fabMembersIncome = (FloatingActionButton) rootView.findViewById(R.id.fabMembersIncome);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.members_income_recycler_view);

        fabMembersIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MembersIncomeFragment.this.getContext(),GroupMembersIncomeListActivity.class);
                startActivity(intent);
            }
        });

        new ParseIncomeHelper(getActivity()).getGroupMemberIncomeMemberFromParseDb(new ParseIncomeHelper.OnReturnedGroupMemberIncomeListener() {
            @Override
            public void onResponse(List<MembersIncome> groupMembersIncomeList) {
                membersIncomes = groupMembersIncomeList;

                mAdapter = new GroupMembersIncomeAdapter(groupMembersIncomeList, new GroupMembersIncomeAdapter.OnGroupMemberClickListener() {
                    @Override
                    public void onGroupMemberClick(MembersIncome groupMemberIncome) {
                        Intent editGroupMemberIncomeIntent = new Intent(getActivity(), EditGroupMemberIncomeActivity.class);
                        editGroupMemberIncomeIntent.putExtra("groupMemberIncomeSource", groupMemberIncome.getSource());
                        editGroupMemberIncomeIntent.putExtra("groupMemberIncomeAmount", groupMemberIncome.getAmount());
                        editGroupMemberIncomeIntent.putExtra("groupMemberIncomePeriod",groupMemberIncome.getDueDate());
                        editGroupMemberIncomeIntent.putExtra("groupMemberIncomeNotes", groupMemberIncome.getNotes());
                        editGroupMemberIncomeIntent.putExtra("groupMemberIncomeParseId", groupMemberIncome.getParseId());
                        startActivity(editGroupMemberIncomeIntent);
                        getActivity().finish();
                    }

                });

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                mAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG, "onFailure: " + error);
            }
        });


        return rootView;
    }

}
