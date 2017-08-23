package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eq62roket.CashTime.helper.IncomeSQLiteHelper;
import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.User;

public class OtherIncomesActivity extends AppCompatActivity {

    EditText edtOthers;
    Button btnOthers;
    IncomeSQLiteHelper myHelper;
    UserCrud userCrud;
    ListView othersListVIew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_incomes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnOthers = (Button) findViewById(R.id.btnOthers);
        edtOthers = (EditText) findViewById(R.id.edtOthers);

        myHelper = new IncomeSQLiteHelper(this);

        userCrud = new UserCrud(this);

        AddOthers();
    }

    public void AddOthers(){
        btnOthers.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!edtOthers.getText().toString().equals("")) {
                            int yVal = Integer.parseInt(String.valueOf(edtOthers.getText()));
                            boolean isInseted = myHelper.insertOthers(yVal);

                            if (isInseted) {
                                // if user adds income, award them 2 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
                                userCrud.updateUser(user);

                                Toast.makeText(OtherIncomesActivity.this, "Your income has been added", Toast.LENGTH_LONG).show();
                                Intent OtherIncomeintent = new Intent(OtherIncomesActivity.this, IncomeActivity.class);
                                OtherIncomesActivity.this.startActivity(OtherIncomeintent);
                                finish();
                            }
                            else {
                                Toast.makeText(OtherIncomesActivity.this, "Your income has not been added", Toast.LENGTH_LONG).show();
                            }

                        }
                        else
                            Toast.makeText(OtherIncomesActivity.this, "Please input amount before submitting", Toast.LENGTH_LONG).show();

                    }

                }
        );
    }


}
