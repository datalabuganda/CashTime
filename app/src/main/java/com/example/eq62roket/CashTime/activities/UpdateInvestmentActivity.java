package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.IncomeSQLiteHelper;

public class UpdateInvestmentActivity extends AppCompatActivity {
    EditText edtUpdateInvestment;
    Button btnUpdateInvestment;

    IncomeSQLiteHelper myHelper;

    private String selectedInvestment;
    private int selectedID;

    private static final String TAG = "UpdateInvestmentAcitivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_investment);
        edtUpdateInvestment = (EditText) findViewById(R.id.edtUpdateInvestment);
        btnUpdateInvestment = (Button) findViewById(R.id.btnUpdateInvestment);

        myHelper = new IncomeSQLiteHelper(this);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedInvestment = receivedIntent.getStringExtra("investment");

        edtUpdateInvestment.setText(selectedInvestment);
        btnUpdateInvestment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edtUpdateInvestment.getText().toString();
                if (!item.equals("")){
                    myHelper.updateSalary(item,selectedID,selectedInvestment);

                }else {
                    Toast.makeText(UpdateInvestmentActivity.this, "You must enter an amount", Toast.LENGTH_SHORT).show();
                }

                Intent IncomeIntent = new Intent(UpdateInvestmentActivity.this, IncomeActivity.class);
                startActivity(IncomeIntent);

            }
        });

    }
}
