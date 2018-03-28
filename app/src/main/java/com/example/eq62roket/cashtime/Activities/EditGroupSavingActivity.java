package com.example.eq62roket.cashtime.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Helper.PeriodHelper;
import com.example.eq62roket.cashtime.Models.GroupSavings;
import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditGroupSavingActivity extends AppCompatActivity {

    private static final String TAG = "EditGroupSavingActivity";

    private Spinner periodSpinner, incomeSourcesSpinner;
    private EditText goalName, savingAmount, savingNote;

    private String selectedPeriod;
    private String selectedIncomeSource;
    private String groupSavingParseId;
    private ParseHelper mParseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_saving);

        mParseHelper = new ParseHelper(EditGroupSavingActivity.this);

        periodSpinner = (Spinner) findViewById(R.id.select_period_spinner);
        incomeSourcesSpinner = (Spinner) findViewById(R.id.select_income_spinner);
        goalName = (EditText) findViewById(R.id.goalName);
        savingAmount = (EditText) findViewById(R.id.savingAmount);
        savingNote = (EditText) findViewById(R.id.savingNote);
        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        final String nameOfGoal = intent.getStringExtra("groupGoalName");
        String amountSaved = intent.getStringExtra("groupSavingAmount");
        String note = intent.getStringExtra("groupSavingNote");
        groupSavingParseId = intent.getStringExtra("groupSavingParseId");

        // Prepopulate goalName
        goalName.setText(nameOfGoal);
        savingAmount.setText(amountSaved);
        savingNote.setText(note);

        // get selected period
        getSelectPeriod();

        // get selected income
        selectIncomeSource(getIncomeSources());

        // Save Transaction
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSavingTransaction();
            }
        });

        // Cancel Transaction
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GroupSavings groupSavingToDelete = new GroupSavings();
                        groupSavingToDelete.setParseId(groupSavingParseId);
                        mParseHelper.deleteGroupSavingFromParseDb(groupSavingToDelete);
                        // TODO: 3/22/18 ====> switch to groupSavingFragment

                        // start TabbedSavingActivity
                        startTabbedSavingActivity();
                        Toast.makeText(EditGroupSavingActivity.this, "Saving deleted successfully", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cancel
                    }
                });

                builder.setMessage(
                        "Deleting saving for '" + nameOfGoal + "' Can not be undone." + "Are You Sure You want to delete this saving?").setTitle("Delete Saving");


                AlertDialog dialog = builder.create();
                dialog.show();

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
        // pick goalName again
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
            if ( !selectedPeriod.equals("")){
                GroupSavings groupSavingToUpdate = new GroupSavings();
                groupSavingToUpdate.setGoalName(nameOfGoal);
                groupSavingToUpdate.setAmount(amountSaved);
                groupSavingToUpdate.setPeriod(selectedPeriod);
                groupSavingToUpdate.setIncomeSource(selectedIncomeSource);
                groupSavingToUpdate.setNotes(note);
                groupSavingToUpdate.setParseId(groupSavingParseId);
                groupSavingToUpdate.setDateAdded(dateToday);

                if (note.trim().equals("")){
                    groupSavingToUpdate.setNotes("No notes");
                }else {
                    groupSavingToUpdate.setNotes(note);
                }

                mParseHelper.updateGroupSavingInParseDb(groupSavingToUpdate);

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
        Intent intent = new Intent(EditGroupSavingActivity.this, TabbedSavingActivity.class);
        startActivity(intent);
        finish();
    }

    public void clearEditTexts(){
        goalName.setText("");
        savingAmount.setText("");
        savingNote.setText("");
    }
}
