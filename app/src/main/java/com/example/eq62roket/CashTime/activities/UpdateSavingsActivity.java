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

public class UpdateSavingsActivity extends AppCompatActivity {

    EditText edtUpdateSavings;
    Button btnUpdateSavings;

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

        myHelper = new SQLiteHelper(this);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedSavings = receivedIntent.getStringExtra("Savings");

        edtUpdateSavings.setText(selectedSavings);
        btnUpdateSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edtUpdateSavings.getText().toString();
                if (!item.equals("")){
                    myHelper.updateSavings(item,selectedID,selectedSavings);

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
