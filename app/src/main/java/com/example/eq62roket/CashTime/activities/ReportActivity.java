package com.example.eq62roket.CashTime.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;

public class ReportActivity extends AppCompatActivity {

    TextView txtTransport, txtEducation, txtHealth, txtSavings, txtOthers, txtTotal, txtHomeneeds;
    SQLiteHelper mySQLiteHelper;
    Button btnGraph;

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

        mySQLiteHelper = new SQLiteHelper(this);

        sumEducation();
        sumTransport();
        sumHealth();
        sumSavings();
        sumOthers();
        sumTotal();
        sumHomeneeds();
    }

    public void sumTransport(){
        int sumTransport = mySQLiteHelper.addAllTransport();
        txtTransport.setText(sumTransport+"");
    }

    public void sumEducation(){
        int sumEducation = mySQLiteHelper.addAllEducation();
        txtEducation.setText(sumEducation+"");
    }

    public void sumHealth(){
        int sumHealth = mySQLiteHelper.addAllHealth();
        txtHealth.setText(sumHealth+"");
    }

    public void sumSavings(){
        int sumSavings = mySQLiteHelper.addAllSavings();
        txtSavings.setText(sumSavings+"");
    }

    public void sumOthers(){
        int sumOthers = mySQLiteHelper.addAllOthers();
        txtOthers.setText(sumOthers+"");
    }

    public void sumTotal(){
        int sumTotal = mySQLiteHelper.addAllCategories();
        txtTotal.setText(sumTotal+"");
    }

    public void sumHomeneeds(){
        int sumHomeneeds = mySQLiteHelper.addAllHomeneeds();
        txtHomeneeds.setText(sumHomeneeds+"");
    }


}
