package com.example.eq62roket.cashtime.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseIncomeHelper;
import com.example.eq62roket.cashtime.Models.GroupIncome;
import com.example.eq62roket.cashtime.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditGroupIncomeActivity extends AppCompatActivity {

    ImageView groupGoalImage;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    Calendar myCalendar = Calendar.getInstance();
    Context context = this;
    String dateFormat = "dd/MM/yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    EditText groupIncomeSource, groupIncomeAmount, groupIncomeNotes, groupIncomePeriod;
    Button groupIncomeDeleteBtn, groupIncomeUpdateBtn;

    private String groupIncomeLocalUniqueID = "";
    private ParseIncomeHelper mParseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_income);

        mParseHelper = new ParseIncomeHelper(this);

        groupIncomeSource = (EditText) findViewById(R.id.editGroupIncomeSource);
        groupIncomeAmount = (EditText) findViewById(R.id.editGroupIncomeAmount);
        groupIncomeNotes = (EditText) findViewById(R.id.editGroupIncomeNotes);
        groupIncomePeriod = (EditText) findViewById(R.id.editGroupIncomePeriod);
        groupIncomeDeleteBtn = (Button) findViewById(R.id.editGroupIncomeDeleteBtn);
        groupIncomeUpdateBtn = (Button) findViewById(R.id.editGroupIncomeUpdateBtn);

        // get Intent data
        Intent intent = getIntent();
        String amountOfIncome = intent.getStringExtra("groupIncomeAmount");
        final String source0fIncome = intent.getStringExtra("groupIncomeSource");
        String notesAboutIncome = intent.getStringExtra("groupIncomeNotes");
        String periodOfIncome= intent.getStringExtra("groupIncomePeriod");
        String nameOfGroup = intent.getStringExtra("groupName");
        groupIncomeLocalUniqueID = intent.getStringExtra("groupIncomeLocalUniqueID");

        groupIncomeSource.setText(source0fIncome);
        groupIncomeAmount.setText(amountOfIncome);
        groupIncomeNotes.setText(notesAboutIncome);
        groupIncomePeriod.setText(periodOfIncome);

        groupIncomeUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateGroupIncome();
            }
        });

        groupIncomeDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // start a dialog fragment
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(view.getContext());
                // Add the buttons
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

        groupIncomePeriod.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    private void updateDate() {
        groupIncomePeriod.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateGroupIncome(){
        // add new group goal to db
        if ( !groupIncomeSource.getText().toString().equals("") &&
                !groupIncomeAmount.getText().toString().equals("") &&
                !groupIncomePeriod.getText().toString().equals("")){
            String amountOfIncome = groupIncomeAmount.getText().toString();
            String source0fIncome = groupIncomeSource.getText().toString();
            String notesAboutIncome = groupIncomeNotes.getText().toString();
            String periodOfIncome = groupIncomePeriod.getText().toString();


            GroupIncome groupIncome = new GroupIncome();
            groupIncome.setAmount(amountOfIncome);
            groupIncome.setSource(source0fIncome);
            groupIncome.setNotes(notesAboutIncome);
            groupIncome.setPeriod(periodOfIncome);
            if (!groupIncomeLocalUniqueID.equals("")){
                groupIncome.setLocalUniqueID(groupIncomeLocalUniqueID);
            }
            mParseHelper.updateGroupIncomeInParseDb(groupIncome);

            startTabbedIncomeActivity();

            Toast.makeText(context, "Group Income " + groupIncome.getSource() + " Updated", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedIncomeActivity(){
        Intent tabbedIncomeIntent = new Intent(EditGroupIncomeActivity.this, TabbedIncomeActivity.class);
        tabbedIncomeIntent.putExtra("position", "0");
        startActivity(tabbedIncomeIntent);
        finish();
    }
}
