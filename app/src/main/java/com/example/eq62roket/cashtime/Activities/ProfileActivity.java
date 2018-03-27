package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.eq62roket.cashtime.R;
import com.parse.Parse;
import com.parse.ParseUser;

public class ProfileActivity extends AppCompatActivity {
    TextView profilePhone, profileUsername, editProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profilePhone = (TextView)findViewById(R.id.profilePhone);
        profileUsername = (TextView)findViewById(R.id.profileUsername);

        Parse.initialize(this);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null){
            String username = currentUser.getString("username");
            String phone = currentUser.getString("phone");

            profileUsername.setText(username);
            profilePhone.setText(phone);
        }

    }
}
