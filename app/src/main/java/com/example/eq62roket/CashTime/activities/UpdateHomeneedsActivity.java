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

public class UpdateHomeneedsActivity extends AppCompatActivity {

    EditText edtUpdateHomeneeds;
    Button btnUpdateHomeneeds;

    SQLiteHelper myHelper;

    private String selectedHomeneeds;
    private int selectedID;

    private static final String TAG = "UpdateHomeneedsAcitivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_homeneeds);

        edtUpdateHomeneeds = (EditText) findViewById(R.id.amtUpdateHomeneeds);
        btnUpdateHomeneeds = (Button) findViewById(R.id.btnUpdateHomeneeds);

        myHelper = new SQLiteHelper(this);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedHomeneeds = receivedIntent.getStringExtra("Homeneeds");

        edtUpdateHomeneeds.setText(selectedHomeneeds);
        btnUpdateHomeneeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edtUpdateHomeneeds.getText().toString();
                if (!item.equals("")){
                    myHelper.updateHomeneeds(item,selectedID,selectedHomeneeds);

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
