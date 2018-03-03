package com.example.eq62roket.cashtime.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eq62roket.cashtime.Models.MemberSavings;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.MemberSavingsAdapter;

import java.util.ArrayList;
import java.util.List;


public class MembersSavingsFragment extends Fragment {

    private List<MemberSavings> mMemberSavings = new ArrayList<>();
    private MemberSavingsAdapter mMemberSavingsAdapter;
    private RecyclerView mRecyclerView;

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

        mMemberSavingsAdapter = new MemberSavingsAdapter(mMemberSavings);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mMemberSavingsAdapter);

        // add data to mMembersSavings
        prepareMemberData();

        return rootView;
    }

    private void prepareMemberData(){
        MemberSavings memberSavings = null;
        memberSavings = new MemberSavings("Jeff Kiwa", 3000);
        mMemberSavings.add(memberSavings);

        memberSavings = new MemberSavings("Anold Kimitu", 46200);
        mMemberSavings.add(memberSavings);

        memberSavings = new MemberSavings("Lora Aninta", 58000);
        mMemberSavings.add(memberSavings);

        memberSavings = new MemberSavings("Phifi Queen", 70000);
        mMemberSavings.add(memberSavings);

        memberSavings = new MemberSavings("Waren Kintu", 46000);
        mMemberSavings.add(memberSavings);


    }

}
