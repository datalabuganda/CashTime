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

public class UpdateHealthActivity extends AppCompatActivity {

    EditText edtUpdateHealth;
    Button btnUpdateHealth;
ExpenditureCrud expenditureCrud;
    SQLiteHelper myHelper;

    private String selectedHealth;
    private int selectedID;

    private static final String TAG = "UpdateHealthAcitivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_health);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtUpdateHealth = (EditText) findViewById(R.id.amtUpdateHealth);
        btnUpdateHealth = (Button) findViewById(R.id.btnUpdateHealth);

        expenditureCrud = new ExpenditureCrud(this);

        Intent receivedIntent = getIntent();

        final int healthAmount = receivedIntent.getExtras().getInt("HEALTH_AMOUNT");
        final long selectedID = receivedIntent.getExtras().getLong("HEALTH_ID");

        edtUpdateHealth.setText("" + healthAmount);
        btnUpdateHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edtUpdateHealth.getText().toString();
                if (!item.equals("")){
                    expenditureCrud.updateHealth(item, (int) selectedID,healthAmount);
                    expenditureCrud.updateSyncExpenditure(0);

                }else {
                    Toast.makeText(UpdateHealthActivity.this, "You must enter an amount", Toast.LENGTH_SHORT).show();
                }

                Intent ExpenditureIntent = new Intent(UpdateHealthActivity.this, ExpenditureActivity.class);
                startActivity(ExpenditureIntent);
                finish();

            }
        });

    }
}
