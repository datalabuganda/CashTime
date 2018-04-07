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

import com.example.eq62roket.cashtime.Activities.EditGroupMemberIncomeActivity;
import com.example.eq62roket.cashtime.Activities.GroupMembersIncomeListActivity;
import com.example.eq62roket.cashtime.Helper.ParseIncomeHelper;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.Models.MembersIncome;
import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupMembersIncomeAdapter;

import java.util.ArrayList;
import java.util.List;


public class MembersIncomeFragment extends Fragment implements SearchView.OnQueryTextListener{
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
                        editGroupMemberIncomeIntent.putExtra("memberIncomeSource", groupMemberIncome.getSource());
                        editGroupMemberIncomeIntent.putExtra("memberIncomeAmount", groupMemberIncome.getAmount());
                        editGroupMemberIncomeIntent.putExtra("memberIncomePeriod",groupMemberIncome.getPeriod());
                        editGroupMemberIncomeIntent.putExtra("memberIncomeNotes", groupMemberIncome.getNotes());
                        editGroupMemberIncomeIntent.putExtra("memberParseId", groupMemberIncome.getParseId());
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.member_income, menu);

        MenuItem menuItem = menu.findItem(R.id.members_income_search);

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
        ArrayList<MembersIncome> newList = new ArrayList<>();
        for (MembersIncome membersIncome : membersIncomes){
            String name = membersIncome.getMemberUserName().toLowerCase();
            String incomeSource = membersIncome.getSource().toLowerCase();
            String notes = membersIncome.getNotes().toLowerCase();
            String period = membersIncome.getPeriod().toLowerCase();
            if (name.contains(newText)){
                newList.add(membersIncome);
            }
            if (incomeSource.contains(newText)){
                newList.add(membersIncome);
            }
            if (notes.contains(newText)){
                newList.add(membersIncome);
            }
            if (period.contains(newText)){
                newList.add(membersIncome);
            }
        }
        mAdapter.setFilter(newList);
        return true;
    }
}
