package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class AddTipsActivity extends AppCompatActivity {
    private static final String TAG = "AddTipsActivity";

    private Button btnCancel, btnSave;
    private EditText tipText;
    private TextView goalName;

    private String groupGoalLocalUniqueID, groupLocalUniqueID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tips);

        Intent addTipsIntent = getIntent();
        String nameOfGoal = addTipsIntent.getStringExtra("goalName");
        groupLocalUniqueID = addTipsIntent.getStringExtra("groupLocalUniqueID");
        groupGoalLocalUniqueID = addTipsIntent.getStringExtra("groupGoalLocalUniqueID");

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        goalName = (TextView) findViewById(R.id.goalName);
        tipText = (EditText) findViewById(R.id.tipNotes);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSave = (Button) findViewById(R.id.btnSave);

        // prepopulate goalName field
        goalName.setText(nameOfGoal);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTip();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTabbedBarriersTipsActivity();
            }
        });
    }

    public void saveTip(){
        if ( !goalName.getText().toString().equals("") &&
                !tipText.getText().toString().equals("")){

            Date today = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            String dateToday = simpleDateFormat.format(today);

            final Tip newTip = new Tip();
            newTip.setGoalName(goalName.getText().toString());
            newTip.setIntroText(tipText.getText().toString());
            newTip.setDateAdded(dateToday);
            newTip.setDateModified(dateToday);
            newTip.setGroupLocalUniqueID(groupLocalUniqueID);
            newTip.setGroupGoalLocalUniqueID(groupGoalLocalUniqueID);
            new ParseHelper(AddTipsActivity.this).saveTipToParseDb(newTip);

            startTabbedBarriersTipsActivity();
            Toast.makeText(AddTipsActivity.this, "Tip " + newTip.getGoalName() + " saved", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedBarriersTipsActivity(){
        Intent tabbedBarriersTipsActivity = new Intent(AddTipsActivity.this, TabbedBarriersTipsActivity.class);
        tabbedBarriersTipsActivity.putExtra("position", "1");
        startActivity(tabbedBarriersTipsActivity);
        finish();
    }
}
