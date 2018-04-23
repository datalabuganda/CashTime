package com.example.eq62roket.cashtime.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.eq62roket.cashtime.Helper.PeriodHelper;
import com.example.eq62roket.cashtime.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    EditText period;
    String[] incomePeriods = {"Weekly", "Monthly"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }
}
