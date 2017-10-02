package com.example.eq62roket.CashTime.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
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

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.DatabaseHelper;
import com.example.eq62roket.CashTime.helper.IncomeCrud;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.adapters.GoalListAdapter;
import com.example.eq62roket.CashTime.adapters.LoanAdapter;
import com.example.eq62roket.CashTime.helper.IncomeSQLiteHelper;
import com.example.eq62roket.CashTime.helper.UserCrud;

import com.example.eq62roket.CashTime.models.Goal;
import com.example.eq62roket.CashTime.models.Income;
import com.example.eq62roket.CashTime.models.User;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import static com.example.eq62roket.CashTime.R.id.parent;


public class LoanActivity extends AppCompatActivity {

    private static final String TAG = "LoanActivity";
    EditText edtLoans,  loanspinner;
    Button btnLoans;
    IncomeCrud incomeCrud;
    UserCrud userCrud;
    ListView LoanListVIew;
    LoanAdapter loanAdapter;


//    public static String[] loan = {"Daily", "Weekly", "Monthly"};
    public static String[] loan = {"Daily", "Weekly", "Monthly"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);
        incomeCrud = new IncomeCrud(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnLoans = (Button) findViewById(R.id.btnUpdateLoans);
        edtLoans = (EditText) findViewById(R.id.edtUpdateLoans);
        LoanListVIew = (ListView) findViewById(R.id.LoanListView);
        loanspinner = (EditText) findViewById(R.id.loanSpinner);

        // adapter for gender
        ArrayAdapter<String> loanSpinnerAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                loan
        );

        MaterialBetterSpinner materialLoanSpinner = (MaterialBetterSpinner) findViewById(R.id.loanSpinner);
        materialLoanSpinner.setAdapter(loanSpinnerAdapter);

        ArrayList<Income> loanArrayList = new ArrayList<>();
        loanArrayList = incomeCrud.getAllLoan();

        userCrud = new UserCrud(this);

        loanAdapter = new LoanAdapter(this, R.layout.loan_list_adapter, loanArrayList);
        LoanListVIew.setAdapter(loanAdapter);

        AddLoan();


    }


    public void AddLoan(){
        btnLoans.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        int yVal = Integer.parseInt(String.valueOf(edtLoans.getText()));
                        if (!edtLoans.getText().toString().equals("") && !loanspinner.getText().toString().equals("")) {
//                            int yVal = Integer.parseInt(String.valueOf(edtLoans.getText()));
                            boolean isInseted = incomeCrud.insertLoan(Integer.parseInt(edtLoans.getText().toString()), loanspinner.getText().toString());
                            if (isInseted) {
                                // if user adds income, award them 2 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
                                user.setSyncStatus(0);
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
                            Toast.makeText(LoanActivity.this, "Please input amount and period before submitting", Toast.LENGTH_LONG).show();
                        }

                    }

                }
        );
    }

}
