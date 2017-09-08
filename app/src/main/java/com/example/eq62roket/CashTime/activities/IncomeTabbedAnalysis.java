package com.example.eq62roket.CashTime.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.IncomeCrud;
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

public class IncomeTabbedAnalysis extends Fragment {
    BarChart barChartIncome;
    IncomeCrud incomeCrud;
    //SQLiteDatabase sqLiteDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.income_tabbed_analysis, container, false);
        barChartIncome = (BarChart) rootView.findViewById(R.id.barchartIncome);

        incomeCrud = new IncomeCrud(getActivity());
        //sqLiteDatabase = incomeCrud.getWritableDatabase();

        ArrayList<BarEntry> entries = new ArrayList<>();

        int sumSalary = incomeCrud.addAllSalary();
        int sumInvestment = incomeCrud.addAllInvestment();
        int sumLoan = incomeCrud.addAllLoan();
        int sumOthers = incomeCrud.addAllOthers();;

        entries.add(new BarEntry(sumSalary, 0));
        entries.add(new BarEntry(sumInvestment, 1));
        entries.add(new BarEntry(sumLoan, 2));
        entries.add(new BarEntry(sumOthers, 3));


        BarDataSet dataset = new BarDataSet(entries, "Income");


        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Salary");
        labels.add("Investment");
        labels.add("Loan");
        labels.add("Others");



        BarData data = new BarData(labels, dataset);

        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChartIncome.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChartIncome.getXAxis().setLabelsToSkip(0);
        barChartIncome.setTouchEnabled(false);
        barChartIncome.setDragEnabled(false);
        barChartIncome.setScaleEnabled(false);
        barChartIncome.setVisibleXRangeMaximum(1);
        barChartIncome.setData(data);
        barChartIncome.animateY(3000);



        return rootView;

    }
}
