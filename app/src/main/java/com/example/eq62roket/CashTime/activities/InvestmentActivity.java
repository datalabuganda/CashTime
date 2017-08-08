package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.CashTime.helper.IncomeSQLiteHelper;
import com.example.eq62roket.CashTime.R;

public class InvestmentActivity extends AppCompatActivity {

    EditText edtInvestments;
    Button btnInvestments;

    IncomeSQLiteHelper myHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnInvestments = (Button) findViewById(R.id.btnInvestment);
        edtInvestments = (EditText) findViewById(R.id.edtInvestment);
        myHelper = new IncomeSQLiteHelper(this);

        btnInvestments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Incomeintent = new Intent(InvestmentActivity.this, IncomeActivity.class);
                InvestmentActivity.this.startActivity(Incomeintent);
            }
        });

        AddInvestments();
    }

    public void AddInvestments(){
        btnInvestments.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int yVal = Integer.parseInt(String.valueOf(edtInvestments.getText()));
                        boolean isInseted = myHelper.insertInvestment(yVal);
                        if (isInseted = true)
                            Toast.makeText(InvestmentActivity.this, "Your income has been added", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(InvestmentActivity.this, "Your income has not been added", Toast.LENGTH_LONG).show();
                        Intent Investmentintent = new Intent(InvestmentActivity.this, IncomeActivity.class);
                        InvestmentActivity.this.startActivity(Investmentintent);
                    }

                }
        );
    }


}
