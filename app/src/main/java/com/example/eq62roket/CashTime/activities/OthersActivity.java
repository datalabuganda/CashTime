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
import com.example.eq62roket.CashTime.adapters.EducationAdapter;
import com.example.eq62roket.CashTime.adapters.OthersAdapter;
import com.example.eq62roket.CashTime.helper.DatabaseHelper;
import com.example.eq62roket.CashTime.helper.ExpenditureCrud;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Expenditure;
import com.example.eq62roket.CashTime.models.User;

import java.util.ArrayList;

public class OthersActivity extends AppCompatActivity {
    ExpenditureCrud expenditureCrud;
    EditText edtOthers;
    Button btnOthers;
    private static final String TAG = "OthersActivity";
    ListView OthersListView;
    UserCrud userCrud;
    OthersAdapter othersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        edtOthers = (EditText) findViewById(R.id.amtOthers);
        btnOthers = (Button) findViewById(R.id.btnOthers);
        expenditureCrud = new ExpenditureCrud(this);
        OthersListView = (ListView) findViewById(R.id.othersListView);

        userCrud = new UserCrud(this);

        ArrayList<Expenditure> othersArrayList = new ArrayList<>();
        othersArrayList = expenditureCrud.getAllOthers();

        othersAdapter = new OthersAdapter(this, R.layout.others_list_adapter, othersArrayList);
        OthersListView.setAdapter(othersAdapter);

        AddOthers();
//        populateListView();
    }

    public void AddOthers(){
        btnOthers.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (!edtOthers.getText().toString().equals("")) {
                            int yVal = Integer.parseInt(String.valueOf(edtOthers.getText()));
                            boolean isInseted = expenditureCrud.insertOthers(yVal);
                            if (isInseted) {
                                // if user spends on any expense, award them 2 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
                                user.setSyncStatus(0);
                                userCrud.updateUser(user);

                                Intent Othersintent = new Intent(OthersActivity.this, ExpenditureActivity.class);
                                OthersActivity.this.startActivity(Othersintent);
                                Toast.makeText(OthersActivity.this, "Your costs of other items have been stored", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else {
                                Toast.makeText(OthersActivity.this, "Other costs have not been stored", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(OthersActivity.this, "Please input amount before submitting", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );

    }

    private void populateListView(){
        Log.d(TAG, "populateListView: Displayng data in the listView");

        Cursor data = expenditureCrud.getOthers();
        Log.d(TAG, "here is data: " + data);
//        ArrayList<Income> salaryDataList = new ArrayList<>();
//        salaryDataList = (ArrayList<Income>) myHelper.getSalary();
//
//
//        SalaryAdapter salaryAdapterListAdapter = new SalaryAdapter(this, R.layout.salary_list_adapter, salaryDataList);
//        SalaryListVIew.setAdapter(salaryAdapterListAdapter);

        ArrayList<String> listData = new ArrayList<>();

        while (data.moveToNext()){
            listData.add(data.getString(data.getColumnIndex(DatabaseHelper.COLUMN_EXPENDITURE_OTHERS)));

        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        OthersListView.setAdapter(adapter);

        OthersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String Others = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on" + Others);

                Cursor data = expenditureCrud.getOthersID(Others);
                int OthersID = -1;
                while (data.moveToNext()){
                    OthersID = data.getInt(0);

                }
                if (OthersID > -1){
                    Intent editOthersIntent = new Intent(OthersActivity.this, UpdateOthersActivity.class);
                    editOthersIntent.putExtra("id", OthersID);
                    editOthersIntent.putExtra("Others", Others);
                    Log.d(TAG, "almost through: " + Others);
                    startActivity(editOthersIntent);
                    finish();

                }else {

                }
            }
        });
    }
}
