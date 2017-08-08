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

public class HomeneedsActivity extends AppCompatActivity {

    SQLiteHelper myHelper;
    EditText edtHomeneeds;
    Button btnHomeneeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeneeds);

        edtHomeneeds = (EditText) findViewById(R.id.amtHomeneeds);
        btnHomeneeds = (Button) findViewById(R.id.btnHomeneeds);
        myHelper = new SQLiteHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AddHomeneeds();

    }

    public void AddHomeneeds(){
        btnHomeneeds.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int yVal = Integer.parseInt(String.valueOf(edtHomeneeds.getText()));
                        boolean isInseted = myHelper.insertHomeneeds(yVal);
                        if (isInseted = true)
                            Toast.makeText(HomeneedsActivity.this, "Your home needs costs have been stored", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(HomeneedsActivity.this, "Your home needs costs where not stored", Toast.LENGTH_LONG).show();
                        Intent Homeneedsintent = new Intent(HomeneedsActivity.this, ExpenditureActivity.class);
                        HomeneedsActivity.this.startActivity(Homeneedsintent);
                    }

                }
        );
    }
}
