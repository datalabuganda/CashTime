package com.cashtime.cashtimefinal;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import java.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddGoalActivity extends AppCompatActivity {

    EditText etGoalName, etGoalAmount, etChooseDate;
    Button btnAddGoal;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        etGoalName = (EditText) findViewById(R.id.etGoalName);
        etGoalAmount = (EditText) findViewById(R.id.etGoalAmount);
        etChooseDate = (EditText) findViewById(R.id.etChooDate);
        btnAddGoal = (Button) findViewById(R.id.btnAddGoal);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        btnAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Editable goalName = etGoalName.getText();
                Editable goalAmount = etGoalAmount.getText();
                Editable chooseDate = etChooseDate.getText();

                if (!TextUtils.isEmpty(goalName) &&
                        !TextUtils.isEmpty(goalAmount) &&
                        !TextUtils.isEmpty(chooseDate))
                {
                    Intent intent = new Intent(AddGoalActivity.this, HomeActivity.class);
                    startActivity(intent);
                    Toast.makeText(AddGoalActivity.this, "Goal added successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AddGoalActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @SuppressWarnings("deprecation")
    public void setDate(View view){
        showDialog(999);
        // Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if (id == 999){
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            showDate(i, i1 + 1, i2);
        }
    };

    private void showDate(int year, int month, int day){
        etChooseDate.setText(new StringBuilder().append(day).append("/")
        .append(month).append("/").append(year));
    }
}
