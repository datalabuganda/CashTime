package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegistrationActivity extends AppCompatActivity {
    EditText username, userPhone, userHousehold, userBusiness, userGender, userEducationLevel, userNationality, userLocation, userPassword;
    TextView userRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Parse.initialize(this);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        username = (EditText)findViewById(R.id.username);
        userPhone = (EditText)findViewById(R.id.userPhoneNumber);
        userHousehold = (EditText)findViewById(R.id.userHousehold);
        userBusiness = (EditText)findViewById(R.id.userBusiness);
        userGender = (EditText)findViewById(R.id.userGender);
        userEducationLevel = (EditText)findViewById(R.id.userEducationLevel);
        userNationality = (EditText)findViewById(R.id.userNationality);
        userLocation = (EditText)findViewById(R.id.userLocation);
        userPassword = (EditText)findViewById(R.id.userPassword);

        userRegister = (TextView) findViewById(R.id.userRegister);

        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mUsername = username.getText().toString().trim();
                String mUserPhone = userPhone.getText().toString().trim();
                String mUserHousehold = userHousehold.getText().toString().trim();
                String mUserBusiness = userBusiness.getText().toString().trim();
                String mUserGender = userGender.getText().toString().trim();
                String mUserEducationLevel = userEducationLevel.getText().toString().trim();
                String mUserNationality = userNationality.getText().toString().trim();
                String mUserLocation = userLocation.getText().toString().trim();
                String mUserPassword = userPassword.getText().toString().trim();

                ParseUser user = new ParseUser();
                user.setUsername(mUsername);
                user.setPassword(mUserPassword);
                user.put("phone", mUserPhone);
                user.put("household", mUserHousehold);
                user.put("gender", mUserGender);
                user.put("business", mUserBusiness);
                user.put("educationLevel", mUserEducationLevel);
                user.put("nationality", mUserNationality);
                user.put("location", mUserLocation);
                user.put("isLeader", "1");

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            Intent homeIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(homeIntent);
                            Toast.makeText(RegistrationActivity.this, "You have registered", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegistrationActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });




    }
}
