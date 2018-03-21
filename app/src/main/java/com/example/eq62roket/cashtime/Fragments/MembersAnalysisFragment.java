package com.example.eq62roket.cashtime.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class MembersAnalysisFragment extends Fragment {
    PieChart membersPieChart;
    BarChart membersBarChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_members_analysis, container, false);

        membersPieChart = (PieChart) rootView.findViewById(R.id.membersPieChart);
        membersBarChart = (BarChart) rootView.findViewById(R.id.membersBarGraph);


        membersPieChart.setDragDecelerationFrictionCoef(0.99f);

        membersPieChart.setDrawHoleEnabled(false);
        membersPieChart.setHoleColor(Color.WHITE);
        membersPieChart.setDrawEntryLabels(false);
        membersPieChart.setTransparentCircleRadius(1f);
//        summaryPieChart.setCenterText("Expenses");
//        summaryPieChart.setCenterTextColor(Color.RED);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(31f, "Rik"));
        yValues.add(new PieEntry(24f, "Tony"));
        yValues.add(new PieEntry(55f, "Sytskey"));
        yValues.add(new PieEntry(45f, "Ivan"));
        yValues.add(new PieEntry(67f, "Patricia"));
        yValues.add(new PieEntry(10f, "Probuse"));


        membersPieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));

        membersPieChart.setData(data);

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

        membersBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        BarData barData = new BarData(barDataSet);

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        membersBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//        summartBarChart.getXAxis().setLabelsToSkip(0);
        membersBarChart.setTouchEnabled(false);
        membersBarChart.setDragEnabled(false);
        membersBarChart.setScaleEnabled(false);
        membersBarChart.setVisibleXRangeMaximum(1);
        membersBarChart.setData(barData);

        return rootView;

    }

}
