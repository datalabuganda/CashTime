package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.adapters.LoanAdapter;
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

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String Daily = df.format(addday);
        String Weekly = df.format(addweek);
        String Monthly = df.format(addmonth);



//        Date [] dates = { addday, addweek, addmonth };

        String[] loan = {Daily, Weekly, Monthly};

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

        String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        final String speriod = this.loanPeriod();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDate = new Date();

        try{
            convertedDate = dateFormat.parse(speriod);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "converted date: " + convertedDate);

        if (System.currentTimeMillis() > convertedDate.getTime()){
            btnLoans.setEnabled(true);
        }
        else{
            btnLoans.setEnabled(false);
        }

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

    public String loanPeriod(){
        String period = incomeCrud.getLoanPeriod();
        return period;

    }

}
