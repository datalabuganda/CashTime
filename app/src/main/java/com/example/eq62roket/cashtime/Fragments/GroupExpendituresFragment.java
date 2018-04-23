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

import com.example.eq62roket.cashtime.Activities.EditGroupExpenditureActivity;
import com.example.eq62roket.cashtime.Activities.ExpenditureToGroupActivity;
import com.example.eq62roket.cashtime.Helper.ParseExpenditureHelper;
import com.example.eq62roket.cashtime.Models.GroupExpenditure;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupExpenditureAdapter;

import java.util.ArrayList;
import java.util.List;


public class GroupExpendituresFragment extends Fragment implements SearchView.OnQueryTextListener{
    private static String TAG = "GroupExpendituresFragment";
    FloatingActionButton fabGroupExpenditures;

    List<GroupExpenditure> groupExpenditures = null;
    private RecyclerView recyclerView;
    private GroupExpenditureAdapter mAdapter;

    private TextView emptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View rootView = inflater.inflate(R.layout.fragment_group_expenditures, container, false);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.group_expenditures_recycler_view);

        fabGroupExpenditures = (FloatingActionButton) rootView.findViewById(R.id.fabGroupExpenditures);

        fabGroupExpenditures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupExpendituresFragment.this.getContext(),ExpenditureToGroupActivity.class);
                startActivity(intent);
            }
        });

        new ParseExpenditureHelper(getActivity()).getGroupExpenditureFromParseDb(new ParseExpenditureHelper.OnReturnedGroupExpenditureListener() {
            @Override
            public void onResponse(List<GroupExpenditure> groupExpendituresList) {

                if (groupExpendituresList.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }else {
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    groupExpenditures = groupExpendituresList;

                    mAdapter = new GroupExpenditureAdapter(groupExpendituresList, new GroupExpenditureAdapter.OnGroupExpenditureClickListener() {
                        @Override
                        public void onGroupExpenditureClick(GroupExpenditure groupExpenditure) {
                            Intent editGroupExpenditureIntent = new Intent(getActivity(), EditGroupExpenditureActivity.class);
                            editGroupExpenditureIntent.putExtra("groupExpenditureCategory", groupExpenditure.getCategory());
                            editGroupExpenditureIntent.putExtra("groupExpenditureAmount", groupExpenditure.getAmount());
                            editGroupExpenditureIntent.putExtra("groupExpenditureDate", groupExpenditure.getDate());
                            editGroupExpenditureIntent.putExtra("groupExpenditureNotes", groupExpenditure.getNotes());
                            editGroupExpenditureIntent.putExtra("groupExpenditureLocalUniqueID", groupExpenditure.getLocalUniqueID());
                            editGroupExpenditureIntent.putExtra("groupName", groupExpenditure.getGroupName());
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
        inflater.inflate(R.menu.group_expenditure, menu);

        MenuItem menuItem = menu.findItem(R.id.group_expenditure_search);

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
        ArrayList<GroupExpenditure> newList = new ArrayList<>();
        for (GroupExpenditure groupExpenditures : groupExpenditures){
            String name = groupExpenditures.getGroupName().toLowerCase();
            String category = groupExpenditures.getCategory().toLowerCase();
            String amount = groupExpenditures.getAmount().toLowerCase();
            String date = groupExpenditures.getDate().toLowerCase();
            if (name.contains(newText)){
                newList.add(groupExpenditures);
            }else if (category.contains(newText)){
                newList.add(groupExpenditures);
            }else if (amount.contains(newText)){
                newList.add(groupExpenditures);
            }else if (date.contains(newText)){
                newList.add(groupExpenditures);
            }
        }
        mAdapter.setFilter(newList);
        return true;

    }
}
