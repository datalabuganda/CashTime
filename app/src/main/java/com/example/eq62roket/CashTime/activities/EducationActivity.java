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

public class EducationActivity extends AppCompatActivity {

    SQLiteHelper myHelper;
    EditText edtEducation;
    Button btnEducation;
    UserCrud userCrud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtEducation = (EditText) findViewById(R.id.amtEducation);
        btnEducation = (Button) findViewById(R.id.btnEducation);
        myHelper = new SQLiteHelper(this);

        userCrud = new UserCrud(this);

        btnEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtEducation.getText().toString().equals("")){
                    int yVal = Integer.parseInt(String.valueOf(edtEducation.getText()));
                    boolean isInseted = myHelper.insertEducation(yVal);
                    if (isInseted) {
                        // if user spends on any expense, award them 2 points
                        User user = userCrud.getLastUserInserted();
                        user.setPoints(2);
                        userCrud.updateUser(user);

                        Intent Educationintent = new Intent(EducationActivity.this, ExpenditureActivity.class);
                        EducationActivity.this.startActivity(Educationintent);
                        Toast.makeText(EducationActivity.this, "Education costs have been stored", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(EducationActivity.this, "Education costs where not stored", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(EducationActivity.this, "Please input amount before submitting", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}
