package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Helper.ParseUserHelper;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class AddNewMemberActivity extends AppCompatActivity {
    private static final String TAG = "AddNewMemberActivity";
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
                saveUser();
            }
        });

    }

    private void saveUser(){
        // add new group goal to db
        if ( !groupMemberUsername.getText().toString().equals("") &&
                !groupMemberPhone.getText().toString().equals("")){
            String mUsername = groupMemberUsername.getText().toString().trim();
            String mUserPhone = groupMemberPhone.getText().toString().trim();
            String mUserHousehold = groupMemberHousehold.getText().toString().trim();
            String mUserBusiness = groupMemberBusiness.getText().toString().trim();
            String mUserGender = groupMemberGender.getText().toString().trim();
            String mUserEducationLevel = groupMemberEducationLevel.getText().toString().trim();
            String mUserNationality = groupMemberNationality.getText().toString().trim();
            String mUserLocation = groupMemberLocation.getText().toString().trim();


            User user = new User();
            user.setUserName(mUsername);
            user.setPhoneNumber(mUserPhone);
            user.setHousehold(mUserHousehold);
            user.setGender(mUserGender);
            user.setBusiness(mUserBusiness);
            user.setEducationLevel(mUserEducationLevel);
            user.setNationality(mUserNationality);
            user.setLocation(mUserLocation);


            Log.d(TAG, "user: " + user);

            // TODO: 3/22/18 =====> save object to db
            new ParseUserHelper(this).saveUserToParseDb(user);
            startGroupMembersActivity();

            Toast.makeText(AddNewMemberActivity.this, "Group Goal " + user.getUserName() + " saved", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(AddNewMemberActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startGroupMembersActivity(){
        Intent tabbedGoalsIntent = new Intent(AddNewMemberActivity.this, GroupMembersActivity.class);
        startActivity(tabbedGoalsIntent);
        finish();
    }
}
