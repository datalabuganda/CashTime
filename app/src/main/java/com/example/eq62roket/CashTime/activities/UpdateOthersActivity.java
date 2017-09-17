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

public class UpdateOthersActivity extends AppCompatActivity {

    EditText edtUpdateOthers;
    Button btnUpdateOthers;
    ExpenditureCrud expenditureCrud;

    SQLiteHelper myHelper;

    private String selectedOthers;
    private int selectedID;

    private static final String TAG = "UpdateOthersAcitivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_others);

        edtUpdateOthers = (EditText) findViewById(R.id.edtUpdateOthers);
        btnUpdateOthers = (Button) findViewById(R.id.btnUpdateOthers);

        expenditureCrud = new ExpenditureCrud(this);

        Intent receivedIntent = getIntent();

        final int othersAmount = receivedIntent.getExtras().getInt("OTHERS_AMOUNT");
        final long selectedID = receivedIntent.getExtras().getLong("OTHERS_ID");

        edtUpdateOthers.setText("" + othersAmount);
        btnUpdateOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edtUpdateOthers.getText().toString();
                if (!item.equals("")){
                    expenditureCrud.updateOthers(item, (int) selectedID,othersAmount);

                }else {
                    Toast.makeText(UpdateOthersActivity.this, "You must enter an amount", Toast.LENGTH_SHORT).show();
                }

                Intent ExpenditureIntent = new Intent(UpdateOthersActivity.this, ExpenditureActivity.class);
                startActivity(ExpenditureIntent);
                finish();

            }
        });
    }
}
