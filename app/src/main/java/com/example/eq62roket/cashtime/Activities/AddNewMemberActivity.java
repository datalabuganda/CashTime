package com.example.eq62roket.cashtime.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.R;

public class AddNewMemberActivity extends AppCompatActivity {
    EditText editName, editPhoneNumber, editNationality, editLocation, editLevelOfEducation;
    TextView addNewMember;
    Spinner  houseHoldComposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_member);

        editName = (EditText)findViewById(R.id.memberName);
        editPhoneNumber = (EditText)findViewById(R.id.memberPhoneNumber);
        editNationality = (EditText)findViewById(R.id.memberNationality);
        houseHoldComposition = (Spinner)findViewById(R.id.memberHouseholdCompostion);
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

    private void addNewGroupMember(String name, String phone) {
        Toast.makeText(this, "Adding a new Member.....", Toast.LENGTH_SHORT).show();
    }

}
