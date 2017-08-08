package com.example.eq62roket.CashTime.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;

public class MainActivity extends AppCompatActivity {

    SQLiteHelper myHelper;
    EditText edtTransport, edtEducation, edtHealth, edtSavings, edtOthers;
    Button btnTransport, btnEducation, btnHealth, btnSavings, btnOthers, btnSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        edtEducation = (EditText) findViewById(R.id.edtEducation);
//        edtTransport = (EditText) findViewById(R.id.edtTransport);
//        edtHealth = (EditText) findViewById(R.id.edtHealth);
//        edtSavings = (EditText) findViewById(R.id.edtSavings);
//        edtOthers = (EditText) findViewById(R.id.edtLoans);
//        btnEducation = (Button) findViewById(R.id.btnEducation);
//        btnTransport = (Button) findViewById(R.id.btnTransport);
//        btnHealth = (Button) findViewById(R.id.btnHealth);
//        btnSavings = (Button) findViewById(R.id.btnSavings);
//        btnOthers = (Button) findViewById(R.id.btnLoans);
//        btnSum = (Button) findViewById(R.id.btnSum);
//
//        myHelper = new SQLiteHelper(this);
//
//        btnSum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent SumIntent = new Intent(MainActivity.this, SumsActivity.class);
//                MainActivity.this.startActivity(SumIntent);
//            }
//        });
//
//        AddEducation();
//        AddTransport();
//        AddSavings();
//        AddHealth();
//        AddOthers();
    }
//
//    public void AddTransport(){
//        btnTransport.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int yVal = Integer.parseInt(String.valueOf(edtTransport.getText()));
//                        boolean isInseted = myHelper.insertTransport(yVal);
//                        if (isInseted = true)
//                            Toast.makeText(MainActivity.this, "Goal is inserted", Toast.LENGTH_LONG).show();
//                        else
//                            Toast.makeText(MainActivity.this, "Goal is not inserted", Toast.LENGTH_LONG).show();
//                        //Intent Homeintent = new Intent(GoalCreateActivity.this, HomeActivity.class);
//                        //GoalCreateActivity.this.startActivity(Homeintent);
//                    }
//
//                }
//        );
//    }
//
//    public void AddEducation(){
//        btnEducation.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int yVal = Integer.parseInt(String.valueOf(edtEducation.getText()));
//                        boolean isInseted = myHelper.insertEducation(yVal);
//                        if (isInseted = true)
//                            Toast.makeText(MainActivity.this, "Goal is inserted", Toast.LENGTH_LONG).show();
//                        else
//                            Toast.makeText(MainActivity.this, "Goal is not inserted", Toast.LENGTH_LONG).show();
//                        //Intent Homeintent = new Intent(GoalCreateActivity.this, HomeActivity.class);
//                        //GoalCreateActivity.this.startActivity(Homeintent);
//                    }
//
//                }
//        );
//    }
//
//    public void AddHealth(){
//        btnHealth.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int yVal = Integer.parseInt(String.valueOf(edtHealth.getText()));
//                        boolean isInseted = myHelper.insertHealth(yVal);
//                        if (isInseted = true)
//                            Toast.makeText(MainActivity.this, "Goal is inserted", Toast.LENGTH_LONG).show();
//                        else
//                            Toast.makeText(MainActivity.this, "Goal is not inserted", Toast.LENGTH_LONG).show();
//                        //Intent Homeintent = new Intent(GoalCreateActivity.this, HomeActivity.class);
//                        //GoalCreateActivity.this.startActivity(Homeintent);
//                    }
//
//                }
//        );
//    }
//
//    public void AddSavings(){
//        btnSavings.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int yVal = Integer.parseInt(String.valueOf(edtSavings.getText()));
//                        boolean isInseted = myHelper.insertSavings(yVal);
//                        if (isInseted = true)
//                            Toast.makeText(MainActivity.this, "Goal is inserted", Toast.LENGTH_LONG).show();
//                        else
//                            Toast.makeText(MainActivity.this, "Goal is not inserted", Toast.LENGTH_LONG).show();
//                        //Intent Homeintent = new Intent(GoalCreateActivity.this, HomeActivity.class);
//                        //GoalCreateActivity.this.startActivity(Homeintent);
//                    }
//
//                }
//        );
//    }
//
//
//
//    public void AddOthers(){
//        btnOthers.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int yVal = Integer.parseInt(String.valueOf(edtOthers.getText()));
//                        boolean isInseted = myHelper.insertOthers(yVal);
//                        if (isInseted = true)
//                            Toast.makeText(MainActivity.this, "Goal is inserted", Toast.LENGTH_LONG).show();
//                        else
//                            Toast.makeText(MainActivity.this, "Goal is not inserted", Toast.LENGTH_LONG).show();
//                        //Intent Homeintent = new Intent(GoalCreateActivity.this, HomeActivity.class);
//                        //GoalCreateActivity.this.startActivity(Homeintent);
//                    }
//
//                }
//        );
//    }

}
