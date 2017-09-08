package com.example.eq62roket.CashTime.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eq62roket.CashTime.adapters.LoanAdapter;
import com.example.eq62roket.CashTime.adapters.SalaryAdapter;
import com.example.eq62roket.CashTime.helper.IncomeSQLiteHelper;
import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.User;

import java.util.ArrayList;

import static com.google.android.gms.internal.a.G;

public class SalaryActivity extends AppCompatActivity {
    private static final String TAG = "SalaryActivity";
    Button btnSalary, updateSalary;
    EditText edtSalary;
    IncomeSQLiteHelper myHelper;
    UserCrud userCrud;
    ListView SalaryListVIew;
    SalaryAdapter salaryAdapter;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSalary = (Button) findViewById(R.id.btnSalary);
        edtSalary = (EditText) findViewById(R.id.edtSalary);
        SalaryListVIew = (ListView) findViewById(R.id.SalaryListView);

        myHelper = new IncomeSQLiteHelper(this);

        userCrud = new UserCrud(this);


//        updateSalary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent updateIntent = new Intent(SalaryActivity.this, UpdateSalaryActivity.class);
//                startActivity(updateIntent);
//            }
//        });

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
                            boolean isInseted = myHelper.insertSalary(yVal);
                            if (isInseted) {
                                // if user adds income, award them 2 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
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

        Cursor data = myHelper.getSalary();
        salaryAdapter = new SalaryAdapter(this, data);
        SalaryListVIew.setAdapter(salaryAdapter);

        SalaryListVIew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor item = (Cursor) parent.getItemAtPosition(position);

                view.setSelected(true);
                salaryAdapter.notifyDataSetChanged();
//                G.t(getActivity(), "Now working with category: " + item.getName());
            }
        });

//        Log.d(TAG, "here is data: " + data);
//        ArrayList<String> listData = new ArrayList<>();
//        while (data.moveToNext()){
//            listData.add(data.getString(data.getColumnIndex(myHelper.COL_3)));
//
//        }
//        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
//        SalaryListVIew.setAdapter(adapter);

//        SalaryListVIew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
//                Cursor c = (Cursor) arg0.getItemAtPosition(arg2);
////                Log.d(TAG, "onItemClick: You clicked on" + salary);
////
////                Cursor data = myHelper.getSalaryID(salary);
////                int salaryID = -1;
////                while (data.moveToNext()){
////                    salaryID = data.getInt(0);
//
//                }
////                if (salaryID > -1){
////                    Intent editSalaryIntent = new Intent(SalaryActivity.this, UpdateSalaryActivity.class);
////                    editSalaryIntent.putExtra("id", salaryID);
////                    editSalaryIntent.putExtra("salary", salary);
////                    Log.d(TAG, "almost through: " + salary);
////                    startActivity(editSalaryIntent);
////                    finish();
////
////                }else {
////
////                }
////            }
//        });
    }
}
