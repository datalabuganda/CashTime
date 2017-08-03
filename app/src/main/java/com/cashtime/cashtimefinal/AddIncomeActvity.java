package com.cashtime.cashtimefinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddIncomeActvity extends AppCompatActivity {

    TextView tvTotalIncome;
    Button btnSalary, btnLoan, btnOther, btnInvestment;

    private int totalIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income_actvity);

        tvTotalIncome = (TextView) findViewById(R.id.tvTotalIncome);
        btnSalary = (Button) findViewById(R.id.btnSalary);
        btnLoan = (Button) findViewById(R.id.btnLoan);
        btnOther = (Button) findViewById(R.id.btnOther);
        btnInvestment = (Button) findViewById(R.id.btnInvestment);

        totalIncome = 100000;

        tvTotalIncome.setText(String.valueOf(totalIncome));

        btnSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddIncomeActvity.this, IncomeSourceActivity.class);
                startActivity(intent);
            }
        });
    }
}
