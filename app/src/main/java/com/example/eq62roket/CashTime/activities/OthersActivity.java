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

public class OthersActivity extends AppCompatActivity {

    SQLiteHelper myHelper;
    EditText edtOthers;
    Button btnOthers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        edtOthers = (EditText) findViewById(R.id.amtOthers);
        btnOthers = (Button) findViewById(R.id.btnOthers);
        myHelper = new SQLiteHelper(this);

        AddOthers();
    }

    public void AddOthers(){
        btnOthers.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int yVal = Integer.parseInt(String.valueOf(edtOthers.getText()));
                        boolean isInseted = myHelper.insertOthers(yVal);
                        if (isInseted = true)
                            Toast.makeText(OthersActivity.this, "Your costs of other items have been stored", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(OthersActivity.this, "Other costs have not been stored", Toast.LENGTH_LONG).show();
                        Intent Othersintent = new Intent(OthersActivity.this, ExpenditureActivity.class);
                        OthersActivity.this.startActivity(Othersintent);
                    }

                }
        );
    }
}
