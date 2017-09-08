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

public class UpdateEducationActivity extends AppCompatActivity {

    EditText edtUpdateEducation;
    Button btnUpdateEducation;

    SQLiteHelper myHelper;

    private String selectedEducation;
    private int selectedID;

    private static final String TAG = "UpdateEducationAcitivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_education);

        edtUpdateEducation = (EditText) findViewById(R.id.amtUpdateEducation);
        btnUpdateEducation = (Button) findViewById(R.id.btnUpdateEducation);

        myHelper = new SQLiteHelper(this);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedEducation = receivedIntent.getStringExtra("Education");

        edtUpdateEducation.setText(selectedEducation);
        btnUpdateEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edtUpdateEducation.getText().toString();
                if (!item.equals("")){
                    myHelper.updateEducation(item,selectedID,selectedEducation);

                }else {
                    Toast.makeText(UpdateEducationActivity.this, "You must enter an amount", Toast.LENGTH_SHORT).show();
                }

                Intent ExpenditureIntent = new Intent(UpdateEducationActivity.this, ExpenditureActivity.class);
                startActivity(ExpenditureIntent);
                finish();

            }
        });
    }
}
