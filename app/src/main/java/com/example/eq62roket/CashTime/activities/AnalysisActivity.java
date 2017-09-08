package com.example.eq62roket.CashTime.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.ExpenditureCrud;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class AnalysisActivity extends AppCompatActivity {
    BarChart barChart;
    ExpenditureCrud expenditureCrud;
    //SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        expenditureCrud = new ExpenditureCrud(this);
        //sqLiteDatabase = expenditureCrud.getWritableDatabase();

        setUpPieChart();

    }

    private void setUpPieChart(){
        barChart = (BarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> entries = new ArrayList<>();

        int sumTransport = expenditureCrud.addAllTransport();
        int sumEducation = expenditureCrud.addAllEducation();
        int sumHealth = expenditureCrud.addAllHealth();
        int sumSavings = expenditureCrud.addAllSavings(null);
        int sumOthers = expenditureCrud.addAllOthers();
        int sumHomeneeds = expenditureCrud.addAllHomeneeds();

        entries.add(new BarEntry(sumTransport, 0));
        entries.add(new BarEntry(sumEducation, 1));
        entries.add(new BarEntry(sumHealth, 2));
        entries.add(new BarEntry(sumSavings, 3));
        entries.add(new BarEntry(sumOthers, 4));
        entries.add(new BarEntry(sumHomeneeds, 5));



        BarDataSet dataset = new BarDataSet(entries, "Expenditures");


        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Transport");
        labels.add("Education");
        labels.add("Medical");
        labels.add("Savings");
        labels.add("Others");
        labels.add("Home Needs");


        BarData data = new BarData(labels, dataset);

        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setLabelsToSkip(0);
        barChart.setTouchEnabled(false);
        barChart.setDragEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setVisibleXRangeMaximum(1);
        barChart.setData(data);
        barChart.animateY(5000);

    }
}
