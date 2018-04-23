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
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MemberAnalysisActivity extends AppCompatActivity {
    private static final String TAG = "MemberAnalysisActivity";
    TextView totalExpenditure, totalIncome, totalSavings;
    PieChart pieChart;
    BarChart incomeBarChart, expenditureBarChart;

    private String groupMemberLocalUniqueID = "";
    private ParseExpenditureHelper mParseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_analysis);

        totalExpenditure = (TextView)findViewById(R.id.totalMemberExpenditure);
        totalIncome = (TextView)findViewById(R.id.totalMemberIncome);
        totalSavings = (TextView)findViewById(R.id.totalMemberSavings);

        mParseHelper = new ParseExpenditureHelper(MemberAnalysisActivity.this);

        pieChart = (PieChart) findViewById(R.id.groupPieChart);
        incomeBarChart = (BarChart) findViewById(R.id.groupBarGraph);
        expenditureBarChart = (BarChart) findViewById(R.id.expenditureBarGraph);

        Intent intent = getIntent();
        String memberUserName = intent.getStringExtra("userName");
        groupMemberLocalUniqueID = intent.getStringExtra("memberLocalUniqueID");

        Log.d(TAG, "username " + memberUserName);
        Log.d(TAG, "groupMemberLocalUniqueID " + groupMemberLocalUniqueID);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(memberUserName);
        actionBar.setHomeButtonEnabled(true);


        String totalMemberIncome = String.valueOf(this.totalMemberIncome());
        String totalMemberExpenditure = String.valueOf(this.totalMemberExpenditure());
        String totalMemberSavings = String.valueOf(this.totalMemberSavings());

        totalExpenditure.setText(new CashTimeUtils().currencyFormatter(totalMemberExpenditure));
        totalIncome.setText(new CashTimeUtils().currencyFormatter(totalMemberIncome));
        totalSavings.setText(new CashTimeUtils().currencyFormatter(totalMemberSavings));

        totalMemberExpenditure();
        totalMemberIncome();
        pieChart();
        IncomeBarGraph();
        ExpenditureBarGraph();
    }

    /************************** Total Expenditure and Total Income Pie Chart **********************/
    public void pieChart(){

        pieChart.setDragDecelerationFrictionCoef(0.99f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setDrawEntryLabels(false);
        pieChart.setTransparentCircleRadius(1f);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        int totalIncome = this.totalMemberIncome();
        int totalExpenditure = this.totalMemberExpenditure();
        int totalSavings = this.totalMemberSavings();

        yValues.add(new PieEntry(totalIncome, "Income"));
        yValues.add(new PieEntry(totalExpenditure, "Expenditure"));
        yValues.add(new PieEntry(totalSavings, "Savings"));

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));

        pieChart.setData(data);

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

        BarDataSet barDataSet = new BarDataSet(entries, "Expenditure");
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Loan");
        labels.add("Salary");
        labels.add("Donation");
        labels.add("Wage");
        labels.add("Investment");
        labels.add("Savings");

        final XAxis xAxis = incomeBarChart.getXAxis();

        xAxis.setLabelCount(entries.size());
        xAxis.setLabelRotationAngle(30);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setTextColor(Color.RED);
        incomeBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        BarData barData = new BarData(barDataSet);

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        incomeBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        incomeBarChart.setTouchEnabled(false);
        incomeBarChart.setDragEnabled(false);
        incomeBarChart.setScaleEnabled(false);
        incomeBarChart.setVisibleXRangeMaximum(1);
        incomeBarChart.setData(barData);
        incomeBarChart.setNoDataText("No expenditure entered yet");

        incomeBarChart.setDescription(null);    // Hide the description
        incomeBarChart.getAxisRight().setDrawLabels(false);

        incomeBarChart.getLegend().setEnabled(false);   // Hide the legend
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

        BarDataSet barDataSet = new BarDataSet(entries, "Income");
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Rent");
        labels.add("Food");
        labels.add("Medical");
        labels.add("Transport");
        labels.add("Leisure");
        labels.add("Others");
        labels.add("Comm't");
        labels.add("Ent'mt");
        labels.add("Gift");
        labels.add("Leisure");

        /************************************* x axis **************************************/
        final XAxis xAxis = expenditureBarChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        expenditureBarChart.getRendererXAxis().getPaintAxisLabels().setTextAlign(Paint.Align.LEFT);
        xAxis.setLabelRotationAngle(30);
        xAxis.setLabelCount(entries.size());
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setTextColor(Color.RED);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        expenditureBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        BarData barData = new BarData(barDataSet);

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        expenditureBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        expenditureBarChart.setTouchEnabled(false);
        expenditureBarChart.setDragEnabled(false);
        expenditureBarChart.setScaleEnabled(false);
        expenditureBarChart.setVisibleXRangeMaximum(1);
        expenditureBarChart.setData(barData);
        expenditureBarChart.setNoDataText("No expenditure entered yet");

        expenditureBarChart.setDescription(null);    // Hide the description
        expenditureBarChart.getAxisRight().setDrawLabels(false);

        expenditureBarChart.getLegend().setEnabled(false);   // Hide the legend
    }

    /************************************* Total Member Expenditure *******************************/

    public int totalMemberExpenditure(){
        int sumOfExpenditure = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfExpenditure += Integer.parseInt(results.get(i).getString("memberExpenditureAmount"));
                Log.d(TAG, "totalMemberExpenditure: " + sumOfExpenditure);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfExpenditure;
    }

    /***************************************** Total Member Income *********************************/

    public int totalMemberIncome(){
        int sumOfIncome = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberIncome");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfIncome += Integer.parseInt(results.get(i).getString("memberIncomeAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfIncome;
    }

    /******************************* Total Member Savings ****************************************/

    public int totalMemberSavings(){
        int totalSavings = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupMemberSavings");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                totalSavings += Integer.parseInt(results.get(i).getString("memberSavingAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return totalSavings;
    }

    /**********************************************************************************************/
    public int totalLoan(){
        int sumOfLoan = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberIncome");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        query.whereContains("memberIncomeSource", "Loan");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfLoan += Integer.parseInt(results.get(i).getString("memberIncomeAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfLoan;
    }

    public int totalSalary(){
        int sumOfSalary = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberIncome");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        query.whereContains("memberIncomeSource", "Salary");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfSalary += Integer.parseInt(results.get(i).getString("memberIncomeAmount"));
                Log.d(TAG, "totalSalary: " + sumOfSalary);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfSalary;
    }

    public int totalSavings(){
        int sumOfSavings = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberIncome");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        query.whereContains("memberIncomeSource", "Savings");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfSavings += Integer.parseInt(results.get(i).getString("memberIncomeAmount"));
                Log.d(TAG, "totalSalary: " + sumOfSavings);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfSavings;
    }

    public int totalInvestment(){
        int sumOfInvestment = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberIncome");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        query.whereContains("memberIncomeSource", "Investment");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfInvestment += Integer.parseInt(results.get(i).getString("memberIncomeAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "totalInvestment: " + sumOfInvestment);
        return sumOfInvestment;
    }

    public int totalWage(){
        int sumOfWage = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberIncome");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        query.whereContains("memberIncomeSource", "Wage");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfWage += Integer.parseInt(results.get(i).getString("memberIncomeAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfWage;
    }

    public int totalDonation(){
        int sumOfDonation = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberIncome");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        query.whereContains("memberIncomeSource", "Donation");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfDonation += Integer.parseInt(results.get(i).getString("memberIncomeAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfDonation;
    }

    /**********************************************************************************************/
    public int totalRent(){
        int sumOfRent = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        query.whereContains("memberExpenditureCategory", "Rent");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfRent += Integer.parseInt(results.get(i).getString("memberExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfRent;
    }

    public int totalFood(){
        int sumOfFood = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        query.whereContains("memberExpenditureCategory", "Food");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfFood += Integer.parseInt(results.get(i).getString("memberExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfFood;
    }

    public int totalMedical(){
        int sumOfInvestment = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        query.whereContains("memberExpenditureCategory", "Medical");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfInvestment += Integer.parseInt(results.get(i).getString("memberExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfInvestment;
    }

    public int totalTransport(){
        int sumOfWage = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        query.whereContains("memberExpenditureCategory", "Transport");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfWage += Integer.parseInt(results.get(i).getString("memberExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfWage;
    }

    public int totalLeisure(){
        int sumOfLeisure = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        query.whereContains("memberExpenditureCategory", "Leisure");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfLeisure += Integer.parseInt(results.get(i).getString("memberExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfLeisure;
    }

    public int totalOthers(){
        int sumOfDonation = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        query.whereContains("memberExpenditureCategory", "Others");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfDonation += Integer.parseInt(results.get(i).getString("memberExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfDonation;
    }

    public int totalCommunication() {
        int sumOfCommunication = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        query.whereContains("memberExpenditureCategory", "Communication");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfCommunication += Integer.parseInt(results.get(i).getString("memberExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfCommunication;
    }

    public int totalEntertainment() {
        int sumOfEntertainment = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        query.whereContains("memberExpenditureCategory", "Entertainment");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfEntertainment += Integer.parseInt(results.get(i).getString("memberExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfEntertainment;
    }

    public int totalGift() {
        int sumOfGift = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        query.whereContains("memberExpenditureCategory", "Gift");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfGift += Integer.parseInt(results.get(i).getString("memberExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfGift;

    }
    public int totalClothes() {
        int sumOfClothes = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberExpenditure");
        query.fromLocalDatastore();
        query.whereEqualTo("memberLocalUniqueID", groupMemberLocalUniqueID);
        query.whereContains("memberExpenditureCategory", "Clothes");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfClothes += Integer.parseInt(results.get(i).getString("memberExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfClothes;
    }
}
