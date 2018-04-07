package com.example.eq62roket.cashtime.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseIncomeHelper;
import com.example.eq62roket.cashtime.Models.MembersIncome;
import com.example.eq62roket.cashtime.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditGroupMemberIncomeActivity extends AppCompatActivity {
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    Calendar myCalendar = Calendar.getInstance();
    Context context = this;
    String dateFormat = "dd/MM/yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    EditText groupMemberIncomeAmount, groupMemberIncomeSource, groupMemberIncomePeriod, groupMemberIncomeNotes;
    Button groupMemberIncomeDeleteBtn, groupMemberIncomeUpdateBtn;

    private String groupMemberIncomeParseId = "";
    private ParseIncomeHelper mParseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_member_income);


        mParseHelper = new ParseIncomeHelper(this);

        groupMemberIncomePeriod = (EditText) findViewById(R.id.editGroupMemberIncomePeriod);
        groupMemberIncomeAmount = (EditText) findViewById(R.id.editGroupMemberIncomeAmount);
        groupMemberIncomeSource = (EditText) findViewById(R.id.editGroupMemberIncomeSource);
        groupMemberIncomeNotes = (EditText) findViewById(R.id.editGroupMemberIncomeNotes);
        groupMemberIncomeDeleteBtn = (Button) findViewById(R.id.editGroupMemberIncomeDeleteBtn);
        groupMemberIncomeUpdateBtn = (Button) findViewById(R.id.editGroupMemberIncomeUpdateBtn);


        // get Intent data
        Intent intent = getIntent();
        final String amountOfIncome = intent.getStringExtra("memberIncomeAmount");
        final String source0fIncome = intent.getStringExtra("memberIncomeSource");
        final String notesAboutIncome = intent.getStringExtra("memberIncomeNotes");
        final String periodOfIncome = intent.getStringExtra("memberIncomePeriod");
        groupMemberIncomeParseId = intent.getStringExtra("memberParseId");

        Log.d(" member parse id", "onCreate: " + groupMemberIncomeParseId);


        groupMemberIncomeSource.setText(source0fIncome);
        groupMemberIncomeAmount.setText(amountOfIncome);
        groupMemberIncomeNotes.setText(notesAboutIncome);
        groupMemberIncomePeriod.setText(periodOfIncome);

        groupMemberIncomeUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateGroupIncome();
            }
        });

        groupMemberIncomeDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // start a dialog fragment
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(view.getContext());
                // Add the buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        MembersIncome groupMemberIncomeToDelete = new MembersIncome();
                        groupMemberIncomeToDelete.setParseId(groupMemberIncomeParseId);
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


                // Create the AlertDialog
                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };

        groupMemberIncomePeriod.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void updateDate() {
        groupMemberIncomePeriod.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateGroupIncome(){
        // add new group income to db
        if ( !groupMemberIncomeAmount.getText().toString().equals("") &&
                !groupMemberIncomeSource.getText().toString().equals("")&&
                !groupMemberIncomePeriod.getText().toString().equals("")){

            String amountOfIncome = groupMemberIncomeAmount.getText().toString();
            String source0fIncome = groupMemberIncomeSource.getText().toString();
            String notesAboutIncome = groupMemberIncomeNotes.getText().toString();
            String periodOfIncome = groupMemberIncomePeriod.getText().toString();

            MembersIncome membersIncome = new MembersIncome();
            membersIncome.setAmount(amountOfIncome);
            membersIncome.setSource(source0fIncome);
            membersIncome.setNotes(notesAboutIncome);
            membersIncome.setPeriod(periodOfIncome);

            if (!groupMemberIncomeParseId.equals("")){
                membersIncome.setParseId(groupMemberIncomeParseId);
            }
            mParseHelper.updateGroupMemberIncomeInParseDb(membersIncome);

            startTabbedIncomeActivity();

            Toast.makeText(context, "Member Income " + membersIncome.getSource() + " Updated", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedIncomeActivity(){
        Intent tabbedIncomeIntent = new Intent(EditGroupMemberIncomeActivity.this, TabbedIncomeActivity.class);
        startActivity(tabbedIncomeIntent);
        finish();
    }
}
