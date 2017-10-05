package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.database.Cursor;
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
import com.example.eq62roket.CashTime.adapters.EducationAdapter;
import com.example.eq62roket.CashTime.adapters.LoanAdapter;
import com.example.eq62roket.CashTime.helper.DatabaseHelper;
import com.example.eq62roket.CashTime.helper.ExpenditureCrud;
import com.example.eq62roket.CashTime.helper.IncomeCrud;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Expenditure;
import com.example.eq62roket.CashTime.models.Income;
import com.example.eq62roket.CashTime.models.User;

import java.util.ArrayList;

public class EducationActivity extends AppCompatActivity {
    private static final String TAG = "SavingsActivity";

    ExpenditureCrud expenditureCrud;
    EditText edtEducation;
    Button btnEducation;
    UserCrud userCrud;
    ListView EducationListVIew;
    EducationAdapter educationAdapter;
    IncomeCrud incomeCrud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EducationListVIew = (ListView) findViewById(R.id.educationListView);
        edtEducation = (EditText) findViewById(R.id.amtEducation);
        btnEducation = (Button) findViewById(R.id.btnEducation);
        expenditureCrud = new ExpenditureCrud(this);
        incomeCrud = new IncomeCrud(this);

        ArrayList<Expenditure> educationArrayList = new ArrayList<>();
        educationArrayList = expenditureCrud.getAllEducation();
        final int remainingincome = this.remainingIncome();

        educationAdapter = new EducationAdapter(this, R.layout.education_list_adapter, educationArrayList);
        EducationListVIew.setAdapter(educationAdapter);

        userCrud = new UserCrud(this);


        btnEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtEducation.getText().toString().equals("")) {
                    int yVal = Integer.parseInt(String.valueOf(edtEducation.getText()));
                    if (yVal <= remainingincome) {
                        boolean isInseted = expenditureCrud.insertEducation(yVal);
                        if (isInseted) {
                            // if user spends on any expense, award them 2 points
                            User user = userCrud.getLastUserInserted();
                            user.setPoints(2);
                            user.setSyncStatus(0);
                            userCrud.updateUser(user);

                            Intent Educationintent = new Intent(EducationActivity.this, ExpenditureActivity.class);
                            EducationActivity.this.startActivity(Educationintent);
                            Toast.makeText(EducationActivity.this, "Education costs have been stored", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(EducationActivity.this, "Education costs where not stored", Toast.LENGTH_LONG).show();

                        }
                    }
                    else {
                        Toast.makeText(EducationActivity.this, "Your don't have enough income to spend on Education", Toast.LENGTH_LONG).show();

                    }
                }
                else {
                    Toast.makeText(EducationActivity.this, "Please input amount before submitting", Toast.LENGTH_LONG).show();
                }

            }
        });

        remainingIncome();


    }

    public int remainingIncome(){
        int totalIncome = incomeCrud.addAllIncome();
        int totalExpenditure = expenditureCrud.addAllCategories();
        int remainingIncome = totalIncome - totalExpenditure;
        return remainingIncome;

    }

}
