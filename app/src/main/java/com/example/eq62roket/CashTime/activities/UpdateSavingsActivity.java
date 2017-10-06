package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.ExpenditureCrud;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;

public class UpdateSavingsActivity extends AppCompatActivity {

    EditText edtUpdateSavings;
    Button btnUpdateSavings;
    ExpenditureCrud expenditureCrud;

    SQLiteHelper myHelper;

    private String selectedSavings;
    private int selectedID;

    private static final String TAG = "UpdateSavingsAcitivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_savings);

        edtUpdateSavings = (EditText) findViewById(R.id.amtUpdateSavings);
        btnUpdateSavings = (Button) findViewById(R.id.btnUpdateSavings);

        expenditureCrud = new ExpenditureCrud(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent receivedIntent = getIntent();

        final int savingsAmount = receivedIntent.getExtras().getInt("SAVINGS_AMOUNT");
        final long selectedID = receivedIntent.getExtras().getLong("SAVINGS_ID");

        edtUpdateSavings.setText("" + savingsAmount);
        btnUpdateSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edtUpdateSavings.getText().toString();
                if (!item.equals("")){
                    expenditureCrud.updateSavings(item, (int) selectedID,savingsAmount);
                    expenditureCrud.updateSyncExpenditure(0);

                }else {
                    Toast.makeText(UpdateSavingsActivity.this, "You must enter an amount", Toast.LENGTH_SHORT).show();
                }

                Intent ExpenditureIntent = new Intent(UpdateSavingsActivity.this, ExpenditureActivity.class);
                startActivity(ExpenditureIntent);
                finish();

            }
        });
    }
}
