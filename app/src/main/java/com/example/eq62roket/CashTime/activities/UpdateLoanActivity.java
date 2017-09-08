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

public class UpdateLoanActivity extends AppCompatActivity {
    EditText edtUpdateLoan;
    Button btnUpdateLoan;

    IncomeCrud incomeCrud;

    private String selectedLoan;
    private int selectedID;

    private static final String TAG = "UpdateLoanAcitivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_loan);

        edtUpdateLoan = (EditText) findViewById(R.id.edtUpdateLoans);
        btnUpdateLoan = (Button) findViewById(R.id.btnUpdateLoans);

        incomeCrud = new IncomeCrud(this);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedLoan = receivedIntent.getStringExtra("loan");

        edtUpdateLoan.setText(selectedLoan);
        btnUpdateLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edtUpdateLoan.getText().toString();
                if (!item.equals("")){
                    incomeCrud.updateLoan(item,selectedID,selectedLoan);

                }else {
                    Toast.makeText(UpdateLoanActivity.this, "You must enter an amount", Toast.LENGTH_SHORT).show();
                }

                Intent IncomeIntent = new Intent(UpdateLoanActivity.this, IncomeActivity.class);
                startActivity(IncomeIntent);
                finish();

            }
        });

    }
}
