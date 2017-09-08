package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.eq62roket.CashTime.helper.DatabaseHelper;
import com.example.eq62roket.CashTime.helper.IncomeCrud;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.User;

import java.util.ArrayList;

public class SalaryActivity extends AppCompatActivity {
    private static final String TAG = "SalaryActivity";
    Button btnSalary, updateSalary;
    EditText edtSalary;
    IncomeCrud incomeCrud;
    UserCrud userCrud;
    ListView SalaryListVIew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSalary = (Button) findViewById(R.id.btnSalary);
        edtSalary = (EditText) findViewById(R.id.edtSalary);
        SalaryListVIew = (ListView) findViewById(R.id.SalaryListView);

        incomeCrud = new IncomeCrud(this);

        userCrud = new UserCrud(this);

        AddSalary();
        populateListView();
    }

    public void AddSalary(){
        btnSalary.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!edtSalary.getText().toString().equals("")) {
                            int yVal = Integer.parseInt(String.valueOf(edtSalary.getText()));
                            boolean isInseted = incomeCrud.insertSalary(yVal);
                            if (isInseted) {
                                Log.d(TAG, "phpId: "+ incomeCrud.getPhpID());
                                Log.d(TAG, "insertedSyncStatusLocal: "+ incomeCrud.getSyncStatus());
                                // if user adds income, award them 2 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
                                user.setSyncStatus(0);
                                userCrud.updateUser(user);

                                Toast.makeText(SalaryActivity.this, "Your income has been added", Toast.LENGTH_LONG).show();
                                Intent Salaryintent = new Intent(SalaryActivity.this, IncomeActivity.class);
                                SalaryActivity.this.startActivity(Salaryintent);
                                finish();
                            }
                            else {
                                Toast.makeText(SalaryActivity.this, "Your income has not been added", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(SalaryActivity.this, "Please input amount before submitting", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );


    }

    private void populateListView(){
        Log.d(TAG, "populateListView: Displayng data in the listView");

        Cursor data = incomeCrud.getSalary();
        Log.d(TAG, "here is data: " + data);
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(data.getColumnIndex(DatabaseHelper.COLUMN_INCOME_SALARY)));

        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        SalaryListVIew.setAdapter(adapter);

        SalaryListVIew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String salary = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on" + salary);

                Cursor data = incomeCrud.getSalaryID(salary);
                int salaryID = -1;
                while (data.moveToNext()){
                    salaryID = data.getInt(0);

                }
                if (salaryID > -1){
                    Intent editSalaryIntent = new Intent(SalaryActivity.this, UpdateSalaryActivity.class);
                    editSalaryIntent.putExtra("id", salaryID);
                    editSalaryIntent.putExtra("salary", salary);
                    Log.d(TAG, "almost through: " + salary);
                    startActivity(editSalaryIntent);
                    finish();

                }else {

                }
            }
        });
    }
}
