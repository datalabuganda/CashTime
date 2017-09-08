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

public class UpdateTransportActivity extends AppCompatActivity {

    EditText edtUpdateTransport;
    Button btnUpdateTransport;

    SQLiteHelper myHelper;

    private String selectedTransport;
    private int selectedID;

    private static final String TAG = "UpdateTransportAcitivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_transport);

        edtUpdateTransport = (EditText) findViewById(R.id.amtUpdateTransport);
        btnUpdateTransport = (Button) findViewById(R.id.btnUpdateTransport);

        myHelper = new SQLiteHelper(this);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedTransport = receivedIntent.getStringExtra("Transport");

        edtUpdateTransport.setText(selectedTransport);
        btnUpdateTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edtUpdateTransport.getText().toString();
                if (!item.equals("")){
                    myHelper.updateTransport(item,selectedID,selectedTransport);

                }else {
                    Toast.makeText(UpdateTransportActivity.this, "You must enter an amount", Toast.LENGTH_SHORT).show();
                }

                Intent ExpenditureIntent = new Intent(UpdateTransportActivity.this, ExpenditureActivity.class);
                startActivity(ExpenditureIntent);
                finish();

            }
        });


    }
}
