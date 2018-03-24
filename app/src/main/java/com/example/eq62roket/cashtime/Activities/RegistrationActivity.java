package com.example.eq62roket.cashtime.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.eq62roket.cashtime.R;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Parse.initialize(this);

        ParseInstallation.getCurrentInstallation().saveInBackground();

    }
}
