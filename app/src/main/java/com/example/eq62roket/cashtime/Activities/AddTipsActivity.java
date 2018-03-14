package com.example.eq62roket.cashtime.Activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.cashtime.R;

public class AddTipsActivity extends AppCompatActivity {

    private Button btnAddTip;
    private TextInputEditText addGoalName;
    private EditText tipText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tips);

        addGoalName = (TextInputEditText) findViewById(R.id.addGoalName);
        tipText = (EditText) findViewById(R.id.tipText);
        btnAddTip = (Button) findViewById(R.id.btnAddTip);

        btnAddTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddTipsActivity.this, "Adding a tip ....", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
