package com.example.eq62roket.cashtime.Activities;

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

import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Helper.ParseIncomeHelper;
import com.example.eq62roket.cashtime.Helper.PeriodHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupSavingsSumListener;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.Models.GroupSavings;
import com.example.eq62roket.cashtime.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddGroupSavingsActivity extends AppCompatActivity {

    private static final String TAG = "AddGroupSavingsActivity";
    private MaterialBetterSpinner materialIncomeSourceSpinner, materialPeriodSpinner;
    private EditText savingAmount, savingNote;
    private TextView goalName;
    private String selectedPeriod;
    private String selectedIncomeSource;
    private String groupLocalUniqueID;
    private String groupGoalLocalUniqueID;
    private String groupGoalAmount;
    private String groupGoalDueDate;
    private ParseHelper mParseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_savings);

        mParseHelper = new ParseHelper(AddGroupSavingsActivity.this);

        materialPeriodSpinner = (MaterialBetterSpinner) findViewById(R.id.select_period_spinner);
        materialIncomeSourceSpinner = (MaterialBetterSpinner) findViewById(R.id.select_income_spinner);
        goalName = (TextView) findViewById(R.id.goalName);
        savingAmount = (EditText) findViewById(R.id.savingAmount);
        savingNote = (EditText) findViewById(R.id.savingNote);
        Button btnSave = (Button) findViewById(R.id.btnSave);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);

        Intent intent = getIntent();
        String nameOfGoal = intent.getStringExtra("goalName");
        groupLocalUniqueID = intent.getStringExtra("groupLocalUniqueID");
        groupGoalLocalUniqueID = intent.getStringExtra("groupGoalLocalUniqueID");
        groupGoalAmount = intent.getStringExtra("groupGoalAmount");
        groupGoalDueDate = intent.getStringExtra("groupGoalDueDate");

        goalName.setText(nameOfGoal);

        getSelectedPeriod();

        getSelectedIncomeSource(getIncomeSources());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSavingTransaction();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearEditTexts();
                startTabbedSavingActivity();
            }
        });
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

    public void getSelectedIncomeSource(List<String> incomeSourcesList){
        ArrayAdapter<String> incomeSourcesAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                incomeSourcesList
        );
        materialIncomeSourceSpinner.setAdapter(incomeSourcesAdapter);

        materialIncomeSourceSpinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                selectedIncomeSource = editable.toString();
            }
        });

    }

    public void saveSavingTransaction(){
        final String[] savingPeriod = {""};
        final String nameOfGoal = goalName.getText().toString();

        if ( !savingAmount.getText().toString().equals("") &&
                !goalName.getText().toString().equals("") &&
                selectedPeriod != null &&
                selectedIncomeSource != null){

            final GroupGoals groupGoal = new GroupGoals();
            groupGoal.setGroupLocalUniqueID(groupLocalUniqueID);
            groupGoal.setLocalUniqueID(groupGoalLocalUniqueID);
            groupGoal.setAmount(groupGoalAmount);
            groupGoal.setDueDate(groupGoalDueDate);
            mParseHelper.getTotalGroupSavingsFromParseDb(groupGoal, new OnReturnedGroupSavingsSumListener() {
                @Override
                public void onResponse(int groupGoalTotalSavings) {
                    String amountSaved = savingAmount.getText().toString();
                    String note = savingNote.getText().toString();

                    new ParseIncomeHelper(AddGroupSavingsActivity.this).getTotalGroupIncomeFromParseDb(groupLocalUniqueID);

                    int groupGoalTotalCost = Integer.parseInt(groupGoal.getAmount());
                    int amountToSave = Integer.valueOf(amountSaved);
                    int amountRemaining = groupGoalTotalCost - (groupGoalTotalSavings + amountToSave);

                    // TODO: 4/5/18 ====> toast calculation buggy... ie test 60000 and 100000...10000 
                    if ( amountToSave > amountRemaining && amountRemaining != 0 ){
                        int userRemainingAmount = 0;
                        if (amountRemaining < 0){
                            userRemainingAmount = groupGoalTotalCost - groupGoalTotalSavings;
                        }else {
                            userRemainingAmount = amountRemaining;
                        }
                        Toast.makeText(
                                AddGroupSavingsActivity.this,
                                "You can not save " + amountToSave + ", you need " + userRemainingAmount + " to complete your goal",
                                Toast.LENGTH_LONG).show();
                    }else {

                        Date today = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                        String dateToday = simpleDateFormat.format(today);
                        try {
                            Date groupGoalDueDate = simpleDateFormat.parse(groupGoal.getDueDate());
                            Date todayZdate = simpleDateFormat.parse(dateToday);
                            GroupGoals completedGroupGoal = new GroupGoals();
                            completedGroupGoal.setLocalUniqueID(groupGoalLocalUniqueID);
                            completedGroupGoal.setGroupLocalUniqueID(groupLocalUniqueID);

                            if ( amountRemaining == 0 && todayZdate.before(groupGoalDueDate) ){
                                completedGroupGoal.setGroupGoalStatus("completed");
                                completedGroupGoal.setCompletedDate(dateToday);
                                mParseHelper.updateGroupGoalCompleteStatusInParseDb(completedGroupGoal);
                            }else if (amountRemaining == 0 && todayZdate.equals(groupGoalDueDate)){
                                completedGroupGoal.setGroupGoalStatus("completed");
                                completedGroupGoal.setCompletedDate(dateToday);
                                mParseHelper.updateGroupGoalCompleteStatusInParseDb(completedGroupGoal);
                            }else if (amountRemaining != 0 && todayZdate.after(groupGoalDueDate)){
                                completedGroupGoal.setGroupGoalStatus("failed");
                                completedGroupGoal.setCompletedDate(dateToday);
                                mParseHelper.updateGroupGoalCompleteStatusInParseDb(completedGroupGoal);
                            }else {
                                completedGroupGoal.setGroupGoalStatus("incomplete");
                                completedGroupGoal.setCompletedDate("");
                                mParseHelper.updateGroupGoalCompleteStatusInParseDb(completedGroupGoal);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (selectedPeriod.equals("Daily")){
                            savingPeriod[0] = new PeriodHelper().getDailyDate();
                        }else if (selectedPeriod.equals("Weekly")){
                            savingPeriod[0] = new PeriodHelper().getWeeklyDate();
                        }else if (selectedPeriod.equals("Monthly")){
                            savingPeriod[0] = new PeriodHelper().getMonthlyDate();
                        }
                        if (!selectedPeriod.equals("")){
                            GroupSavings newGroupSaving = new GroupSavings();
                            newGroupSaving.setAmount(amountSaved);
                            newGroupSaving.setGoalName(nameOfGoal);
                            newGroupSaving.setIncomeSource(selectedIncomeSource);
                            newGroupSaving.setPeriod(selectedPeriod);
                            newGroupSaving.setDateAdded(dateToday);
                            newGroupSaving.setGroupLocalUniqueID(groupLocalUniqueID);
                            newGroupSaving.setGroupGoalLocalUniqueID(groupGoalLocalUniqueID);
                            if (note.trim().equals("")){
                                newGroupSaving.setNotes("No notes");
                            }else {
                                newGroupSaving.setNotes(note);
                            }
                            mParseHelper.saveGroupSavingsToParseDb(newGroupSaving);
                            // TODO: 3/29/18 ====> award the user 3 points
                            startTabbedSavingActivity();
                            Toast.makeText(AddGroupSavingsActivity.this, "Saving recorded", Toast.LENGTH_SHORT).show();

                        }
                    }

                }

                @Override
                public void onFailure(String error) {

                }
            });

        } else {
            Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
        }

    }

    public List<String> getIncomeSources(){
        List<String> incomeSourcesList = new ArrayList<>();
        incomeSourcesList.add("Donation");
        incomeSourcesList.add("Investment");
        incomeSourcesList.add("Loan");
        incomeSourcesList.add("Salary");
        incomeSourcesList.add("Saving");
        incomeSourcesList.add("Wage");

        return incomeSourcesList;
    }

    public void startTabbedSavingActivity(){
        Intent tabbedSavingIntent = new Intent(AddGroupSavingsActivity.this, TabbedSavingActivity.class);
        tabbedSavingIntent.putExtra("position", "0");
        startActivity(tabbedSavingIntent);
        finish();
    }

    public void clearEditTexts(){
        goalName.setText("");
        savingAmount.setText("");
        savingNote.setText("");
    }
}
