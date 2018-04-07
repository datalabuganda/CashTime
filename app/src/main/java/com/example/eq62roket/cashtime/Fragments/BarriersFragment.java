package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Activities.BarrierToGroupGoalsActivity;
import com.example.eq62roket.cashtime.Activities.EditBarrierActivity;
import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupBarrierListener;
import com.example.eq62roket.cashtime.Models.Barrier;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.BarrierAdapter;

import java.util.List;


public class BarriersFragment extends Fragment {

    private static final String TAG = "GroupSavingsFragment";

    private RecyclerView mRecyclerView;
    private TextView emptyView;
    private BarrierAdapter mBarrierAdapter;

    private FloatingActionButton mFloatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View rootView = inflater.inflate(R.layout.fragment_group_barriers, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BarrierToGroupGoalsActivity.class);
                startActivity(intent);

            }
        });

        new ParseHelper(getActivity()).getGroupBarriersFromParseDb(new OnReturnedGroupBarrierListener() {
            @Override
            public void onResponse(List<Barrier> barrierList) {
                if (barrierList.isEmpty()){
                    mRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }else {
                    emptyView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    mBarrierAdapter = new BarrierAdapter(barrierList, new BarrierAdapter.OnBarrierClickListener() {
                        @Override
                        public void onBarrierSelected(Barrier barrier) {
                            Intent editBarrierIntent = new Intent(getContext(), EditBarrierActivity.class);
                            editBarrierIntent.putExtra("barrierGoalName", barrier.getGoalName());
                            editBarrierIntent.putExtra("barrierName", barrier.getBarrierName());
                            editBarrierIntent.putExtra("barrierNotes", barrier.getBarrierText());
                            editBarrierIntent.putExtra("barrierParseId", barrier.getParseId());
                            startActivity(editBarrierIntent);
                            getActivity().finish();
                        }
                    });
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setAdapter(mBarrierAdapter);
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });

        return rootView;
    }

}
