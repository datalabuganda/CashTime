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
import com.example.eq62roket.cashtime.Models.Barrier;
import com.example.eq62roket.cashtime.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddBarrierActivity extends AppCompatActivity {

    private static final String TAG = "AddBarrierActivity";
    private Button btnCancel, btnSave;
    private EditText barrierNotes, barrierName;
    private TextView goalName;
    private String groupLocalUniqueID, groupGoalLocalUniqueID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barrier);

        Intent addBarrierIntent = getIntent();
        String nameOfGoal = addBarrierIntent.getStringExtra("goalName");
        groupLocalUniqueID = addBarrierIntent.getStringExtra("groupLocalUniqueID");
        groupGoalLocalUniqueID = addBarrierIntent.getStringExtra("groupGoalLocalUniqueID");

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        goalName = (TextView) findViewById(R.id.goalName);
        barrierName = (EditText) findViewById(R.id.barrierName);
        barrierNotes = (EditText) findViewById(R.id.barrierNotes);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSave = (Button) findViewById(R.id.btnSave);

        // prepopulate goalName field
        goalName.setText(nameOfGoal);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBarrier();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTabbedBarriersTipsctivity();
            }
        });
    }

    public void saveBarrier(){
        if ( !goalName.getText().toString().equals("") &&
                !barrierName.getText().toString().equals("") &&
                !barrierNotes.getText().toString().equals("")){

            Date today = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            String dateToday = simpleDateFormat.format(today);

            final Barrier newBarrier = new Barrier();
            newBarrier.setGoalName(goalName.getText().toString());
            newBarrier.setBarrierName(barrierName.getText().toString());
            newBarrier.setBarrierText(barrierNotes.getText().toString());
            newBarrier.setGroupLocalUniqueID(groupLocalUniqueID);
            newBarrier.setGroupGoalLocalUniqueID(groupGoalLocalUniqueID);
            newBarrier.setDateAdded(dateToday);
            newBarrier.setTipGiven(false);
            new ParseHelper(AddBarrierActivity.this).saveGroupBarrierToParseDb(newBarrier);

            startTabbedBarriersTipsctivity();
            Toast.makeText(AddBarrierActivity.this, "Good to save " + newBarrier.getBarrierName(), Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedBarriersTipsctivity(){
        Intent tabbedBarriersTipsActivity = new Intent(AddBarrierActivity.this, TabbedBarriersTipsActivity.class);
        tabbedBarriersTipsActivity.putExtra("position", "0");
        startActivity(tabbedBarriersTipsActivity);
        finish();
    }
}
