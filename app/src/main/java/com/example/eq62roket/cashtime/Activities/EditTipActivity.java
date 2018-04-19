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
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Models.Tip;
import com.example.eq62roket.cashtime.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditTipActivity extends AppCompatActivity {

    private static final String TAG = "EditTipActivity";
    private EditText tipNotes;
    private TextView goalName;
    private Button btnUpdate, btnDelete;

    private ParseHelper mParseHelper;
    private String tipLocalUniqueID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tip);

        mParseHelper = new ParseHelper(EditTipActivity.this);

        goalName = (TextView) findViewById(R.id.goalName);
        tipNotes = (EditText) findViewById(R.id.tipNotes);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        Intent editTipIntent = getIntent();
        final String nameOfGoal = editTipIntent.getStringExtra("nameOfGoal");
        final String tip = editTipIntent.getStringExtra("tipText");
        String addedDate = editTipIntent.getStringExtra("tipAddDate");
        tipLocalUniqueID = editTipIntent.getStringExtra("tipLocalUniqueID");

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
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Tip tipToDelete = new Tip();
                        tipToDelete.setLocalUniqueID(tipLocalUniqueID);
                        mParseHelper.deleteTipFromParseDb(tipToDelete);

                        startTabbedBarriersTipsActivity();
                        Toast.makeText(EditTipActivity.this, "Tip deleted successfully", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cancel
                    }
                });

                builder.setMessage(
                        "Deleting Tip for '" + nameOfGoal + "' Can not be undone." + "Are You Sure You want to delete this tip?")
                        .setTitle("Delete Tip");

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

            final Tip tipToUpdate = new Tip();
            tipToUpdate.setLocalUniqueID(tipLocalUniqueID);
            tipToUpdate.setIntroText(tipNotes.getText().toString());
            tipToUpdate.setDateModified(dateToday);
            mParseHelper.updateTipsInParseDb(tipToUpdate);

            startTabbedBarriersTipsActivity();
            Toast.makeText(EditTipActivity.this, "Tip for " + tipToUpdate.getIntroText() + " updated Successfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedBarriersTipsActivity(){
        Intent tabbedBarriersTipsActivity = new Intent(EditTipActivity.this, TabbedBarriersTipsActivity.class);
        tabbedBarriersTipsActivity.putExtra("position", "1");
        startActivity(tabbedBarriersTipsActivity);
        finish();
    }
}
