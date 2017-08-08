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

public class EducationActivity extends AppCompatActivity {

    SQLiteHelper myHelper;
    EditText edtEducation;
    Button btnEducation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtEducation = (EditText) findViewById(R.id.amtEducation);
        btnEducation = (Button) findViewById(R.id.btnEducation);
        myHelper = new SQLiteHelper(this);

        btnEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int yVal = Integer.parseInt(String.valueOf(edtEducation.getText()));
                if (edtEducation.length() != 0){
                    AddEducation(yVal);
                    edtEducation.setText("");
                }else{
                    Toast.makeText(EducationActivity.this, "Fill in your amount", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void AddEducation(int yVal){

                        boolean isInseted = myHelper.insertEducation(yVal);
                        if (isInseted = true)
                            Toast.makeText(EducationActivity.this, "Education costs have been stored", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(EducationActivity.this, "Education costs where not stored", Toast.LENGTH_LONG).show();
                        Intent Educationintent = new Intent(EducationActivity.this, ExpenditureActivity.class);
                        EducationActivity.this.startActivity(Educationintent);
    }
}
