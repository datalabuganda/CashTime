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
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Models.MembersGoals;
import com.example.eq62roket.cashtime.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditMemberGoalActivity extends AppCompatActivity {

    private static final String TAG = "EditMemberGoalActivity";

    TextView memberGoalDueDate;
    Calendar myCalendar = Calendar.getInstance();
    Context context = this;
    String dateFormat = "dd/MM/yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    Button memberGoalDeleteBtn, memberGoalUpdateBtn;
    private TextView memberName;
    private EditText memberGoalName, memberGoalAmount, memberGoalNote;

    private ParseHelper mParseHelper;
    private String memberGoalLocalUniqueID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member_goal);

        mParseHelper = new ParseHelper(this);

        memberGoalDueDate = (TextView) findViewById(R.id.memberGoalDueDate);
        memberGoalNote = (EditText) findViewById(R.id.memberGoalNotes);
        memberGoalAmount = (EditText) findViewById(R.id.memberGoalAmount);
        memberGoalName = (EditText) findViewById(R.id.memberGoalName);
        memberName = (TextView) findViewById(R.id.memberName);
        memberGoalUpdateBtn = (Button) findViewById(R.id.memberGoalUpdateBtn);
        memberGoalDeleteBtn = (Button) findViewById(R.id.memberGoalDeleteBtn);

        // get intent data
        Intent intent = getIntent();
        String nameOfMember = intent.getStringExtra("groupMemberName");
        final String nameOfGoal = intent.getStringExtra("groupMemberGoalName");
        String goalAmount = intent.getStringExtra("groupMemberGoalAmount");
        String goalDeadline = intent.getStringExtra("groupMemberGoalDeadline");
        String goalNotes = intent.getStringExtra("groupMemberGoalNotes");
        memberGoalLocalUniqueID = intent.getStringExtra("memberGoalLocalUniqueID");

        // prepopulate memberName
        memberName.setText(nameOfMember);
        memberGoalName.setText(nameOfGoal);
        memberGoalAmount.setText(goalAmount);
        memberGoalDueDate.setText(goalDeadline);
        memberGoalNote.setText(goalNotes);

        memberGoalUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMemberGoal();
            }
        });

        memberGoalDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(view.getContext());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MembersGoals memberGoalToDelete = new MembersGoals();
                        memberGoalToDelete.setLocalUniqueID(memberGoalLocalUniqueID);
                        mParseHelper.deleteMemberGoalFromParseDb(memberGoalToDelete);

                        startTabbedGoalsActivity();
                        Toast.makeText(EditMemberGoalActivity.this, "Goal deleted successfully", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cancel
                    }
                });

                builder.setMessage(
                        "Deleting '" + nameOfGoal + "' Can not be undone." + "Are You Sure You want to delete this goal?").setTitle("Delete Member Goal");

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

    private void saveMemberGoal(){
        if ( !memberGoalName.getText().toString().equals("") &&
                !memberGoalAmount.getText().toString().equals("")){
            String nameOfGoal = memberGoalName.getText().toString();
            String costOfGoal = memberGoalAmount.getText().toString();
            String goalDeadline = memberGoalDueDate.getText().toString();
            String goalNotes = memberGoalNote.getText().toString();

            final MembersGoals membersGoalToUpdate = new MembersGoals();
            membersGoalToUpdate.setMemberGoalAmount(costOfGoal);
            membersGoalToUpdate.setMemberGoalName(nameOfGoal);
            membersGoalToUpdate.setMemberGoalDueDate(goalDeadline);
            membersGoalToUpdate.setMemberGoalNotes(goalNotes);
            membersGoalToUpdate.setLocalUniqueID(memberGoalLocalUniqueID);
            mParseHelper.updateMemberGoalInParseDb(membersGoalToUpdate);

            startTabbedGoalsActivity();
            Toast.makeText(
                    context,
                    "Group Goal " + membersGoalToUpdate.getMemberGoalName() + " saved",
                    Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedGoalsActivity(){
        Intent tabbedGoalsIntent = new Intent(EditMemberGoalActivity.this, TabbedGoalsActivity.class);
        tabbedGoalsIntent.putExtra("position", "1");
        startActivity(tabbedGoalsIntent);
        finish();
    }
}
