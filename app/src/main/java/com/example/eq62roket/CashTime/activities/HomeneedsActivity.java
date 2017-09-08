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

public class HomeneedsActivity extends AppCompatActivity {

    SQLiteHelper myHelper;
    EditText edtHomeneeds;
    Button btnHomeneeds;
    UserCrud userCrud;
    ListView HomeneedsListView;
    private static final String TAG = "SavingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeneeds);

        edtHomeneeds = (EditText) findViewById(R.id.amtHomeneeds);
        btnHomeneeds = (Button) findViewById(R.id.btnHomeneeds);
        HomeneedsListView = (ListView) findViewById(R.id.HomeneedslistView);
        myHelper = new SQLiteHelper(this);

        userCrud = new UserCrud(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userCrud = new UserCrud(this);

        AddHomeneeds();
        populateListView();

    }

    public void AddHomeneeds(){
        btnHomeneeds.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                                // if user creates goal for the first time, award them 3 points
                        if (!edtHomeneeds.getText().toString().equals("")) {
                            int yVal = Integer.parseInt(String.valueOf(edtHomeneeds.getText()));
                            boolean isInseted = myHelper.insertHomeneeds(yVal);
                            if (isInseted) {
                                // if user spends on any expense, award them 2 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
                                userCrud.updateUser(user);

                                Toast.makeText(HomeneedsActivity.this, "Your Home needs have been stored", Toast.LENGTH_LONG).show();
                                Intent Homeneeds = new Intent(HomeneedsActivity.this, ExpenditureActivity.class);
                                HomeneedsActivity.this.startActivity(Homeneeds);
                                finish();
                            } else {
                                Toast.makeText(HomeneedsActivity.this, "Your Home needs have not been stored", Toast.LENGTH_LONG).show();
                            }
                        }
                            else {
                                Toast.makeText(HomeneedsActivity.this, "Your home needs costs where not stored", Toast.LENGTH_LONG).show();
                            }


                    }

                }
        );
    }

    private void populateListView(){
        Log.d(TAG, "populateListView: Displayng data in the listView");

        Cursor data = myHelper.getHomeneeds();
        Log.d(TAG, "here is data: " + data);
//        ArrayList<Income> salaryDataList = new ArrayList<>();
//        salaryDataList = (ArrayList<Income>) myHelper.getSalary();
//
//
//        SalaryAdapter salaryAdapterListAdapter = new SalaryAdapter(this, R.layout.salary_list_adapter, salaryDataList);
//        SalaryListVIew.setAdapter(salaryAdapterListAdapter);

        ArrayList<String> listData = new ArrayList<>();

        while (data.moveToNext()){
            listData.add(data.getString(data.getColumnIndex(myHelper.COL_11)));

        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        HomeneedsListView.setAdapter(adapter);

        HomeneedsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String Homeneeds = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on" + Homeneeds);

                Cursor data = myHelper.getHomeneedsID(Homeneeds);
                int HomeneedsID = -1;
                while (data.moveToNext()){
                    HomeneedsID = data.getInt(0);

                }
                if (HomeneedsID > -1){
                    Intent editHomeneedsIntent = new Intent(HomeneedsActivity.this, UpdateHomeneedsActivity.class);
                    editHomeneedsIntent.putExtra("id", HomeneedsID);
                    editHomeneedsIntent.putExtra("Homeneeds", Homeneeds);
                    Log.d(TAG, "almost through: " + Homeneeds);
                    startActivity(editHomeneedsIntent);
                    finish();

                }else {

                }
            }
        });
    }

}
