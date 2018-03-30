package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Activities.EditMemberSavingActivity;
import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedMemberSavingsListener;
import com.example.eq62roket.cashtime.Models.MemberSavings;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.MemberSavingsAdapter;

import java.util.List;


public class MembersSavingsFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "MembersSavingsFragment";

    private MemberSavingsAdapter mMemberSavingsAdapter;
    private RecyclerView mRecyclerView;

    private FloatingActionButton mFloatingActionButton;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(
                R.layout.fragment_members_savings,
                container,
                false
        );
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);

        // add saving
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), MemberSavingToGoalsActivity.class);
//                startActivity(intent);
                Toast.makeText(getActivity(), "This Feature is still under development", Toast.LENGTH_SHORT).show();

            }
        });

        new ParseHelper(getActivity()).getMemberSavingsFromParseDb(new OnReturnedMemberSavingsListener() {
            @Override
            public void onResponse(List<MemberSavings> memberSavingsList) {

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
                        intent.putExtra("memberSavingParseId", memberSavings.getParseId());
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(mMemberSavingsAdapter);
            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG, "onFailure: " + error);
            }
        });

        return rootView;
    }

}
