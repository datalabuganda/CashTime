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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Activities.BarrierToGroupGoalsActivity;
import com.example.eq62roket.cashtime.Activities.EditBarrierActivity;
import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupBarrierListener;
import com.example.eq62roket.cashtime.Models.Barrier;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.BarrierAdapter;

import java.util.ArrayList;
import java.util.List;


public class BarriersFragment extends Fragment implements SearchView.OnQueryTextListener{

    private static final String TAG = "GroupSavingsFragment";

    private RecyclerView mRecyclerView;
    private TextView emptyView;
    private BarrierAdapter mBarrierAdapter;

    private FloatingActionButton mFloatingActionButton;

    List<Barrier> barriers = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View rootView = inflater.inflate(R.layout.fragment_group_barriers, container, false);
        setHasOptionsMenu(true);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BarrierToGroupGoalsActivity.class);
                startActivity(intent);

            }
        });

        new ParseHelper(getActivity()).getGroupBarriersFromParseDb(new OnReturnedGroupBarrierListener() {
            @Override
            public void onResponse(List<Barrier> barrierList) {
                if (barrierList.isEmpty()){
                    mRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }else {
                    barriers = barrierList;

                    emptyView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    mBarrierAdapter = new BarrierAdapter(barrierList, new BarrierAdapter.OnBarrierClickListener() {
                        @Override
                        public void onBarrierSelected(Barrier barrier) {
                            Intent editBarrierIntent = new Intent(getContext(), EditBarrierActivity.class);
                            editBarrierIntent.putExtra("barrierGoalName", barrier.getGoalName());
                            editBarrierIntent.putExtra("barrierName", barrier.getBarrierName());
                            editBarrierIntent.putExtra("barrierNotes", barrier.getBarrierText());
                            editBarrierIntent.putExtra("barrierLocalUniqueID", barrier.getLocalUniqueID());
                            startActivity(editBarrierIntent);
                            getActivity().finish();
                        }
                    });
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setAdapter(mBarrierAdapter);
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });

        return rootView;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.barriers, menu);

        MenuItem menuItem = menu.findItem(R.id.barriers_search);

        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setBackgroundColor(Color.WHITE);
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setTextColor(Color.BLACK);
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(Color.GRAY);

        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<Barrier> newList = new ArrayList<>();
        for (Barrier barriers : barriers){
            String text = barriers.getBarrierText().toLowerCase();
            String barrierName = barriers.getBarrierName().toLowerCase();
            String goalName = barriers.getGoalName().toLowerCase();
            if (text.contains(newText)){
                newList.add(barriers);
            }else if (barrierName.contains(newText)){
                newList.add(barriers);
            }else if (goalName.contains(newText)){
                newList.add(barriers);
            }
        }
        mBarrierAdapter.setFilter(newList);
        return true;
    }
}
