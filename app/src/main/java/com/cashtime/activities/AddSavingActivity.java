package com.cashtime.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cashtime.helper.SavingCrud2;

public class AddSavingActivity extends AppCompatActivity {

    private static final String TAG = "AddSavingActivity";

    private SavingCrud2 mSavingCrud;

    TextView tvCurrentSaving;
    EditText etAmount;
    Button btnAddSaving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_saving);

      /*  tvCurrentSaving = (TextView) findViewById(R.id.tvCurrentSaving);
        etAmount = (EditText) findViewById(R.id.etAmount);
        btnAddSaving = (Button) findViewById(R.id.btnAddSaving);

       mSavingCrud = new SavingCrud2(this);

        btnAddSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable amount = etAmount.getText();

                if (!TextUtils.isEmpty(amount)){
                    int intAmount = Integer.parseInt(amount.toString());
                    // Add saving to database
                    Saving saving = new Saving();
                    saving.setTotalSaving(intAmount);
                    mSavingCrud.createSaving(saving);

                    Toast.makeText(AddSavingActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddSavingActivity.this, FunnelActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(AddSavingActivity.this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

    }
}
