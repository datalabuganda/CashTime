package com.example.eq62roket.cashtime.Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.eq62roket.cashtime.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupExpendituresAnalysisActivity extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener{
    private static String TAG = "GroupExpendituresAnalysisActivity";
    private LineChart geaLineChart;
    private PieChart geaPieChart;
    TextView totalGroupExpenditure;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_expenditures_analysis);

        totalGroupExpenditure = (TextView)findViewById(R.id.totalGroupExpenditure);

        geaLineChart = (LineChart)findViewById(R.id.geaLineChart);
        geaPieChart = (PieChart)findViewById(R.id.geaPieChart);


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

        groupExpenditurePieChart();

        totalGroupExpenditure();
        totalGroupExpenditureByCategory();
    }

    public void groupExpenditurePieChart(){
        this.totalGroupExpenditureByCategory();
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

        yValues.add(new PieEntry(24f, "Remaining"));


        geaPieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "Group Expenditure");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));


        geaPieChart.setData(data);
    }


    public void totalGroupExpenditure(){
        final ParseQuery query = ParseQuery.getQuery("GroupExpenditure");
        query.findInBackground(new FindCallback<ParseObject>() {
            int sum;
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                for (ParseObject p: objects){
                    sum += Integer.valueOf(p.getString("groupExpenditureAmount"));
                    Log.d(TAG, "sum" + sum);
                }
                if (e == null){
                    totalGroupExpenditure.setText(String.valueOf(sum));
                }
            }
        });

    }

    public void totalRent(){
        ParseQuery query = ParseQuery.getQuery("GroupExpenditure");
        query.whereEqualTo("groupExpenditureCategory", "ent");
        query.findInBackground(new FindCallback<ParseObject>() {
            int rentSum;
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                for (ParseObject p: objects){
                    rentSum += Integer.valueOf(p.getString("groupExpenditureAmount"));
                }

            }

        });
    }

    public void totalGroupExpenditureByCategory(){
        final ParseQuery categoriesQuery = ParseQuery.getQuery("GroupExpenditure");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Categories");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                for (ParseObject p: objects){
                    String categories = p.getString("categoryName");
                    String[] categoriesList = {categories};
                    Log.d("Categories", "Categories " + categoriesList);
                    categoriesQuery.whereContainedIn("groupExpenditureCategory", Arrays.asList(categoriesList));
                    categoriesQuery.findInBackground(new FindCallback<ParseObject>() {
                        int sum;
                        @Override
                        public void done(List<ParseObject> amounts, ParseException e) {
                            for (ParseObject p: amounts){
                                sum += Integer.valueOf(p.getString("groupExpenditureAmount"));
                                final String categories = p.getString("groupExpenditureCategory");
                                Log.d(TAG, "sum" + sum);
                                Log.d(TAG, "categories" + categories);

                                Map<String, Integer> mapCategories = new HashMap<String, Integer>();
                                mapCategories.put(categories,sum);
                                Log.d(TAG, "mapCategories: " + mapCategories);

                            }

                        }

                    });
                }
            }
        });

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
