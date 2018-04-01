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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddGroupGoalsActivity extends AppCompatActivity {

    private static final String TAG = "AddGroupGoalsActivity";

    TextView groupGoalDueDate;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    Calendar myCalendar = Calendar.getInstance();
    Context context = this;
    String dateFormat = "dd/MM/yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    EditText groupGoalNote, groupGoalAmount, groupGoalName;
    Button groupCancelBtn, groupSaveBtn;

    private String groupParseId, groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_goals);

        Intent groupDetailsIntent = getIntent();
        groupParseId = groupDetailsIntent.getStringExtra("groupParseId");
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

            GroupGoals groupGoals = new GroupGoals();
            groupGoals.setGroupGoalStatus("incomplete");
            groupGoals.setAmount(costOfGoal);
            groupGoals.setName(nameOfGoal);
            groupGoals.setDueDate(goalDeadline);
            groupGoals.setNotes(goalNotes);
            groupGoals.setGroupId(groupParseId);
            groupGoals.setGroupName(groupName);

            // TODO: 3/22/18 =====> redirect to group fragment with updated group goals
            new ParseHelper(this).saveGroupGoalsToParseDb(groupGoals);
            startTabbedGoalsActivity();

            Toast.makeText(context, "Group Goal " + groupGoals.getName() + " saved", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedGoalsActivity(){
        Intent tabbedGoalsIntent = new Intent(AddGroupGoalsActivity.this, HomeActivity.class);
        startActivity(tabbedGoalsIntent);
        finish();
    }
}
