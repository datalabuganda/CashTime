package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.eq62roket.cashtime.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    TextView profilePhone, profileUsername, profileNationality, profileLocation, profileBusiness,
            numberOfGroups, numberOfGroupGoals, numberOfMemberGoals, numberOfCompletedGroupGoals,
            numberOfIncompleteGroupGoals, numberOfFailedGroupGoals, numberOfMembersCompletedGoals, numberOfMembersIncompleteGoals,
            numberOfGroupMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profilePhone = (TextView)findViewById(R.id.profilePhone);
        profileUsername = (TextView)findViewById(R.id.profileUsername);
        profileNationality = (TextView)findViewById(R.id.profileNationality);
        profileLocation = (TextView)findViewById(R.id.profileLocation);
        profileBusiness = (TextView)findViewById(R.id.profileBusiness);
        numberOfGroups = (TextView)findViewById(R.id.numberOfGroups);
        numberOfGroupMembers = (TextView)findViewById(R.id.numberOfGroupMembers);
        numberOfGroupGoals = (TextView)findViewById(R.id.numberOfGroupGoals);
        numberOfCompletedGroupGoals = (TextView)findViewById(R.id.completeGroupGoals);
        numberOfIncompleteGroupGoals = (TextView)findViewById(R.id.incompleteGroupGoals);
        numberOfFailedGroupGoals = (TextView)findViewById(R.id.failedGroupGoals);

        Parse.initialize(this);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null){
            String username = currentUser.getString("username");
            String phone = currentUser.getString("userPhone");
            String location = currentUser.getString("userLocation");
            String business = currentUser.getString("userBusiness");
            String nationality = currentUser.getString("userNationality");
            int totalGroups = this.totalNumberOfGroups();
            int totalGroupMembers = this.totalNumberOfGroupMembers();

            profileUsername.setText(username);
            profilePhone.setText(phone);
            profileNationality.setText(nationality);
            profileLocation.setText(location);
            profileBusiness.setText(business);
            numberOfGroups.setText(Integer.toString(totalGroups));
            numberOfGroupMembers.setText(Integer.toString(totalGroupMembers));
        }
        totalNumberOfGroups();
        totalNumberOfGroupGoals();
        totalNumberOfGroupMembers();
        totalNumberOfCompleteGroupGoals();
        totalNumberOfIncompleteGroupGoals();
        totalNumberOfFailedGroupGoals();
    }

    public int totalNumberOfGroups(){
        int numberOfGroups = 0;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_Groups");
        query.whereEqualTo("groupLeaderId", currentUserId);
        try {
            List<ParseObject> results = query.find();
            numberOfGroups = results.size();
            Log.d("group counts", "totalNumberOfGroups: " + results.size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfGroups;
    }

    /********************************* Total Number Of Group Goals ********************************/

    public int totalNumberOfGroupGoals(){
        int numberOfGroupGoals = 0;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupGoals");
        query.whereEqualTo("userId", currentUserId);
        try {
            List<ParseObject> results = query.find();
            numberOfGroupGoals = results.size();
            Log.d("goals", "totalNumberOfGroupsGoals: " + results.size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfGroupGoals;
    }

    /********************************* Total Number Of Incomplete Group Goals ********************************/

    public int totalNumberOfIncompleteGroupGoals(){
        int numberOfIncompleteGroupGoals = 0;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupGoals");
        query.whereEqualTo("userId", currentUserId);
        query.whereContains("goalStatus", "incomplete");
        try {
            List<ParseObject> results = query.find();
            numberOfIncompleteGroupGoals = results.size();
            Log.d("Incomplete group goals", "totalNumberOfIncompleteGroupsGoals: " + results.size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfIncompleteGroupGoals;
    }

    /********************************* Total Number Of Group Goals ********************************/

    public int totalNumberOfCompleteGroupGoals(){
        int numberOfCompleteGroupGoals = 0;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupGoals");
        query.whereEqualTo("userId", currentUserId);
        query.whereContains("goalStatus", "completed");
        try {
            List<ParseObject> results = query.find();
            numberOfCompleteGroupGoals = results.size();
            Log.d("Complete group goals", "totalNumberOfCompleteGroupsGoals: " + results.size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfCompleteGroupGoals;
    }

    /********************************* Total Number Of Group Goals ********************************/

    public int totalNumberOfFailedGroupGoals(){
        int numberOfFailedGroupGoals = 0;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupGoals");
        query.whereEqualTo("userId", currentUserId);
        query.whereContains("goalStatus", "failed");
        try {
            List<ParseObject> results = query.find();
            numberOfFailedGroupGoals = results.size();
            Log.d("Failed group goals", "totalNumberOfFailedGroupsGoals: " + results.size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfFailedGroupGoals;
    }

    /******************************** Total Number Of Group Members *******************************/

    public int totalNumberOfGroupMembers(){
        int numberOfGroupMembers = 0;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupMembers");
        query.whereEqualTo("memberCreatorId", currentUserId);
        try {
            List<ParseObject> results = query.find();
            numberOfGroupMembers = results.size();
            Log.d("group members", "totalNumberOfGroupMembers: " + results.size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfGroupMembers;
    }

    /****************************** Total Number Of incomplete group goals*************************/
}
