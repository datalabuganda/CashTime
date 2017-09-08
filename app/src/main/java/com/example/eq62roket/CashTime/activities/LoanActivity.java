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

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.adapters.GoalListAdapter;
import com.example.eq62roket.CashTime.adapters.LoanAdapter;
import com.example.eq62roket.CashTime.helper.IncomeSQLiteHelper;
import com.example.eq62roket.CashTime.helper.UserCrud;

import com.example.eq62roket.CashTime.models.Goal;
import com.example.eq62roket.CashTime.models.Income;
import com.example.eq62roket.CashTime.models.User;

import java.util.ArrayList;

import static com.example.eq62roket.CashTime.R.id.parent;


public class LoanActivity extends AppCompatActivity {

    private static final String TAG = "LoanActivity";
    EditText edtLoans;
    Button btnLoans;
    IncomeSQLiteHelper myHelper;
    UserCrud userCrud;
    ListView LoanListVIew;
    LoanAdapter loanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);
        myHelper = new IncomeSQLiteHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnLoans = (Button) findViewById(R.id.btnUpdateLoans);
        edtLoans = (EditText) findViewById(R.id.edtUpdateLoans);
        LoanListVIew = (ListView) findViewById(R.id.LoanListView);

        ArrayList<Income> loanArrayList = new ArrayList<>();
        loanArrayList = myHelper.getAllLoan();

        loanAdapter = new LoanAdapter(this, R.layout.loan_list_adapter, loanArrayList);
        LoanListVIew.setAdapter(loanAdapter);

//        LoanListVIew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });

        AddLoan();


//        populateListView();
    }


    public void AddLoan(){
        btnLoans.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!edtLoans.getText().toString().equals("")) {
                            int yVal = Integer.parseInt(String.valueOf(edtLoans.getText()));
                            boolean isInseted = myHelper.insertLoan(yVal);
                            if (isInseted) {
                                // if user adds income, award them 2 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
                                userCrud.updateUser(user);

                                Toast.makeText(LoanActivity.this, "Your income has been added", Toast.LENGTH_LONG).show();
                                Intent Loanintent = new Intent(LoanActivity.this, IncomeActivity.class);
                                LoanActivity.this.startActivity(Loanintent);
                                finish();
                            } else {
                                Toast.makeText(LoanActivity.this, "Your income has not been added", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(LoanActivity.this, "Please input amount before submitting", Toast.LENGTH_LONG).show();
                        }

                    }

                }
        );
    }

//    private void populateListView(){
//        Log.d(TAG, "populateListView: Displayng data in the listView");
//
//        Cursor data = myHelper.getLoan();
//
//        //        Log.d(TAG, "here is data: " + data);
////        loanAdapter = new LoanAdapter(this, data);
////        LoanListVIew.setAdapter(loanAdapter);
//
//
//        LoanListVIew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                String loan = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();
//                Intent editIntent = new Intent(LoanActivity.this, UpdateLoanActivity.class);
//                startActivity(editIntent);
//                Log.d(TAG, "onItemClick: You clicked on" + loan);
//
//                Cursor data = myHelper.getLoanID(loan);
//                Log.d(TAG, "onItemClick: data" + data);
//                int loanID = -1;
//                while (data.moveToNext()){
//                    loanID = data.getInt(0);
//
//                }
//                if (loanID > -1){
//                    Intent editLoanIntent = new Intent(LoanActivity.this, UpdateLoanActivity.class);
//                    editLoanIntent.putExtra("id", loanID);
//                    editLoanIntent.putExtra("loan", loan);
//                    Log.d(TAG, "almost through: " + loan);
//                    startActivity(editLoanIntent);
//                    finish();
//
//                }else {
//
//                }
//            }
//        });
//    }

}
