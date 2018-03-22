package com.example.eq62roket.cashtime;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.adapters.DatabaseAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddGroupMembersIncomeActivity extends AppCompatActivity {
    private static final String TAG = "AddGroupMembersIncomeActivity";
    TextView membersName, deleteText;
    Spinner memberIncomePeriod;
    EditText memberIncomeDate, incomeSource, memberIncomeNotes, membersIncomeAmount;
    ImageView addIncomeSource;

    Button memberIncomeSaveButton;


    Calendar myCalendar = Calendar.getInstance();
    Context context = this;
    String dateFormat = "dd.MM.yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_members_income);


        membersName = (TextView)findViewById(R.id.memberNameIncome);
        memberIncomePeriod = (Spinner)findViewById(R.id.memberIncomePeriod);
        memberIncomeDate = (EditText)findViewById(R.id.memberIncomeDate);
        memberIncomeNotes = (EditText)findViewById(R.id.memberIncomeNotes);
        membersIncomeAmount = (EditText)findViewById(R.id.memberIncomeAmount);

        memberIncomeSaveButton = (Button)findViewById(R.id.memberIncomeSaveButton);

        memberIncomeSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent saveIncomeIntent = new Intent(AddGroupMembersIncomeActivity.this, IncomeActivity.class);
                startActivity(saveIncomeIntent);
            }
        });


        addIncomeSource = (ImageView)findViewById(R.id.addIncomeSource);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add income source");

        incomeSource=new EditText(this);
        builder.setView(incomeSource);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String source = incomeSource.getText().toString();
                Toast.makeText(getApplicationContext(), source, Toast.LENGTH_SHORT).show();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        final AlertDialog alertDialog = builder.create();


//        addIncomeSource.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.show();
//            }
//        });


        // init - set date to current date
        long currentdate = System.currentTimeMillis();
        String dateString = sdf.format(currentdate);
        memberIncomeDate.setText(dateString);


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };

        memberIncomeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //Receive member from main activity
        Intent intent = getIntent();

//        final String name = intent.getExtras().getString("Name");

//        membersName.setText(name);

        int oneDay = 1;
        int oneWeek = 7;
        int oneMonth = 1;

        Date now = new Date();
        Date now1 = new Date();
        Date now2 = new Date();

        Date addday = addDay(now, oneDay);
        Date addweek = addWeek(now1, oneWeek);
        Date addmonth = addMonth(now2, oneMonth);


        DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        String Daily = df.format(addday);
        String Weekly = df.format(addweek);
        String Monthly = df.format(addmonth);

        Map<String, String> dailyPeriod = new HashMap<String, String>();
        dailyPeriod.put("Daily", Daily);
        System.out.println(dailyPeriod.get("Daily"));
        Log.d(TAG, "Daily Period: " + dailyPeriod.get("Daily"));
        String daily = dailyPeriod.get("Daily");

        Map<String, String> weeklyPeriod = new HashMap<>();
        weeklyPeriod.put("Weekly", Weekly);
        Log.d(TAG, "Weekly Period: " + weeklyPeriod.get("Weekly"));

        Map<String, String> monthlyPeriod = new HashMap<>();
        monthlyPeriod.put("Monthly", Monthly);
        Log.d(TAG, "Monthly Period: " + monthlyPeriod.get("Monthly"));


        String[] periods = {String.valueOf(dailyPeriod), String.valueOf(weeklyPeriod), String.valueOf(monthlyPeriod)};


        String[] others = {Daily, Weekly, Monthly};
        String[] incomePeriods = {dailyPeriod.get(Daily), dailyPeriod.get(Weekly), dailyPeriod.get(Monthly)};


        ArrayAdapter<String> othersSpinnerAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                others
        );

        Spinner materialOthersSpinner = (Spinner) findViewById(R.id.memberIncomePeriod);
        materialOthersSpinner.setAdapter(othersSpinnerAdapter);

    }


    public static Date addDay(Date d, int days) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return  cal.getTime();
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

    private void updateDate() {
        memberIncomeDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void AddMemberIncome(String id, String source, String amount, String date, String period, String notes){
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);

        databaseAdapter.openDatabase();

        long result = databaseAdapter.addMemberIncome(id, source,amount, date, period, notes);

        if (result>0){
            Toast.makeText(AddGroupMembersIncomeActivity.this,"Member Income added", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(AddGroupMembersIncomeActivity.this, "Failed to add member income", Toast.LENGTH_SHORT).show();
        }
    }
}
