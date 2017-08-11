package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eq62roket.CashTime.helper.IncomeSQLiteHelper;
import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ExpenditureActivity extends AppCompatActivity {

    ImageView imgTransport, imgEducation, imgSavings, imgHomeneeds, imgOthers, imgHealth;
    BarChart barChart;
    SQLiteHelper myHelper;
    IncomeSQLiteHelper incomeMyHelper;
    TextView txtRemainingIncome;
    int remainingIncome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure);

        imgEducation = (ImageView) findViewById(R.id.imgEducation);
        imgTransport = (ImageView) findViewById(R.id.imgTransport);
        imgSavings = (ImageView) findViewById(R.id.imgSavings);
        imgHomeneeds = (ImageView) findViewById(R.id.imgHomeneeds);
        imgHealth = (ImageView) findViewById(R.id.imgHealth);
        imgOthers = (ImageView) findViewById(R.id.imgOthers);
        txtRemainingIncome = (TextView) findViewById(R.id.txtRemainingIncome);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myHelper = new SQLiteHelper(this);
        incomeMyHelper = new IncomeSQLiteHelper(this);


        imgOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Otherintent = new Intent(ExpenditureActivity.this, OthersActivity.class);
                ExpenditureActivity.this.startActivity(Otherintent);
            }
        });

        imgEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Educationintent = new Intent(ExpenditureActivity.this, EducationActivity.class);
                ExpenditureActivity.this.startActivity(Educationintent);
            }
        });

        imgHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Healthintent = new Intent(ExpenditureActivity.this, HealthActivity.class);
                ExpenditureActivity.this.startActivity(Healthintent);
            }
        });

        imgTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Transportintent = new Intent(ExpenditureActivity.this, TransportActivity.class);
                ExpenditureActivity.this.startActivity(Transportintent);
            }
        });

        imgHomeneeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Homeneedsintent = new Intent(ExpenditureActivity.this, HomeneedsActivity.class);
                ExpenditureActivity.this.startActivity(Homeneedsintent);
            }
        });

        imgSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Savingsintent = new Intent(ExpenditureActivity.this, SavingsActivity.class);
                ExpenditureActivity.this.startActivity(Savingsintent);
            }
        });

        setUpBarGraph();
        remainingIncome();
    }

    private void setUpBarGraph(){
        barChart = (BarChart) findViewById(R.id.barchart);
        int remaining = this.remainingIncome();

        ArrayList<BarEntry> entries = new ArrayList<>();

        //int sumIncome = myHelper.addAllInvestment();

        //entries.add(new BarEntry(sumIncome, 0));

        entries.add(new BarEntry(remaining, 0));

        BarDataSet dataset = new BarDataSet(entries, "Expenditure");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("");


        BarData data = new BarData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

<<<<<<< HEAD
//        barChart.setTouchEnabled(true);
//        barChart.setDragEnabled(true);
//        barChart.setScaleEnabled(true);
        barChart.setData(data);
   //     barChart.animateY(5000);
=======
        barChart.setTouchEnabled(false);
        barChart.setDragEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setData(data);
        barChart.animateY(50);
>>>>>>> origin/master
    }

    public int remainingIncome(){
        int totalIncome = incomeMyHelper.addAllIncome();
        int totalExpenditure = myHelper.addAllCategories();

        remainingIncome = totalIncome - totalExpenditure;
        txtRemainingIncome.setText(remainingIncome+"");

        return remainingIncome;

    }
}
