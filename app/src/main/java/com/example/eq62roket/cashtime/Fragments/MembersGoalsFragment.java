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
import android.widget.TextView;

import com.example.eq62roket.cashtime.Activities.EditMemberGoalActivity;
import com.example.eq62roket.cashtime.Activities.MemberGoalSelectMemberActivity;
import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedMemberGoalListener;
import com.example.eq62roket.cashtime.Models.MembersGoals;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.AllMembersGoalsAdapter;

import java.util.ArrayList;
import java.util.List;


public class MembersGoalsFragment extends Fragment implements SearchView.OnQueryTextListener{
    private static final String TAG = "MembersGoalsFragment";

    private List<MembersGoals> membersGoals = null;
    private RecyclerView recyclerView;
    private TextView emptyView;
    private AllMembersGoalsAdapter mAdapter;
    private FloatingActionButton fabMembersGoals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_members_goals, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.members_goals_recycler_view);
        fabMembersGoals = (FloatingActionButton) rootView.findViewById(R.id.fabMembersGoals);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);

        fabMembersGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addMembersGoalsIntent = new Intent(MembersGoalsFragment.this.getContext(), MemberGoalSelectMemberActivity.class);
                startActivity(addMembersGoalsIntent);
            }
        });

        new ParseHelper(getActivity()).getAllMemberGoalsFromParseDb(new OnReturnedMemberGoalListener() {
            @Override
            public void onResponse(List<MembersGoals> membersGoalsList) {
                if (membersGoalsList.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }else {
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    membersGoals = membersGoalsList;

                    mAdapter = new AllMembersGoalsAdapter(getActivity(), membersGoalsList, new AllMembersGoalsAdapter.OnMemberGoalClickListener() {
                        @Override
                        public void onMemberGoalClick(MembersGoals membersGoals) {
                            Intent editMemberGoalIntent = new Intent(getActivity(), EditMemberGoalActivity.class);
                            editMemberGoalIntent.putExtra("groupMemberName", membersGoals.getMemberName());
                            editMemberGoalIntent.putExtra("groupMemberGoalName", membersGoals.getMemberGoalName());
                            editMemberGoalIntent.putExtra("groupMemberGoalAmount", membersGoals.getMemberGoalAmount());
                            editMemberGoalIntent.putExtra("groupMemberGoalDeadline", membersGoals.getMemberGoalDueDate());
                            editMemberGoalIntent.putExtra("groupMemberGoalNotes", membersGoals.getMemberGoalNotes());
                            editMemberGoalIntent.putExtra("memberGoalLocalUniqueID", membersGoals.getLocalUniqueID());
                            startActivity(editMemberGoalIntent);
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
        inflater.inflate(R.menu.members_goals, menu);

        MenuItem menuItem = menu.findItem(R.id.members_goals_search);

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
        ArrayList<MembersGoals> newList = new ArrayList<>();
        for (MembersGoals membersGoals : membersGoals){
            String name = membersGoals.getMemberName().toLowerCase();
            String goal = membersGoals.getMemberGoalName().toLowerCase();
            String amount = membersGoals.getMemberGoalAmount().toLowerCase();
            String date = membersGoals.getMemberGoalDueDate().toLowerCase();
            if (name.contains(newText)){
                newList.add(membersGoals);
            }else if (goal.contains(newText)){
                newList.add(membersGoals);
            }else if (amount.contains(newText)){
                newList.add(membersGoals);
            }else if (date.contains(newText)){
                newList.add(membersGoals);
            }
        }
        mAdapter.setFilter(newList);
        return true;
    }
}
