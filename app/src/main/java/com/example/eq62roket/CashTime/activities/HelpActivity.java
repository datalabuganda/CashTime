package com.example.eq62roket.CashTime.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.eq62roket.CashTime.R;


public class HelpActivity extends AppCompatActivity {

    private static final String TAG = "HelpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }
}
