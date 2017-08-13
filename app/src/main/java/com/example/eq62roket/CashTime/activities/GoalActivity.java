package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.User;
import com.parse.Parse;
import com.parse.ParseObject;

public class GoalActivity extends AppCompatActivity {

    UserCrud userCrud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("462s45ze2vn6x2vrfyfenqmksngx5xbs")
                .server("https://oxfamdataservice.org/parse/")
                .build()
        );

        userCrud = new UserCrud(this);

        // Get the last inserted user
        User lastInsertedUser = userCrud.getLastUserInserted();

        // add details of this user to a ParseObject
        ParseObject user = new ParseObject("TestUser");
        user.put("HouseHoldComposition", lastInsertedUser.getHousehold());
        user.put("Age", lastInsertedUser.getAge());
        user.put("Sex", lastInsertedUser.getSex());
        user.put("CountryOfBirth", lastInsertedUser.getNationality());
        user.put("Points", lastInsertedUser.getPoints());
        user.saveInBackground();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoalActivity.this, AddGoalActivity.class);
                startActivity(intent);
            }
        });
    }
}
