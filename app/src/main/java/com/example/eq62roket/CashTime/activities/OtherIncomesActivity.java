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

import com.example.eq62roket.CashTime.adapters.LoanAdapter;
import com.example.eq62roket.CashTime.adapters.OtherIncome;
import com.example.eq62roket.CashTime.adapters.SalaryAdapter;
import com.example.eq62roket.CashTime.helper.IncomeSQLiteHelper;
import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.DatabaseHelper;
import com.example.eq62roket.CashTime.helper.IncomeCrud;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Income;
import com.example.eq62roket.CashTime.models.User;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

public class OtherIncomesActivity extends AppCompatActivity {

    private static final String TAG = "OtherIncomesActivity";
    EditText edtOthers, othersspinner;
    Button btnOthers;
    IncomeCrud incomeCrud;
    UserCrud userCrud;
    ListView othersListVIew;
    OtherIncome otherincomeAdapter;

    public static String[] others = {"Daily", "Weekly", "Monthly"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_incomes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnOthers = (Button) findViewById(R.id.btnOthers);
        edtOthers = (EditText) findViewById(R.id.edtOthers);
        othersListVIew = (ListView) findViewById(R.id.otherIncomeListView);

        othersspinner = (EditText) findViewById(R.id.othersSpinner);

        // adapter for gender
        ArrayAdapter<String> othersSpinnerAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                others
        );

        MaterialBetterSpinner materialOthersSpinner = (MaterialBetterSpinner) findViewById(R.id.othersSpinner);
        materialOthersSpinner.setAdapter(othersSpinnerAdapter);

        incomeCrud = new IncomeCrud(this);

        userCrud = new UserCrud(this);

        ArrayList<Income> loanArrayList = new ArrayList<>();
        loanArrayList = incomeCrud.getAllOthers();

        otherincomeAdapter = new OtherIncome(this, R.layout.otherincome_list_adapter, loanArrayList);
        othersListVIew.setAdapter(otherincomeAdapter);



        AddOthers();
    }

    public void AddOthers(){
        btnOthers.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!edtOthers.getText().toString().equals("") && !othersspinner.getText().toString().equals("")) {
//                            int yVal = Integer.parseInt(String.valueOf(edtLoans.getText()));
                            boolean isInseted = incomeCrud.insertOthers(Integer.parseInt(edtOthers.getText().toString()),othersspinner.getText().toString());
                            if (isInseted) {
                                // if user adds income, award them 2 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
                                user.setSyncStatus(0);
                                userCrud.updateUser(user);

                                Toast.makeText(OtherIncomesActivity.this, "Your income has been added", Toast.LENGTH_LONG).show();

                                Intent othersintent = new Intent(OtherIncomesActivity.this, IncomeActivity.class);
                                OtherIncomesActivity.this.startActivity(othersintent);
                                finish();
                            } else {
                                Toast.makeText(OtherIncomesActivity.this, "Your income has not been added", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(OtherIncomesActivity.this, "Please input amount and period before submitting", Toast.LENGTH_LONG).show();
                        }

                        }

                }
        );
    }


}
