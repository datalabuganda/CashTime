package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eq62roket.cashtime.Activities.AddGroupExpenditureActivity;
import com.example.eq62roket.cashtime.R;


public class GroupExpendituresFragment extends Fragment {
    FloatingActionButton fabGroupExpenditures;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View rootView = inflater.inflate(R.layout.fragment_group_expenditures, container, false);

        fabGroupExpenditures = (FloatingActionButton) rootView.findViewById(R.id.fabGroupExpenditures);

        fabGroupExpenditures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupExpendituresFragment.this.getContext(),AddGroupExpenditureActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }


}
