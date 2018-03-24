package com.example.eq62roket.cashtime.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eq62roket.cashtime.Activities.GroupExpendituresAnalysisActivity;
import com.example.eq62roket.cashtime.Activities.GroupIncomeAnalysisActivity;
import com.example.eq62roket.cashtime.R;
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

public class GroupAnalysisFragment extends Fragment {
    PieChart groupPieChart;
    BarChart groupBarChart;
    CardView geaCardView, giaCardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_group_analysis, container, false);
        groupPieChart = (PieChart)rootView.findViewById(R.id.groupPieChart);
        groupBarChart = (BarChart)rootView.findViewById(R.id.groupBarGraph);
        geaCardView = (CardView)rootView.findViewById(R.id.geaCardView);
        giaCardView = (CardView)rootView.findViewById(R.id.giaCardView);


        groupPieChart.setDragDecelerationFrictionCoef(0.99f);

        groupPieChart.setDrawHoleEnabled(false);
        groupPieChart.setHoleColor(Color.WHITE);
        groupPieChart.setDrawEntryLabels(false);
        groupPieChart.setTransparentCircleRadius(1f);
//        summaryPieChart.setCenterText("Expenses");
//        summaryPieChart.setCenterTextColor(Color.RED);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(31f, "Saved"));
        yValues.add(new PieEntry(24f, "Remaining"));


        groupPieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));

        groupPieChart.setData(data);

        //Expense BarChart

        ArrayList<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(1, 20));
        entries.add(new BarEntry(2, 34));
        entries.add(new BarEntry(3, 22));
        entries.add(new BarEntry(4, 55));
        entries.add(new BarEntry(5, 14));

        BarDataSet barDataSet = new BarDataSet(entries, "Amount Saved");

        ArrayList<String> labels = new ArrayList<>();
        labels.add("Otim");
        labels.add("Rik");
        labels.add("Ivan");
        labels.add("Patricia");
        labels.add("Probuse");

        groupBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        BarData barData = new BarData(barDataSet);

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        groupBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//        summartBarChart.getXAxis().setLabelsToSkip(0);
        groupBarChart.setTouchEnabled(false);
        groupBarChart.setDragEnabled(false);
        groupBarChart.setScaleEnabled(false);
        groupBarChart.setVisibleXRangeMaximum(1);
        groupBarChart.setData(barData);

        giaCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giaIntent = new Intent(GroupAnalysisFragment.this.getActivity(), GroupIncomeAnalysisActivity.class);
                startActivity(giaIntent);
            }
        });

        geaCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent geaIntent = new Intent(GroupAnalysisFragment.this.getActivity(), GroupExpendituresAnalysisActivity.class);
                startActivity(geaIntent);
            }
        });

        return rootView;

    }
}
