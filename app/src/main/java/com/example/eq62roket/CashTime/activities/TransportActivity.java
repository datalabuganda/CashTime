package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.ExpenditureCrud;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.User;

public class TransportActivity extends AppCompatActivity {

    ExpenditureCrud expenditureCrud;
    EditText edtTransport;
    Button btnTransport;
    UserCrud userCrud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);

        edtTransport = (EditText) findViewById(R.id.amtTransport);
        btnTransport = (Button) findViewById(R.id.btnTransport);
        expenditureCrud = new ExpenditureCrud(this);

        userCrud = new UserCrud(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnTransport.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        int yVal = Integer.parseInt(String.valueOf(edtTransport.getText()));
//                        boolean isInseted = incomeCrud.insertTransport(yVal);
//                        if (isInseted = true)
//                            Toast.makeText(TransportActivity.this, "Your transport costs have been stored", Toast.LENGTH_LONG).show();
//                        else
//                            Toast.makeText(TransportActivity.this, "Your transport costs have not been stored", Toast.LENGTH_LONG).show();
//                        Intent Transportintent = new Intent(TransportActivity.this, ExpenditureActivity.class);
//                        TransportActivity.this.startActivity(Transportintent);
                        if (!edtTransport.getText().toString().equals("")){
                            int yVal = Integer.parseInt(String.valueOf(edtTransport.getText()));
                            boolean isInseted = expenditureCrud.insertTransport(yVal);
                            if (isInseted) {
                                // if user adds a saving, award them 5 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
                                user.setSyncStatus(0);
                                userCrud.updateUser(user);

                                // Log.d(TAG, "goal status " + goal.getCompleteStatus());


                                Toast.makeText(TransportActivity.this, "Your savings have been stored", Toast.LENGTH_LONG).show();
                                Intent Savingsintent = new Intent(TransportActivity.this, ExpenditureActivity.class);
                                TransportActivity.this.startActivity(Savingsintent);
                                finish();
                                //Log.d(TAG, "goal saved " + myHelper.addAllSavings(null));
                            }
                            else {
                                Toast.makeText(TransportActivity.this, "Your savings have not been stored", Toast.LENGTH_LONG).show();
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
