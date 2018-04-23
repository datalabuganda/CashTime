package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.example.eq62roket.cashtime.Activities.EditMemberSavingActivity;
import com.example.eq62roket.cashtime.Activities.MemberSavingToGoalsActivity;
import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedMemberSavingsListener;
import com.example.eq62roket.cashtime.Models.MemberSavings;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.MemberSavingsAdapter;

import java.util.ArrayList;
import java.util.List;


public class MembersSavingsFragment extends android.support.v4.app.Fragment implements SearchView.OnQueryTextListener{

    private static final String TAG = "MembersSavingsFragment";

    private MemberSavingsAdapter mMemberSavingsAdapter;
    private RecyclerView mRecyclerView;
    private TextView emptyView;
    private List<MemberSavings> mMemberSavings = null;

    private FloatingActionButton mFloatingActionButton;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View rootView = inflater.inflate(
                R.layout.fragment_members_savings,
                container,
                false
        );
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MemberSavingToGoalsActivity.class);
                startActivity(intent);

            }
        });

        new ParseHelper(getActivity()).getMemberSavingsFromParseDb(new OnReturnedMemberSavingsListener() {
            @Override
            public void onResponse(List<MemberSavings> memberSavingsList) {
                if (memberSavingsList.isEmpty()){
                    mRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }else {
                    mMemberSavings = memberSavingsList;
                    emptyView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    mMemberSavingsAdapter = new MemberSavingsAdapter(memberSavingsList, new MemberSavingsAdapter.OnSavingClickListener() {
                        @Override
                        public void onSavingClick(MemberSavings memberSavings) {
                            Intent intent = new Intent(getActivity(), EditMemberSavingActivity.class);
                            intent.putExtra("goalName", memberSavings.getGoalName());
                            intent.putExtra("memberName",memberSavings.getMemberName());
                            intent.putExtra("savingAmount", String.valueOf(memberSavings.getSavingAmount()));
                            intent.putExtra("dateAdded", memberSavings.getDateAdded());
                            intent.putExtra("savingPeriod", memberSavings.getPeriod());
                            intent.putExtra("incomeSource", memberSavings.getIncomeSource());
                            intent.putExtra("savingNote", memberSavings.getSavingNote());
                            intent.putExtra("memberSavingLocalUniqueID", memberSavings.getLocalUniqueID());
                            startActivity(intent);
                        }
                    });
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setAdapter(mMemberSavingsAdapter);
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
        inflater.inflate(R.menu.member_savings, menu);

        MenuItem menuItem = menu.findItem(R.id.member_savings_search);

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
        ArrayList<MemberSavings> newList = new ArrayList<>();
        if (mMemberSavings != null){
            for (MemberSavings memberSavings : mMemberSavings){
                String source = memberSavings.getIncomeSource().toLowerCase();
                String notes = memberSavings.getSavingNote().toLowerCase();
                String goalName = memberSavings.getGoalName().toLowerCase();
                String period = memberSavings.getPeriod().toLowerCase();
                if (source.contains(newText)){
                    newList.add(memberSavings);
                }else if (notes.contains(newText)){
                    newList.add(memberSavings);
                }else if (goalName.contains(newText)){
                    newList.add(memberSavings);
                }else if (period.contains(newText)){
                    newList.add(memberSavings);
                }
            }
            mMemberSavingsAdapter.setFilter(newList);
        }
        return true;
    }
}
