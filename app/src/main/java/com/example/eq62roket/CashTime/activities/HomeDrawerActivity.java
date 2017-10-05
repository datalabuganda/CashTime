package com.example.eq62roket.CashTime.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
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
import com.example.eq62roket.CashTime.helper.ParseConnector;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Goal;
import com.example.eq62roket.CashTime.models.User;
import com.parse.Parse;

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
    ParseConnector parseConnector;
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
    private String actualCompletionDate;

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
            actualCompletionDate = df.format(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if(  goal.getSyncStatus() == 0){
            SavingsActivity.setGoalActualCompleteDate(goal, goalCrud, expenditureCrud);
        }

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
        parseConnector = new ParseConnector(this);

        goalCrud = new GoalCrud(this);
        incomeCrud = new IncomeCrud(this);
        expenditureCrud = new ExpenditureCrud(this);
        userPoints = userCrud.getLastUserInserted().getPoints();


        // server url
        String server_url = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            server_url = "http://oxfamdataservice.org/parse/";
        }
        else {
            server_url = "https://oxfamdataservice.org/parse/";
        }

        // Initialise a Parse Connection
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("462s45ze2vn6x2vrfyfenqmksngx5xbs")
                .server(server_url)
                .build()
        );

        User lastInsertedUser = userCrud.getLastUserInserted();
        int userSyncStatus = lastInsertedUser.getSyncStatus();
        String userParseId = lastInsertedUser.getParseId();

        Log.d(TAG, "userSyncStatus: "+ userSyncStatus);
        Log.d(TAG, "userParseId: " + userParseId);
        // check if last inserted user's information has already synced
        if (userSyncStatus == 0) {
            Log.d(TAG, "userSyncStatus: " + userSyncStatus);
            Log.d(TAG, "userParseId: " + userParseId);
            if (userParseId == null) {
                parseConnector.addUserToParse();
                Log.d(TAG, "user sending ");
            }
            else{
                parseConnector.upDateUser(userParseId);
                lastInsertedUser.setSyncStatus(1);
                userCrud.updateUser(lastInsertedUser);
                Log.d(TAG, "user updating ");
            }

            Log.d(TAG, "userSyncStatus: " + lastInsertedUser.getSyncStatus());

        }

        goal = goalCrud.getLastInsertedGoal();
        int goalSyncStatus = goal.getSyncStatus();
        String goalParseId = goal.getParseId();
        String actualCompletionDate = goal.getActualCompletionDate();
        Log.d(TAG, "actualCompletionDate: " + actualCompletionDate);
        Log.d(TAG, "goalParseId: " + goalParseId);

        Log.d(TAG, "goalSyncStatus: " + goalSyncStatus);
        if (goalSyncStatus == 0){
            if (goalParseId == null){
                parseConnector.addGoalToParse();
                Log.d(TAG, "goal sending ");
            }
            else {
                parseConnector.upDateGoal(goalParseId);
                goal.setSyncStatus(1);
                goalCrud.updateGoal(goal);
            }

        }

        // send expenditure details to server with an internet connection on device.
        int expenditureSyncStatus = expenditureCrud.getSyncStatus();
        if (expenditureSyncStatus == 0){
            parseConnector.addExpenditureToParse();
            expenditureCrud.updateSyncExpenditure(1);
        }

        // send income details to server with an internet connection on device.
        int incomeSyncStatus = incomeCrud.getSyncStatus();
        if (incomeSyncStatus == 0){
            parseConnector.addIncomeToParse();
            incomeCrud.updateSyncIncome();
        }
    }


//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

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
