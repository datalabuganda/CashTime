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

public class TransportActivity extends AppCompatActivity {

    SQLiteHelper myHelper;
    EditText edtTransport;
    Button btnTransport;
    UserCrud userCrud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);

        edtTransport = (EditText) findViewById(R.id.amtTransport);
        btnTransport = (Button) findViewById(R.id.btnTransport);
        myHelper = new SQLiteHelper(this);

        userCrud = new UserCrud(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AddTransport();
    }

    public void AddTransport(){
        btnTransport.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!edtTransport.getText().toString().equals("")) {
                            int yVal = Integer.parseInt(String.valueOf(edtTransport.getText()));
                            boolean isInseted = myHelper.insertTransport(yVal);
                            if (isInseted) {
                                // if user spends on any expense, award them 2 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
                                userCrud.updateUser(user);
                                Toast.makeText(TransportActivity.this, "Your transport costs have been stored", Toast.LENGTH_LONG).show();
                                Intent Transportintent = new Intent(TransportActivity.this, ExpenditureActivity.class);
                                TransportActivity.this.startActivity(Transportintent);
                            }
                            else {
                                Toast.makeText(TransportActivity.this, "Your transport costs have not been stored", Toast.LENGTH_LONG).show();
                            }

                        }
                        else {
                            Toast.makeText(TransportActivity.this, "Please input amount before submitting", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );
    }

}
