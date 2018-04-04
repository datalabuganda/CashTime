package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.TextView;

import com.example.eq62roket.cashtime.Activities.AddNewGroupActivity;
import com.example.eq62roket.cashtime.Activities.GroupAnalysisActivity;
import com.example.eq62roket.cashtime.Activities.GroupExpendituresAnalysisActivity;
import com.example.eq62roket.cashtime.Activities.GroupIncomeAnalysisActivity;
import com.example.eq62roket.cashtime.Activities.GroupMembersActivity;
import com.example.eq62roket.cashtime.Activities.GroupsActivity;
import com.example.eq62roket.cashtime.Helper.ParseGroupHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupsListener;
import com.example.eq62roket.cashtime.Models.Group;
import com.example.eq62roket.cashtime.Models.GroupIncome;
import com.example.eq62roket.cashtime.Models.GroupMember;
import com.example.eq62roket.cashtime.Models.GroupSavings;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupAdapter;
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
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class GroupAnalysisFragment extends Fragment {
    private static final String TAG = "GroupAnalysisFragment";

    private GroupAdapter mGroupAdapter;

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private TextView emptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_group_analysis, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);

        new ParseGroupHelper(getActivity()).getAllGroupsFromParseDb(new OnReturnedGroupsListener() {
            @Override
            public void onResponse(List<Group> groupsList) {
                if (groupsList.isEmpty()){
                    mRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);

                    mGroupAdapter = new GroupAdapter(groupsList, new GroupAdapter.OnGroupClickListener() {
                        @Override
                        public void onGroupSelected(Group group) {
                            Intent groupDetailsIntent = new Intent(getActivity(), GroupAnalysisActivity.class);
                            groupDetailsIntent.putExtra("groupParseId", group.getGroupParseId());
                            groupDetailsIntent.putExtra("groupName", group.getGroupName());
                            groupDetailsIntent.putExtra("groupCentreName", group.getGroupCentreName());
                            groupDetailsIntent.putExtra("groupLocation", group.getLocationOfGroup());
                            groupDetailsIntent.putExtra("groupMemberCount", String.valueOf(group.getGroupMemberCount()));
                            startActivity(groupDetailsIntent);

                        }
                    });
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setAdapter(mGroupAdapter);
                }
            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG, "onFailure: " + error);

            }
        });



//        groupPieChart.setDragDecelerationFrictionCoef(0.99f);
//
//        groupPieChart.setDrawHoleEnabled(false);
//        groupPieChart.setHoleColor(Color.WHITE);
//        groupPieChart.setDrawEntryLabels(false);
//        groupPieChart.setTransparentCircleRadius(1f);
//
//
//        ArrayList<PieEntry> yValues = new ArrayList<>();
//
//        yValues.add(new PieEntry(31f, "Saved"));
//        yValues.add(new PieEntry(24f, "Remaining"));
//
//
//        groupPieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
//
//        PieDataSet dataSet = new PieDataSet(yValues, "");
//        dataSet.setSliceSpace(3f);
//        dataSet.setSelectionShift(5f);
//        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
//
//        PieData data = new PieData((dataSet));
//
//        groupPieChart.setData(data);
//
//        //Expense BarChart
//
//        ArrayList<BarEntry> entries = new ArrayList<>();
//
//        entries.add(new BarEntry(1, 20));
//        entries.add(new BarEntry(2, 34));
//        entries.add(new BarEntry(3, 22));
//        entries.add(new BarEntry(4, 55));
//        entries.add(new BarEntry(5, 14));
//
//        BarDataSet barDataSet = new BarDataSet(entries, "Amount Saved");
//
//        ArrayList<String> labels = new ArrayList<>();
//        labels.add("Otim");
//        labels.add("Rik");
//        labels.add("Ivan");
//        labels.add("Patricia");
//        labels.add("Probuse");
//
//        groupBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
//
//        BarData barData = new BarData(barDataSet);
//
//        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        groupBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
////        summartBarChart.getXAxis().setLabelsToSkip(0);
//        groupBarChart.setTouchEnabled(false);
//        groupBarChart.setDragEnabled(false);
//        groupBarChart.setScaleEnabled(false);
//        groupBarChart.setVisibleXRangeMaximum(1);
//        groupBarChart.setData(barData);
//
//        giaCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent giaIntent = new Intent(GroupAnalysisFragment.this.getActivity(), GroupIncomeAnalysisActivity.class);
//                startActivity(giaIntent);
//            }
//        });
//
//        geaCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent geaIntent = new Intent(GroupAnalysisFragment.this.getActivity(), GroupExpendituresAnalysisActivity.class);
//                startActivity(geaIntent);
//            }
//        });

        return rootView;

    }

}
