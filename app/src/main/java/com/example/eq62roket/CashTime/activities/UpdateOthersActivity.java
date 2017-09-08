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

public class UpdateOthersActivity extends AppCompatActivity {

    EditText edtUpdateOthers;
    Button btnUpdateOthers;

    SQLiteHelper myHelper;

    private String selectedOthers;
    private int selectedID;

    private static final String TAG = "UpdateOthersAcitivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_others2);

        edtUpdateOthers = (EditText) findViewById(R.id.amtUpdateOthers);
        btnUpdateOthers = (Button) findViewById(R.id.btnUpdateOthers);

        myHelper = new SQLiteHelper(this);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedOthers = receivedIntent.getStringExtra("Others");

        edtUpdateOthers.setText(selectedOthers);
        btnUpdateOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edtUpdateOthers.getText().toString();
                if (!item.equals("")){
                    myHelper.updateOthers(item,selectedID,selectedOthers);

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
