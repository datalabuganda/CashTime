package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;
;
import com.example.eq62roket.cashtime.Helper.ParseIncomeHelper;
import com.example.eq62roket.cashtime.Models.GroupExpenditure;
import com.example.eq62roket.cashtime.Models.GroupIncome;
import com.example.eq62roket.cashtime.R;
import com.parse.ParseUser;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddGroupIncomeActivity extends AppCompatActivity {

    private static final String TAG = "AddGroupIncomeActivity";
    private EditText incomeSource, incomeAmount,incomeNotes;
    private Button groupIncomeSaveBtn, groupIncomeCancelBtn;
    private TextView mGroupName;
    private MaterialBetterSpinner materialPeriodSpinner;

    private String groupLocalUniqueID = "";
    private String selectedPeriod;
    private ParseIncomeHelper mParseHelper;

    public static String[] incomeSources = {"Loan", "Investment", "Salary", "Wage", "Donation", "Savings"};
    public static String[] incomePeriods = {"Weekly", "Monthly"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_income);

        incomeSource = (EditText)findViewById(R.id.groupIncomeSource);
        incomeAmount = (EditText)findViewById(R.id.groupIncomeAmount);

        incomeNotes = (EditText)findViewById(R.id.groupIncomeNotes);
        mGroupName = (TextView) findViewById(R.id.groupNameIncome);
        materialPeriodSpinner = (MaterialBetterSpinner) findViewById(R.id.groupIncomePeriod);
        groupIncomeSaveBtn = (Button)findViewById(R.id.groupIncomeSaveBtn);
        groupIncomeCancelBtn = (Button)findViewById(R.id.groupIncomeCancelBtn);

        Intent intent = getIntent();
        String groupName = intent.getStringExtra("groupName");
        groupLocalUniqueID = intent.getStringExtra("groupLocalUniqueID");

        final GroupExpenditure groupExpenditure = new GroupExpenditure();
//        groupExpenditure.setGroupParseId(groupParseId);


        Log.d(TAG, "username " + groupName);
        Log.d(TAG, "groupLocalUniqueID " + groupLocalUniqueID);

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
        getSelectedPeriod();
    }

    public void groupIncomeSources(){
        ArrayAdapter<String> incomeSourceAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                incomeSources
        );

        MaterialBetterSpinner materialIncomeSourceSpinner = (MaterialBetterSpinner)
                findViewById(R.id.groupIncomeSource);
        materialIncomeSourceSpinner.setAdapter(incomeSourceAdapter);

    }

    public void getSelectedPeriod(){
        List<String> periods = new ArrayList<>();
        periods.add("Daily");
        periods.add("Weekly");
        periods.add("Monthly");

        ArrayAdapter<String> periodAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                periods
        );
        materialPeriodSpinner.setAdapter(periodAdapter);

        materialPeriodSpinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                selectedPeriod = editable.toString();
            }
        });

    }


    public String getDateToday(){
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String dateToday = simpleDateFormat.format(today);
        return dateToday;
    }

    private void saveGroupIncome(){
        if ( !incomeSource.getText().toString().equals("") &&
                !incomeAmount.getText().toString().equals("") &&
                selectedPeriod != null ){
            String source = incomeSource.getText().toString();
            String amount = incomeAmount.getText().toString();
            String notes = incomeNotes.getText().toString();
            String groupName = mGroupName.getText().toString();
            String currentUserId = ParseUser.getCurrentUser().getObjectId();

            GroupIncome groupIncome = new GroupIncome();
            groupIncome.setSource(source);
            groupIncome.setAmount(amount);
            groupIncome.setPeriod(selectedPeriod);
            groupIncome.setNotes(notes);
            groupIncome.setGroupLocalUniqueID(groupLocalUniqueID);
            groupIncome.setGroupName(groupName);
            groupIncome.setUserId(currentUserId);

            new ParseIncomeHelper(this).saveGroupIncomeToParseDb(groupIncome);
            startTabbedIncomeActivity();

            Toast.makeText(AddGroupIncomeActivity.this, "Group Income " + groupIncome.getSource() + " saved", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(AddGroupIncomeActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedIncomeActivity(){
        Intent tabbedIncomeIntent = new Intent(AddGroupIncomeActivity.this, TabbedIncomeActivity.class);
        tabbedIncomeIntent.putExtra("position", "0");
        startActivity(tabbedIncomeIntent);
        finish();
    }
}
