package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.PeriodHelper;
import com.example.eq62roket.cashtime.Models.GroupSavings;
import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddGroupSavingsActivity extends AppCompatActivity {
    private static final String TAG = "AddGroupSavingsActivity";

    private Spinner periodSpinner, incomeSourcesSpinner;
    private EditText goalName, savingAmount;
    private Button btnSave;

    private String selectedPeriod;
    private String selectedIncomeSource;
    private  String nameOfGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_savings);

        periodSpinner = (Spinner) findViewById(R.id.select_period_spinner);
        incomeSourcesSpinner = (Spinner) findViewById(R.id.select_income_spinner);
        goalName = (EditText) findViewById(R.id.goalName);
        savingAmount = (EditText) findViewById(R.id.savingAmount);
        btnSave = (Button) findViewById(R.id.btnSave);

        Intent intent = getIntent();
        nameOfGoal = intent.getStringExtra("goalName");

        // Prepopulate goalName and field
        goalName.setText(nameOfGoal);

        // get selected period
        getSelectPeriod();

        // get selected income
        selectIncomeSource(getIncomeSources());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSavingTransaction();
            }
        });


    }

    public void getSelectPeriod(){
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

        periodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Get selected item id
                selectedPeriod = adapterView.getItemAtPosition(i).toString();
                switch (selectedPeriod){
                    case "Daily":
                        Toast.makeText(AddGroupSavingsActivity.this, "You selected " + selectedPeriod, Toast.LENGTH_SHORT).show();
                        break;

                    case "Weekly":
                        Toast.makeText(AddGroupSavingsActivity.this, "You selected " + selectedPeriod, Toast.LENGTH_SHORT).show();
                        break;

                    case "Monthly":
                        Toast.makeText(AddGroupSavingsActivity.this, "You selected " + selectedPeriod, Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        Toast.makeText(AddGroupSavingsActivity.this, "un known selection" , Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void selectIncomeSource(List<String> incomeSourcesList){
        // add incomeSourcesList to incomeSourcesAdapter
        ArrayAdapter<String> incomeSourcesAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, incomeSourcesList
        );
        incomeSourcesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeSourcesSpinner.setAdapter(incomeSourcesAdapter);

        incomeSourcesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedIncomeSource = adapterView.getItemAtPosition(i).toString();

                Toast.makeText(AddGroupSavingsActivity.this, "selected Income Source " + selectedIncomeSource , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void saveSavingTransaction(){
        String period = "";
        String savingPeriod = "";
        String nameOfGoal = goalName.getText().toString();
        String amountSaved = savingAmount.getText().toString();
        String sourceOfIncome = selectedIncomeSource;

        String goalAmount = savingAmount.getText().toString();

        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateToday = simpleDateFormat.format(today);

        if (selectedPeriod == "Daily"){
            savingPeriod = new PeriodHelper().getDailyDate();
        }else if (selectedPeriod == "Weekly"){
            savingPeriod = new PeriodHelper().getWeeklyDate();
        }else if (selectedPeriod == "Monthly"){
            savingPeriod = new PeriodHelper().getMonthlyDate();
        }
        if (!savingPeriod.equals("")){
            GroupSavings groupSavings = new GroupSavings(
                    nameOfGoal, savingPeriod, selectedIncomeSource, "Hey i am a note", goalAmount);
            Toast.makeText(this, "GroupGoals " + groupSavings.getPeriod(), Toast.LENGTH_SHORT).show();
        }

        // Award user 3 point for saving
        User user = new User();
        user.setPoints(3);



    }

    public List<String> getIncomeSources(){
        List<String> incomeSourcesList = new ArrayList<>();
        incomeSourcesList.add("Salary");
        incomeSourcesList.add("Loan");
        incomeSourcesList.add("Investments");

        return incomeSourcesList;
    }
}
