package com.example.eq62roket.cashtime.Activities;

import android.support.annotation.MainThread;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.DatabaseHelper;
import com.example.eq62roket.cashtime.adapters.DatabaseAdapter;

import com.example.eq62roket.cashtime.R;

public class AddNewMemberActivity extends AppCompatActivity {
    DatabaseHelper myDatabaseHelper;
    EditText editName, editPhoneNumber, editNationality, editHouseHoldComposition, editLocation, editLevelOfEducation;
    TextView addNewMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_member);
        myDatabaseHelper = new DatabaseHelper(this);

        editName = (EditText)findViewById(R.id.memberName);
        editPhoneNumber = (EditText)findViewById(R.id.memberPhoneNumber);
        editNationality = (EditText)findViewById(R.id.memberNationality);
        editHouseHoldComposition = (EditText)findViewById(R.id.memberHouseholdCompostion);
        editLocation = (EditText)findViewById(R.id.memberLocation);
        editLevelOfEducation= (EditText)findViewById(R.id.memberLevelOfEducation);

        addNewMember = (TextView) findViewById(R.id.addNewMemberText);

        addNewMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewGroupMember(editName.getText().toString(),editPhoneNumber.getText().toString());

            }
        });

    }

    private void addNewGroupMember(String name, String phone){
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);

        databaseAdapter.openDatabase();

        long result = databaseAdapter.addNewGroupMember(name,phone);

        if (result>0){
            editName.setText("");
            editPhoneNumber.setText("");
            Toast.makeText(AddNewMemberActivity.this,"New member added", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(AddNewMemberActivity.this, "Failed to add new member", Toast.LENGTH_SHORT).show();

        }

        databaseAdapter.close();
    }

}
