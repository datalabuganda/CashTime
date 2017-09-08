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
import com.example.eq62roket.CashTime.models.User;

import java.util.ArrayList;

public class InvestmentActivity extends AppCompatActivity {

    private static final String TAG = "InvestmentActivity";
    EditText edtInvestments;
    Button btnInvestments;
    UserCrud userCrud;
    ListView investmentListView;
    InvestmentAdapter investmentAdapter;

    IncomeCrud incomeCrud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnInvestments = (Button) findViewById(R.id.btnInvestment);
        edtInvestments = (EditText) findViewById(R.id.edtInvestment);
        investmentListView = (ListView) findViewById(R.id.InvestmentListView);
        incomeCrud = new IncomeCrud(this);


        userCrud = new UserCrud(this);

        AddInvestments();
        populateListView();

    }

    public void AddInvestments(){
        btnInvestments.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!edtInvestments.getText().toString().equals("")) {
                            int yVal = Integer.parseInt(String.valueOf(edtInvestments.getText()));
                            boolean isInseted = incomeCrud.insertInvestment(yVal);
                            if (isInseted) {
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

    private void populateListView(){
        Log.d(TAG, "populateListView: Displayng data in the listView");

        Cursor data = incomeCrud.getInvestment();
        investmentAdapter = new InvestmentAdapter(this, data);
        investmentListView.setAdapter(investmentAdapter);
        Log.d(TAG, "here is data: " + data);


        investmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String investment = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on" + investment);

                Cursor data = incomeCrud.getInvestmentID(investment);
                int investmentID = -1;
                while (data.moveToNext()){
                    investmentID = data.getInt(0);

                }
                if (investmentID > -1){
                    Intent editInvestmentIntent = new Intent(InvestmentActivity.this, UpdateInvestmentActivity.class);
                    editInvestmentIntent.putExtra("id", investmentID);
                    editInvestmentIntent.putExtra("investment", investment);
                    Log.d(TAG, "almost through: " + investment);
                    startActivity(editInvestmentIntent);
                    finish();
                }else {

                }
            }
        });
    }



}
