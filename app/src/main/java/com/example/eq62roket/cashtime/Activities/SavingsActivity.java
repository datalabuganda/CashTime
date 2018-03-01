package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.eq62roket.cashtime.R;

public class SavingsActivity extends AppCompatActivity {
    FloatingActionButton fabSavings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
        fabSavings = (FloatingActionButton)findViewById(R.id.addSavings);

        fabSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent savingIntent = new Intent(SavingsActivity.this, AddSavingsActivity.class);
                startActivity(savingIntent);
            }
        });
    }
}
