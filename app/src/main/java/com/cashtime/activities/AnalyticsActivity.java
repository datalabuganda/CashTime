package com.cashtime.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class AnalyticsActivity extends AppCompatActivity {

    private static String TAG = "Analytics";


    private int[] expenses = {25000, 100000, 660000, 44000, 46900, 165000, 23000};
    private String[] categories = {"Food", "Health", "Clothing", "Rent", "Transport", "Saving", "School Fees"};

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        pieChart = (PieChart) findViewById(R.id.pieChart);

        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("July Expenses"); // set a month here
        pieChart.setCenterTextSize(25);
        pieChart.setDrawEntryLabels(true);

        addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value selected from chart");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());

                int pos1 = e.toString().indexOf("y: ");
                String expense = e.toString().substring(pos1 + 2);

                // find a name that is associated with sales
                for (int i = 0; i < expenses.length; i++){
                    if (expenses[i] == (int)Float.parseFloat(expense)){
                        pos1 = i;
                        break;
                    }
                }
                String category = categories[pos1];
                Toast.makeText(AnalyticsActivity.this, "Category " + category + "\n" + "Expenses " + expense, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }


    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> expenseEntrys = new ArrayList<>();
        ArrayList<String> categoryEntrys = new ArrayList<>();

//        populate the data using for loops
        for (int i = 0; i < expenses.length; i++){
            expenseEntrys.add(new PieEntry(expenses[i], i));
        }

        for (int i = 0; i < categories.length; i++){
            categoryEntrys.add(categories[i]);
        }

        // create data set
        PieDataSet pieDataSet = new PieDataSet(expenseEntrys, "<Month> expense");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        // add colors to the dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.MAGENTA);
        colors.add(Color.CYAN);
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GRAY);
        colors.add(Color.YELLOW);

        pieDataSet.setColors(colors);

        // add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        // create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
