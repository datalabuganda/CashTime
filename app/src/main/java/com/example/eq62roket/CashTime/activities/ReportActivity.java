package com.example.eq62roket.CashTime.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.ExpenditureCrud;

import java.text.DecimalFormat;

public class ReportActivity extends AppCompatActivity {

    TextView txtTransport, txtEducation, txtHealth, txtSavings, txtOthers, txtTotal, txtHomeneeds;
    ExpenditureCrud expenditureCrud;
    Button btnGraph;
    DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sums);

        txtEducation = (TextView) findViewById(R.id.txtEducation);
        txtTransport = (TextView) findViewById(R.id.txtTransport);
        txtHealth = (TextView) findViewById(R.id.txtHealth);
        txtSavings = (TextView) findViewById(R.id.txtSavings);
        txtOthers = (TextView) findViewById(R.id.txtOthers);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtHomeneeds = (TextView) findViewById(R.id.txtHomeneeds);
//        btnGraph = (Button) findViewById(R.id.btnBarGraph);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        btnGraph.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent BarGraphIntent = new Intent(ReportActivity.this, AnalysisActivity.class);
//                ReportActivity.this.startActivity(BarGraphIntent);
//            }
//        });

        formatter = new DecimalFormat("#,###,###");
        expenditureCrud = new ExpenditureCrud(this);

        sumEducation();
        sumTransport();
        sumHealth();
        sumSavings();
        sumOthers();
        sumTotal();
        sumHomeneeds();
    }

    public void sumTransport(){
        int sumTransport = expenditureCrud.addAllTransport();
        txtTransport.setText(formatter.format(sumTransport));
    }

    public void sumEducation(){
        int sumEducation = expenditureCrud.addAllEducation();
        txtEducation.setText(formatter.format(sumEducation));
    }

    public void sumHealth(){
        int sumHealth = expenditureCrud.addAllHealth();
        txtHealth.setText(formatter.format(sumHealth));
    }

    public void sumSavings(){
        int sumSavings = expenditureCrud.addAllSavings(null);
        txtSavings.setText(formatter.format(sumSavings));
    }

    public void sumOthers(){
        int sumOthers = expenditureCrud.addAllOthers();
        txtOthers.setText(formatter.format(sumOthers));
    }

    public void sumTotal(){
        int sumTotal = expenditureCrud.addAllCategories();
        txtTotal.setText(formatter.format(sumTotal));
    }

    public void sumHomeneeds(){
        int sumHomeneeds = expenditureCrud.addAllHomeneeds();
        txtHomeneeds.setText(formatter.format(sumHomeneeds));
    }


}
