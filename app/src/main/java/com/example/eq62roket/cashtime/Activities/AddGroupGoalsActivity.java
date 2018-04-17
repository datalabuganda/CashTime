package com.example.eq62roket.cashtime.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Helper.PeriodHelper;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddGroupGoalsActivity extends AppCompatActivity {

    private static final String TAG = "AddGroupGoalsActivity";
    private TextView groupGoalDueDate;
    private Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    private Calendar myCalendar = Calendar.getInstance();
    private Context context = this;
    private String dateFormat = "dd/MM/yyyy";
    private DatePickerDialog.OnDateSetListener date;
    private SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    private EditText groupGoalNote, groupGoalAmount, groupGoalName;
    private Button groupCancelBtn, groupSaveBtn;
    private String groupLocalUniqueID, groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_goals);

        Intent groupDetailsIntent = getIntent();
        groupLocalUniqueID = groupDetailsIntent.getStringExtra("groupLocalUniqueID");
        groupName = groupDetailsIntent.getStringExtra("groupName");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);

        groupGoalDueDate = (TextView) findViewById(R.id.groupGoalDueDate);
        groupGoalNote = (EditText) findViewById(R.id.groupGoalNote);
        groupGoalAmount = (EditText) findViewById(R.id.groupGoalAmount);
        groupGoalName = (EditText) findViewById(R.id.groupGoalName);
        groupSaveBtn = (Button) findViewById(R.id.groupSaveBtn);
        groupCancelBtn = (Button) findViewById(R.id.groupCancelBtn);

        groupSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGroupGoal();
            }
        });

        groupCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTabbedGoalsActivity();
            }
        });


        // init - set date to current date
        long currentdate = System.currentTimeMillis();
        String dateString = sdf.format(currentdate);
        groupGoalDueDate.setText(new PeriodHelper().getMonthlyDate());


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

        groupGoalDueDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    private void updateDate() {
        groupGoalDueDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void saveGroupGoal(){
        if ( !groupGoalName.getText().toString().equals("") &&
                !groupGoalAmount.getText().toString().equals("")){
            String nameOfGoal = groupGoalName.getText().toString();
            String costOfGoal = groupGoalAmount.getText().toString();
            String goalDeadline = groupGoalDueDate.getText().toString();
            String goalNotes = groupGoalNote.getText().toString();
            String dateToday = new PeriodHelper().getDateToday();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            try {
                Date todayZDate = simpleDateFormat.parse(dateToday);
                Date goalZDeadline = simpleDateFormat.parse(goalDeadline);

                final GroupGoals groupGoals = new GroupGoals();
                groupGoals.setAmount(costOfGoal);
                groupGoals.setName(nameOfGoal);
                groupGoals.setDueDate(goalDeadline);
                groupGoals.setGroupLocalUniqueID(groupLocalUniqueID);
                groupGoals.setGroupName(groupName);
                if (goalNotes.trim().equals("")){
                    groupGoals.setNotes("No notes");
                }else {
                    groupGoals.setNotes(goalNotes);
                }
                if (todayZDate.after(goalZDeadline)){
                    groupGoals.setGroupGoalStatus("failed");
                    groupGoals.setCompletedDate(dateToday);
                }else {
                    groupGoals.setGroupGoalStatus("incomplete");
                    groupGoals.setCompletedDate("");
                }
                new ParseHelper(this).saveGroupGoalsToParseDb(groupGoals);
                startTabbedGoalsActivity();
                Toast.makeText(context, "Group Goal " + groupGoals.getName() + " saved", Toast.LENGTH_SHORT).show();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedGoalsActivity(){
        Intent tabbedGoalsIntent = new Intent(AddGroupGoalsActivity.this, TabbedGoalsActivity.class);
        tabbedGoalsIntent.putExtra("position", "0");
        startActivity(tabbedGoalsIntent);
        finish();
    }
}
