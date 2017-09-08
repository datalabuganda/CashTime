package com.example.eq62roket.CashTime.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.adapters.CustomGrid;
import com.example.eq62roket.CashTime.helper.ExpenditureCrud;
import com.example.eq62roket.CashTime.helper.GoalCrud;
import com.example.eq62roket.CashTime.helper.IncomeCrud;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.helper.VolleyHelper;
import com.example.eq62roket.CashTime.models.Goal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeDrawerActivity extends AppCompatActivity{

    String[] web = {
            "Income",
            "Expenditure",
            "Goals",
            "Reports",
            "Analytics",
            "Tips"
    } ;

    int[] imageId = {
            R.drawable.income,
            R.drawable.expenditure,
            R.drawable.goals,
            R.drawable.reports,
            R.drawable.analytics,
            R.drawable.tips
    };

    String[] values = new String[] { "IncomeActivity", "ExpenditureActivity", "GoalsListActivity", "TabbedReportActivity", "TabbedAnalysisActivity", "TipsActivity" };

    ImageView imgGoal, imgIncome, imgExpenditure, imgAnalytics, imgReports, imgTips;
    GridView grid;

//    ParseConnector parseConnector;
    VolleyHelper volleyHelper;
    UserCrud userCrud;
    GoalCrud goalCrud;
    IncomeCrud incomeCrud;
    ExpenditureCrud expenditureCrud;
    Goal goal;

    private static final String TAG = "HomeDrawertActivity";
    private static final String REQUIRED = "Required";

    private long userPoints;
    private int goal_amount;
    private int goal_saving;
    private  Date currentDate;
    private Date goalEndDate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_grid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        expenditureCrud = new ExpenditureCrud(this);
        goalCrud = new GoalCrud(this);

        goal = goalCrud.getLastInsertedGoal();

        goal_amount = goal.getAmount();
        goal_saving = expenditureCrud.addAllSavings(goal.getStartDate());
        int extraSavings = goal_saving - goal_amount;

        //get todays date and the end date of the current goal and parse them into java dates for comparison
        Calendar c = Calendar.getInstance();

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
                goalCrud.updateGoal(goal);
                Log.d(TAG, "goal status " + goalCrud.getLastInsertedGoal().getCompleteStatus());
                Log.d(TAG, "goal Amount " + goal.getAmount());
                Log.d(TAG, "goal saving " + goal_saving);

            }
        }
       // Log.d(TAG, "goal last saving inserted " + expenditureCrud.addAllSavings(null));

        imgGoal = (ImageView) findViewById(R.id.imgGoals);
        imgIncome = (ImageView) findViewById(R.id.imgIncome);
        imgExpenditure = (ImageView) findViewById(R.id.imgExpenditure);
        imgAnalytics = (ImageView) findViewById(R.id.imgAnalytics);
        imgTips = (ImageView) findViewById(R.id.imgTips);
        imgReports = (ImageView) findViewById(R.id.imgReport);


        CustomGrid adapter = new CustomGrid(HomeDrawerActivity.this, web, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String val = values[position];
                Class ourClass  = null;
                try {
                    ourClass = Class.forName("com.example.eq62roket.CashTime.activities."+val);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(HomeDrawerActivity.this, ourClass);
                startActivity(intent);
            }
        });



        if (currentDate.after( goalEndDate )) {

            NotificationCompat.Builder mBuilder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.goal1)
                            .setContentTitle("Goal Deadline ")
                            .setContentText("You were not able to complete your goal in time.")
                            .setAutoCancel(true);

            Intent resultIntent = new Intent(this, GoalsListActivity.class);

            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);


            // Sets an ID for the notification
            int mNotificationId = 1;

            // Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            // Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        userCrud = new UserCrud(this);
        volleyHelper = new VolleyHelper(this);
        goalCrud = new GoalCrud(this);
        incomeCrud = new IncomeCrud(this);
        expenditureCrud = new ExpenditureCrud(this);
        userPoints = userCrud.getLastUserInserted().getPoints();


        int userSyncStatus = userCrud.getLastUserInserted().getSyncStatus();
        String userPhpId = userCrud.getLastUserInserted().getPhpId();


        Log.d(TAG, "userSyncStatus: "+ userSyncStatus);
        if (userSyncStatus == 0) {
            if (userPhpId.equals("0")) {
                volleyHelper.sendUserData();
                Log.d(TAG, "user sending ");
            }
            else{
                volleyHelper.updateUserData();
                Log.d(TAG, "user updating ");
            }

        }

        int goalSyncStatus = goalCrud.getLastInsertedGoal().getSyncStatus();
        String goalPhpId = goalCrud.getLastInsertedGoal().getPhpId();

        if (goalSyncStatus == 0){
            if (goalPhpId.equals("0")){
                volleyHelper.sendGoalData();
                Log.d(TAG, "goal sending ");
            }
            else {
                volleyHelper.updateGoalData();
                Log.d(TAG, "goal updating ");
            }

        }

        int expenditureSyncStatus = expenditureCrud.getSyncStatus();
        int expenditurePhpId = expenditureCrud.getPhpID();

        if (expenditureSyncStatus == 0){
            if (expenditurePhpId == 0){
                volleyHelper.sendExpenditureData();
                Log.d(TAG, "expenditure sending ");
            }
            else{
                volleyHelper.updateExpenditureData();
                Log.d(TAG, "expenditure updating ");
            }
        }


        int incomeSyncStatus = incomeCrud.getSyncStatus();
        int incomePhpId = incomeCrud.getPhpID();


        if (incomeSyncStatus == 0){
            if (incomePhpId == 0){
                volleyHelper.sendIncomeData();
                Log.d(TAG, "income sending ");
            }
            else{
                volleyHelper.updateIncomeData();
                Log.d(TAG, "income updating ");
            }
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_drawer, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem myItem = menu.findItem(R.id.action_help);
        myItem.setTitle("Points: " + userPoints);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}
