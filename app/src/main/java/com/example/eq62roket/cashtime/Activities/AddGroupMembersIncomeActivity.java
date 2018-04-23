package com.example.eq62roket.cashtime.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseIncomeHelper;
import com.example.eq62roket.cashtime.Models.MembersIncome;
import com.example.eq62roket.cashtime.R;
import com.parse.ParseUser;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

public class AddGroupMembersIncomeActivity extends AppCompatActivity {
    private static String TAG = "AddGroupMembersIncomeActivity";

    private TextView groupMemberUserName;
    private EditText memberIncomeSource, memberIncomeNotes, membersIncomeAmount;
    private Button memberIncomeSaveButton, memberIncomeCancelButton;
    private MaterialBetterSpinner materialPeriodSpinner;
    private Context context = this;
    private DatePickerDialog.OnDateSetListener date;
    private String groupMemberLocalUniqueID = "";
    private String selectedPeriod;
    private ParseIncomeHelper mParseHelper;

    public static String[] incomeSources = {"Loan", "Investment", "Salary", "Wage", "Donation", "Savings"};
    public static String[] incomePeriods = {"Weekly", "Monthly"};

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_members_income);
        mParseHelper = new ParseIncomeHelper(this);

        groupMemberUserName = (TextView)findViewById(R.id.memberIncomeName);
        memberIncomeSource = (EditText) findViewById(R.id.memberIncomeSource);

        memberIncomeNotes = (EditText)findViewById(R.id.memberIncomeNotes);
        membersIncomeAmount = (EditText)findViewById(R.id.memberIncomeAmount);
        memberIncomeSaveButton = (Button)findViewById(R.id.memberIncomeSaveButton);
        memberIncomeCancelButton = (Button) findViewById(R.id.memberIncomeCancelBtn);
        materialPeriodSpinner = (MaterialBetterSpinner) findViewById(R.id.membersIncomePeriod);


        ArrayAdapter<String> incomeSourceAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                incomeSources
        );

        MaterialBetterSpinner materialIncomeSourceSpinner = (MaterialBetterSpinner)
                findViewById(R.id.memberIncomeSource);
        materialIncomeSourceSpinner.setAdapter(incomeSourceAdapter);


        Intent intent = getIntent();
        String memberUserName = intent.getStringExtra("userName");
        groupMemberLocalUniqueID = intent.getStringExtra("groupMemberLocalUniqueID");

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

        groupMemberIncomeCategory();
        getSelectedPeriod();

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

    public void groupMemberIncomeCategory(){
        ArrayAdapter<String> incomeSourceAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                incomeSources
        );

        MaterialBetterSpinner materialIncomeSourceSpinner = (MaterialBetterSpinner) findViewById(R.id.memberIncomeSource);
        materialIncomeSourceSpinner.setAdapter(incomeSourceAdapter);
    }



    private void saveGroupMembersIncome(){
        if ( !membersIncomeAmount.getText().toString().equals("") &&
                !memberIncomeSource.getText().toString().equals("") &&
                selectedPeriod != null){
            String groupMemberIncomeSource = memberIncomeSource.getText().toString();
            String groupMemberIncomeAmount = membersIncomeAmount.getText().toString();
            String groupMemberIncomeNotes = memberIncomeNotes.getText().toString();
            String groupMemberUsername = groupMemberUserName.getText().toString();
            String currentUser = ParseUser.getCurrentUser().getObjectId();


            MembersIncome groupMemberIncome = new MembersIncome();
            groupMemberIncome.setSource(groupMemberIncomeSource);
            groupMemberIncome.setAmount(groupMemberIncomeAmount);
            groupMemberIncome.setPeriod(selectedPeriod);
            groupMemberIncome.setNotes(groupMemberIncomeNotes);
            groupMemberIncome.setMemberUserName(groupMemberUsername);
            groupMemberIncome.setMemberLocalUniqueID(groupMemberLocalUniqueID);
            groupMemberIncome.setUserId(currentUser);

            new ParseIncomeHelper(this).saveGroupMemberIncomeToParseDb(groupMemberIncome);
            startTabbedIncomeActivity();

            Toast.makeText(context, "Group Member Income Saved " + groupMemberIncome.getAmount() + " saved", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(context, "Income Amount, Source and Period fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedIncomeActivity(){
        Intent tabbedIncomeIntent = new Intent(AddGroupMembersIncomeActivity.this, TabbedIncomeActivity.class);
        tabbedIncomeIntent.putExtra("position", "1");
        startActivity(tabbedIncomeIntent);
        finish();
    }

}
