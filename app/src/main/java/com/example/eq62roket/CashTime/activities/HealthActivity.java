package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;


public class HealthActivity extends AppCompatActivity {
    private static final String TAG = "SavingsActivity";

    SQLiteHelper myHelper;
    EditText edtHealth;
    Button btnHealth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        edtHealth = (EditText) findViewById(R.id.amtHealth);
        btnHealth = (Button) findViewById(R.id.btnHealth);
        myHelper = new SQLiteHelper(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AddHealth();

    }

    public void AddHealth(){
        btnHealth.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int yVal = Integer.parseInt(String.valueOf(edtHealth.getText()));
                        boolean isInseted = myHelper.insertHealth(yVal);
                        if (isInseted = true)
                            Toast.makeText(HealthActivity.this, "Your health costs have been stored", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(HealthActivity.this, "Your health costs where not stored", Toast.LENGTH_LONG).show();
                        Intent Healthintent = new Intent(HealthActivity.this, ExpenditureActivity.class);
                        HealthActivity.this.startActivity(Healthintent);
                    }

                }
        );
    }
}
