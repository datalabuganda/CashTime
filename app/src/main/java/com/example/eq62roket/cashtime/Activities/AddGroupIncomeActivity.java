package com.example.eq62roket.cashtime.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseExpenditureHelper;
import com.example.eq62roket.cashtime.Helper.ParseIncomeHelper;
import com.example.eq62roket.cashtime.Helper.PeriodHelper;
import com.example.eq62roket.cashtime.Models.GroupExpenditure;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.Models.GroupIncome;
import com.example.eq62roket.cashtime.R;
import com.parse.ParseUser;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddGroupIncomeActivity extends AppCompatActivity {
    private static final String TAG = "AddGroupIncomeActivity";
    EditText incomeSource, incomeAmount,incomeNotes, incomePeriod;
    Button groupIncomeSaveBtn, groupIncomeCancelBtn;
    TextView mGroupName;
    private String selectedPeriod;

    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    Calendar myCalendar = Calendar.getInstance();
    Context context = this;
    String dateFormat = "dd/MM/yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

    private String groupParseId = "";
    private String selectedIncomePeriod;

    String daily, weekly, monthly;
    Calendar mCalendar = Calendar.getInstance();
    Date today = new Date();
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private PeriodHelper periodHelper;
    private ParseIncomeHelper iParseHelper;
    private ParseExpenditureHelper eParseHelper;
    private GroupIncome groupIncome;



    public static String[] incomeSources = {"Loan", "Investment", "Salary", "Wage", "Donation", "Savings"};
    public static String[] incomePeriods = {"Weekly", "Monthly"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_income);

        incomeSource = (EditText)findViewById(R.id.groupIncomeSource);
        incomeAmount = (EditText)findViewById(R.id.groupIncomeAmount);
        incomePeriod = (EditText) findViewById(R.id.groupIncomePeriod);
        incomeNotes = (EditText)findViewById(R.id.groupIncomeNotes);
        mGroupName = (TextView) findViewById(R.id.groupNameIncome);

        groupIncomeSaveBtn = (Button)findViewById(R.id.groupIncomeSaveBtn);
        groupIncomeCancelBtn = (Button)findViewById(R.id.groupIncomeCancelBtn);

        Intent intent = getIntent();
        String groupName = intent.getStringExtra("groupName");
        groupParseId = intent.getStringExtra("groupParseId");

        final GroupExpenditure groupExpenditure = new GroupExpenditure();
        groupExpenditure.setGroupParseId(groupParseId);


        Log.d(TAG, "username " + groupName);
        Log.d(TAG, "parseId " + groupParseId);

        mGroupName.setText(groupName);


        groupIncomeSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGroupIncome();
            }
        });


        groupIncomeCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTabbedIncomeActivity();
            }
        });

        groupIncomeSources();
//        groupIncomePeriods();
        getIncomePeriod();
    }

    public void groupIncomeSources(){
        ArrayAdapter<String> incomeSourceAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                incomeSources
        );

        MaterialBetterSpinner materialIncomeSourceSpinner = (MaterialBetterSpinner) findViewById(R.id.groupIncomeSource);
        materialIncomeSourceSpinner.setAdapter(incomeSourceAdapter);

    }

    public void groupIncomePeriods(){
        ArrayAdapter<String> incomePeriodsAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                incomePeriods
        );

        MaterialBetterSpinner materialIncomePeriosSpinner = (MaterialBetterSpinner) findViewById(R.id.groupIncomePeriod);
        materialIncomePeriosSpinner.setAdapter(incomePeriodsAdapter);

    }

    private void updateDate() {
        incomePeriod.setText(sdf.format(myCalendar.getTime()));
    }

    public String getDailyDate(){
        // returns date one day from today
        mCalendar.setTime(today);
        mCalendar.add(Calendar.DATE, 1);
        String dateTomorrow = mSimpleDateFormat.format(mCalendar.getTime());
        return dateTomorrow;
    }

    public String getWeeklyDate(){
        // returns date one week from today
        mCalendar.setTime(today);
        mCalendar.add(Calendar.DATE, 7);
        String dateNextWeek = mSimpleDateFormat.format(mCalendar.getTime());
        return dateNextWeek;
    }

    public String getMonthlyDate(){
        // return date one month from today
        mCalendar.setTime(today);
        mCalendar.add(Calendar.DATE, 30);
        String dateNextMonth = mSimpleDateFormat.format(mCalendar.getTime());
        return dateNextMonth;
    }

    public String getDateToday(){
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String dateToday = simpleDateFormat.format(today);
        return dateToday;
    }


    public void getIncomePeriod(){
        List<String> incomePeriods = new ArrayList<>();
        incomePeriods.add("Weekly");
        incomePeriods.add("Monthly");

        ArrayAdapter<String> incomePeriodsAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                incomePeriods
        );

        MaterialBetterSpinner materialIncomePeriodsSpinner = (MaterialBetterSpinner) findViewById(R.id.groupIncomePeriod);
        materialIncomePeriodsSpinner.setAdapter(incomePeriodsAdapter);

        materialIncomePeriodsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Get selected period
                selectedIncomePeriod = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private void saveGroupIncome(){
        if ( !incomeSource.getText().toString().equals("") &&
                !incomeAmount.getText().toString().equals("")){
            String source = incomeSource.getText().toString();
            String amount = incomeAmount.getText().toString();
            String notes = incomeNotes.getText().toString();
            String period = incomePeriod.getText().toString();
            String groupName = mGroupName.getText().toString();
            String currentUserId = ParseUser.getCurrentUser().getObjectId();


            Log.d(TAG, "source: " + source);
            Log.d(TAG, "amount: " + amount);
            Log.d(TAG, "notes: " + notes);
            Log.d(TAG, "period: " + period);

            GroupIncome groupIncome = new GroupIncome();
            groupIncome.setSource(source);
            groupIncome.setAmount(amount);
            groupIncome.setPeriod(period);
            groupIncome.setNotes(notes);
            groupIncome.setGroupParseId(groupParseId);
            groupIncome.setGroupName(groupName);
            groupIncome.setUserId(currentUserId);

            Log.d(TAG, "saveGroupIncome: " + groupIncome.getAmount());

            new ParseIncomeHelper(this).saveGroupIncomeToParseDb(groupIncome);
            startTabbedIncomeActivity();

            Toast.makeText(AddGroupIncomeActivity.this, "Group Income " + groupIncome.getSource() + " saved", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(AddGroupIncomeActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedIncomeActivity(){
        Intent tabbedIncomeIntent = new Intent(AddGroupIncomeActivity.this, TabbedIncomeActivity.class);
        tabbedIncomeIntent.putExtra("fragment_index_key", 2);
        startActivity(tabbedIncomeIntent);
        finish();
    }
}
