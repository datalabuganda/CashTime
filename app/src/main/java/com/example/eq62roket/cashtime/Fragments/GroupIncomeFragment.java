package com.example.eq62roket.cashtime.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eq62roket.cashtime.Activities.AddGroupIncomeActivity;
import com.example.eq62roket.cashtime.R;


public class GroupIncomeFragment extends Fragment {
    FloatingActionButton fabGroupIncome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_group_income, container, false);
        fabGroupIncome = (FloatingActionButton) rootView.findViewById(R.id.fabGroupIncome);

        fabGroupIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupIncomeFragment.this.getContext(),AddGroupIncomeActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }

}
