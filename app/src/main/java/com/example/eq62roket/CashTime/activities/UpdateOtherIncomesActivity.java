package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.IncomeCrud;

public class UpdateOtherIncomesActivity extends AppCompatActivity {

    EditText edtUpdateOthers;
    Button btnUpdateOthers;

    IncomeCrud incomeCrud;

    private String selectedOthers;
    private int selectedID;

    private static final String TAG = "UpdateOthersAcitivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_other_incomes);

        edtUpdateOthers = (EditText) findViewById(R.id.edtUpdateOthers);
        btnUpdateOthers = (Button) findViewById(R.id.btnUpdateOthers);

        incomeCrud = new IncomeCrud(this);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedOthers = receivedIntent.getStringExtra("others");

        edtUpdateOthers.setText(selectedOthers);
        btnUpdateOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edtUpdateOthers.getText().toString();
                if (!item.equals("")){
                    incomeCrud.updateOthers(item,selectedID,selectedOthers);

                }else {
                    Toast.makeText(UpdateOtherIncomesActivity.this, "You must enter an amount", Toast.LENGTH_SHORT).show();
                }

                Intent IncomeIntent = new Intent(UpdateOtherIncomesActivity.this, IncomeActivity.class);
                startActivity(IncomeIntent);
                finish();

            }
        });

    }
}
