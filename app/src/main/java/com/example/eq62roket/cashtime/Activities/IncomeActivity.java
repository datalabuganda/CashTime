package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.eq62roket.cashtime.R;

public class IncomeActivity extends AppCompatActivity {
    private FloatingActionButton selectGroupMembersForIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        selectGroupMembersForIncome = (FloatingActionButton) findViewById(R.id.selectGroupMembersForIncome);

        selectGroupMembersForIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addMemberIntent = new Intent(IncomeActivity.this, GroupMembersListIncomeActivity.class);
                startActivity(addMemberIntent);
            }
        });
    }
}
