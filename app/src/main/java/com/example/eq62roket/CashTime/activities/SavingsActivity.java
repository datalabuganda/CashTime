package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.User;

public class SavingsActivity extends AppCompatActivity {

    private static final String TAG = "SavingsActivity";

    SQLiteHelper myHelper;
    UserCrud userCrud;
    EditText edtSavings;
    Button btnSavings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        edtSavings = (EditText) findViewById(R.id.amtSavings);
        btnSavings = (Button) findViewById(R.id.btnSavings);
        myHelper = new SQLiteHelper(this);

        userCrud = new UserCrud(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AddSavings();
    }

    public void AddSavings(){
        btnSavings.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!edtSavings.getText().toString().equals("")){
                            int yVal = Integer.parseInt(String.valueOf(edtSavings.getText()));
                            boolean isInseted = myHelper.insertSavings(yVal);
                            if (isInseted) {
                                // if user adds a saving, award them 5 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(5);
                                userCrud.updateUser(user);

                                Toast.makeText(SavingsActivity.this, "Your savings have been stored", Toast.LENGTH_LONG).show();
                                Intent Savingsintent = new Intent(SavingsActivity.this, ExpenditureActivity.class);
                                SavingsActivity.this.startActivity(Savingsintent);
                            }
                            else {
                                Toast.makeText(SavingsActivity.this, "Your savings have not been stored", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(SavingsActivity.this, "Please input amount before submitting", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );
    }

}
