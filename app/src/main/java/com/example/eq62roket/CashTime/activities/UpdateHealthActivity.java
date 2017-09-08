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

public class UpdateHealthActivity extends AppCompatActivity {

    EditText edtUpdateHealth;
    Button btnUpdateHealth;

    SQLiteHelper myHelper;

    private String selectedHealth;
    private int selectedID;

    private static final String TAG = "UpdateHealthAcitivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_health);

        edtUpdateHealth = (EditText) findViewById(R.id.amtUpdateHealth);
        btnUpdateHealth = (Button) findViewById(R.id.btnUpdateHealth);

        myHelper = new SQLiteHelper(this);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedHealth = receivedIntent.getStringExtra("health");

        edtUpdateHealth.setText(selectedHealth);
        btnUpdateHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edtUpdateHealth.getText().toString();
                if (!item.equals("")){
                    myHelper.updateHealth(item,selectedID,selectedHealth);

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
