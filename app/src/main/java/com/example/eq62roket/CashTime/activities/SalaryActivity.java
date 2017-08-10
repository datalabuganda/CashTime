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
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.User;

public class SalaryActivity extends AppCompatActivity {
    Button btnSalary;
    EditText edtSalary;
    IncomeSQLiteHelper myHelper;
    UserCrud userCrud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSalary = (Button) findViewById(R.id.btnSalary);
        edtSalary = (EditText) findViewById(R.id.edtSalary);
        myHelper = new IncomeSQLiteHelper(this);

        userCrud = new UserCrud(this);

        /*btnSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Incomeintent = new Intent(SalaryActivity.this, IncomeActivity.class);
                SalaryActivity.this.startActivity(Incomeintent);
            }
        });
*/
        AddSalary();
    }

    public void AddSalary(){
        btnSalary.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!edtSalary.getText().toString().equals("")) {
                            int yVal = Integer.parseInt(String.valueOf(edtSalary.getText()));
                            boolean isInseted = myHelper.insertSalary(yVal);
                            if (isInseted) {
                                // if user adds income, award them 2 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
                                userCrud.updateUser(user);

                                Toast.makeText(SalaryActivity.this, "Your income has been added", Toast.LENGTH_LONG).show();
                                Intent Salaryintent = new Intent(SalaryActivity.this, IncomeActivity.class);
                                SalaryActivity.this.startActivity(Salaryintent);
                            }
                            else {
                                Toast.makeText(SalaryActivity.this, "Your income has not been added", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(SalaryActivity.this, "Please input amount before submitting", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );
    }
}
