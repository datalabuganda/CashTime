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

public class SalaryActivity extends AppCompatActivity {
    Button btnSalary;
    EditText edtSalary;
    IncomeSQLiteHelper myHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSalary = (Button) findViewById(R.id.btnSalary);
        edtSalary = (EditText) findViewById(R.id.edtSalary);
        myHelper = new IncomeSQLiteHelper(this);

        btnSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Incomeintent = new Intent(SalaryActivity.this, IncomeActivity.class);
                SalaryActivity.this.startActivity(Incomeintent);
            }
        });

        AddSalary();
    }

    public void AddSalary(){
        btnSalary.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int yVal = Integer.parseInt(String.valueOf(edtSalary.getText()));
                        boolean isInseted = myHelper.insertSalary(yVal);
                        if (isInseted = true)
                            Toast.makeText(SalaryActivity.this, "Your income has been added", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(SalaryActivity.this, "Your income has not been added", Toast.LENGTH_LONG).show();
                        Intent Salaryintent = new Intent(SalaryActivity.this, IncomeActivity.class);
                        SalaryActivity.this.startActivity(Salaryintent);
                    }

                }
        );
    }
}
