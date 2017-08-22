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

import com.example.eq62roket.CashTime.helper.IncomeSQLiteHelper;
import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.User;

import java.util.ArrayList;
import java.util.List;

public class InvestmentActivity extends AppCompatActivity {

    private static final String TAG = "InvestmentActivity";
    EditText edtInvestments;
    Button btnInvestments;
    UserCrud userCrud;
    ListView investmentListView;

    IncomeSQLiteHelper myHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnInvestments = (Button) findViewById(R.id.btnInvestment);
        edtInvestments = (EditText) findViewById(R.id.edtInvestment);
        investmentListView = (ListView) findViewById(R.id.InvestmentListView);
        myHelper = new IncomeSQLiteHelper(this);

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
                            boolean isInseted = myHelper.insertInvestment(yVal);
                            if (isInseted) {
                                // if user adds income, award them 2 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
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

        Cursor data = myHelper.getInvestment();
        Log.d(TAG, "here is data: " + data);
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(4));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        investmentListView.setAdapter(adapter);

        investmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String investment = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on" + investment);

                Cursor data = myHelper.getInvestmentID(investment);
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

                }else {

                }
            }
        });
    }



}
