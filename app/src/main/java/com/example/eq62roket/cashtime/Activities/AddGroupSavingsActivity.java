package com.example.eq62roket.cashtime.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

public class AddGroupSavingsActivity extends AppCompatActivity {
    private static final String TAG = "AddGroupSavingsActivity";
    private Spinner periodSpinner, incomeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_savings);

        periodSpinner = (Spinner) findViewById(R.id.select_period_spinner);
        incomeSpinner = (Spinner) findViewById(R.id.select_income_spinner);

        // Add periods to list
        List<String> periods = new ArrayList<>();
        periods.add("Daily");
        periods.add("Weekly");
        periods.add("Monthly");

        // add list to adapter
        ArrayAdapter<String> periodAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, periods
        );
        periodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodSpinner.setAdapter(periodAdapter);

        // Get back selected item
        String selectedPeriod = periodSpinner.getSelectedItem().toString();
        switch (selectedPeriod){
            case "Daily":
                Toast.makeText(this, "You Selected " + selectedPeriod, Toast.LENGTH_SHORT).show();
                break;

            case "Weekly":
                Toast.makeText(this, "You Selected " + selectedPeriod, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "selected: " + selectedPeriod);
                break;

            case "Monthly":
                Toast.makeText(this, "You Selected " + selectedPeriod, Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(this, "You Selected nothing", Toast.LENGTH_SHORT).show();
                break;
        }


    }
}
