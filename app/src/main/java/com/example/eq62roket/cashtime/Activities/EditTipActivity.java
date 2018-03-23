package com.example.eq62roket.cashtime.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Models.Tip;
import com.example.eq62roket.cashtime.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditTipActivity extends AppCompatActivity {

    private EditText goalName, tipNotes;
    private Button btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tip);

        goalName = (EditText) findViewById(R.id.goalName);
        tipNotes = (EditText) findViewById(R.id.tipNotes);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        // get data from intent.
        Intent editTipIntent = getIntent();
        final String nameOfGoal = editTipIntent.getStringExtra("nameOfGoal");
        String tip = editTipIntent.getStringExtra("tipText");
        String addedDate = editTipIntent.getStringExtra("tipAddDate");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit " + nameOfGoal);
        setSupportActionBar(toolbar);

        goalName.setText(nameOfGoal);
        tipNotes.setText(tip);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTip();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start a dialog fragment
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                // Add the buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Delete Saving, redirect to member goals fragment
                        // TODO: 3/22/18 ====> delete Tip record....redirect to tips fragment

                        // start TabbedSavingActivity
                        startTabbedBarriersTipsctivity();
                        Toast.makeText(EditTipActivity.this, "Tip deleted successfully", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cancel
                    }
                });

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(
                        "Deleting Tip for '" + nameOfGoal + "' Can not be undone." + "Are You Sure You want to delete this tip?")
                        .setTitle("Delete Tip");


                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    public void updateTip(){
        if ( !goalName.getText().toString().equals("") &&
                !tipNotes.getText().toString().equals("")){

            Date today = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            String dateToday = simpleDateFormat.format(today);

            Tip updatedTip = new Tip();
            updatedTip.setGoalName(goalName.getText().toString());
            updatedTip.setIntroText(tipNotes.getText().toString());
            updatedTip.setDateModified(dateToday);

            // TODO: 3/23/18 ====>>> save updatedTip to db....redirect to Tips Fragment

            startTabbedBarriersTipsctivity();

            Toast.makeText(EditTipActivity.this, "Tip for " + updatedTip.getGoalName() + " updated Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedBarriersTipsctivity(){
        Intent tabbedBarriersTipsActivity = new Intent(EditTipActivity.this, TabbedBarriersTipsActivity.class);
        startActivity(tabbedBarriersTipsActivity);
        finish();
    }
}