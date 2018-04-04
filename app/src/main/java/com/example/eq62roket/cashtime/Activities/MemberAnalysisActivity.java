package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Helper.ParseExpenditureHelper;
import com.example.eq62roket.cashtime.Helper.ParseGroupHelper;
import com.example.eq62roket.cashtime.Helper.ParseIncomeHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupMemberListener;
import com.example.eq62roket.cashtime.Models.GroupMember;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.MembersAdapter;
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
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MemberAnalysisActivity extends AppCompatActivity {
    private static final String TAG = "MemberAnalysisActivity";
    TextView name, totalExpenditure, totalIncome;
    PieChart pieChart;
    BarChart incomeBarChart, expenditureBarChart;

    private String groupMemberParseId = "";
    private ParseExpenditureHelper mParseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_analysis);

        name = (TextView)findViewById(R.id.username);
        totalExpenditure = (TextView)findViewById(R.id.totalMemberExpenditure);
        totalIncome = (TextView)findViewById(R.id.totalMemberIncome);

        mParseHelper = new ParseExpenditureHelper(MemberAnalysisActivity.this);

        pieChart = (PieChart) findViewById(R.id.groupPieChart);
        incomeBarChart = (BarChart) findViewById(R.id.groupBarGraph);
        expenditureBarChart = (BarChart) findViewById(R.id.expenditureBarGraph);

        Intent intent = getIntent();
        String memberUserName = intent.getStringExtra("userName");
        groupMemberParseId = intent.getStringExtra("parseId");

        Log.d(TAG, "username " + memberUserName);
        Log.d(TAG, "parseId " + groupMemberParseId);

        name.setText(memberUserName);

        String totalMemberIncome = String.valueOf(this.totalMemberIncome());
        String totalMemberExpenditure = String.valueOf(this.totalMemberExpenditure());

        totalExpenditure.setText(totalMemberExpenditure);
        totalIncome.setText(totalMemberIncome);

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

        yValues.add(new PieEntry(totalIncome, "Income"));
        yValues.add(new PieEntry(totalExpenditure, "Expenditure"));

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

        entries.add(new BarEntry(1, totalLoan));
        entries.add(new BarEntry(2, totalSalary));
        entries.add(new BarEntry(3, totalDonation));
        entries.add(new BarEntry(4, totalWage));
        entries.add(new BarEntry(5, totalInvestment));
        entries.add(new BarEntry(6, totalSavings));

        BarDataSet barDataSet = new BarDataSet(entries, "Expenditure");
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Loan");
        labels.add("Salary");
        labels.add("Donation");
        labels.add("Wage");
        labels.add("Investment");
        labels.add("Savings");

        incomeBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        BarData barData = new BarData(barDataSet);

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        incomeBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        incomeBarChart.setTouchEnabled(false);
        incomeBarChart.setDragEnabled(false);
        incomeBarChart.setScaleEnabled(false);
        incomeBarChart.setVisibleXRangeMaximum(1);
        incomeBarChart.setData(barData);
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

        entries.add(new BarEntry(1, totalRent));
        entries.add(new BarEntry(2, totalFood));
        entries.add(new BarEntry(3, totalMedical));
        entries.add(new BarEntry(4, totalTransport));
        entries.add(new BarEntry(5, totalLeisure));
        entries.add(new BarEntry(5, totalOthers));
        entries.add(new BarEntry(3, totalCommunication));
        entries.add(new BarEntry(4, totalEntertainment));
        entries.add(new BarEntry(5, totalGift));
        entries.add(new BarEntry(5, totalClothes));

        BarDataSet barDataSet = new BarDataSet(entries, "Income");
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Rent");
        labels.add("Food");
        labels.add("Medical");
        labels.add("Transport");
        labels.add("Leisure");
        labels.add("Others");
        labels.add("Communication");
        labels.add("Entertainment");
        labels.add("Gift");
        labels.add("Leisure");

        expenditureBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

        BarData barData = new BarData(barDataSet);

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        expenditureBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        expenditureBarChart.setTouchEnabled(false);
        expenditureBarChart.setDragEnabled(false);
        expenditureBarChart.setScaleEnabled(false);
        expenditureBarChart.setVisibleXRangeMaximum(1);
        expenditureBarChart.setData(barData);
    }


    public int totalMemberExpenditure(){
        int sumOfExpenditure = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GroupMembersExpenditure");
        query.whereEqualTo("groupMemberParseId", groupMemberParseId);
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfExpenditure += Integer.parseInt(results.get(i).getString("groupMembersExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfExpenditure;
    }

    public int totalMemberIncome(){
        int sumOfIncome = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GroupMembersIncome");
        query.whereEqualTo("groupMemberParseId", groupMemberParseId);
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfIncome += Integer.parseInt(results.get(i).getString("groupMemberIncomeAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfIncome;
    }

    /**********************************************************************************************/
    public int totalLoan(){
        int sumOfLoan = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GroupMembersIncome");
        query.whereEqualTo("groupMemberParseId", groupMemberParseId);
        query.whereContains("groupMemberIncomeSource", "Loan");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfLoan += Integer.parseInt(results.get(i).getString("groupMemberIncomeAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfLoan;
    }

    public int totalSalary(){
        int sumOfSalary = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GroupMembersIncome");
        query.whereEqualTo("groupMemberParseId", groupMemberParseId);
        query.whereContains("groupMemberIncomeSource", "Salary");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfSalary += Integer.parseInt(results.get(i).getString("groupMemberIncomeAmount"));
                Log.d(TAG, "totalSalary: " + sumOfSalary);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfSalary;
    }

    public int totalSavings(){
        int sumOfSavings = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GroupMembersIncome");
        query.whereEqualTo("groupMemberParseId", groupMemberParseId);
        query.whereContains("groupMemberIncomeSource", "Savings");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfSavings += Integer.parseInt(results.get(i).getString("groupMemberIncomeAmount"));
                Log.d(TAG, "totalSalary: " + sumOfSavings);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfSavings;
    }

    public int totalInvestment(){
        int sumOfInvestment = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GroupMembersIncome");
        query.whereEqualTo("groupMemberParseId", groupMemberParseId);
        query.whereContains("groupMemberIncomeSource", "Investment");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfInvestment += Integer.parseInt(results.get(i).getString("groupMemberIncomeAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfInvestment;
    }

    public int totalWage(){
        int sumOfWage = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GroupMembersIncome");
        query.whereEqualTo("groupMemberParseId", groupMemberParseId);
        query.whereContains("groupMemberIncomeSource", "Wage");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfWage += Integer.parseInt(results.get(i).getString("groupMemberIncomeAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfWage;
    }

    public int totalDonation(){
        int sumOfDonation = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GroupMembersIncome");
        query.whereEqualTo("groupMemberParseId", groupMemberParseId);
        query.whereContains("groupMemberIncomeSource", "Donation");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfDonation += Integer.parseInt(results.get(i).getString("groupMemberIncomeAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfDonation;
    }

    /**********************************************************************************************/
    public int totalRent(){
        int sumOfRent = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GroupMembersExpenditure");
        query.whereEqualTo("groupMemberParseId", groupMemberParseId);
        query.whereContains("groupMembersExpenditureCategory", "Rent");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfRent += Integer.parseInt(results.get(i).getString("groupMembersExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfRent;
    }

    public int totalFood(){
        int sumOfFood = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GroupMembersExpenditure");
        query.whereEqualTo("groupMemberParseId", groupMemberParseId);
        query.whereContains("groupMembersExpenditureCategory", "Food");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfFood += Integer.parseInt(results.get(i).getString("groupMembersExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfFood;
    }

    public int totalMedical(){
        int sumOfInvestment = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GroupMembersExpenditure");
        query.whereEqualTo("groupMemberParseId", groupMemberParseId);
        query.whereContains("groupMembersExpenditureCategory", "Medical");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfInvestment += Integer.parseInt(results.get(i).getString("groupMembersExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfInvestment;
    }

    public int totalTransport(){
        int sumOfWage = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GroupMembersExpenditure");
        query.whereEqualTo("groupMemberParseId", groupMemberParseId);
        query.whereContains("groupMembersExpenditureCategory", "Wage");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfWage += Integer.parseInt(results.get(i).getString("groupMembersExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfWage;
    }

    public int totalLeisure(){
        int sumOfDonation = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GroupMembersExpenditure");
        query.whereEqualTo("groupMemberParseId", groupMemberParseId);
        query.whereContains("groupMembersExpenditureCategory", "Leisure");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfDonation += Integer.parseInt(results.get(i).getString("groupMembersExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfDonation;
    }

    public int totalOthers(){
        int sumOfDonation = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GroupMembersExpenditure");
        query.whereEqualTo("groupMemberParseId", groupMemberParseId);
        query.whereContains("groupMembersExpenditureCategory", "Others");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfDonation += Integer.parseInt(results.get(i).getString("groupMembersExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfDonation;
    }

    public int totalCommunication() {
        int sumOfCommunication = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GroupMembersExpenditure");
        query.whereEqualTo("groupMemberParseId", groupMemberParseId);
        query.whereContains("groupMembersExpenditureCategory", "Communication");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfCommunication += Integer.parseInt(results.get(i).getString("groupMembersExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfCommunication;
    }

    public int totalEntertainment() {
        int sumOfEntertainment = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GroupMembersExpenditure");
        query.whereEqualTo("groupMemberParseId", groupMemberParseId);
        query.whereContains("groupMembersExpenditureCategory", "Entertainment");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfEntertainment += Integer.parseInt(results.get(i).getString("groupMembersExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfEntertainment;
    }

    public int totalGift() {
        int sumOfGift = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GroupMembersExpenditure");
        query.whereEqualTo("groupMemberParseId", groupMemberParseId);
        query.whereContains("groupMembersExpenditureCategory", "Gift");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfGift += Integer.parseInt(results.get(i).getString("groupMembersExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfGift;

    }
    public int totalClothes() {
        int sumOfClothes = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("GroupMembersExpenditure");
        query.whereEqualTo("groupMemberParseId", groupMemberParseId);
        query.whereContains("groupMembersExpenditureCategory", "Clothes");
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfClothes += Integer.parseInt(results.get(i).getString("groupMembersExpenditureAmount"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sumOfClothes;
    }
}
