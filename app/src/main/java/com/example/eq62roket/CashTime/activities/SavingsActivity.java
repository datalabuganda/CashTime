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

public class SavingsActivity extends AppCompatActivity {

    SQLiteHelper myHelper;
    EditText edtSavings;
    Button btnSavings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        edtSavings = (EditText) findViewById(R.id.amtSavings);
        btnSavings = (Button) findViewById(R.id.btnSavings);
        myHelper = new SQLiteHelper(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AddSavings();
    }

    public void AddSavings(){
        btnSavings.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int yVal = Integer.parseInt(String.valueOf(edtSavings.getText()));
                        boolean isInseted = myHelper.insertSavings(yVal);
                        if (isInseted = true)
                            Toast.makeText(SavingsActivity.this, "Your savings have been stored", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(SavingsActivity.this, "Your savings have not been stored", Toast.LENGTH_LONG).show();
                        Intent Savingsintent = new Intent(SavingsActivity.this, ExpenditureActivity.class);
                        SavingsActivity.this.startActivity(Savingsintent);
                    }

                }
        );
    }

}
