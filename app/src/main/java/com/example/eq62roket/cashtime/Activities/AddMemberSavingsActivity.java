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
import com.example.eq62roket.cashtime.Helper.PeriodHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedMemberSavingsSumListener;
import com.example.eq62roket.cashtime.Models.MemberSavings;
import com.example.eq62roket.cashtime.Models.MembersGoals;
import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddMemberSavingsActivity extends AppCompatActivity {

    private static final String TAG = "AddMemberSavings";
    private MaterialBetterSpinner materialIncomeSourceSpinner, materialPeriodSpinner;
    private EditText savingAmount, savingNote;
    private TextView memberName, goalName;
    private String selectedPeriod;
    private String selectedIncomeSource;
    private String nameOfMember;
    private String memberLocalUniqueID;
    private String memberGoalLocalUniqueID, memberGoalAmount, memberGoalDueDate;
    private ParseHelper mParseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member_savings);

        mParseHelper = new ParseHelper(AddMemberSavingsActivity.this);

        materialPeriodSpinner = (MaterialBetterSpinner) findViewById(R.id.select_period_spinner);
        materialIncomeSourceSpinner = (MaterialBetterSpinner) findViewById(R.id.select_income_spinner);
        goalName = (TextView) findViewById(R.id.goalName);
        memberName = (TextView) findViewById(R.id.memberName);
        savingAmount = (EditText) findViewById(R.id.savingAmount);
        savingNote = (EditText) findViewById(R.id.savingNote);
        Button btnSave = (Button) findViewById(R.id.btnSave);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);

        Intent intent = getIntent();
        String nameOfGoal = intent.getStringExtra("goalName");
        nameOfMember = intent.getStringExtra("memberName");
        memberLocalUniqueID = intent.getStringExtra("memberLocalUniqueID");
        memberGoalLocalUniqueID = intent.getStringExtra("memberGoalLocalUniqueID");
        memberGoalAmount = intent.getStringExtra("memberGoalAmount");
        memberGoalDueDate = intent.getStringExtra("memberGoalDueDate");


        goalName.setText(nameOfGoal);
        memberName.setText(nameOfMember);

        getSelectPeriod();
        selectIncomeSource(getIncomeSources());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSavingTransaction();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTabbedSavingActivity();
            }
        });
    }

    public void getSelectPeriod(){
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

    public void selectIncomeSource(List<String> incomeSourcesList){
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
                selectedIncomeSource != null) {

            final MembersGoals membersGoal = new MembersGoals();
            membersGoal.setLocalUniqueID(memberGoalLocalUniqueID);
            membersGoal.setMemberGoalDueDate(memberGoalDueDate);
            membersGoal.setMemberGoalAmount(memberGoalAmount);
            membersGoal.setMemberLocalUniqueID(memberLocalUniqueID);
            new ParseHelper(AddMemberSavingsActivity.this).
                    getTotalMemberSavingsFromParseDb(membersGoal, new OnReturnedMemberSavingsSumListener() {
                @Override
                public void onResponse(int memberGoalTotalSavings) {
                    String amountSaved = savingAmount.getText().toString();
                    String note = savingNote.getText().toString();

                    int groupGoalTotalCost = Integer.parseInt(membersGoal.getMemberGoalAmount());
                    int amountToSave = Integer.valueOf(amountSaved);
                    int amountRemaining = groupGoalTotalCost - (memberGoalTotalSavings + amountToSave);

                    if ( amountToSave > amountRemaining && amountRemaining != 0 ){
                        int userRemainingAmount = 0;
                        if (amountRemaining < 0){
                            userRemainingAmount = groupGoalTotalCost - memberGoalTotalSavings;
                        }else {
                            userRemainingAmount = amountRemaining;
                        }
                        Toast.makeText(
                                AddMemberSavingsActivity.this,
                                "You can not save " + amountToSave + ", you need " + userRemainingAmount + " to complete your goal",
                                Toast.LENGTH_LONG).show();
                    }else {
                        Date today = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                        String dateToday = simpleDateFormat.format(today);

                        try {
                            Date memberGoalDueDate = simpleDateFormat.parse(membersGoal.getMemberGoalDueDate());
                            Date todayZdate = simpleDateFormat.parse(dateToday);
                            MembersGoals completedmemberGoal = new MembersGoals();
                            completedmemberGoal.setLocalUniqueID(memberGoalLocalUniqueID);
                            completedmemberGoal.setMemberLocalUniqueID(memberLocalUniqueID);

                            if ( amountRemaining == 0 && todayZdate.before(memberGoalDueDate) ){
                                completedmemberGoal.setMemberGoalStatus("completed");
                                completedmemberGoal.setCompleteDate(dateToday);
                                mParseHelper.updateMemberGoalCompleteStatusInParseDb(completedmemberGoal);
                            }else if (amountRemaining == 0 && todayZdate.equals(memberGoalDueDate)){
                                completedmemberGoal.setMemberGoalStatus("completed");
                                completedmemberGoal.setCompleteDate(dateToday);
                                mParseHelper.updateMemberGoalCompleteStatusInParseDb(completedmemberGoal);
                            }else if (amountRemaining != 0 && todayZdate.after(memberGoalDueDate)){
                                completedmemberGoal.setMemberGoalStatus("failed");
                                completedmemberGoal.setCompleteDate(dateToday);
                                mParseHelper.updateMemberGoalCompleteStatusInParseDb(completedmemberGoal);
                            }else {
                                completedmemberGoal.setMemberGoalStatus("incomplete");
                                completedmemberGoal.setCompleteDate("");
                                mParseHelper.updateMemberGoalCompleteStatusInParseDb(completedmemberGoal);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (selectedPeriod == "Daily"){
                            savingPeriod[0] = new PeriodHelper().getDailyDate();
                        }else if (selectedPeriod == "Weekly"){
                            savingPeriod[0] = new PeriodHelper().getWeeklyDate();
                        }else if (selectedPeriod == "Monthly"){
                            savingPeriod[0] = new PeriodHelper().getMonthlyDate();
                        }

                        MemberSavings newMemberSaving = new MemberSavings();
                        newMemberSaving.setGoalName(nameOfGoal);
                        newMemberSaving.setMemberName(nameOfMember);
                        newMemberSaving.setSavingAmount(amountSaved);
                        newMemberSaving.setPeriod(selectedPeriod);
                        newMemberSaving.setIncomeSource(selectedIncomeSource);
                        newMemberSaving.setDateAdded(dateToday);
                        newMemberSaving.setMemberGoalLocalUniqueID(memberGoalLocalUniqueID);
                        newMemberSaving.setMemberLocalUniqueID(memberLocalUniqueID);
                        if (note.trim().equals("")){
                            newMemberSaving.setSavingNote("No Notes");
                        }else {
                            newMemberSaving.setSavingNote(note);
                        }
                        mParseHelper.saveMemberSavingsToParseDb(newMemberSaving);

                        startTabbedSavingActivity();
                        Toast.makeText(AddMemberSavingsActivity.this, "Saving recorded", Toast.LENGTH_SHORT).show();

                        // TODO: 3/21/18 ======>>>>> award user points
                        // Award user 3 point for saving
                        User user = new User();
                        user.setPoints(3);

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
        Intent tabbedSavingIntent = new Intent(AddMemberSavingsActivity.this, TabbedSavingActivity.class);
        tabbedSavingIntent.putExtra("position", "1");
        startActivity(tabbedSavingIntent);
        finish();
    }
}
