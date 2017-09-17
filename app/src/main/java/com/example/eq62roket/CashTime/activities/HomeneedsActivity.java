package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.adapters.HomeNeedsAdapter;
import com.example.eq62roket.CashTime.adapters.OthersAdapter;
import com.example.eq62roket.CashTime.helper.DatabaseHelper;
import com.example.eq62roket.CashTime.helper.ExpenditureCrud;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Expenditure;
import com.example.eq62roket.CashTime.models.User;

import java.util.ArrayList;

public class HomeneedsActivity extends AppCompatActivity {

    ExpenditureCrud expenditureCrud;
    EditText edtHomeneeds;
    Button btnHomeneeds;
    UserCrud userCrud;
    ListView HomeneedsListView;
    HomeNeedsAdapter homeNeedsAdapter;
    private static final String TAG = "HomeneedsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeneeds);

        edtHomeneeds = (EditText) findViewById(R.id.amtHomeneeds);
        btnHomeneeds = (Button) findViewById(R.id.btnHomeneeds);

        expenditureCrud = new ExpenditureCrud(this);

        HomeneedsListView = (ListView) findViewById(R.id.HomeneedslistView);

        ArrayList<Expenditure> homeneedsArrayList = new ArrayList<>();
        homeneedsArrayList = expenditureCrud.getAllHomeneeds();

        homeNeedsAdapter = new HomeNeedsAdapter(this, R.layout.homeneeds_list_adapter, homeneedsArrayList);
        HomeneedsListView.setAdapter(homeNeedsAdapter);

        userCrud = new UserCrud(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userCrud = new UserCrud(this);

        AddHomeneeds();

    }

    public void AddHomeneeds(){
        btnHomeneeds.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                                // if user creates goal for the first time, award them 3 points
                        if (!edtHomeneeds.getText().toString().equals("")) {
                            int yVal = Integer.parseInt(String.valueOf(edtHomeneeds.getText()));
                            boolean isInseted = expenditureCrud.insertHomeneeds(yVal);
                            if (isInseted) {
                                // if user spends on any expense, award them 2 points
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
                                user.setSyncStatus(0);
                                userCrud.updateUser(user);

                                Toast.makeText(HomeneedsActivity.this, "Your Home needs have been stored", Toast.LENGTH_LONG).show();
                                Intent Homeneeds = new Intent(HomeneedsActivity.this, ExpenditureActivity.class);
                                HomeneedsActivity.this.startActivity(Homeneeds);
                                finish();
                            } else {
                                Toast.makeText(HomeneedsActivity.this, "Your Home needs have not been stored", Toast.LENGTH_LONG).show();
                            }
                        }
                            else {
                                Toast.makeText(HomeneedsActivity.this, "Your home needs costs where not stored", Toast.LENGTH_LONG).show();
                            }


                    }

                }
        );
    }

}
