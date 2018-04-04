package com.example.eq62roket.cashtime.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Helper.ParseIncomeHelper;
import com.example.eq62roket.cashtime.Models.MembersIncome;
import com.example.eq62roket.cashtime.R;
import com.parse.ParseUser;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AddGroupMembersIncomeActivity extends AppCompatActivity {
    private static String TAG = "AddGroupMembersIncomeActivity";
    TextView groupMemberUserName;
    EditText memberIncomeSource, memberIncomeNotes, membersIncomeAmount;

    Button memberIncomeSaveButton, memberIncomeCancelButton;

    ImageView addIncomeSourceIcon;

    TextView memberIncomePeriod;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    Calendar myCalendar = Calendar.getInstance();
    Context context = this;
    String dateFormat = "dd/MM/yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

    private String groupMemberParseId = "";
    private ParseIncomeHelper mParseHelper;

    public static String[] incomeSources = {"Loan", "Investment", "Salary", "Wage", "Donation", "Savings"};

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_members_income);
        mParseHelper = new ParseIncomeHelper(this);

        groupMemberUserName = (TextView)findViewById(R.id.memberIncomeName);
        memberIncomeSource = (EditText) findViewById(R.id.memberIncomeSource);
        memberIncomePeriod = (TextView) findViewById(R.id.membersIncomePeriod);
        memberIncomeNotes = (EditText)findViewById(R.id.memberIncomeNotes);
        membersIncomeAmount = (EditText)findViewById(R.id.memberIncomeAmount);

        addIncomeSourceIcon = (ImageView) findViewById(R.id.addIncomeSourceIcon);

        addIncomeSourceIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent incomeSourceIntent = new Intent(AddGroupMembersIncomeActivity.this, AddIncomeSourceActivity.class);
                startActivity(incomeSourceIntent);
            }
        });

        memberIncomeSaveButton = (Button)findViewById(R.id.memberIncomeSaveButton);
        memberIncomeCancelButton = (Button) findViewById(R.id.memberIncomeCancelBtn);


        ArrayAdapter<String> incomeSourceAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                incomeSources
        );

        MaterialBetterSpinner materialIncomeSourceSpinner = (MaterialBetterSpinner) findViewById(R.id.memberIncomeSource);
        materialIncomeSourceSpinner.setAdapter(incomeSourceAdapter);


        Intent intent = getIntent();
        String memberUserName = intent.getStringExtra("userName");
        groupMemberParseId = intent.getStringExtra("parseId");

        groupMemberUserName.setText(memberUserName);


        memberIncomeSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGroupMembersIncome();
            }
        });

        memberIncomeCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTabbedIncomeActivity();
            }
        });

        // init - set date to current date
        long currentdate = System.currentTimeMillis();
        String dateString = sdf.format(currentdate);


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

        memberIncomePeriod.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        groupMemberIncomeCategory();

    }

    public void groupMemberIncomeCategory(){
        ArrayAdapter<String> incomeSourceAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                incomeSources
        );

        MaterialBetterSpinner materialIncomeSourceSpinner = (MaterialBetterSpinner) findViewById(R.id.memberIncomeSource);
        materialIncomeSourceSpinner.setAdapter(incomeSourceAdapter);
    }

    private void updateDate() {
        memberIncomePeriod.setText(sdf.format(myCalendar.getTime()));
    }

    private void saveGroupMembersIncome(){
        if ( !membersIncomeAmount.getText().toString().equals("") &&
                !memberIncomeSource.getText().toString().equals("") &&
                !memberIncomePeriod.getText().toString().equals("")){
            String groupMemberIncomeSource = memberIncomeSource.getText().toString();
            String groupMemberIncomeAmount = membersIncomeAmount.getText().toString();
            String groupMemberIncomeIncomePeriod = memberIncomePeriod.getText().toString();
            String groupMemberIncomeNotes = memberIncomeNotes.getText().toString();
            String groupMemberUsername = groupMemberUserName.getText().toString();
            String currentUser = ParseUser.getCurrentUser().getObjectId();


            MembersIncome groupMemberIncome = new MembersIncome();
            groupMemberIncome.setSource(groupMemberIncomeSource);
            groupMemberIncome.setAmount(groupMemberIncomeAmount);
            groupMemberIncome.setDueDate(groupMemberIncomeIncomePeriod);
            groupMemberIncome.setNotes(groupMemberIncomeNotes);
            groupMemberIncome.setMemberUserName(groupMemberUsername);
            groupMemberIncome.setMemberParseId(groupMemberParseId);
            groupMemberIncome.setUserId(currentUser);

            Log.d("Income", "groupMemberUserName: " + groupMemberIncome.getMemberUserName());


            // TODO: 3/22/18 =====> save object to db
            new ParseIncomeHelper(this).saveGroupMemberIncomeToParseDb(groupMemberIncome);
            startTabbedIncomeActivity();

            Toast.makeText(context, "Group Member Income Saved " + groupMemberIncome.getAmount() + " saved", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedIncomeActivity(){
        Intent tabbedIncomeIntent = new Intent(AddGroupMembersIncomeActivity.this, TabbedIncomeActivity.class);
        startActivity(tabbedIncomeIntent);
        finish();
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

}
