package com.cashtime.cashtimefinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cashtime.helper.SavingCrud;
import com.cashtime.helper.SavingCrud2;
import com.cashtime.models.Saving;
import com.numetriclabz.numandroidcharts.FunnelChart;

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
