package com.example.eq62roket.CashTime.activities;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by eq62roket on 8/22/17.
 */

public class ExpenditureTabbedAnalysis extends Fragment {
    BarChart barChart;
    SQLiteHelper myHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.expenditure_tabbed_analysis, container, false);


        barChart = (BarChart) rootView.findViewById(R.id.barchart);

        myHelper = new SQLiteHelper(getActivity());
        sqLiteDatabase = myHelper.getWritableDatabase();

        ArrayList<BarEntry> entries = new ArrayList<>();

        int sumTransport = myHelper.addAllTransport();
        int sumEducation = myHelper.addAllEducation();
        int sumHealth = myHelper.addAllHealth();
        int sumSavings = myHelper.addAllSavings();
        int sumOthers = myHelper.addAllOthers();
        int sumHomeneeds = myHelper.addAllHomeneeds();

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



        return rootView;
    }


}
