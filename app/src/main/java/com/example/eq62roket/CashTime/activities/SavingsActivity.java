package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.adapters.SavingsAdapter;
import com.example.eq62roket.CashTime.helper.ExpenditureCrud;
import com.example.eq62roket.CashTime.helper.GoalCrud;
import com.example.eq62roket.CashTime.helper.IncomeCrud;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.helper.VolleyHelper;
import com.example.eq62roket.CashTime.models.Expenditure;
import com.example.eq62roket.CashTime.models.Goal;
import com.example.eq62roket.CashTime.models.User;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.eq62roket.CashTime.R.id.txtRemainingIncome;

public class SavingsActivity extends AppCompatActivity {

    private static final String TAG = "SavingsActivity";

    ExpenditureCrud expenditureCrud;
    UserCrud userCrud;
    EditText edtSavings;
    Button btnSavings;
    GoalCrud goalCrud;
    Goal goal;
    IncomeCrud incomeCrud;

    private Date currentDate;
    private Date goalEndDate;

    ListView SavingsListView;
    SavingsAdapter savingsAdapter;

    DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        edtSavings = (EditText) findViewById(R.id.amtSavings);
        btnSavings = (Button) findViewById(R.id.btnSavings);

        expenditureCrud = new ExpenditureCrud(this);
        SavingsListView = (ListView) findViewById(R.id.savingsListView);

        ArrayList<Expenditure> savingsArrayList = new ArrayList<>();
        savingsArrayList = expenditureCrud.getAllSavings();

        savingsAdapter = new SavingsAdapter(this, R.layout.savings_list_adapter, savingsArrayList);
        SavingsListView.setAdapter(savingsAdapter);
        formatter = new DecimalFormat("#,###,###");

        userCrud = new UserCrud(this);
        goalCrud = new GoalCrud(this);
        incomeCrud = new IncomeCrud(this);

        goal = goalCrud.getLastInsertedGoal();

        final int remaining = this.remainingIncome();

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
        String formattedDate = df.format(c.getTime());

        try {
            currentDate = df.parse(formattedDate);
            goalEndDate = df.parse(goal.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnSavings.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!edtSavings.getText().toString().equals("")) {
                            int yVal = Integer.parseInt(String.valueOf(edtSavings.getText()));

                            if (yVal <= remaining) {
                                boolean isInseted = expenditureCrud.insertSavings(yVal);
//                                if (isInseted) {
//                                    // if user adds a saving, award them 5 points
//                                    User user = userCrud.getLastUserInserted();
//                                    user.setPoints(5);
//                                    user.setSyncStatus(0);
//                                    Log.d(TAG, "expenditurephpId: "+ expenditureCrud.getPhpID());
//                                    Log.d(TAG, "expendituresync status: "+ expenditureCrud.getSyncStatus());
//                                    userCrud.updateUser(user);
//
//                                    Toast.makeText(SavingsActivity.this, "Your savings have been stored", Toast.LENGTH_LONG).show();
//                                    Intent Savingsintent = new Intent(SavingsActivity.this, ExpenditureActivity.class);
//                                    SavingsActivity.this.startActivity(Savingsintent);
//                                    finish();
//                                    //Log.d(TAG, "goal saved " + incomeCrud.addAllSavings(null));
//                                }
//                                else {
//                                    Toast.makeText(SavingsActivity.this, "Your savings have not been stored", Toast.LENGTH_LONG).show();
//                                }
////=======
//                            boolean isInseted = expenditureCrud.insertSavings(yVal);

                                if (isInseted) {
                                    // if user adds a saving, award them 5 points
                                    User user = userCrud.getLastUserInserted();
                                    user.setPoints(5);
                                    user.setSyncStatus(0);
                                    Log.d(TAG, "expenditurephpId: " + expenditureCrud.getPhpID());
                                    Log.d(TAG, "expendituresync status: " + expenditureCrud.getSyncStatus());
                                    userCrud.updateUser(user);

                                    //sync goal with server if internet is availabe
                                    SavingsActivity.setGoalActualCompleteDate(goal, goalCrud, expenditureCrud);
                                    new VolleyHelper(SavingsActivity.this).updateGoalData(goal.getParseId());


                                    Toast.makeText(SavingsActivity.this, "Your savings have been stored", Toast.LENGTH_LONG).show();
                                    Intent Savingsintent = new Intent(SavingsActivity.this, ExpenditureActivity.class);
                                    SavingsActivity.this.startActivity(Savingsintent);
                                    finish();
                                    //Log.d(TAG, "goal saved " + incomeCrud.addAllSavings(null));
                                } else {
                                    Toast.makeText(SavingsActivity.this, "Your don't have enough income to save", Toast.LENGTH_LONG).show();

                                }

                            }

                        }
                        else {
                            Toast.makeText(SavingsActivity.this, "Please input amount before submitting", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );

        remainingIncome();
    }

    public int remainingIncome(){
        int totalIncome = incomeCrud.addAllIncome();
        int totalExpenditure = expenditureCrud.addAllCategories();

        int remainingIncome = totalIncome - totalExpenditure;
        return remainingIncome;

    }
    public static void setGoalActualCompleteDate(Goal goal, GoalCrud goalCrud, ExpenditureCrud expenditureCrud){

        Date currentDate = null;
        Date goalEndDate = null;

        int goal_amount = goal.getAmount();
        int goal_saving = expenditureCrud.addAllSavings(goal.getStartDate());

        Calendar c = Calendar.getInstance();
        //System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
        String formattedDate = df.format(c.getTime());

        try {
            currentDate = df.parse(formattedDate);
            goalEndDate = df.parse(goal.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // set goal complete status only if its active
        if(  goal.getCompleteStatus() == 0 && currentDate.before(goalEndDate)  ) {
            if ((goal_saving + goal.getSurplus()) >= goal_amount) {
                goal.setCompleteStatus(1);
                Log.d(TAG, "goal status " + goalCrud.getLastInsertedGoal().getCompleteStatus());
                Log.d(TAG, "goal Amount " + goal.getAmount());
                Log.d(TAG, "goal saving " + goal_saving);

            }
        }
        if(  goal.getCompleteStatus() == 1 && currentDate.before(goalEndDate)  ) {
            //Log.d(TAG, "onCreate actualCompletionDate: " + actualCompletionDate);
            goal.setActualCompletionDate(df.format(currentDate));
            goal.setSyncStatus(0);
            Log.d(TAG, "goal actualCompletionDate: " + goalCrud.getLastInsertedGoal().getActualCompletionDate());


        }
        goalCrud.updateGoal(goal);
        //Log.d(TAG, "goal after update actualCompletionDate: " + goal.getActualCompletionDate());
        //Log.d(TAG, "goal actualCompletionDate: " + goalCrud.getLastInsertedGoal().getActualCompletionDate());
        //Log.d(TAG, "goal actualCompletionDate: " + goalCrud.getLastInsertedGoal().getCompleteStatus());

    }

}
