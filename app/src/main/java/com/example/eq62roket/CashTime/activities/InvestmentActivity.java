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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

        int oneDay = 1;
        int oneWeek = 7;
        int oneMonth = 1;

        Date now = new Date();
        Date now1 = new Date();
        Date now2 = new Date();

        Date addday = addDay(now, oneDay);
        Date addweek = addWeek(now1, oneWeek);
        Date addmonth = addMonth(now2, oneMonth);

        Log.d(TAG, "add one day: " + addday);
        Log.d(TAG, "add one week: " + addweek);
        Log.d(TAG, "add one month: " + addmonth);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String Daily = df.format(addday);
        String Weekly = df.format(addweek);
        String Monthly = df.format(addmonth);



//        Date [] dates = { addday, addweek, addmonth };

        String[] investment = {Daily, Weekly, Monthly};

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

        String currentDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        final String speriod = this.investmentPeriod();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date convertedDate = new Date();

        try{
            convertedDate = dateFormat.parse(speriod);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "converted date: " + convertedDate);

        if (System.currentTimeMillis() > convertedDate.getTime()){
            btnInvestments.setEnabled(true);
        }
        else{
            btnInvestments.setEnabled(false);
        }


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

    public static Date addDay(Date d, int days) {
        // Calendar cal = Calendar.getInstance().getInstance();
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.add(Calendar.DAY_OF_YEAR, days);
        d = cal.getTime();
        return  d;
    }

    public static Date addWeek(Date date, int numberOfDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, numberOfDays);
        return cal.getTime();
    }

    public static Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
    }

    public String investmentPeriod(){
        String period = incomeCrud.getInvestmentPeriod();
        return period;

    }

}
