package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.IncomeCrud;
import com.example.eq62roket.CashTime.adapters.LoanAdapter;


public class UpdateLoanActivity extends AppCompatActivity {
    EditText edtUpdateLoan;
    Button btnUpdateLoan;

    IncomeCrud incomeCrud;

    private static final String TAG = "UpdateLoanAcitivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_loan);

        edtUpdateLoan = (EditText) findViewById(R.id.edtUpdateLoans);
        btnUpdateLoan = (Button) findViewById(R.id.btnUpdateLoans);

        incomeCrud = new IncomeCrud(this);

        Intent receivedIntent = getIntent();

        final int loanAmount = receivedIntent.getExtras().getInt("LOAN_AMOUNT");
        final long selectedID = receivedIntent.getExtras().getLong("LOAN_ID");


        edtUpdateLoan.setText("" + loanAmount);

        Log.d(TAG, "loan Amount" + loanAmount);
        btnUpdateLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edtUpdateLoan.getText().toString();
                Log.d(TAG, "update valued" + item);
                if (!item.equals("")){

                    incomeCrud.updateLoan(item, (int) selectedID, loanAmount);
                    Log.d(TAG, "loan amount" + loanAmount);
                    Log.d(TAG, "loan id" + selectedID);


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
