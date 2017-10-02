package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eq62roket.CashTime.adapters.InvestmentAdapter;
import com.example.eq62roket.CashTime.adapters.LoanAdapter;
import com.example.eq62roket.CashTime.helper.IncomeSQLiteHelper;
import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.DatabaseHelper;
import com.example.eq62roket.CashTime.helper.IncomeCrud;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Income;
import com.example.eq62roket.CashTime.models.User;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

public class InvestmentActivity extends AppCompatActivity {

    private static final String TAG = "InvestmentActivity";
    EditText edtInvestments, investmentspinner;
    Button btnInvestments;
    UserCrud userCrud;
    ListView investmentListView;
    InvestmentAdapter investmentAdapter;

    IncomeCrud incomeCrud;

    public static String[] investment = {"Daily", "Weekly", "Monthly"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnInvestments = (Button) findViewById(R.id.btnInvestment);
        edtInvestments = (EditText) findViewById(R.id.edtInvestment);
        investmentspinner = (EditText) findViewById(R.id.investmentSpinner);
        investmentListView = (ListView) findViewById(R.id.InvestmentListView);

        ArrayAdapter<String> investmentSpinnerAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                investment
        );

        MaterialBetterSpinner materialInvestmentSpinner = (MaterialBetterSpinner) findViewById(R.id.investmentSpinner);
        materialInvestmentSpinner.setAdapter(investmentSpinnerAdapter);
        incomeCrud = new IncomeCrud(this);

        ArrayList<Income> loanArrayList = new ArrayList<>();
        loanArrayList = incomeCrud.getAllInvestment();

        investmentAdapter = new InvestmentAdapter(this, R.layout.investment_list_adapter, loanArrayList);
        investmentListView.setAdapter(investmentAdapter);



        userCrud = new UserCrud(this);

        AddInvestments();
//        populateListView();

    }

    public void AddInvestments(){
        btnInvestments.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!edtInvestments.getText().toString().equals("") && !investmentspinner.getText().toString().equals("")) {
//                            int yVal = Integer.parseInt(String.valueOf(edtInvestments.getText()));
                            boolean isInserted = incomeCrud.insertInvestment(Integer.parseInt(edtInvestments.getText().toString()), investmentspinner.getText().toString());
                            if (isInserted) {
                                // if user adds income, award them 2 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
                                user.setSyncStatus(0);
                                userCrud.updateUser(user);

                                Toast.makeText(InvestmentActivity.this, "Your income has been added", Toast.LENGTH_LONG).show();
                                Intent Investmentintent = new Intent(InvestmentActivity.this, IncomeActivity.class);
                                InvestmentActivity.this.startActivity(Investmentintent);
                                finish();
                            }
                            else {
                                Toast.makeText(InvestmentActivity.this, "Your income has not been added", Toast.LENGTH_LONG).show();
                            }

                        }
                        else{
                            Toast.makeText(InvestmentActivity.this, "Please input amount before submitting", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );
    }

}
