package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class AddNewMemberActivity extends AppCompatActivity {
    EditText editName, editPhoneNumber, editNationality, editLocation, editLevelOfEducation;
    TextView addNewMember;
    Spinner  houseHoldComposition;

    EditText groupMemberUsername, groupMemberLocation, groupMemberPhone, groupMemberBusiness,
            groupMemberNationality, groupMemberEducationLevel, groupMemberGender, groupMemberHousehold;
    CardView groupMemberRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_member);

        groupMemberBusiness = (EditText) findViewById(R.id.groupMembersBusiness);
        groupMemberUsername = (EditText) findViewById(R.id.groupMemberUsername);
        groupMemberLocation = (EditText) findViewById(R.id.groupMemberLocation);
        groupMemberPhone = (EditText) findViewById(R.id.groupMembersPhoneNumber);
        groupMemberNationality = (EditText) findViewById(R.id.groupMembersNationality);
        groupMemberEducationLevel = (EditText) findViewById(R.id.groupMembersEducationLevel);
        groupMemberGender = (EditText) findViewById(R.id.groupMembersGender);
        groupMemberHousehold = (EditText) findViewById(R.id.groupMembersHousehold);

        groupMemberRegister = (CardView)findViewById(R.id.groupMemberRegister);

        groupMemberRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mUsername = groupMemberUsername.getText().toString().trim();
                String mUserPhone = groupMemberPhone.getText().toString().trim();
                String mUserHousehold = groupMemberHousehold.getText().toString().trim();
                String mUserBusiness = groupMemberBusiness.getText().toString().trim();
                String mUserGender = groupMemberGender.getText().toString().trim();
                String mUserEducationLevel = groupMemberEducationLevel.getText().toString().trim();
                String mUserNationality = groupMemberNationality.getText().toString().trim();
                String mUserLocation = groupMemberLocation.getText().toString().trim();
                String currentUser = ParseUser.getCurrentUser().getObjectId();

                ParseObject groupMember = new ParseObject("Members");
                groupMember.put("username", mUsername);
                groupMember.put("phone", mUserPhone);
                groupMember.put("household", mUserHousehold);
                groupMember.put("gender", mUserGender);
                groupMember.put("business", mUserBusiness);
                groupMember.put("educationLevel", mUserEducationLevel);
                groupMember.put("nationality", mUserNationality);
                groupMember.put("location", mUserLocation);
                groupMember.put("isMember", "1");
//                groupMember.put("UserId", currentUser);

                groupMember.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            Toast.makeText(AddNewMemberActivity.this, "Group Member Added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddNewMemberActivity.this, GroupMembersActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(AddNewMemberActivity.this, "Failed to add a group member", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

    }

    private void addNewGroupMember(String name, String phone) {
        Toast.makeText(this, "Adding a new Member.....", Toast.LENGTH_SHORT).show();

    }
}
