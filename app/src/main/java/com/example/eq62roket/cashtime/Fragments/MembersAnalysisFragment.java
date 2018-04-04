package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eq62roket.cashtime.Activities.AddGroupMembersIncomeActivity;
import com.example.eq62roket.cashtime.Activities.MemberAnalysisActivity;
import com.example.eq62roket.cashtime.Activities.MembersExpenditureAnalysisActivity;
import com.example.eq62roket.cashtime.Activities.MembersIncomeAnalysisActivity;
import com.example.eq62roket.cashtime.Helper.ParseGroupHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupMemberListener;
import com.example.eq62roket.cashtime.Models.GroupMember;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.MembersAdapter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MembersAnalysisFragment extends Fragment {
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
                        editUserIntent.putExtra("parseId", groupMember.getMemberParseId());

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
}
