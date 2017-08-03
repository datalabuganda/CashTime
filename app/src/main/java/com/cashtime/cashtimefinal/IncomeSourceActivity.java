package com.cashtime.cashtimefinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IncomeSourceActivity extends AppCompatActivity {

    EditText etAddMoney;
    Button btnOkay;

    public static int addedIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_source);

        etAddMoney = (EditText) findViewById(R.id.etAddMoney);
        btnOkay = (Button) findViewById(R.id.btnOkay);



        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addedIncome = Integer.parseInt(etAddMoney.getText().toString());
                Intent intent = new Intent(IncomeSourceActivity.this, AddIncomeActvity.class);
                startActivity(intent);
            }
        });
    }
}
