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

public class UpdateHomeneedsActivity extends AppCompatActivity {

    EditText edtUpdateHomeneeds;
    Button btnUpdateHomeneeds;
    ExpenditureCrud expenditureCrud;

    SQLiteHelper myHelper;

    private String selectedHomeneeds;
    private int selectedID;

    private static final String TAG = "UpdateHomeneedsAcitivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_homeneeds);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtUpdateHomeneeds = (EditText) findViewById(R.id.amtUpdateHomeneeds);
        btnUpdateHomeneeds = (Button) findViewById(R.id.btnUpdateHomeneeds);

        expenditureCrud = new ExpenditureCrud(this);

        Intent receivedIntent = getIntent();

        final int homeneedsAmount = receivedIntent.getExtras().getInt("HOMENEEDS_AMOUNT");
        final long selectedID = receivedIntent.getExtras().getLong("HOMENEEDS_ID");

        edtUpdateHomeneeds.setText("" + homeneedsAmount);
        btnUpdateHomeneeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edtUpdateHomeneeds.getText().toString();
                if (!item.equals("")){
                    expenditureCrud.updateHomeneeds(item, (int) selectedID,homeneedsAmount);
                    expenditureCrud.updateSyncExpenditure(0);

                }else {
                    Toast.makeText(UpdateHomeneedsActivity.this, "You must enter an amount", Toast.LENGTH_SHORT).show();
                }

                Intent ExpenditureIntent = new Intent(UpdateHomeneedsActivity.this, ExpenditureActivity.class);
                startActivity(ExpenditureIntent);
                finish();

            }
        });

    }
}
