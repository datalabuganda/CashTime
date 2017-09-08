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
import com.example.eq62roket.CashTime.helper.GoalCrud;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Goal;
import com.example.eq62roket.CashTime.models.User;

import java.util.ArrayList;

public class SavingsActivity extends AppCompatActivity {

    private static final String TAG = "SavingsActivity";

    SQLiteHelper myHelper;
    UserCrud userCrud;
    EditText edtSavings;
    Button btnSavings;

    ListView SavingsListView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        edtSavings = (EditText) findViewById(R.id.amtSavings);
        btnSavings = (Button) findViewById(R.id.btnSavings);
        SavingsListView = (ListView) findViewById(R.id.savingsListView);
        myHelper = new SQLiteHelper(this);

        userCrud = new UserCrud(this);




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnSavings.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!edtSavings.getText().toString().equals("")){
                            int yVal = Integer.parseInt(String.valueOf(edtSavings.getText()));
                            boolean isInseted = myHelper.insertSavings(yVal);
                            if (isInseted) {
                                // if user adds a saving, award them 5 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(5);
                                userCrud.updateUser(user);

                               // Log.d(TAG, "goal status " + goal.getCompleteStatus());


                                Toast.makeText(SavingsActivity.this, "Your savings have been stored", Toast.LENGTH_LONG).show();
                                Intent Savingsintent = new Intent(SavingsActivity.this, ExpenditureActivity.class);
                                SavingsActivity.this.startActivity(Savingsintent);
                                finish();
                                //Log.d(TAG, "goal saved " + myHelper.addAllSavings(null));
                            }
                            else {
                                Toast.makeText(SavingsActivity.this, "Your savings have not been stored", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(SavingsActivity.this, "Please input amount before submitting", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );
        populateListView();
    }

    private void populateListView(){
        Log.d(TAG, "populateListView: Displayng data in the listView");

        Cursor data = myHelper.getSavings();
        Log.d(TAG, "here is data: " + data);
//        ArrayList<Income> salaryDataList = new ArrayList<>();
//        salaryDataList = (ArrayList<Income>) myHelper.getSalary();
//
//
//        SalaryAdapter salaryAdapterListAdapter = new SalaryAdapter(this, R.layout.salary_list_adapter, salaryDataList);
//        SalaryListVIew.setAdapter(salaryAdapterListAdapter);

        ArrayList<String> listData = new ArrayList<>();

        while (data.moveToNext()){
            listData.add(data.getString(data.getColumnIndex(myHelper.COL_8)));

        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        SavingsListView.setAdapter(adapter);

        SavingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String Savings = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on" + Savings);

                Cursor data = myHelper.getSavingsID(Savings);
                int SavingsID = -1;
                while (data.moveToNext()){
                    SavingsID = data.getInt(0);

                }
                if (SavingsID > -1){
                    Intent editSavingsIntent = new Intent(SavingsActivity.this, UpdateSavingsActivity.class);
                    editSavingsIntent.putExtra("id", SavingsID);
                    editSavingsIntent.putExtra("Savings", Savings);
                    Log.d(TAG, "almost through: " + Savings);
                    startActivity(editSavingsIntent);
                    finish();

                }else {

                }
            }
        });
    }

}
