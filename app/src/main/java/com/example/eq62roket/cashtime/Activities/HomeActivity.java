package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.eq62roket.cashtime.R;
import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "HomeActivity";

    // TODO: 4/18/18 Add restore functionality....if there is internet...restore user data from online db

    private CardView goalsCardView, analyticsCardView, tipsCardView, profileCardView, expenditureCardView, incomeCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null){
        }else {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        goalsCardView = (CardView)findViewById(R.id.transactionsCardView);
        analyticsCardView = (CardView)findViewById(R.id.analyticsCardView);
        tipsCardView = (CardView)findViewById(R.id.tipsCardView);
        profileCardView = (CardView)findViewById(R.id.profileCardView);
        incomeCardView = (CardView)findViewById(R.id.incomeCardView);
        expenditureCardView = (CardView)findViewById(R.id.expenditureCardView);

        goalsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goalsIntent = new Intent(HomeActivity.this, TabbedGoalsActivity.class);
                startActivity(goalsIntent);
            }
        });

        analyticsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent analyticsIntent = new Intent(HomeActivity.this, TabbedAnalysisActivity.class);
                startActivity(analyticsIntent);
            }
        });

        profileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(HomeActivity.this, TabbedSavingActivity.class);
                startActivity(profileIntent);
            }
        });

        tipsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tipsIntent = new Intent(HomeActivity.this, TabbedBarriersTipsActivity.class);
                startActivity(tipsIntent);
            }
        });


        incomeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent incomeIntent = new Intent(HomeActivity.this, TabbedIncomeActivity.class);
                startActivity(incomeIntent);
            }
        });

        expenditureCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent expenditureIntent = new Intent(HomeActivity.this, TabbedExpenditureActivity.class);
                startActivity(expenditureIntent);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.group){
            Intent newGroup = new Intent(HomeActivity.this, AddNewGroupActivity.class);
            startActivity(newGroup);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.groupMembers) {
            Intent groupMembersIntent = new Intent(HomeActivity.this, GroupsActivity.class);
            startActivity(groupMembersIntent);
            // Handle the camera action
        } else if (id == R.id.profile) {
            Intent profileIntent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(profileIntent);

        } else if (id == R.id.calendar) {
            Intent calendarIntent = new Intent(HomeActivity.this, CalendarActivity.class);
            startActivity(calendarIntent);

        }         if (id == R.id.settings) {
            Intent settingsIntent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);

        }else if (id == R.id.logout) {
            // TODO: 4/23/18 check for internet connection before logging out user 
            ParseUser.logOut();
            Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
