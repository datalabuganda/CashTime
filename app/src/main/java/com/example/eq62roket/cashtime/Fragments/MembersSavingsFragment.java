package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eq62roket.cashtime.Activities.MemberSavingToGoalsActivity;
import com.example.eq62roket.cashtime.Activities.MemberSavingsDetail;
import com.example.eq62roket.cashtime.Models.MemberSavings;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.MemberSavingsAdapter;

import java.util.ArrayList;
import java.util.List;


public class MembersSavingsFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "MembersSavingsFragment";

    private List<MemberSavings> mMemberSavings = new ArrayList<>();
    private MemberSavingsAdapter mMemberSavingsAdapter;
    private RecyclerView mRecyclerView;

    private FloatingActionButton mFloatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(
                R.layout.fragment_members_savings,
                container,
                false
        );
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);

        mMemberSavingsAdapter = new MemberSavingsAdapter(mMemberSavings, new MemberSavingsAdapter.OnSavingClickListener() {
            @Override
            public void onSavingClick(MemberSavings memberSavings) {
                Intent intent = new Intent(getActivity(), MemberSavingsDetail.class);
                intent.putExtra("name", memberSavings.getMemberName());
                intent.putExtra("amount", String.valueOf(memberSavings.getsavingAmount()));
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mMemberSavingsAdapter);

        // add saving
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch to goals fragment
                Intent intent = new Intent(getActivity(), MemberSavingToGoalsActivity.class);
                startActivity(intent);

            }
        });

        // add data to mMembersSavings
        prepareMemberData();

        return rootView;
    }

    private void prepareMemberData(){
        MemberSavings memberSavings = null;
        memberSavings = new MemberSavings("Jeff Kiwa", "Buy A shirt", "Weekly", "Salary", "22/3/2020", 3000);
        mMemberSavings.add(memberSavings);

        memberSavings = new MemberSavings("Anold Kimitu", "Buy A gomesi", "Monthly", "Investment", "20/3/2020",  46200);
        mMemberSavings.add(memberSavings);

        memberSavings = new MemberSavings("Mukamaniwalinda Harrison","Buy a piglet", "Daily", "Loan", "22/12/2020",  580000000);
        mMemberSavings.add(memberSavings);

        memberSavings = new MemberSavings("Phifi Queen", "Buy seeds", "Monthly", "Salary", "12/3/2020",  70000);
        mMemberSavings.add(memberSavings);

        memberSavings = new MemberSavings("Waren Kintu", "Buy A shirt", "Weekly", "Salary", "22/3/2020",  46000);
        mMemberSavings.add(memberSavings);


    }

}
