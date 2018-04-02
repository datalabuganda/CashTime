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

import com.example.eq62roket.cashtime.Helper.ParseGroupHelper;
import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Helper.PeriodHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupMemberListener;
import com.example.eq62roket.cashtime.Models.GroupMember;
import com.example.eq62roket.cashtime.Models.MembersGoals;
import com.example.eq62roket.cashtime.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddMembersGoalsActivity extends AppCompatActivity {

    TextView memberGoalDueDate;
    Calendar myCalendar = Calendar.getInstance();
    Context context = this;
    String dateFormat = "dd/MM/yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    Button memberGoalCancelBtn, memberGoalSaveBtn;
    private TextView memberName;
    private EditText memberGoalName, memberGoalAmount, memberGoalNote;
    private String groupMemberParseId;
    private ParseGroupHelper mParseGroupHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_members_goals);

        mParseGroupHelper = new ParseGroupHelper(AddMembersGoalsActivity.this);

        Intent addMemberGoalIntent = getIntent();
        groupMemberParseId = addMemberGoalIntent.getStringExtra("groupMemberParseId");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);

        memberGoalDueDate = (TextView) findViewById(R.id.memberGoalDueDate);
        memberGoalNote = (EditText) findViewById(R.id.memberGoalNotes);
        memberGoalAmount = (EditText) findViewById(R.id.memberGoalAmount);
        memberGoalName = (EditText) findViewById(R.id.memberGoalName);
        memberName = (TextView) findViewById(R.id.memberName);
        memberGoalSaveBtn = (Button) findViewById(R.id.memberGoalSaveBtn);
        memberGoalCancelBtn = (Button) findViewById(R.id.memberGoalCancelBtn);

        mParseGroupHelper.getMemberUserFromParseDb(groupMemberParseId, new OnReturnedGroupMemberListener() {
            @Override
            public void onResponse(List<GroupMember> groupMembersList) {
                memberName.setText(groupMembersList.get(0).getMemberUsername());
            }

            @Override
            public void onFailure(String error) {

            }
        });

        memberGoalSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGroupGoal();
            }
        });

        memberGoalCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTabbedGoalsActivity();
            }
        });


        // init - set date to current date
        long currentdate = System.currentTimeMillis();
        String dateString = sdf.format(currentdate);
        memberGoalDueDate.setText(new PeriodHelper().getMonthlyDate());


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

        memberGoalDueDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateDate() {
        memberGoalDueDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void saveGroupGoal(){
        // add new group goal to db
        if ( !memberGoalName.getText().toString().equals("") &&
                !memberGoalAmount.getText().toString().equals("")){
            String nameOfGoal = memberGoalName.getText().toString();
            String costOfGoal = memberGoalAmount.getText().toString();
            String goalDeadline = memberGoalDueDate.getText().toString();
            String goalNotes = memberGoalNote.getText().toString();
            String nameOfMember = memberName.getText().toString();

            MembersGoals newMembersGoal = new MembersGoals();
            newMembersGoal.setMemberGoalStatus("incomplete");
            newMembersGoal.setMemberGoalAmount(costOfGoal);
            newMembersGoal.setMemberGoalName(nameOfGoal);
            newMembersGoal.setMemberGoalDueDate(goalDeadline);
            newMembersGoal.setMemberName(nameOfMember);
            newMembersGoal.setMemberParseId(groupMemberParseId);
            if (goalNotes.isEmpty()){
                newMembersGoal.setMemberGoalNotes("No Notes Added");
            }else {
                newMembersGoal.setMemberGoalNotes(goalNotes);
            }
            new ParseHelper(this).saveMemberGoalsToParseDb(newMembersGoal);

            startTabbedGoalsActivity();

            Toast.makeText(context, "Group Goal " + newMembersGoal.getMemberGoalName() + " saved", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedGoalsActivity(){
        Intent tabbedGoalsIntent = new Intent(AddMembersGoalsActivity.this, TabbedGoalsActivity.class);
        startActivity(tabbedGoalsIntent);
        finish();
    }
}
