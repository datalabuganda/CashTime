package com.example.eq62roket.cashtime.Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

public class SummaryActivity extends AppCompatActivity {

    PieChart summaryPieChart;
    BarChart expensesBarChart, salesBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        summaryPieChart = (PieChart)findViewById(R.id.summaryPieChart);
        expensesBarChart = (BarChart)findViewById(R.id.summaryBarGraph);

        //PieChart graph
        summaryPieChart.setUsePercentValues(false);
        summaryPieChart.getDescription().setEnabled(false);
        summaryPieChart.setExtraOffsets(5, 10, 5, 5);


        summaryPieChart.setDragDecelerationFrictionCoef(0.99f);

        summaryPieChart.setDrawHoleEnabled(false);
        summaryPieChart.setHoleColor(Color.WHITE);
        summaryPieChart.setDrawEntryLabels(false);
        summaryPieChart.setTransparentCircleRadius(1f);
//        summaryPieChart.setCenterText("Expenses");
//        summaryPieChart.setCenterTextColor(Color.RED);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(31f, "Saved"));
        yValues.add(new PieEntry(24f, "Remaining"));


        summaryPieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));

        summaryPieChart.setData(data);

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

        expensesBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        BarData barData = new BarData(barDataSet);

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        expensesBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//        summartBarChart.getXAxis().setLabelsToSkip(0);
        expensesBarChart.setTouchEnabled(false);
        expensesBarChart.setDragEnabled(false);
        expensesBarChart.setScaleEnabled(false);
        expensesBarChart.setVisibleXRangeMaximum(1);
        expensesBarChart.setData(barData);

    }
}
