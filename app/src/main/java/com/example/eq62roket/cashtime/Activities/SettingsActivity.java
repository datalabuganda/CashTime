package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Helper.PeriodHelper;
import com.example.eq62roket.cashtime.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    private LinearLayout shareLl, rateLl, privacyPolicyLl, termsOfServiceLl, licensesLl, versionsLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        shareLl = findViewById(R.id.share);
        rateLl = findViewById(R.id.rate);
        privacyPolicyLl = findViewById(R.id.privacyPolicy);
        termsOfServiceLl = findViewById(R.id.termsOfService);
        licensesLl = findViewById(R.id.license);
        versionsLl = findViewById(R.id.versions);

        shareLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        privacyPolicyLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPrivacyPolicy = new Intent(getApplicationContext(), PrivacyPolicyActivity.class);
                startActivity(intentPrivacyPolicy);
                finish();
            }
        });

        termsOfServiceLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent termsOfServiceIntent = new Intent(getApplicationContext(), TermsOfServiceActivity.class);
                startActivity(termsOfServiceIntent);
                finish();
            }
        });

        licensesLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent licensesIntent = new Intent(getApplicationContext(), LicenseActivity.class);
                startActivity(licensesIntent);
                finish();
            }
        });

        versionsLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent versionsIntent = new Intent(getApplicationContext(), VersionsActivity.class);
                startActivity(versionsIntent);
                finish();
            }
        });

    }
}
