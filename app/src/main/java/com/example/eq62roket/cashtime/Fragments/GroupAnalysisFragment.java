package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.TextView;

import com.example.eq62roket.cashtime.Activities.AddNewGroupActivity;
import com.example.eq62roket.cashtime.Activities.GroupAnalysisActivity;
import com.example.eq62roket.cashtime.Helper.ParseGroupHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupsListener;
import com.example.eq62roket.cashtime.Models.Group;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupAdapter;
import java.util.List;

public class GroupAnalysisFragment extends Fragment implements SearchView.OnQueryTextListener{
    private static final String TAG = "GroupAnalysisFragment";

    private GroupAdapter mGroupAdapter;

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private TextView emptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_group_analysis, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);

        new ParseGroupHelper(getActivity()).getAllGroupsFromParseDb(new OnReturnedGroupsListener() {
            @Override
            public void onResponse(List<Group> groupsList) {
                if (groupsList.isEmpty()){
                    mRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);

                    mGroupAdapter = new GroupAdapter(groupsList, new GroupAdapter.OnGroupClickListener() {
                        @Override
                        public void onGroupSelected(Group group) {
                            Intent groupDetailsIntent = new Intent(getActivity(), GroupAnalysisActivity.class);
                            groupDetailsIntent.putExtra("groupParseId", group.getGroupParseId());
                            groupDetailsIntent.putExtra("groupName", group.getGroupName());
                            groupDetailsIntent.putExtra("groupCentreName", group.getGroupCentreName());
                            groupDetailsIntent.putExtra("groupLocation", group.getLocationOfGroup());
                            groupDetailsIntent.putExtra("groupMemberCount", String.valueOf(group.getGroupMemberCount()));
                            startActivity(groupDetailsIntent);

                        }
                    });
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setAdapter(mGroupAdapter);
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
        inflater.inflate(R.menu.group_analysis, menu);

        MenuItem menuItem = menu.findItem(R.id.group_analysis_search);

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
