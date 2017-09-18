package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.IncomeCrud;

public class UpdateSalaryActivity extends AppCompatActivity {

    EditText edtUpdateSalary;
    Button btnUpdateSalary;

    IncomeCrud incomeCrud;

    private String selectedSalary;
    private int selectedID;

    private static final String TAG = "UpdateSalaryAcitivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_salary);
        edtUpdateSalary = (EditText) findViewById(R.id.edtUpdateSalary);
        btnUpdateSalary = (Button) findViewById(R.id.btnUpdateSalary);

        incomeCrud = new IncomeCrud(this);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedSalary = receivedIntent.getStringExtra("salary");

        edtUpdateSalary.setText(selectedSalary);
        btnUpdateSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edtUpdateSalary.getText().toString();
                if (!item.equals("")){
                    incomeCrud.updateSalary(item,selectedID,selectedSalary);

                }else {
                    Toast.makeText(UpdateSalaryActivity.this, "You must enter an amount", Toast.LENGTH_SHORT).show();
                }

                Intent IncomeIntent = new Intent(UpdateSalaryActivity.this, IncomeActivity.class);
                startActivity(IncomeIntent);
                finish();

            }
        });
    }
}