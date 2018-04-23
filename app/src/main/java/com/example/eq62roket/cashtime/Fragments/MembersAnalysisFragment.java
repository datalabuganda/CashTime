package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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

import com.example.eq62roket.cashtime.Activities.MemberAnalysisActivity;
import com.example.eq62roket.cashtime.Helper.ParseGroupHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupMemberListener;
import com.example.eq62roket.cashtime.Models.GroupMember;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.MembersAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.List;

public class MembersAnalysisFragment extends Fragment implements SearchView.OnQueryTextListener{
    PieChart membersPieChart;
    BarChart membersBarChart;
    CardView miaCardView, meaCardView;

    private static String TAG = "MemberAnalysisActivity";
    private List<GroupMember> mGroupMemberUsers = null;
    private RecyclerView mRecyclerView;
    private MembersAdapter mMembersAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View rootView = inflater.inflate(R.layout.fragment_members_analysis, container, false);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        new ParseGroupHelper(getActivity()).getAllMembersFromParseDb(new OnReturnedGroupMemberListener() {
            @Override
            public void onResponse(List<GroupMember> memberList) {

                mMembersAdapter = new MembersAdapter(memberList, new MembersAdapter.OnGroupMemberClickListener() {
                    @Override
                    public void onGroupMemberClick(GroupMember groupMember) {
                        Intent editUserIntent = new Intent(getActivity(), MemberAnalysisActivity.class);
                        editUserIntent.putExtra("userName", groupMember.getMemberUsername());
                        editUserIntent.putExtra("memberLocalUniqueID", groupMember.getLocalUniqueID());

                        startActivity(editUserIntent);
                        getActivity().finish();
                    }

                });

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                mRecyclerView.setAdapter(mMembersAdapter);

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
        inflater.inflate(R.menu.member_analysis, menu);

        MenuItem menuItem = menu.findItem(R.id.member_analysis_search);

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
        return true;
    }
}
