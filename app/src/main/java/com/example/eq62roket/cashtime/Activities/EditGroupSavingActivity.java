package com.example.eq62roket.cashtime.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.example.eq62roket.cashtime.Models.GroupSavings;
import com.example.eq62roket.cashtime.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditGroupSavingActivity extends AppCompatActivity {

    private static final String TAG = "EditGroupSavingActivity";
    private MaterialBetterSpinner materialIncomeSourceSpinner, materialPeriodSpinner;
    private EditText savingAmount, savingNote;
    private TextView goalName;

    private String selectedPeriod;
    private String selectedIncomeSource;
    private String groupSavingLocalUniqueID;
    private ParseHelper mParseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_saving);

        mParseHelper = new ParseHelper(EditGroupSavingActivity.this);

        materialPeriodSpinner = (MaterialBetterSpinner) findViewById(R.id.select_period_spinner);
        materialIncomeSourceSpinner = (MaterialBetterSpinner) findViewById(R.id.select_income_spinner);
        goalName = (TextView) findViewById(R.id.goalName);
        savingAmount = (EditText) findViewById(R.id.savingAmount);
        savingNote = (EditText) findViewById(R.id.savingNote);
        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        final String nameOfGoal = intent.getStringExtra("groupGoalName");
        String amountSaved = intent.getStringExtra("groupSavingAmount");
        String note = intent.getStringExtra("groupSavingNote");
        groupSavingLocalUniqueID = intent.getStringExtra("groupSavingLocalUniqueID");

        goalName.setText(nameOfGoal);
        savingAmount.setText(amountSaved);
        savingNote.setText(note);

        getSelectPeriod();

        selectIncomeSource(getIncomeSources());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSavingTransaction();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GroupSavings groupSavingToDelete = new GroupSavings();
                        groupSavingToDelete.setLocalUniqueID(groupSavingLocalUniqueID);
                        mParseHelper.deleteGroupSavingFromParseDb(groupSavingToDelete);

                        // TODO: 4/7/18 ==== deduct 3 points that were added for this goal
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
        String savingPeriod = "";
        // pick goalName again
        String nameOfGoal = goalName.getText().toString();

        if ( !savingAmount.getText().toString().equals("")
                && !goalName.getText().toString().equals("") &&
                selectedPeriod != null &&
                selectedIncomeSource != null){

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
                groupSavingToUpdate.setLocalUniqueID(groupSavingLocalUniqueID);

                if (note.trim().equals("")){
                    groupSavingToUpdate.setNotes("No notes");
                }else {
                    groupSavingToUpdate.setNotes(note);
                }
                mParseHelper.updateGroupSavingInParseDb(groupSavingToUpdate);

                startTabbedSavingActivity();
                Toast.makeText(EditGroupSavingActivity.this, "Saving Updated", Toast.LENGTH_SHORT).show();
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
        Intent tabbedSavingIntent = new Intent(EditGroupSavingActivity.this, TabbedSavingActivity.class);
        tabbedSavingIntent.putExtra("position", "0");
        startActivity(tabbedSavingIntent);
    }

}
