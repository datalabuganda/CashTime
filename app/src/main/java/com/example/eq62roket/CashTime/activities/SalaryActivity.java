package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_INCOME_SALARY;


public class SalaryActivity extends AppCompatActivity {
    private static final String TAG = "SalaryActivity";
    Button btnSalary, updateSalary;
    EditText edtSalary, salaryspinner;
    IncomeCrud incomeCrud;
    UserCrud userCrud;
    ListView SalaryListVIew;
    SalaryAdapter salaryAdapter;
    Cursor c;
    public SQLiteDatabase db;
    public DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSalary = (Button) findViewById(R.id.btnSalary);
        edtSalary = (EditText) findViewById(R.id.edtSalary);
        SalaryListVIew = (ListView) findViewById(R.id.SalaryListView);
        salaryspinner = (EditText) findViewById(R.id.salarySpinner);

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


        //String[] salary = new String[]{Daily, Weekly, Monthly};

        Map<String, String> salary = new HashMap<String, String>();
        salary.put("Daily", Daily);
        salary.put("Weekly", Weekly);
        salary.put("Daily", Daily);

        salary.get("Daily");
        salary.get("Weekly");
        salary.get("Monthly");

        ArrayAdapter<String> salarySpinnerAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                (List<String>) salary
        );

        MaterialBetterSpinner materialSalarySpinner = (MaterialBetterSpinner) findViewById(R.id.salarySpinner);
        materialSalarySpinner.setAdapter(salarySpinnerAdapter);

        incomeCrud = new IncomeCrud(this);

        userCrud = new UserCrud(this);


        ArrayList<Income> loanArrayList = new ArrayList<>();
        loanArrayList = incomeCrud.getAllSalary();

        salaryAdapter = new SalaryAdapter(this, R.layout.salary_list_adapter, loanArrayList);
        SalaryListVIew.setAdapter(salaryAdapter);

        String currentDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        final String speriod = this.salaryPeriod();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date convertedDate = new Date();

        try{
            convertedDate = dateFormat.parse(speriod);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "converted date: " + convertedDate);

        if (System.currentTimeMillis() > convertedDate.getTime()){
            btnSalary.setEnabled(true);
        }
        else{
            btnSalary.setEnabled(false);
        }
        AddSalary();
        salaryPeriod();

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

    public String salaryPeriod(){
        String period = incomeCrud.getSalaryPeriod();
        return period;

    }

}