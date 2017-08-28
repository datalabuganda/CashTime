package com.example.eq62roket.CashTime.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.GoalCrud;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Expenditure;
import com.example.eq62roket.CashTime.models.Goal;
import com.example.eq62roket.CashTime.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddGoalActivity extends AppCompatActivity {

    private static final String TAG = "AddGoalActivity";

    EditText etGoalName, etGoalAmount, etChooseDate;
    Button btnAddGoal;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    private GoalCrud goalCrud;
    private UserCrud userCrud;
    private Goal goal;

    private  Date currentDate;
    private Date goalEndDate;

    SQLiteHelper sqLiteHelper;

    SimpleDateFormat simpleDateFormat;

    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        etGoalName = (EditText) findViewById(R.id.etGoalName);
        etGoalAmount = (EditText) findViewById(R.id.etGoalAmount);
        etChooseDate = (EditText) findViewById(R.id.etChooDate);
        btnAddGoal = (Button) findViewById(R.id.btnAddGoal);

        userCrud = new UserCrud(this);
        goalCrud = new GoalCrud(this);
        goal = new Goal();
        sqLiteHelper = new SQLiteHelper(this);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        //get todays date and the end date of the current goal and parse them into java dates for comparison
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
        String formattedDate = df.format(c.getTime());

        try {
            currentDate = df.parse(formattedDate);

            Goal last_inserted = goalCrud.getLastInsertedGoal();
            goalEndDate = null;
            if(last_inserted != null){
                goalEndDate = df.parse(last_inserted.getEndDate());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }



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
                    String goal_name = goalName.toString();
                    int goal_amount = Integer.parseInt(goalAmount.toString());
                    String start_date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date());
                    String end_date = chooseDate.toString();
                    User user = userCrud.getLastUserInserted();

                    // if user creates goal for the first time, award them 3 points
                    user.setPoints(3);
                    userCrud.updateUser(user);

                    goal.setName(goal_name);
                    goal.setAmount(goal_amount);
                    //goal.setStartDate(start_date);
                    goal.setEndDate(end_date);
                    goal.setSyncStatus(0);
                    goal.setUser(user);

                   // Log.d(TAG, "start date  "+ goal.getStartDate());

                    if (goalCrud.getLastInsertedGoal() != null){
                        Goal goalLastInserted = goalCrud.getLastInsertedGoal();
                        if (
                                goalLastInserted.getCompleteStatus() == 1 || (goal.getCompleteStatus() == 0 && currentDate.after(goalEndDate))  ){
                            int goalLastInsertedAmount = goalLastInserted.getAmount();
                            int goalLastInsertedSavings = sqLiteHelper.addAllSavings(goalLastInserted.getStartDate()) + goalLastInserted.getSurplus();

                            //under normal circumstances ie the goal was completed..this is our surplus
                            int goalLastInsertedSurplus = goalLastInsertedSavings - goalLastInsertedAmount;

                            //if the last goal was failed, the whole saved amount becomes the next surplus ie we don't subtract the goal's amount
                            if(  goalEndDate != null && (goal.getCompleteStatus() == 0 && currentDate.after(goalEndDate))  ) {
                                goalLastInsertedSurplus += goalLastInsertedAmount;
                            }
                            Log.d(TAG, " goalLastInsertedSurplus" + goalLastInsertedSurplus);

                            if (goalLastInsertedSurplus < 0){
                                goalLastInsertedSurplus = 0;
                            }
                            goal.setSurplus(goalLastInsertedSurplus);

                        }
                    }
                    else {
                        goal.setSurplus(0);
                    }

                    goalCrud.createGoal(goal);
                    //Log.d(TAG, "new goal start date: "+ goalCrud.getLastInsertedGoal().getStartDate());

                    Intent intent = new Intent(AddGoalActivity.this, HomeDrawerActivity.class);
                    Toast.makeText(AddGoalActivity.this, "Goal added successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();

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
