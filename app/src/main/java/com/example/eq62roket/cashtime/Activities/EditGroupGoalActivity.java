package com.example.eq62roket.cashtime.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditGroupGoalActivity extends AppCompatActivity {

    TextView groupGoalDueDate;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    Calendar myCalendar = Calendar.getInstance();
    Context context = this;
    String dateFormat = "dd/MM/yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    EditText groupGoalNote, groupGoalAmount, groupGoalName;
    Button groupDeleteBtn, groupUpdateBtn;

    private String groupGoalParseId = "";
    private ParseHelper mParseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_goal);

        mParseHelper = new ParseHelper(this);

        groupGoalDueDate = (TextView) findViewById(R.id.groupGoalDueDate);
        groupGoalNote = (EditText) findViewById(R.id.groupGoalNote);
        groupGoalAmount = (EditText) findViewById(R.id.groupGoalAmount);
        groupGoalName = (EditText) findViewById(R.id.groupGoalName);
        groupUpdateBtn = (Button) findViewById(R.id.groupUpdateBtn);
        groupDeleteBtn = (Button) findViewById(R.id.groupDeleteBtn);

        // get Intent data
        Intent intent = getIntent();
        final String nameOfGoal = intent.getStringExtra("groupGoalName");
        String costOfGoal = intent.getStringExtra("groupGoalAmount");
        String goalDeadline = intent.getStringExtra("groupGoalDeadline");
        String goalNote = intent.getStringExtra("groupGoalNotes");
        groupGoalParseId = intent.getStringExtra("groupGoalParseId");

        groupGoalName.setText(nameOfGoal);
        groupGoalAmount.setText(costOfGoal);
        groupGoalDueDate.setText(goalDeadline);
        groupGoalNote.setText(goalNote);

        groupUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateGroupGoal();
            }
        });

        groupDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // start a dialog fragment
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(view.getContext());
                // Add the buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        GroupGoals groupGoalToDelete = new GroupGoals();
                        groupGoalToDelete.setParseId(groupGoalParseId);
                        mParseHelper.deleteGroupGoalFromParseDb(groupGoalToDelete);
                        startTabbedGoalsActivity();
                        Toast.makeText(EditGroupGoalActivity.this, "Goal deleted successfully", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cancel
                    }
                });

                builder.setMessage(
                        "Deleting Group Goal '" + nameOfGoal + "' Can not be undone." + "Are You Sure You want to delete this goal?").setTitle("Delete Group Goal");


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

        groupGoalDueDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    private void SelectImage(){
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(EditGroupGoalActivity.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")){

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                }else if (items[i].equals("Gallery")){

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("images/*");
                    startActivityForResult(intent.createChooser(intent, "Select file"), SELECT_FILE);

                }else if (items[i].equals("Cancel")){
                    dialogInterface.dismiss();
                }
            }
        });

        builder.show();
    }

    private void updateDate() {
        groupGoalDueDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateGroupGoal(){
        if ( !groupGoalName.getText().toString().equals("") &&
                !groupGoalAmount.getText().toString().equals("")){
            String nameOfGoal = groupGoalName.getText().toString();
            String costOfGoal = groupGoalAmount.getText().toString();
            String goalDeadline = groupGoalDueDate.getText().toString();
            String goalNotes = groupGoalNote.getText().toString();

            GroupGoals groupGoal = new GroupGoals();
            groupGoal.setGroupGoalStatus("incomplete");
            groupGoal.setAmount(costOfGoal);
            groupGoal.setName(nameOfGoal);
            groupGoal.setDueDate(goalDeadline);
            if (!groupGoalParseId.equals("")){
                groupGoal.setParseId(groupGoalParseId);
            }
            if (goalNotes.trim().equals("")){
                groupGoal.setNotes("No notes");
            }else {
                groupGoal.setNotes(goalNotes);
            }
            mParseHelper.updateGroupGoalInParseDb(groupGoal);

            startTabbedGoalsActivity();

            Toast.makeText(context, "Group Goal " + groupGoal.getName() + " Updated", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedGoalsActivity(){
        Intent tabbedGoalsIntent = new Intent(EditGroupGoalActivity.this, HomeActivity.class);
        startActivity(tabbedGoalsIntent);
        finish();
    }
}
