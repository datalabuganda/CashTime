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
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.User;


public class HealthActivity extends AppCompatActivity {
    private static final String TAG = "SavingsActivity";

    SQLiteHelper myHelper;
    EditText edtHealth;
    Button btnHealth;
    UserCrud userCrud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        edtHealth = (EditText) findViewById(R.id.amtHealth);
        btnHealth = (Button) findViewById(R.id.btnHealth);
        myHelper = new SQLiteHelper(this);

        userCrud = new UserCrud(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AddHealth();

    }

    public void AddHealth(){
        btnHealth.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!edtHealth.getText().toString().equals("")) {
                            int yVal = Integer.parseInt(String.valueOf(edtHealth.getText()));
                            boolean isInseted = myHelper.insertHealth(yVal);
                            if (isInseted) {
                                // if user spends on any expense, award them 2 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
                                userCrud.updateUser(user);

                                Toast.makeText(HealthActivity.this, "Your health costs have been stored", Toast.LENGTH_LONG).show();
                                Intent Healthintent = new Intent(HealthActivity.this, ExpenditureActivity.class);
                                HealthActivity.this.startActivity(Healthintent);
                                finish();
                            }
                            else {
                                Toast.makeText(HealthActivity.this, "Your health costs where not stored", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(HealthActivity.this, "Please input amount before submitting", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );
    }
}
