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

public class OtherIncomesActivity extends AppCompatActivity {

    private static final String TAG = "OtherIncomesActivity";
    EditText edtOthers;
    Button btnOthers;
    IncomeCrud incomeCrud;
    UserCrud userCrud;
    ListView othersListVIew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_incomes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnOthers = (Button) findViewById(R.id.btnOthers);
        edtOthers = (EditText) findViewById(R.id.edtOthers);
        othersListVIew = (ListView) findViewById(R.id.otherIncomeListView);

        incomeCrud = new IncomeCrud(this);

        userCrud = new UserCrud(this);

        AddOthers();
        populateListView();
    }

    public void AddOthers(){
        btnOthers.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!edtOthers.getText().toString().equals("")) {
                            int yVal = Integer.parseInt(String.valueOf(edtOthers.getText()));
                            boolean isInseted = incomeCrud.insertOthers(yVal);

                            if (isInseted) {
                                // if user adds income, award them 2 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
                                user.setSyncStatus(0);
                                userCrud.updateUser(user);

                                Toast.makeText(OtherIncomesActivity.this, "Your income has been added", Toast.LENGTH_LONG).show();
                                Intent OtherIncomeintent = new Intent(OtherIncomesActivity.this, IncomeActivity.class);
                                OtherIncomesActivity.this.startActivity(OtherIncomeintent);
                                finish();
                            }
                            else {
                                Toast.makeText(OtherIncomesActivity.this, "Your income has not been added", Toast.LENGTH_LONG).show();
                            }

                        }
                        else
                            Toast.makeText(OtherIncomesActivity.this, "Please input amount before submitting", Toast.LENGTH_LONG).show();

                    }

                }
        );
    }

    private void populateListView(){
        Log.d(TAG, "populateListView: Displayng data in the listView");

        Cursor data = incomeCrud.getOthers();
        Log.d(TAG, "here is data: " + data);
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(data.getColumnIndex(DatabaseHelper.COLUMN_INCOME_OTHERS)));

        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        othersListVIew.setAdapter(adapter);

        othersListVIew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String others = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on" + others);

                Cursor data = incomeCrud.getOthersID(others);
                int othersID = -1;
                while (data.moveToNext()){
                    othersID = data.getInt(0);

                }
                if (othersID > -1){
                    Intent editOthersIntent = new Intent(OtherIncomesActivity.this, UpdateOtherIncomesActivity.class);
                    editOthersIntent.putExtra("id", othersID);
                    editOthersIntent.putExtra("others", others);
                    Log.d(TAG, "almost through: " + others);
                    startActivity(editOthersIntent);
                    finish();
                }else {

                }
            }
        });
    }



}
