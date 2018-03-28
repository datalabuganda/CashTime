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
import com.example.eq62roket.cashtime.Models.MemberSavings;
import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddMemberSavingsActivity extends AppCompatActivity {

    private Spinner periodSpinner, incomeSourcesSpinner;
    private EditText goalName, savingAmount, savingNote;
    private TextView memberName;

    private String selectedPeriod;
    private String selectedIncomeSource;
    private String nameOfMember;

    private ParseHelper mParseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member_savings);

        mParseHelper = new ParseHelper(AddMemberSavingsActivity.this);

        periodSpinner = (Spinner) findViewById(R.id.select_period_spinner);
        incomeSourcesSpinner = (Spinner) findViewById(R.id.select_income_spinner);
        goalName = (EditText) findViewById(R.id.goalName);
        memberName = (TextView) findViewById(R.id.memberName);
        savingAmount = (EditText) findViewById(R.id.savingAmount);
        savingNote = (EditText) findViewById(R.id.savingNote);
        Button btnSave = (Button) findViewById(R.id.btnSave);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);

        Intent intent = getIntent();
        String nameOfGoal = intent.getStringExtra("goalName");
        nameOfMember = intent.getStringExtra("memberName");

        // Prepopulate goalName and memberName
        goalName.setText(nameOfGoal);
        memberName.setText(nameOfMember);


        // get selected period
        getSelectPeriod();

        // get selected income
        selectIncomeSource(getIncomeSources());

        // Save Transaction
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSavingTransaction();
            }
        });

        // Cancel Transaction
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
        // Add periods to list
        List<String> periods = new ArrayList<>();
        periods.add("Daily");
        periods.add("Weekly");
        periods.add("Monthly");

        // add list to adapter
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
        // add incomeSourcesList to incomeSourcesAdapter
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
        String savingPeriod = "";
        String nameOfGoal = goalName.getText().toString();

        if ( !savingAmount.getText().toString().equals("")
                && !goalName.getText().toString().equals("") ){

            String amountSaved = savingAmount.getText().toString();
            String note = savingNote.getText().toString();

            Date today = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            String dateToday = simpleDateFormat.format(today);

            if (selectedPeriod == "Daily"){
                savingPeriod = new PeriodHelper().getDailyDate();
            }else if (selectedPeriod == "Weekly"){
                savingPeriod = new PeriodHelper().getWeeklyDate();
            }else if (selectedPeriod == "Monthly"){
                savingPeriod = new PeriodHelper().getMonthlyDate();
            }
            if (!selectedPeriod.equals("")){
                MemberSavings newMemberSaving = new MemberSavings();
                newMemberSaving.setGoalName(nameOfGoal);
                newMemberSaving.setMemberName(nameOfMember);
                newMemberSaving.setSavingAmount(amountSaved);
                newMemberSaving.setPeriod(selectedPeriod);
                newMemberSaving.setIncomeSource(selectedIncomeSource);
                newMemberSaving.setDateAdded(dateToday);
                if (note.trim().equals("")){
                    newMemberSaving.setSavingNote("No Notes");
                }else {
                    newMemberSaving.setSavingNote(note);
                }
                mParseHelper.saveMemberSavingsToParseDb(newMemberSaving);


//                GroupSavings groupSavings = new GroupSavings(
//                        nameOfGoal, savingPeriod, selectedIncomeSource, note, dateToday, amountSaved);
                Toast.makeText(this, "Saving recorded", Toast.LENGTH_SHORT).show();

                // TODO: 3/21/18 ======>>>>> award user points

                // Award user 3 point for saving
                User user = new User();
                user.setPoints(3);

                // start TabbedSavingActivity
                startTabbedSavingActivity();

            }
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
