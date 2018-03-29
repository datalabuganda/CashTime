package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseRegistrationHelper;
import com.example.eq62roket.cashtime.Interfaces.OnSuccessfulRegistrationListener;
import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;

public class AddNewMemberActivity extends AppCompatActivity {

    EditText groupMemberUsername, groupMemberLocation, groupMemberPhone, groupMemberBusiness,
            groupMemberNationality, groupMemberEducationLevel, groupMemberGender, groupMemberHousehold;
    CardView groupMemberRegister;

    private String groupParseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_member);

        Intent addNewMemberIntent = getIntent();
        groupParseId = addNewMemberIntent.getStringExtra("groupParseId");

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

                if (!groupMemberUsername.getText().toString().equals("") &&
                        !groupMemberPhone.getText().toString().equals("") &&
                        !groupMemberHousehold.getText().toString().equals("") &&
                        !groupMemberBusiness.getText().toString().equals("") &&
                        !groupMemberGender.getText().toString().equals("") &&
                        !groupMemberEducationLevel.getText().toString().equals("") &&
                        !groupMemberNationality.getText().toString().equals("") &&
                        !groupMemberLocation.getText().toString().equals("")){

                    String mUsername = groupMemberUsername.getText().toString().trim();
                    String mUserPhone = groupMemberPhone.getText().toString().trim();
                    String mUserHousehold = groupMemberHousehold.getText().toString().trim();
                    String mUserBusiness = groupMemberBusiness.getText().toString().trim();
                    String mUserGender = groupMemberGender.getText().toString().trim();
                    String mUserEducationLevel = groupMemberEducationLevel.getText().toString().trim();
                    String mUserNationality = groupMemberNationality.getText().toString().trim();
                    String mUserLocation = groupMemberLocation.getText().toString().trim();

                    User newgroupMember = new User();
                    newgroupMember.setUserName(mUsername);
                    newgroupMember.setPhoneNumber(mUserPhone);
                    newgroupMember.setHousehold(mUserHousehold);
                    newgroupMember.setBusiness(mUserBusiness);
                    newgroupMember.setGender(mUserGender);
                    newgroupMember.setEducationLevel(mUserEducationLevel);
                    newgroupMember.setNationality(mUserNationality);
                    newgroupMember.setLocation(mUserLocation);
                    newgroupMember.setPassword(mUserPhone);
                    newgroupMember.setIsLeader(false);
                    newgroupMember.setGroupMember(true);
                    newgroupMember.setGroupId(groupParseId);
                    newgroupMember.setPoints(3);

                    new ParseRegistrationHelper(AddNewMemberActivity.this)
                            .saveRegisteredUserToParseDb(newgroupMember, new OnSuccessfulRegistrationListener() {
                                @Override
                                public void onResponse(String success) {
                                    Intent intent = new Intent(AddNewMemberActivity.this, GroupMembersActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(AddNewMemberActivity.this, "Group Member Added", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onFailure(String error) {
                                    Toast.makeText(AddNewMemberActivity.this, "Member not Added " + error, Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {
                    Toast.makeText(AddNewMemberActivity.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
