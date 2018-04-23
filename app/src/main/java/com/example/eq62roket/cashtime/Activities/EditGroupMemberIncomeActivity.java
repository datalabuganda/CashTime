package com.example.eq62roket.cashtime.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

public class EditGroupMemberIncomeActivity extends AppCompatActivity {

    private Context context = this;
    private EditText groupMemberIncomeAmount, groupMemberIncomeNotes;
    private Button groupMemberIncomeDeleteBtn, groupMemberIncomeUpdateBtn;
    private ParseIncomeHelper mParseHelper;
    private MaterialBetterSpinner materialPeriodSpinner, materialIncomeSourceSpinner;
    private TextView memberName;

    private String memberIncomeLocalUniqueID = "";
    private String selectedPeriod;
    private String selectedIncomeSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_member_income);

        mParseHelper = new ParseIncomeHelper(this);

        groupMemberIncomeAmount = (EditText) findViewById(R.id.editGroupMemberIncomeAmount);
        groupMemberIncomeNotes = (EditText) findViewById(R.id.editGroupMemberIncomeNotes);
        groupMemberIncomeDeleteBtn = (Button) findViewById(R.id.editGroupMemberIncomeDeleteBtn);
        groupMemberIncomeUpdateBtn = (Button) findViewById(R.id.editGroupMemberIncomeUpdateBtn);
        materialPeriodSpinner = (MaterialBetterSpinner) findViewById(R.id.editGroupMemberIncomePeriod);
        materialIncomeSourceSpinner = (MaterialBetterSpinner) findViewById(R.id.editGroupMemberIncomeSource);
        memberName = (TextView)findViewById(R.id.memberName);

        getSelectedPeriod();
        getSelectedIncomeSource(getIncomeSources());

        Intent intent = getIntent();
        final String amountOfIncome = intent.getStringExtra("memberIncomeAmount");
        final String source0fIncome = intent.getStringExtra("memberIncomeSource");
        final String notesAboutIncome = intent.getStringExtra("memberIncomeNotes");
        final String periodOfIncome = intent.getStringExtra("memberIncomePeriod");
        memberIncomeLocalUniqueID = intent.getStringExtra("memberIncomeLocalUniqueID");
        String nameOfMember = intent.getStringExtra("memberUsername");

        groupMemberIncomeAmount.setText(amountOfIncome);
        groupMemberIncomeNotes.setText(notesAboutIncome);
        memberName.setText(nameOfMember);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit " + nameOfMember +"'s" + " " + "Income");
        actionBar.setHomeButtonEnabled(true);

        groupMemberIncomeUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateGroupIncome();
            }
        });

        groupMemberIncomeDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(view.getContext());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        MembersIncome groupMemberIncomeToDelete = new MembersIncome();
                        groupMemberIncomeToDelete.setLocalUniqueID(memberIncomeLocalUniqueID);
                        mParseHelper.deleteGroupMemberIncomeFromParseDb(groupMemberIncomeToDelete);
                        startTabbedIncomeActivity();
                        Toast.makeText(EditGroupMemberIncomeActivity.this, "Income deleted successfully", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cancel
                    }
                });

                builder.setMessage(
                        "Deleting Income '" + source0fIncome + "' Can not be undone." + "Are You Sure You want to delete this income?").setTitle("Delete Income");

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
        if ( !groupMemberIncomeAmount.getText().toString().equals("") &&
                selectedIncomeSource != null &&
                selectedPeriod != null){

            String amountOfIncome = groupMemberIncomeAmount.getText().toString();
            String notesAboutIncome = groupMemberIncomeNotes.getText().toString();

            MembersIncome membersIncome = new MembersIncome();
            membersIncome.setAmount(amountOfIncome);
            membersIncome.setSource(selectedIncomeSource);
            membersIncome.setNotes(notesAboutIncome);
            membersIncome.setPeriod(selectedPeriod);

            if (!memberIncomeLocalUniqueID.equals("")){
                membersIncome.setLocalUniqueID(memberIncomeLocalUniqueID);
            }
            mParseHelper.updateGroupMemberIncomeInParseDb(membersIncome);

            startTabbedIncomeActivity();

            Toast.makeText(context, "Member Income " + membersIncome.getSource() + " Updated", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(context, "Income amount and source are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedIncomeActivity(){
        Intent tabbedIncomeIntent = new Intent(EditGroupMemberIncomeActivity.this, TabbedIncomeActivity.class);
        tabbedIncomeIntent.putExtra("position", "1");
        startActivity(tabbedIncomeIntent);
        finish();
    }
}
