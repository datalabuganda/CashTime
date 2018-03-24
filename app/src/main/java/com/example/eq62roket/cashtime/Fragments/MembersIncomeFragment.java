package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eq62roket.cashtime.Activities.AddGroupMembersIncomeActivity;
import com.example.eq62roket.cashtime.R;


public class MembersIncomeFragment extends Fragment {
    FloatingActionButton fabMembersIncome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_members_income, container, false);
        fabMembersIncome = (FloatingActionButton) rootView.findViewById(R.id.fabMembersIncome);

        fabMembersIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MembersIncomeFragment.this.getContext(),AddGroupMembersIncomeActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }

}
