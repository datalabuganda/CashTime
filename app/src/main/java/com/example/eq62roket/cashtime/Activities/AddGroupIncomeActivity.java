package com.example.eq62roket.cashtime.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseExpenditureHelper;
import com.example.eq62roket.cashtime.Helper.ParseIncomeHelper;
import com.example.eq62roket.cashtime.Models.GroupIncome;
import com.example.eq62roket.cashtime.R;
import com.parse.ParseUser;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddGroupIncomeActivity extends AppCompatActivity {
    private static final String TAG = "AddGroupIncomeActivity";
    EditText incomeSource, incomeAmount,incomeNotes;
    Button groupIncomeSaveBtn, groupIncomeCancelBtn;
    TextView mGroupName;

    TextView incomePeriod;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    Calendar myCalendar = Calendar.getInstance();
    Context context = this;
    String dateFormat = "dd/MM/yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

    private String groupParseId = "";
    private ParseIncomeHelper mParseHelper;

    public static String[] incomeSources = {"Loan", "Investment", "Salary", "Wage", "Donation", "Savings"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_income);

        incomeSource = (EditText)findViewById(R.id.groupIncomeSource);
        incomeAmount = (EditText)findViewById(R.id.groupIncomeAmount);
        incomePeriod = (TextView) findViewById(R.id.groupIncomePeriod);
        incomeNotes = (EditText)findViewById(R.id.groupIncomeNotes);
        mGroupName = (TextView) findViewById(R.id.groupNameIncome);

        groupIncomeSaveBtn = (Button)findViewById(R.id.groupIncomeSaveBtn);
        groupIncomeCancelBtn = (Button)findViewById(R.id.groupIncomeCancelBtn);

        Intent intent = getIntent();
        String groupName = intent.getStringExtra("groupName");
        groupParseId = intent.getStringExtra("groupParseId");

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

        incomePeriod.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        groupIncomeSources();
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

    private void updateDate() {
        incomePeriod.setText(sdf.format(myCalendar.getTime()));
    }


    private void saveGroupIncome(){
        // add new group goal to db
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
