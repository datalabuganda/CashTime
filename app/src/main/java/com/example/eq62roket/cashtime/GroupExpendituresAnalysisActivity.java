package com.example.eq62roket.cashtime;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Map;

public class GroupExpendituresAnalysisActivity extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener{
    private static final String TAG = "GroupExpendituresAnalysisActivity";
    private LineChart geaLineChart;
    private PieChart geaPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_expenditures_analysis);

        geaLineChart = (LineChart)findViewById(R.id.geaLineChart);
        geaPieChart = (PieChart)findViewById(R.id.geaPieChart);

        //*********************** Pie Chart *************************//

        geaPieChart.setDragDecelerationFrictionCoef(0.99f);

        geaPieChart.setDrawHoleEnabled(false);
        geaPieChart.setCenterTextColor(Color.BLACK);
        geaPieChart.setDrawEntryLabels(false);
        geaPieChart.setTransparentCircleRadius(1f);
        geaPieChart.setDrawEntryLabels(true);
        geaPieChart.setHoleColor(Color.BLACK);
        geaPieChart.setEntryLabelTextSize(10);
        geaPieChart.setTouchEnabled(true);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(30000, "Hoes"));
        yValues.add(new PieEntry(24000, "Labor"));

        yValues.add(new PieEntry(90000, "Harvesting"));
        yValues.add(new PieEntry(27000, "Transport"));

        yValues.add(new PieEntry(50000, "Shares"));
        yValues.add(new PieEntry(5000, "Education"));

        yValues.add(new PieEntry(76000, "Herbcides"));
        yValues.add(new PieEntry(12000, "Pestcides"));

        yValues.add(new PieEntry(20000, "Taxes"));
        yValues.add(new PieEntry(19000, "Water"));

        yValues.add(new PieEntry(33000, "Electricity"));
        yValues.add(new PieEntry(36000, "Sacco"));

        yValues.add(new PieEntry(61000, "Apio"));
        yValues.add(new PieEntry(4000, "Mutegyeki"));

        yValues.add(new PieEntry(64000, "Simon"));
        yValues.add(new PieEntry(9000, "Alinda"));


        geaPieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "Group Expenditure");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));

        geaPieChart.setData(data);

        //*********************** Line Chart ************************//
        geaLineChart.setOnChartGestureListener(this);
        geaLineChart.setOnChartValueSelectedListener(this);

        geaLineChart.setDragEnabled(true);
        geaLineChart.setScaleEnabled(true);

        ArrayList<Entry> values = new ArrayList<>();

        values.add(new Entry(0, 60f));
        values.add(new Entry(1, 20f));
        values.add(new Entry(2, 55f));
        values.add(new Entry(3, 40f));
        values.add(new Entry(4, 10f));
        values.add(new Entry(5, 63f));
        values.add(new Entry(6, 34f));
        values.add(new Entry(7, 75f));
        values.add(new Entry(8, 90f));


        LineDataSet set1 = new LineDataSet(values, "Group Expenditure");
        set1.setFillAlpha(110);

        set1.setColor(Color.GREEN);
        set1.setLineWidth(3f);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.RED);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData dataPieChart = new LineData(dataSets);

        geaLineChart.setData(dataPieChart);
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
