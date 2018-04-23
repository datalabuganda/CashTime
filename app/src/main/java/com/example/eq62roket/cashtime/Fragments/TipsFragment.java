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

import com.example.eq62roket.cashtime.Activities.GoalTipsActivity;
import com.example.eq62roket.cashtime.Activities.TipsToGroupGoalsActivity;
import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedTipsListener;
import com.example.eq62roket.cashtime.Models.Tip;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.TipsAdapter;

import java.util.ArrayList;
import java.util.List;


public class TipsFragment extends Fragment implements SearchView.OnQueryTextListener{

    private static final String TAG = "GroupSavingsFragment";

    private RecyclerView mRecyclerView;
    private TextView emptyView;
    private TipsAdapter mTipsAdapter;

    private FloatingActionButton mFloatingActionButton;

    List<Tip> tips = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View rootView = inflater.inflate(R.layout.activity_tips, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TipsToGroupGoalsActivity.class);
                startActivity(intent);

            }
        });

        new ParseHelper(getActivity()).getAllTipsFromParseDb(new OnReturnedTipsListener() {
            @Override
            public void onResponse(List<Tip> tipsList) {
                if (tipsList.isEmpty()){
                    mRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    tips = tipsList;
                    emptyView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    mTipsAdapter = new TipsAdapter(tipsList, new TipsAdapter.OnTipClickListener() {
                        @Override
                        public void onTipSelected(Tip tip) {
                            Intent goalTipsIntent = new Intent(getActivity(), GoalTipsActivity.class);
                            goalTipsIntent.putExtra("goalName", tip.getGoalName());
                            startActivity(goalTipsIntent);
                        }
                    });

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setAdapter(mTipsAdapter);
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
        inflater.inflate(R.menu.tips, menu);

        MenuItem menuItem = menu.findItem(R.id.tips_search);

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
        ArrayList<Tip> newList = new ArrayList<>();
        for (Tip tips : tips){
            String notes = tips.getIntroText().toLowerCase();
            if (notes.contains(newText)){
                newList.add(tips);
            }
        }
        mTipsAdapter.setFilter(newList);
        return true;
    }
}
