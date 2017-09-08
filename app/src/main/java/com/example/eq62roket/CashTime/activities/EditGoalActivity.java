package com.example.eq62roket.CashTime.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.GoalCrud;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Goal;
import com.example.eq62roket.CashTime.models.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class EditGoalActivity extends AppCompatActivity {

    private static final String TAG = "EditGoalActivity";

    EditText etGoalName, etGoalAmount, etChooseDate;
    Button btnUpdateGoal;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    Goal goal;

    private GoalCrud goalCrud;
    private UserCrud userCrud;

    SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goal);

        etGoalName =(EditText) findViewById(R.id.etGoalName);
        etGoalAmount = (EditText) findViewById(R.id.etGoalAmount);
        etChooseDate = (EditText) findViewById(R.id.etChooseDate);
        btnUpdateGoal = (Button) findViewById(R.id.btnUpdateGoal);

        userCrud = new UserCrud(this);
        goalCrud = new GoalCrud(this);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // Receive Data from Intent
        Intent intent = this.getIntent();
        final long goal_id = intent.getExtras().getLong("GOAL_ID");
        String goal_name = intent.getExtras().getString("GOAL_NAME");
        int goal_amount = intent.getExtras().getInt("GOAL_AMOUNT");
        String goal_enddate = intent.getExtras().getString("GOAL_ENDDATE");

        final String goal_startdate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        final User goal_user = userCrud.getLastUserInserted();

        etGoalName.setText(goal_name);
        etGoalAmount.setText("" + goal_amount);
        etChooseDate.setText(goal_enddate);



//        // update the database
//        goalCrud.updateGoal(goal);

        btnUpdateGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String new_goal_name = String.valueOf(etGoalName.getText());
                int new_goal_amount = Integer.parseInt(etGoalAmount.getText().toString());
                String new_goal_enddate = String.valueOf(etChooseDate.getText());

                String goalPhpId = goalCrud.getLastInsertedGoal().getPhpId();


                // create a goal instance with the above information
                goal = new Goal(goal_id, new_goal_name,  new_goal_amount, goal_startdate, new_goal_enddate, goal_user, goalPhpId);
                goal.setSyncStatus(0);
                // update the database
                goalCrud.updateGoal(goal);

                Intent updateIntent = new Intent(EditGoalActivity.this, GoalsListActivity.class);
                Toast.makeText(EditGoalActivity.this, "Goal updated to " + goal.getName(),  Toast.LENGTH_SHORT).show();
                startActivity(updateIntent);
                finish();
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
