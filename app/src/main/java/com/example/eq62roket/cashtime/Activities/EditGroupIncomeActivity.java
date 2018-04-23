package com.example.eq62roket.cashtime.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseIncomeHelper;
import com.example.eq62roket.cashtime.Models.GroupIncome;
import com.example.eq62roket.cashtime.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

public class EditGroupIncomeActivity extends AppCompatActivity {

    private Context context = this;
    private DatePickerDialog.OnDateSetListener date;
    private EditText groupIncomeAmount, groupIncomeNotes;
    private Button groupIncomeDeleteBtn, groupIncomeUpdateBtn;
    private MaterialBetterSpinner materialPeriodSpinner, materialIncomeSourceSpinner;
    private TextView groupName;

    private String groupIncomeLocalUniqueID = "";
    private String selectedPeriod;
    private String selectedIncomeSource;
    private ParseIncomeHelper mParseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_income);

        mParseHelper = new ParseIncomeHelper(this);

        groupIncomeAmount = (EditText) findViewById(R.id.editGroupIncomeAmount);
        groupIncomeNotes = (EditText) findViewById(R.id.editGroupIncomeNotes);
        groupIncomeDeleteBtn = (Button) findViewById(R.id.editGroupIncomeDeleteBtn);
        groupIncomeUpdateBtn = (Button) findViewById(R.id.editGroupIncomeUpdateBtn);
        materialPeriodSpinner = (MaterialBetterSpinner) findViewById(R.id.editGroupIncomePeriod);
        materialIncomeSourceSpinner = (MaterialBetterSpinner) findViewById(R.id.editGroupIncomeSource);
        groupName = (TextView)findViewById(R.id.groupName);

        getSelectedPeriod();
        getSelectedIncomeSource(getIncomeSources());

        Intent intent = getIntent();
        String amountOfIncome = intent.getStringExtra("groupIncomeAmount");
        final String source0fIncome = intent.getStringExtra("groupIncomeSource");
        String notesAboutIncome = intent.getStringExtra("groupIncomeNotes");
        String periodOfIncome= intent.getStringExtra("groupIncomePeriod");
        String nameOfGroup = intent.getStringExtra("groupName");
        groupIncomeLocalUniqueID = intent.getStringExtra("groupIncomeLocalUniqueID");

        Log.d("group name", "onCreate: " + nameOfGroup);

        groupIncomeAmount.setText(amountOfIncome);
        groupIncomeNotes.setText(notesAboutIncome);
        groupName.setText(nameOfGroup);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit " + nameOfGroup + "'s" + " " + "Income");
        actionBar.setHomeButtonEnabled(true);

        groupIncomeUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateGroupIncome();
            }
        });

        groupIncomeDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(view.getContext());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        GroupIncome groupIncomeToDelete = new GroupIncome();
                        groupIncomeToDelete.setLocalUniqueID(groupIncomeLocalUniqueID);
                        mParseHelper.deleteGroupIncomeFromParseDb(groupIncomeToDelete);
                        startTabbedIncomeActivity();
                        Toast.makeText(EditGroupIncomeActivity.this, "Group income deleted successfully", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cancel
                    }
                });

                builder.setMessage(
                        "Deleting Group Income '" + source0fIncome + "' Can not be undone." + "Are You Sure You want to delete this income?").setTitle("Delete Group Income");


                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();

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

    private void updateGroupIncome(){
        if ( !groupIncomeAmount.getText().toString().equals("") &&
                selectedIncomeSource != null && selectedPeriod != null){
            String amountOfIncome = groupIncomeAmount.getText().toString();
            String notesAboutIncome = groupIncomeNotes.getText().toString();


            GroupIncome groupIncome = new GroupIncome();
            groupIncome.setAmount(amountOfIncome);
            groupIncome.setSource(selectedIncomeSource);
            groupIncome.setNotes(notesAboutIncome);
            groupIncome.setPeriod(selectedPeriod);
            if (!groupIncomeLocalUniqueID.equals("")){
                groupIncome.setLocalUniqueID(groupIncomeLocalUniqueID);
            }
            mParseHelper.updateGroupIncomeInParseDb(groupIncome);

            startTabbedIncomeActivity();

            Toast.makeText(context, "Group Income " + groupIncome.getSource() + " Updated", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(context, "Income amount and source are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedIncomeActivity(){
        Intent tabbedIncomeIntent = new Intent(EditGroupIncomeActivity.this, TabbedIncomeActivity.class);
        tabbedIncomeIntent.putExtra("position", "0");
        startActivity(tabbedIncomeIntent);
        finish();
    }
}
