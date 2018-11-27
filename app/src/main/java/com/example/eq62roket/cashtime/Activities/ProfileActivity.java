package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    TextView profilePhone, profileUsername, profileNationality, profileLocation, profileBusiness,
            numberOfGroups, numberOfGroupGoals, numberOfMemberGoals, numberOfCompletedGroupGoals,
            numberOfIncompleteGroupGoals, numberOfFailedGroupGoals, numberOfMembersCompletedGoals,
            numberOfMembersIncompleteGoals,  numberOfGroupMembers, numberOfMembersFailedGoals,
            numberOfTips, editProfile;

    List<User> users = null;

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
        numberOfMemberGoals = (TextView)findViewById(R.id.numberOfMembersGoals);
        numberOfMembersCompletedGoals = (TextView)findViewById(R.id.completeMembersGoals);
        numberOfMembersIncompleteGoals = (TextView)findViewById(R.id.incompleteMembersGoals);
        numberOfMembersFailedGoals = (TextView)findViewById(R.id.failedMembersGoals);
        numberOfTips = (TextView)findViewById(R.id.tips);
        editProfile = findViewById(R.id.editProfile);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null){
            final String username = currentUser.getString("username");
            final String phone = currentUser.getString("userPhone");
            final String location = currentUser.getString("userLocation");
            final String business = currentUser.getString("userBusiness");
            final String nationality = currentUser.getString("userNationality");

            final String household = currentUser.getString("userHousehold");
            final String gender = currentUser.getString("userGender");
            final String education = currentUser.getString("userEducationLevel");

            int totalGroups = this.totalNumberOfGroups();
            int totalGroupMembers = this.totalNumberOfGroupMembers();
            int totalGroupGoals = this.totalNumberOfGroupGoals();
            int totalIncompleteGoals = this.totalNumberOfIncompleteGroupGoals();
            int totalFailedGroupGoals = this.totalNumberOfFailedGroupGoals();
            int totalCompleteGroupGoals = this.totalNumberOfCompleteGroupGoals();
            int totalCompleteMembersGoals = this.totalNumberOfCompleteMembersGoals();
            int totalIncompleteMembersGoals = this.totalNumberOfIncompleteMembersGoals();
            int totalFailedMembersGoals = this.totalNumberOfFailedMembersGoals();
            int totalMembersGoals = this.totalNumberOfMembersGoals();
            int totalTips = this.totalNumberOfTips();

            profileUsername.setText(username);
            profilePhone.setText(phone);
            profileNationality.setText(nationality);
            profileLocation.setText(location);
            profileBusiness.setText(business);
            numberOfGroups.setText(Integer.toString(totalGroups));
            numberOfGroupMembers.setText(Integer.toString(totalGroupMembers));
            numberOfGroupGoals.setText(Integer.toString(totalGroupGoals));
            numberOfIncompleteGroupGoals.setText(Integer.toString(totalIncompleteGoals));
            numberOfFailedGroupGoals.setText(Integer.toString(totalFailedGroupGoals));
            numberOfCompletedGroupGoals.setText(Integer.toString(totalCompleteGroupGoals));
            numberOfMembersFailedGoals.setText(Integer.toString(totalFailedMembersGoals));
            numberOfMembersCompletedGoals.setText(Integer.toString(totalCompleteMembersGoals));
            numberOfMembersIncompleteGoals.setText(Integer.toString(totalIncompleteMembersGoals));
            numberOfMemberGoals.setText(Integer.toString(totalMembersGoals));
            numberOfTips.setText(Integer.toString(totalTips));

            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String currentUserId = ParseUser.getCurrentUser().getObjectId();
                    Intent editProfileIntent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                    editProfileIntent.putExtra("objectId", currentUserId);
                    editProfileIntent.putExtra("userLocation", location);
                    editProfileIntent.putExtra("userPhone", phone);
                    editProfileIntent.putExtra("userEducationLevel", education);
                    editProfileIntent.putExtra("userBusiness", business);
                    editProfileIntent.putExtra("userGender", gender);
                    editProfileIntent.putExtra("userNationality", nationality);
                    editProfileIntent.putExtra("userHousehold", household);
                    editProfileIntent.putExtra("username", username);
                    startActivity(editProfileIntent);
                }
            });

        }
        totalNumberOfGroups();
        totalNumberOfGroupGoals();
        totalNumberOfGroupMembers();
        totalNumberOfCompleteGroupGoals();
        totalNumberOfIncompleteGroupGoals();
        totalNumberOfFailedGroupGoals();
        totalNumberOfCompleteMembersGoals();
        totalNumberOfIncompleteMembersGoals();
        totalNumberOfFailedMembersGoals();
        totalNumberOfMembersGoals();
    }


    public int totalNumberOfGroups(){
        int numberOfGroups = 0;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_Groups");
        query.fromLocalDatastore();
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
        query.fromLocalDatastore();
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
        query.fromLocalDatastore();
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

    /********************************* Total Number Of complete Group Goals ********************************/

    public int totalNumberOfCompleteGroupGoals(){
        int numberOfCompleteGroupGoals = 0;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupGoals");
        query.fromLocalDatastore();
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

    /********************************* Total Number Of Failed Group Goals ********************************/

    public int totalNumberOfFailedGroupGoals(){
        int numberOfFailedGroupGoals = 0;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupGoals");
        query.fromLocalDatastore();
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
        query.fromLocalDatastore();
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

    /********************************* Total Number Of  Members Goals ********************************/

    public int totalNumberOfMembersGoals(){
        int numberOfIncompleteGroupGoals = 0;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberGoals");
        query.fromLocalDatastore();
        query.whereEqualTo("savingCreatorId", currentUserId);
//        query.whereContains("memberGoalStatus", "incomplete");
        try {
            List<ParseObject> results = query.find();
            numberOfIncompleteGroupGoals = results.size();
            Log.d("Total members goals", "totalNumberOfMembersGoals: " + results.size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfIncompleteGroupGoals;
    }


    /********************************* Total Number Of complete Group Goals ********************************/

    public int totalNumberOfCompleteMembersGoals(){
        int numberOfCompleteGroupGoals = 0;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberGoals");
        query.fromLocalDatastore();
        query.whereEqualTo("savingCreatorId", currentUserId);
        query.whereContains("memberGoalStatus", "completed");
        try {
            List<ParseObject> results = query.find();
            numberOfCompleteGroupGoals = results.size();
            Log.d("Complete member goals", "totalNumberOfCompleteMembersGoals: " + results.size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfCompleteGroupGoals;
    }

    /********************************* Total Number Of incomplete Group Goals ********************************/

    public int totalNumberOfIncompleteMembersGoals(){
        int numberOfIncompleteGroupGoals = 0;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberGoals");
        query.fromLocalDatastore();
        query.whereEqualTo("savingCreatorId", currentUserId);
        query.whereContains("memberGoalStatus", "incomplete");
        try {
            List<ParseObject> results = query.find();
            numberOfIncompleteGroupGoals = results.size();
            Log.d("Complete members goals", "totalNumberOfCompleteMembersGoals: " + results.size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfIncompleteGroupGoals;
    }

    /********************************* Total Number Of Failed Group Goals ********************************/

    public int totalNumberOfFailedMembersGoals(){
        int numberOfFailedGroupGoals = 0;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_MemberGoals");
        query.fromLocalDatastore();
        query.whereEqualTo("savingCreatorId", currentUserId);
        query.whereContains("memberGoalStatus", "failed");
        try {
            List<ParseObject> results = query.find();
            numberOfFailedGroupGoals = results.size();
            Log.d("Failed members goals", "totalNumberOfFailedMembersGoals: " + results.size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfFailedGroupGoals;
    }


    /********************************* Total Number Of Tips ************************************************/

    public int totalNumberOfTips(){
        int numberOfTips = 0;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_Tips");
        query.fromLocalDatastore();
        query.whereEqualTo("userId", currentUserId);
        try {
            List<ParseObject> results = query.find();
            numberOfTips = results.size();
            Log.d("Tips", "totalNumberOfTips: " + results.size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfTips;
    }
}
