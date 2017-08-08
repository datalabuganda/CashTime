package com.example.eq62roket.CashTime.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.eq62roket.CashTime.R;

public class TipsActivity extends AppCompatActivity implements ActionBar.TabListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar ab = getSupportActionBar();
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ab.addTab(ab.newTab().setText("Transport").setTabListener(this));
        ab.addTab(ab.newTab().setText("Savings").setTabListener(this));
        ab.addTab(ab.newTab().setText("Medication").setTabListener(this));
        ab.addTab(ab.newTab().setText("Education").setTabListener(this));
        ab.addTab(ab.newTab().setText("Others").setTabListener(this));
        ab.addTab(ab.newTab().setText("Home needs").setTabListener(this));


    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        int nTabSelected = tab.getPosition();
        switch (nTabSelected) {
            case 0:
                setContentView(R.layout.actionbar_tab_1);
                break;
            case 1:
                setContentView(R.layout.actionbar_tab_2);
                break;
            case 2:
                setContentView(R.layout.actionbar_tab_3);
                break;
            case 3:
                setContentView(R.layout.actionbar_education);
                break;
            case 4:
                setContentView(R.layout.actionbar_others);
                break;
            case 5:
                setContentView(R.layout.action_homeneeds);
                break;
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }



}
