package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eq62roket.cashtime.Activities.BarrierToGroupGoalsActivity;
import com.example.eq62roket.cashtime.Models.Barrier;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.BarrierAdapter;

import java.util.ArrayList;
import java.util.List;


public class BarriersFragment extends Fragment {

    private static final String TAG = "GroupSavingsFragment";

    List<Barrier> mBarriersList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private BarrierAdapter mBarrierAdapter;

    private FloatingActionButton mFloatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_group_savings, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);


        mBarrierAdapter = new BarrierAdapter(mBarriersList, new BarrierAdapter.OnBarrierClickListener() {
            @Override
            public void onBarrierSelected(Barrier barrier) {
//                Intent editBarrierIntent = new Intent(getContext(), EditBarrierActivity.class);
//                startActivity(editBarrierIntent);
//                getActivity().finish();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mBarrierAdapter);


        // add saving
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch to goals fragment
                Intent intent = new Intent(getActivity(), BarrierToGroupGoalsActivity.class);
                startActivity(intent);

            }
        });

        // Add data to arrayList
        prepareSavingData();


        return rootView;
    }

    private void prepareSavingData(){

        Barrier barrier = new Barrier("Failed to Buy 5 Bee hives","Money was not enough" , "12/1/1980");
        mBarriersList.add(barrier);

        barrier = new Barrier("Failed to Buy seeds","Stores are really far" , "12/1/2020");
        mBarriersList.add(barrier);

        barrier = new Barrier("Failed to harvest 20L of honey","Looks like my bees are bored or not serious" , "12/11/2000");
        mBarriersList.add(barrier);

        barrier = new Barrier("Failed to get 5 sacks of cassava","Soil does not have enough cassava to give me" , "11/12/2030");
        mBarriersList.add(barrier);

        mBarrierAdapter.notifyDataSetChanged();

    }
}
