package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.User;

import java.util.ArrayList;


public class HealthActivity extends AppCompatActivity {
    private static final String TAG = "SavingsActivity";

    SQLiteHelper myHelper;
    EditText edtHealth;
    Button btnHealth;
    UserCrud userCrud;
    ListView healthListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        edtHealth = (EditText) findViewById(R.id.amtHealth);
        btnHealth = (Button) findViewById(R.id.btnHealth);
        healthListView = (ListView) findViewById(R.id.healthListView);
        myHelper = new SQLiteHelper(this);

        userCrud = new UserCrud(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AddHealth();

    }

    public void AddHealth(){
        btnHealth.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!edtHealth.getText().toString().equals("")) {
                            int yVal = Integer.parseInt(String.valueOf(edtHealth.getText()));
                            boolean isInseted = myHelper.insertHealth(yVal);
                            if (isInseted) {
                                // if user spends on any expense, award them 2 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
                                userCrud.updateUser(user);

                                Toast.makeText(HealthActivity.this, "Your health costs have been stored", Toast.LENGTH_LONG).show();
                                Intent Healthintent = new Intent(HealthActivity.this, ExpenditureActivity.class);
                                HealthActivity.this.startActivity(Healthintent);
                                finish();
                            }
                            else {
                                Toast.makeText(HealthActivity.this, "Your health costs where not stored", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(HealthActivity.this, "Please input amount before submitting", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );

        populateListView();
    }
    private void populateListView(){
        Log.d(TAG, "populateListView: Displayng data in the listView");

        Cursor data = myHelper.getHealth();
        Log.d(TAG, "here is data: " + data);
//        ArrayList<Income> salaryDataList = new ArrayList<>();
//        salaryDataList = (ArrayList<Income>) myHelper.getSalary();
//
//
//        SalaryAdapter salaryAdapterListAdapter = new SalaryAdapter(this, R.layout.salary_list_adapter, salaryDataList);
//        SalaryListVIew.setAdapter(salaryAdapterListAdapter);

        ArrayList<String> listData = new ArrayList<>();

        while (data.moveToNext()){
            listData.add(data.getString(data.getColumnIndex(myHelper.COL_6)));

        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        healthListView.setAdapter(adapter);

        healthListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String health = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on" + health);

                Cursor data = myHelper.getHealthID(health);
                int healthID = -1;
                while (data.moveToNext()){
                    healthID = data.getInt(0);

                }
                if (healthID > -1){
                    Intent editHealthIntent = new Intent(HealthActivity.this, UpdateHealthActivity.class);
                    editHealthIntent.putExtra("id", healthID);
                    editHealthIntent.putExtra("health", health);
                    Log.d(TAG, "almost through: " + health);
                    startActivity(editHealthIntent);
                    finish();

                }else {

                }
            }
        });
    }


}
