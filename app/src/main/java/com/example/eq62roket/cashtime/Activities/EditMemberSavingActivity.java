package com.example.eq62roket.cashtime.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import com.example.eq62roket.cashtime.Models.MemberSavings;
import com.example.eq62roket.cashtime.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditMemberSavingActivity extends AppCompatActivity {

    private MaterialBetterSpinner materialIncomeSourceSpinner, materialPeriodSpinner;
    private EditText savingAmount, savingNote;
    private TextView goalName, memberName;
    private String selectedPeriod;
    private String selectedIncomeSource;
    private ParseHelper mParseHelper;
    private String memberSavingLocalUniqueID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member_saving);

        mParseHelper = new ParseHelper(EditMemberSavingActivity.this);

        materialPeriodSpinner = (MaterialBetterSpinner) findViewById(R.id.select_period_spinner);
        materialIncomeSourceSpinner = (MaterialBetterSpinner) findViewById(R.id.select_income_spinner);
        goalName = (TextView) findViewById(R.id.goalName);
        memberName = (TextView) findViewById(R.id.memberName);
        savingAmount = (EditText) findViewById(R.id.savingAmount);
        savingNote = (EditText) findViewById(R.id.savingNote);
        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        final String nameOfGoal = intent.getStringExtra("goalName");
        String nameOfMember = intent.getStringExtra("memberName");
        String amountSaved = intent.getStringExtra("savingAmount");
        String notes = intent.getStringExtra("savingNote");
        String period = intent.getStringExtra("savingPeriod");
        String sourceOfIncome = intent.getStringExtra("incomeSource");
        memberSavingLocalUniqueID = intent.getStringExtra("memberSavingLocalUniqueID");

        // Prepopulate goalName and memberName
        goalName.setText(nameOfGoal);
        memberName.setText(nameOfMember);
        savingAmount.setText(amountSaved);
        savingNote.setText(notes);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit " + nameOfMember + "'s" + " " + "Saving");
        actionBar.setHomeButtonEnabled(true);


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
                        MemberSavings memberSavingToDelete = new MemberSavings();
                        memberSavingToDelete.setLocalUniqueID(memberSavingLocalUniqueID);
                        mParseHelper.deleteMemberSavingFromParseDb(memberSavingToDelete);
                        startTabbedSavingActivity();
                        Toast.makeText(EditMemberSavingActivity.this, "Saving deleted successfully", Toast.LENGTH_SHORT).show();

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
        String nameOfGoal = goalName.getText().toString();

        if ( !savingAmount.getText().toString().equals("")
                && !goalName.getText().toString().equals("")
                && !memberName.getText().toString().equals("")
                && selectedPeriod != null
                && selectedIncomeSource != null){

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
                MemberSavings memberSavingToUpdate = new MemberSavings();
                memberSavingToUpdate.setLocalUniqueID(memberSavingLocalUniqueID);
                memberSavingToUpdate.setSavingAmount(amountSaved);
                memberSavingToUpdate.setPeriod(selectedPeriod);
                memberSavingToUpdate.setIncomeSource(selectedIncomeSource);
                if (note.trim().equals("")){
                    memberSavingToUpdate.setSavingNote("No notes");
                } else {
                    memberSavingToUpdate.setSavingNote(note);
                }
                mParseHelper.updateMemberSavingInParseDb(memberSavingToUpdate);

                startTabbedSavingActivity();
                Toast.makeText(EditMemberSavingActivity.this, "Saving Updated", Toast.LENGTH_SHORT).show();

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
        Intent tabbedSavingIntent = new Intent(EditMemberSavingActivity.this, TabbedSavingActivity.class);
        tabbedSavingIntent.putExtra("position", "1");
        startActivity(tabbedSavingIntent);
        finish();
    }
}
