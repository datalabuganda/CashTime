package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eq62roket.CashTime.adapters.LoanAdapter;
import com.example.eq62roket.CashTime.adapters.SalaryAdapter;
import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.DatabaseHelper;
import com.example.eq62roket.CashTime.helper.IncomeCrud;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Income;
import com.example.eq62roket.CashTime.models.User;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;


public class SalaryActivity extends AppCompatActivity {
    private static final String TAG = "SalaryActivity";
    Button btnSalary, updateSalary;
    EditText edtSalary, salaryspinner;
    IncomeCrud incomeCrud;
    UserCrud userCrud;
    ListView SalaryListVIew;
    SalaryAdapter salaryAdapter;
    Cursor c;
    public static String[] salary = {"Daily", "Weekly", "Monthly"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSalary = (Button) findViewById(R.id.btnSalary);
        edtSalary = (EditText) findViewById(R.id.edtSalary);
        SalaryListVIew = (ListView) findViewById(R.id.SalaryListView);
        salaryspinner = (EditText) findViewById(R.id.salarySpinner);

        ArrayAdapter<String> salarySpinnerAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                salary
        );

        MaterialBetterSpinner materialLoanSpinner = (MaterialBetterSpinner) findViewById(R.id.salarySpinner);
        materialLoanSpinner.setAdapter(salarySpinnerAdapter);

        incomeCrud = new IncomeCrud(this);

        userCrud = new UserCrud(this);

        ArrayList<Income> loanArrayList = new ArrayList<>();
        loanArrayList = incomeCrud.getAllSalary();

        salaryAdapter = new SalaryAdapter(this, R.layout.salary_list_adapter, loanArrayList);
        SalaryListVIew.setAdapter(salaryAdapter);

        AddSalary();
    }

    public void AddSalary(){
        btnSalary.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!edtSalary.getText().toString().equals("") && !salaryspinner.getText().toString().equals("")) {
//                            int yVal = Integer.parseInt(String.valueOf(edtSalary.getText()));
                            boolean isInseted = incomeCrud.insertSalary(Integer.parseInt(edtSalary.getText().toString()), salaryspinner.getText().toString());
                            if (isInseted) {
                                Log.d(TAG, "phpId: "+ incomeCrud.getPhpID());
                                Log.d(TAG, "insertedSyncStatusLocal: "+ incomeCrud.getSyncStatus());
                                // if user adds income, award them 2 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
                                user.setSyncStatus(0);
                                userCrud.updateUser(user);

                                Toast.makeText(SalaryActivity.this, "Your income has been added", Toast.LENGTH_LONG).show();
                                Intent Salaryintent = new Intent(SalaryActivity.this, IncomeActivity.class);
                                SalaryActivity.this.startActivity(Salaryintent);
                                finish();
                            }
                            else {
                                Toast.makeText(SalaryActivity.this, "Your income has not been added", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(SalaryActivity.this, "Please input amount and period before submitting", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );


    }

}