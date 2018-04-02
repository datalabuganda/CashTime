package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseExpenditureCategoryHelper;
import com.example.eq62roket.cashtime.Helper.ParseIncomeHelper;
import com.example.eq62roket.cashtime.Helper.ParseIncomeSourceHelper;
import com.example.eq62roket.cashtime.Models.ExpenditureCategories;
import com.example.eq62roket.cashtime.Models.IncomeSources;
import com.example.eq62roket.cashtime.R;
import com.parse.ParseUser;

public class AddIncomeSourceActivity extends AppCompatActivity {
    private static String TAG = "AddIncomeSourceActivity";
    EditText incomeSource;
    Button incomeSourceCancelBtn, incomeSourceSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income_source);

        incomeSource = (EditText)findViewById(R.id.addIncomeSource);

        incomeSourceCancelBtn = (Button)findViewById(R.id.incomeSourceCancelBtn);
        incomeSourceSaveBtn = (Button)findViewById(R.id.incomeSourceSaveBtn);

        incomeSourceSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIncomeSource();
            }
        });

        incomeSourceCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTabbedIncomeActivity();
            }
        });
    }

    private void saveIncomeSource(){
        if ( !incomeSource.getText().toString().equals("")){
            String nameOfSource = incomeSource.getText().toString();

            IncomeSources incomeSources = new IncomeSources();
            incomeSources.setName(nameOfSource);

            Log.d(TAG, "saveIncomeSource: " + incomeSources);

            // TODO: 3/22/18 =====> save object to db
            new ParseIncomeSourceHelper(this).saveSourcesToParseDb(incomeSources);
            startTabbedIncomeActivity();

            Toast.makeText(AddIncomeSourceActivity.this, "Income Source " + incomeSources.getName() + " saved", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(AddIncomeSourceActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedIncomeActivity(){
        Intent tabbedIncomeSourcesIntent = new Intent(AddIncomeSourceActivity.this, TabbedIncomeActivity.class);
        startActivity(tabbedIncomeSourcesIntent);
        finish();
    }
}
