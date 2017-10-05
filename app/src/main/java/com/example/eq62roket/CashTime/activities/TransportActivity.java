package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.adapters.OthersAdapter;
import com.example.eq62roket.CashTime.adapters.TransportAdapter;
import com.example.eq62roket.CashTime.helper.ExpenditureCrud;
import com.example.eq62roket.CashTime.helper.IncomeCrud;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Expenditure;
import com.example.eq62roket.CashTime.models.Transport;
import com.example.eq62roket.CashTime.models.User;

import java.util.ArrayList;

public class TransportActivity extends AppCompatActivity {

    ExpenditureCrud expenditureCrud;
    EditText edtTransport;
    Button btnTransport;
    UserCrud userCrud;
    ListView  transportListView;
    TransportAdapter transportAdapter;
    IncomeCrud incomeCrud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);

        edtTransport = (EditText) findViewById(R.id.amtTransport);
        btnTransport = (Button) findViewById(R.id.btnTransport);
        expenditureCrud = new ExpenditureCrud(this);
        transportListView = (ListView) findViewById(R.id.TransportListView);

        userCrud = new UserCrud(this);
        incomeCrud = new IncomeCrud(this);

        ArrayList<Expenditure> transportArrayList = new ArrayList<>();
        transportArrayList = expenditureCrud.getAllTransport();
        final int remainingincome = this.remainingIncome();

        transportAdapter = new TransportAdapter(this, R.layout.transport_list_adapter, transportArrayList);
        transportListView.setAdapter(transportAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnTransport.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!edtTransport.getText().toString().equals("")) {
                            int yVal = Integer.parseInt(String.valueOf(edtTransport.getText()));
                            if (yVal <= remainingincome) {
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
                                } else {
                                    Toast.makeText(TransportActivity.this, "Your savings have not been stored", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(TransportActivity.this, "Your don't have enough income to spend on Transport", Toast.LENGTH_LONG).show();

                            }
                        }
                        else {
                            Toast.makeText(TransportActivity.this, "Please input amount before submitting", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );

        remainingIncome();
    }

    public int remainingIncome(){
        int totalIncome = incomeCrud.addAllIncome();
        int totalExpenditure = expenditureCrud.addAllCategories();

        int remainingIncome = totalIncome - totalExpenditure;
        return remainingIncome;

    }


}
