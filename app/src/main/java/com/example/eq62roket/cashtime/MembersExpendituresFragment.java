package com.example.eq62roket.cashtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class MembersExpendituresFragment extends Fragment {
    FloatingActionButton fabMembersExpenditures;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_members_expenditures, container, false);
        fabMembersExpenditures = (FloatingActionButton) rootView.findViewById(R.id.fabMemberExpenditures);

        fabMembersExpenditures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MembersExpendituresFragment.this.getContext(),AddGroupMembersExpendituresActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
