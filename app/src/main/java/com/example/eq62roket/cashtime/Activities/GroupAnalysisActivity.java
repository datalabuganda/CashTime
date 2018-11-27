package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Helper.CashTimeUtils;
import com.example.eq62roket.cashtime.Helper.ParseExpenditureHelper;
import com.example.eq62roket.cashtime.Helper.ParseGroupHelper;
import com.example.eq62roket.cashtime.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class GroupAnalysisActivity extends AppCompatActivity {
    private static final String TAG = "GroupAnalysisActivity";
    TextView totalGroupExpenditure, totalGroupIncome, totalGroupSavings;
    PieChart pieChart;
    BarChart incomeBarChart, expenditureBarChart, savingsBarChart;

    private String groupLocalUniqueID;
    private String nameOfGroup;
    private ParseGroupHelper mParseGroupHelper;
    private ParseExpenditureHelper eParseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_analysis);

        mParseGroupHelper = new ParseGroupHelper(GroupAnalysisActivity.this);

        Intent groupIntent = getIntent();
        groupLocalUniqueID = groupIntent.getStringExtra("groupLocalUniqueID");
        nameOfGroup = groupIntent.getStringExtra("groupName");
        Log.d(TAG, "groupLocalUniqueID: " + groupLocalUniqueID);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(nameOfGroup);
        actionBar.setHomeButtonEnabled(true);

        totalGroupExpenditure = (TextView)findViewById(R.id.totalGroupExpenditure);
        totalGroupIncome = (TextView)findViewById(R.id.totalGroupIncome);
        totalGroupSavings = (TextView)findViewById(R.id.totalGroupSavings);

        pieChart = (PieChart) findViewById(R.id.groupPieChart);
        incomeBarChart = (BarChart) findViewById(R.id.groupBarGraph);
        expenditureBarChart = (BarChart) findViewById(R.id.expenditureBarGraph);
        savingsBarChart = (BarChart) findViewById(R.id.savingsBarGraph);

        String totalIncome = String.valueOf(this.totalGroupIncome());
        String totalExpenditure = String.valueOf(this.totalGroupExpenditure());
        String totalSavings = String.valueOf(this.totalGroupSavings());

        totalGroupExpenditure.setText(new CashTimeUtils().currencyFormatter(totalExpenditure));
        totalGroupIncome.setText(new CashTimeUtils().currencyFormatter(totalIncome));
        totalGroupSavings.setText(new CashTimeUtils().currencyFormatter(totalSavings));


        totalGroupExpenditure();
        totalGroupSavings();
        totalGroupIncome();
        pieChart();
        totalLoan();

        IncomeBarGraph();
        ExpenditureBarGraph();
        SavingsBarGraph();


    }
    /*******************************************Savings BarGraph*************************************/
    public void SavingsBarGraph(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        int totalLoan = this.totalSavingsLoan();
        int totalSalary = this.totalSavingsSalary();
        int totalDonation = this.totalSavingsDonation();
        int totalWage = this.totalSavingsWage();
        int totalInvestment = this.totalSavingsInvestment();
        int totalSavings = this.totalSavingsSavings();

        entries.add(new BarEntry(0, totalLoan));
        entries.add(new BarEntry(1, totalSalary));
        entries.add(new BarEntry(2, totalDonation));
        entries.add(new BarEntry(3, totalWage));
        entries.add(new BarEntry(4, totalInvestment));
        entries.add(new BarEntry(5, totalSavings));

        BarDataSet barDataSet = new BarDataSet(entries, "Savings");
        final ArrayList<String> labels = new ArrayList<>();
        labels.add("Loan");
        labels.add("Salary");
        labels.add("Donation");
        labels.add("Wage");
        labels.add("Investment");
        labels.add("Savings");

        /************************************ x axis label design ***********************************/
        final XAxis xAxis = savingsBarChart.getXAxis();
        xAxis.setLabelCount(entries.size());
        xAxis.setXOffset(40000);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setTextColor(Color.RED);
        savingsBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        savingsBarChart.getDescription().setEnabled(false);

        BarData barData = new BarData(barDataSet);

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        savingsBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        savingsBarChart.setTouchEnabled(false);
        savingsBarChart.setDragEnabled(false);
        savingsBarChart.setScaleEnabled(true);
        savingsBarChart.setVisibleXRangeMaximum(1);
        savingsBarChart.setData(barData);

        savingsBarChart.setDescription(null);    // Hide the description
        savingsBarChart.getAxisRight().setDrawLabels(false);

        savingsBarChart.getLegend().setEnabled(true);   // Hide the legend

    }



    /*******************************************Income BarGraph*************************************/
    public void IncomeBarGraph(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        int totalLoan = this.totalLoan();
        int totalSalary = this.totalSalary();
        int totalDonation = this.totalDonation();
        int totalWage = this.totalWage();
        int totalInvestment = this.totalInvestment();
        int totalSavings = this.totalSavings();

        entries.add(new BarEntry(0, totalLoan));
        entries.add(new BarEntry(1, totalSalary));
        entries.add(new BarEntry(2, totalDonation));
        entries.add(new BarEntry(3, totalWage));
        entries.add(new BarEntry(4, totalInvestment));
        entries.add(new BarEntry(5, totalSavings));

        BarDataSet barDataSet = new BarDataSet(entries, "Income");
        final ArrayList<String> labels = new ArrayList<>();
        labels.add("Loan");
        labels.add("Salary");
        labels.add("Donation");
        labels.add("Wage");
        labels.add("Investment");
        labels.add("Savings");

        /***************** x axis label design ********************/
        final XAxis xAxis = incomeBarChart.getXAxis();
        xAxis.setLabelCount(entries.size());
        xAxis.setXOffset(40000);
//        xAxis.setLabelRotationAngle(90);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setTextColor(Color.RED);
        incomeBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        incomeBarChart.getDescription().setEnabled(false);

        BarData barData = new BarData(barDataSet);

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        incomeBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        incomeBarChart.setTouchEnabled(false);
        incomeBarChart.setDragEnabled(false);
        incomeBarChart.setScaleEnabled(true);
        incomeBarChart.setVisibleXRangeMaximum(1);
        incomeBarChart.setData(barData);

        incomeBarChart.setDescription(null);    // Hide the description
        incomeBarChart.getAxisRight().setDrawLabels(false);

        incomeBarChart.getLegend().setEnabled(true);   // Hide the legend
//        incomeBarChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
//        incomeBarChart.getLegend().setWordWrapEnabled(true);
//        incomeBarChart.getLegend().setTextSize(2);
    }

    /******************************************Expenditure BarGraph*********************************/

    public void ExpenditureBarGraph(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        int totalRent = this.totalRent();
        int totalFood = this.totalFood();
        int totalMedical = this.totalMedical();
        int totalTransport = this.totalTransport();
        int totalLeisure = this.totalLeisure();
        int totalOthers = this.totalOthers();
        int totalCommunication = this.totalCommunication();
        int totalEntertainment = this.totalEntertainment();
        int totalGift = this.totalGift();
        int totalClothes = this.totalClothes();

        entries.add(new BarEntry(0, totalRent));
        entries.add(new BarEntry(1, totalFood));
        entries.add(new BarEntry(2, totalMedical));
        entries.add(new BarEntry(3, totalTransport));
        entries.add(new BarEntry(4, totalLeisure));
        entries.add(new BarEntry(5, totalOthers));
        entries.add(new BarEntry(6, totalCommunication));
        entries.add(new BarEntry(7, totalEntertainment));
        entries.add(new BarEntry(8, totalGift));
        entries.add(new BarEntry(9, totalClothes));

        BarDataSet barDataSet = new BarDataSet(entries, "Expenditure");
        final ArrayList<String> labels = new ArrayList<>();
        labels.add("Rent");
        labels.add("Food");
        labels.add("Medic");
        labels.add("Trans");
        labels.add("Leisure");
        labels.add("Others");
        labels.add("Comm");
        labels.add("Ent");
        labels.add("Gift");
        labels.add("Clothes");

        /************************************* x axis **************************************/
        final XAxis xAxis = expenditureBarChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        expenditureBarChart.getRendererXAxis().getPaintAxisLabels().setTextAlign(Paint.Align.LEFT);
//        xAxis.setLabelRotationAngle(30);
        xAxis.setLabelCount(entries.size());
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setTextColor(Color.RED);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        expenditureBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));


        BarData barData = new BarData(barDataSet);

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        expenditureBarChart.setTouchEnabled(false);
        expenditureBarChart.setDragEnabled(false);
        expenditureBarChart.setScaleEnabled(false);
        expenditureBarChart.setData(barData);


        expenditureBarChart.setDescription(null);    // Hide the description
        expenditureBarChart.getAxisRight().setDrawLabels(false);

        expenditureBarChart.getLegend().setEnabled(true);   // Hide the legend
//        expenditureBarChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        expenditureBarChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
//        expenditureBarChart.getLegend().setWordWrapEnabled(true);
//        expenditureBarChart.getLegend().setTextSize(2);

    }

    /*********************************Income and Expenditure PieChart****************************/
    public void pieChart(){

        pieChart.setDragDecelerationFrictionCoef(0.99f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setDrawEntryLabels(false);
        pieChart.setTransparentCircleRadius(1f);
        pieChart.setDescription(null);    // Hide the description

        pieChart.getLegend().setEnabled(true);   // Hide the legend
        pieChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        pieChart.getLegend().setTextSize(2);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        int totalIncome = this.totalGroupIncome();
        int totalExpenditure = this.totalGroupExpenditure();
        int totalSavings = this.totalGroupSavings();

        yValues.add(new PieEntry(totalIncome, "Income"));
        yValues.add(new PieEntry(totalExpenditure, "Expenditure"));
        yValues.add(new PieEntry(totalSavings, "Savings"));

        String currentUserId = ParseUser.getCurrentUser().getObjectId();

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));

        pieChart.setData(data);

    }
    /******************************* Total Group Savings ****************************************/

    public int totalGroupSavings(){
        int totalSavings = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupSavings");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                totalSavings += Integer.parseInt(results.get(i).getString("groupSavingAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return totalSavings;
    }

    /******************************* Total Group Expenditure ************************************/
    public int totalGroupExpenditure(){
        int sumOfExpenditure = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfExpenditure += Integer.parseInt(results.get(i).getString("groupExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfExpenditure;
    }

    /********************************** Total Group Income ****************************************/
    public int totalGroupIncome(){

        int sumOfIncome = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupIncome");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfIncome += Integer.parseInt(results.get(i).getString("groupIncomeAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfIncome;
    }

    /****************************** Total Group Savings by Source *********************************/
    public int totalSavingsLoan(){
        int sumOfLoan = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupSavings");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupSavingIncomeSource", "Loan");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfLoan += Integer.parseInt(results.get(i).getString("groupSavingAmount"));
                Log.d(TAG, "totalLoan: " + sumOfLoan);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfLoan;
    }

    public int totalSavingsSavings(){
        int sumOfSavings = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupSavings");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupSavingIncomeSource", "Savings");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfSavings += Integer.parseInt(results.get(i).getString("groupSavingAmount"));
                Log.d(TAG, "totalSavings: " + sumOfSavings);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfSavings;
    }

    public int totalSavingsSalary(){
        int sumOfSalary = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupSavings");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupSavingIncomeSource", "Salary");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfSalary += Integer.parseInt(results.get(i).getString("groupSavingAmount"));
                Log.d(TAG, "sumOfSalary: " + sumOfSalary);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfSalary;
    }

    public int totalSavingsInvestment(){
        int sumOfInvestment = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupSavings");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupSavingIncomeSource", "Investment");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfInvestment += Integer.parseInt(results.get(i).getString("groupSavingAmount"));
                Log.d(TAG, "sumOfInvestment: " + sumOfInvestment);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfInvestment;
    }

    public int totalSavingsWage(){
        int sumOfWage = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupSavings");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupSavingIncomeSource", "Wage");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfWage += Integer.parseInt(results.get(i).getString("groupSavingAmount"));
                Log.d(TAG, "sumOfWage: " + sumOfWage);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfWage;
    }

    public int totalSavingsDonation(){
        int sumOfDonation = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupSavings");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupSavingIncomeSource", "Donation");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfDonation += Integer.parseInt(results.get(i).getString("groupSavingAmount"));
                Log.d(TAG, "sumOfDonation: " + sumOfDonation);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfDonation;
    }

    /****************************** Total Income by Source ****************************************/
    public int totalLoan(){
        int sumOfLoan = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupIncome");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupIncomeSource", "Loan");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfLoan += Integer.parseInt(results.get(i).getString("groupIncomeAmount"));
                Log.d(TAG, "totalLoan: " + sumOfLoan);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfLoan;
    }

    public int totalSavings(){
        int sumOfSavings = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupIncome");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupIncomeSource", "Savings");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfSavings += Integer.parseInt(results.get(i).getString("groupIncomeAmount"));
                Log.d(TAG, "totalSavings: " + sumOfSavings);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfSavings;
    }

    public int totalSalary(){
        int sumOfSalary = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupIncome");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupIncomeSource", "Salary");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfSalary += Integer.parseInt(results.get(i).getString("groupIncomeAmount"));
                Log.d(TAG, "totalSalary: " + sumOfSalary);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfSalary;
    }

    public int totalInvestment(){
        int sumOfInvestment = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupIncome");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupIncomeSource", "Investment");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfInvestment += Integer.parseInt(results.get(i).getString("groupIncomeAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfInvestment;
    }

    public int totalWage(){
        int sumOfWage = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupIncome");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupIncomeSource", "Wage");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfWage += Integer.parseInt(results.get(i).getString("groupIncomeAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfWage;
    }

    public int totalDonation(){
        int sumOfDonation = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupIncome");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupIncomeSource", "Donation");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfDonation += Integer.parseInt(results.get(i).getString("groupIncomeAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfDonation;
    }

    /***************************** Total Expenditure by Category ***********************************/
    public int totalRent(){
        int sumOfRent = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupExpenditureCategory", "Rent");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfRent += Integer.parseInt(results.get(i).getString("groupExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfRent;
    }

    public int totalFood(){
        int sumOfFood = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupExpenditureCategory", "Food");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfFood += Integer.parseInt(results.get(i).getString("groupExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfFood;
    }

    public int totalMedical(){
        int sumOfInvestment = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupExpenditureCategory", "Medical");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfInvestment += Integer.parseInt(results.get(i).getString("groupExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfInvestment;
    }

    public int totalTransport(){
        int sumOfWage = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupExpenditureCategory", "Transport");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfWage += Integer.parseInt(results.get(i).getString("groupExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfWage;
    }

    public int totalLeisure(){
        int sumOfDonation = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupExpenditureCategory", "Leisure");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfDonation += Integer.parseInt(results.get(i).getString("groupExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfDonation;
    }

    public int totalOthers(){
        int sumOfDonation = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupExpenditureCategory", "Others");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfDonation += Integer.parseInt(results.get(i).getString("groupExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfDonation;
    }

    public int totalCommunication() {
        int sumOfCommunication = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupExpenditureCategory", "Communication");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfCommunication += Integer.parseInt(results.get(i).getString("groupExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfCommunication;
    }

    public int totalEntertainment() {
        int sumOfEntertainment = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupExpenditureCategory", "Entertainment");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfEntertainment += Integer.parseInt(results.get(i).getString("groupExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfEntertainment;
    }

    public int totalGift() {
        int sumOfGift = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupExpenditureCategory", "Gift");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfGift += Integer.parseInt(results.get(i).getString("groupExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfGift;

    }
    public int totalClothes() {
        int sumOfClothes = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        query.whereContains("groupExpenditureCategory", "Clothes");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfClothes += Integer.parseInt(results.get(i).getString("groupExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfClothes;
    }

}

