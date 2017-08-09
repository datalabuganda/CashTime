package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;

public class HomeDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView imgGoal, imgIncome, imgExpenditure, imgAnalytics, imgReports, imgTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        imgGoal = (ImageView) findViewById(R.id.imgGoals);
        imgIncome = (ImageView) findViewById(R.id.imgIncome);
        imgExpenditure = (ImageView) findViewById(R.id.imgExpenditure);
        imgAnalytics = (ImageView) findViewById(R.id.imgAnalytics);
        imgTips = (ImageView) findViewById(R.id.imgTips);
        imgReports = (ImageView) findViewById(R.id.imgReport);

        imgGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeGoalintent = new Intent(HomeDrawerActivity.this, GoalsListActivity.class);
                HomeDrawerActivity.this.startActivity(HomeGoalintent);
            }
        });

        imgIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeIncomeintent = new Intent(HomeDrawerActivity.this, IncomeActivity.class);
                HomeDrawerActivity.this.startActivity(HomeIncomeintent);
            }
        });

        imgExpenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeExpenditureintent = new Intent(HomeDrawerActivity.this, ExpenditureActivity.class);
                HomeDrawerActivity.this.startActivity(HomeExpenditureintent);
            }
        });

        imgAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeAnalyticsintent = new Intent(HomeDrawerActivity.this, AnalysisActivity.class);
                HomeDrawerActivity.this.startActivity(HomeAnalyticsintent);
            }
        });

        imgReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeReportsintent = new Intent(HomeDrawerActivity.this, ReportActivity.class);
                HomeDrawerActivity.this.startActivity(HomeReportsintent);
            }
        });

        imgTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeTipsintent = new Intent(HomeDrawerActivity.this, TipsActivity.class);
                HomeDrawerActivity.this.startActivity(HomeTipsintent);
            }
        });

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_editprofile) {
            Toast.makeText(this, "Edit your profile", Toast.LENGTH_LONG).show();
            // Handle the camera action
        } else if (id == R.id.nav_goals) {
            Intent goalslistIntent = new Intent(HomeDrawerActivity.this, GoalsListActivity.class);
            startActivity(goalslistIntent);
            Toast.makeText(this, "Goals", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_income) {
            Intent incomeIntent = new Intent(HomeDrawerActivity.this, IncomeActivity.class);
            startActivity(incomeIntent);
            Toast.makeText(this, "Income", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_expenditure) {
            Intent expenditureIntent = new Intent(HomeDrawerActivity.this, ExpenditureActivity.class);
            startActivity(expenditureIntent);
            Toast.makeText(this, "Expenditure", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_reports) {
            Intent reportsIntent = new Intent(HomeDrawerActivity.this, ReportActivity.class);
            startActivity(reportsIntent);
            Toast.makeText(this, "Reports", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_analysis) {
            Intent analysisIntent = new Intent(HomeDrawerActivity.this, AnalysisActivity.class);
            startActivity(analysisIntent);
            Toast.makeText(this, "Analysis", Toast.LENGTH_LONG).show();

        }else if (id == R.id.nav_tips) {
            Intent tipsIntent = new Intent(HomeDrawerActivity.this, TipsActivity.class);
            startActivity(tipsIntent);
            Toast.makeText(this, "Tips", Toast.LENGTH_LONG).show();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
