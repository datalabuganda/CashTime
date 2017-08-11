package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eq62roket.CashTime.helper.IncomeSQLiteHelper;
import com.example.eq62roket.CashTime.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class IncomeActivity extends AppCompatActivity {

    ImageView imgSalary, imgInvestment, imgOthers, imgLoan;
    BarChart barChart;
    IncomeSQLiteHelper myHelper;
    TextView totalIncome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        imgInvestment = (ImageView) findViewById(R.id.imgInvestment);
        imgLoan = (ImageView) findViewById(R.id.imgLoan);
        imgOthers = (ImageView) findViewById(R.id.imgOthers);
        imgSalary = (ImageView) findViewById(R.id.imgSalary);
        totalIncome = (TextView) findViewById(R.id.txtTotalIncome);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new IncomeSQLiteHelper(this);


        imgInvestment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Investmentintent = new Intent(IncomeActivity.this, InvestmentActivity.class);
                IncomeActivity.this.startActivity(Investmentintent);
            }
        });

        imgSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Salaryintent = new Intent(IncomeActivity.this, SalaryActivity.class);
                IncomeActivity.this.startActivity(Salaryintent);
            }
        });

        imgOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Othersintent = new Intent(IncomeActivity.this, OtherIncomesActivity.class);
                IncomeActivity.this.startActivity(Othersintent);
            }
        });

        imgLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Loanintent = new Intent(IncomeActivity.this, LoanActivity.class);
                IncomeActivity.this.startActivity(Loanintent);
            }
        });

        setUpBarGraph();
        sumAllIncomes();

    }

    private void setUpBarGraph(){
        barChart = (BarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> entries = new ArrayList<>();

        int sumIncome = myHelper.addAllIncome();

        entries.add(new BarEntry(sumIncome, 0));


        BarDataSet dataset = new BarDataSet(entries, "Income");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Income");


        BarData data = new BarData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);


//        barChart.setTouchEnabled(true);
//        barChart.setDragEnabled(true);
//        barChart.setScaleEnabled(true);
        barChart.setData(data);
//        barChart.animateY(5000);

        barChart.setTouchEnabled(false);
        barChart.setDragEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setData(data);
        barChart.animateY(50);

    }

    public void sumAllIncomes(){
        int sumIncome = myHelper.addAllIncome();
        totalIncome.setText(sumIncome+"");
    }
}
