package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Helper.PeriodHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedMemberSavingsSumListener;
import com.example.eq62roket.cashtime.Models.MemberSavings;
import com.example.eq62roket.cashtime.Models.MembersGoals;
import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddMemberSavingsActivity extends AppCompatActivity {

    private static final String TAG = "AddMemberSavings";

    private Spinner periodSpinner, incomeSourcesSpinner;
    private EditText savingAmount, savingNote;
    private TextView memberName, goalName;

    private String selectedPeriod;
    private String selectedIncomeSource;
    private String nameOfMember;
    private String memberParseId;
    private String goalParseId, memberGoalAmount, memberGoalDueDate;

    private ParseHelper mParseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member_savings);

        mParseHelper = new ParseHelper(AddMemberSavingsActivity.this);

        periodSpinner = (Spinner) findViewById(R.id.select_period_spinner);
        incomeSourcesSpinner = (Spinner) findViewById(R.id.select_income_spinner);
        goalName = (TextView) findViewById(R.id.goalName);
        memberName = (TextView) findViewById(R.id.memberName);
        savingAmount = (EditText) findViewById(R.id.savingAmount);
        savingNote = (EditText) findViewById(R.id.savingNote);
        Button btnSave = (Button) findViewById(R.id.btnSave);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);

        Intent intent = getIntent();
        String nameOfGoal = intent.getStringExtra("goalName");
        nameOfMember = intent.getStringExtra("memberName");
        memberParseId = intent.getStringExtra("memberParseId");
        goalParseId = intent.getStringExtra("goalParseId");
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
                clearEditTexts();

                // start TabbedSavingActivity
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
                this, android.R.layout.simple_spinner_item, periods
        );
        periodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodSpinner.setAdapter(periodAdapter);

        periodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Get selected period
                selectedPeriod = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void selectIncomeSource(List<String> incomeSourcesList){
        ArrayAdapter<String> incomeSourcesAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, incomeSourcesList
        );
        incomeSourcesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeSourcesSpinner.setAdapter(incomeSourcesAdapter);

        incomeSourcesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedIncomeSource = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void saveSavingTransaction(){
        final String[] savingPeriod = {""};
        final String nameOfGoal = goalName.getText().toString();

        if ( !savingAmount.getText().toString().equals("")
                && !goalName.getText().toString().equals("") ){

            final MembersGoals membersGoal = new MembersGoals();
            membersGoal.setParseId(goalParseId);
            membersGoal.setMemberGoalDueDate(memberGoalDueDate);
            membersGoal.setMemberGoalAmount(memberGoalAmount);
            membersGoal.setMemberParseId(memberParseId);
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
                            completedmemberGoal.setParseId(goalParseId);
                            completedmemberGoal.setMemberParseId(memberParseId);

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
                        if (!selectedPeriod.equals("")){
                            MemberSavings newMemberSaving = new MemberSavings();
                            newMemberSaving.setGoalName(nameOfGoal);
                            newMemberSaving.setMemberName(nameOfMember);
                            newMemberSaving.setSavingAmount(amountSaved);
                            newMemberSaving.setPeriod(selectedPeriod);
                            newMemberSaving.setIncomeSource(selectedIncomeSource);
                            newMemberSaving.setDateAdded(dateToday);
                            newMemberSaving.setGoalParseId(goalParseId);
                            newMemberSaving.setMemberParseId(memberParseId);
                            if (note.trim().equals("")){
                                newMemberSaving.setSavingNote("No Notes");
                            }else {
                                newMemberSaving.setSavingNote(note);
                            }
                            mParseHelper.saveMemberSavingsToParseDb(newMemberSaving);

                            Toast.makeText(AddMemberSavingsActivity.this, "Saving recorded", Toast.LENGTH_SHORT).show();

                            // TODO: 3/21/18 ======>>>>> award user points

                            // Award user 3 point for saving
                            User user = new User();
                            user.setPoints(3);

                            // start TabbedSavingActivity
                            startTabbedSavingActivity();

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
        incomeSourcesList.add("Salary");
        incomeSourcesList.add("Loan");
        incomeSourcesList.add("Investments");

        return incomeSourcesList;
    }

    public void startTabbedSavingActivity(){
        Intent intent = new Intent(AddMemberSavingsActivity.this, TabbedSavingActivity.class);
        startActivity(intent);
        finish();
    }

    public void clearEditTexts(){
        goalName.setText("");
        savingAmount.setText("");
        savingNote.setText("");
    }
}
