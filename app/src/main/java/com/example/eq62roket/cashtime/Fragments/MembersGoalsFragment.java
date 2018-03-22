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

import com.example.eq62roket.cashtime.Activities.EditMemberGoalActivity;
import com.example.eq62roket.cashtime.Activities.GroupMembersActivity;
import com.example.eq62roket.cashtime.Models.MembersGoals;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.MembersGoalsAdapter;

import java.util.ArrayList;
import java.util.List;


public class MembersGoalsFragment extends Fragment implements SearchView.OnQueryTextListener{
    private List<MembersGoals> membersGoalsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MembersGoalsAdapter mAdapter;
    private FloatingActionButton fabMembersGoals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_members_goals, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.members_goals_recycler_view);
        fabMembersGoals = (FloatingActionButton) rootView.findViewById(R.id.fabMembersGoals);

        fabMembersGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addMembersGoalsIntent = new Intent(MembersGoalsFragment.this.getContext(),GroupMembersActivity.class);
                startActivity(addMembersGoalsIntent);
                getActivity().finish();
            }
        });

        mAdapter = new MembersGoalsAdapter(membersGoalsList, new MembersGoalsAdapter.OnMemberGoalClickListener() {
            @Override
            public void onMemberGoalClick(MembersGoals membersGoals) {
                Intent editMemberGoalIntent = new Intent(getActivity(), EditMemberGoalActivity.class);
                editMemberGoalIntent.putExtra("groupMemberName", membersGoals.getMemberName());
                editMemberGoalIntent.putExtra("groupMemberGoalName", membersGoals.getMemberGoalName());
                editMemberGoalIntent.putExtra("groupMemberGoalAmount", membersGoals.getMemberGoalAmount());
                editMemberGoalIntent.putExtra("groupMemberGoalDeadline", membersGoals.getMemberGoalDueDate());
                editMemberGoalIntent.putExtra("groupMemberGoalNotes", membersGoals.getMemberGoalNotes());
                startActivity(editMemberGoalIntent);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

        prepareMembersGoalsData();

        return rootView;
    }

    private void prepareMembersGoalsData() {
        MembersGoals membersGoals = new MembersGoals("Otim Tony", "Buy 5 hives", "500000", "12/2/2019", "incomplete", "Some Notes Too");
        membersGoalsList.add(membersGoals);

        membersGoals = new MembersGoals("Nimukama Probuse", "Buy a Cow", "703000", "02/06/2019", "incomplete", "Some Notes also");
        membersGoalsList.add(membersGoals);

        membersGoals = new MembersGoals("Muguya Ivan", "Buy 10 axes", "3000", "02/06/2018", "incomplete", "Some Notes");
        membersGoalsList.add(membersGoals);

        membersGoals = new MembersGoals("Nimukama Probuse", "Buy 2 Hives", "500000", "02/01/2019", "incomplete", "Some Notes Too");
        membersGoalsList.add(membersGoals);

        membersGoals = new MembersGoals("Rik Linssen", "Buy Casava stems", "300000", "11/05/2019", "incomplete", "Some Notes Too");
        membersGoalsList.add(membersGoals);

        membersGoals = new MembersGoals("Nimukama Probuse", "Buy 2 hoes", "13000", "04/08/2019", "incomplete", "Some Notes Too");
        membersGoalsList.add(membersGoals);

        membersGoals = new MembersGoals("Nimukama Probuse", "Buy a Cow", "703000", "02/06/2019", "incomplete", "Some Notes Too");
        membersGoalsList.add(membersGoals);

        membersGoals = new MembersGoals("Nimukama Probuse", "Buy a Cow", "703000", "02/06/2019", "incomplete", "Some Notes Too");
        membersGoalsList.add(membersGoals);
        mAdapter.notifyDataSetChanged();
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
        for (MembersGoals membersGoals : membersGoalsList){
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
