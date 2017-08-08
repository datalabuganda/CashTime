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

public class LoanActivity extends AppCompatActivity {

    EditText edtLoans;
    Button btnLoans;
    IncomeSQLiteHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);
        myHelper = new IncomeSQLiteHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnLoans = (Button) findViewById(R.id.btnLoans);
        edtLoans = (EditText) findViewById(R.id.edtLoans);

        btnLoans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Incomeintent = new Intent(LoanActivity.this, IncomeActivity.class);
                LoanActivity.this.startActivity(Incomeintent);
            }
        });

        AddLoan();
    }

    public void AddLoan(){
        btnLoans.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int yVal = Integer.parseInt(String.valueOf(edtLoans.getText()));
                        boolean isInseted = myHelper.insertLoan(yVal);
                        if (isInseted = true)
                            Toast.makeText(LoanActivity.this, "Your income has been added", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(LoanActivity.this, "Your income has not been added", Toast.LENGTH_LONG).show();
                        Intent Loanintent = new Intent(LoanActivity.this, IncomeActivity.class);
                        LoanActivity.this.startActivity(Loanintent);
                    }

                }
        );
    }
}
