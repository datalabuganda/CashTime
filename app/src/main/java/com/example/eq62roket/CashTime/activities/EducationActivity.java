package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.database.Cursor;
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
import com.example.eq62roket.CashTime.helper.ExpenditureCrud;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.User;

import java.util.ArrayList;

public class EducationActivity extends AppCompatActivity {
    private static final String TAG = "SavingsActivity";

    ExpenditureCrud expenditureCrud;
    EditText edtEducation;
    Button btnEducation;
    UserCrud userCrud;
    ListView EducationListVIew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EducationListVIew = (ListView) findViewById(R.id.educationListView);
        edtEducation = (EditText) findViewById(R.id.amtEducation);
        btnEducation = (Button) findViewById(R.id.btnEducation);
        expenditureCrud = new ExpenditureCrud(this);

        userCrud = new UserCrud(this);

        btnEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtEducation.getText().toString().equals("")) {
                    int yVal = Integer.parseInt(String.valueOf(edtEducation.getText()));
                    boolean isInseted = expenditureCrud.insertEducation(yVal);
                    if (isInseted) {
                        // if user spends on any expense, award them 2 points
                        User user = userCrud.getLastUserInserted();
                        user.setPoints(2);
                        user.setSyncStatus(0);
                        userCrud.updateUser(user);

                        Intent Educationintent = new Intent(EducationActivity.this, ExpenditureActivity.class);
                        EducationActivity.this.startActivity(Educationintent);
                        Toast.makeText(EducationActivity.this, "Education costs have been stored", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(EducationActivity.this, "Education costs where not stored", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(EducationActivity.this, "Please input amount before submitting", Toast.LENGTH_LONG).show();
                }

            }
        });

        populateListView();

    }

    private void populateListView(){
        Log.d(TAG, "populateListView: Displayng data in the listView");

        Cursor data = expenditureCrud.getEducation();
        Log.d(TAG, "here is data: " + data);
//        ArrayList<Income> salaryDataList = new ArrayList<>();
//        salaryDataList = (ArrayList<Income>) myHelper.getSalary();
//
//
//        SalaryAdapter salaryAdapterListAdapter = new SalaryAdapter(this, R.layout.salary_list_adapter, salaryDataList);
//        SalaryListVIew.setAdapter(salaryAdapterListAdapter);

        ArrayList<String> listData = new ArrayList<>();

        while (data.moveToNext()){
            listData.add(data.getString(data.getColumnIndex(DatabaseHelper.COLUMN_EXPENDITURE_EDUCATION)));

        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        EducationListVIew.setAdapter(adapter);

        EducationListVIew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String education = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on" + education);

                Cursor data = expenditureCrud.getEducationID(education);
                int educationID = -1;
                while (data.moveToNext()){
                    educationID = data.getInt(0);

                }
                if (educationID > -1){
                    Intent editEducationIntent = new Intent(EducationActivity.this, UpdateEducationActivity.class);
                    editEducationIntent.putExtra("id", educationID);
                    editEducationIntent.putExtra("Education", education);
                    Log.d(TAG, "almost through: " + education);
                    startActivity(editEducationIntent);
                    finish();

                }else {

                }
            }
        });
       }

}
