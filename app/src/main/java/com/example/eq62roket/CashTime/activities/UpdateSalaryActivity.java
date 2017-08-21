package com.example.eq62roket.CashTime.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.GoalCrud;
import com.example.eq62roket.CashTime.helper.IncomeCrud;
import com.example.eq62roket.CashTime.helper.IncomeSQLiteHelper;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Goal;
import com.example.eq62roket.CashTime.models.Income;

import java.util.ArrayList;

public class SalaryListActivity extends AppCompatActivity {
    ListView salaryList;
    IncomeSQLiteHelper myHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_list);

        salaryList = (ListView) findViewById(R.id.salaryListView);
        myHelper = new IncomeSQLiteHelper(this);


//        ArrayList<String> theSalary = new ArrayList<>();
//        Cursor data = myHelper.selectSalary();
//
//        if (data.getCount() == 0){
//            Toast.makeText(SalaryListActivity.this,"You have not entered your salary yet", Toast.LENGTH_LONG ).show();
//        }else {
//            while (data.moveToNext()){
//                theSalary.add(data.getString(2));
//                theSalary.add(data.getString(3));
//                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, theSalary);
//                salaryList.setAdapter(listAdapter);
//            }
//        }


    }
}
