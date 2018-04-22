package com.example.eq62roket.cashtime.Helper;

import android.content.Context;
import android.util.Log;

import com.example.eq62roket.cashtime.Interfaces.OnSuccessfulLoginListener;
import com.example.eq62roket.cashtime.Interfaces.OnSuccessfulRegistrationListener;
import com.example.eq62roket.cashtime.Models.User;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by etwin on 3/28/18.
 */

public class ParseRegistrationHelper {

    private static final String TAG = "ParseRegistrationHelper";

    private Context mContext;

    public ParseRegistrationHelper(Context context){
        mContext = context;
    }

    public void saveRegisteredUserToParseDb(User newUser, final OnSuccessfulRegistrationListener onSuccessfulRegistrationListener){
        ParseUser parseUser = new ParseUser();
        parseUser.setUsername(newUser.getUserName());
        parseUser.setPassword(newUser.getPassword());
        parseUser.put("userPhone", newUser.getPhoneNumber());
        parseUser.put("userHousehold", newUser.getHousehold());
        parseUser.put("userGender", newUser.getGender());
        parseUser.put("userBusiness", newUser.getBusiness());
        parseUser.put("userEducationLevel", newUser.getEducationLevel());
        parseUser.put("userNationality", newUser.getNationality());
        parseUser.put("userLocation", newUser.getLocation());
        parseUser.put("isLeader", newUser.getIsLeader());
        parseUser.put("userPoints", newUser.getPoints());
        parseUser.pinInBackground();
        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    onSuccessfulRegistrationListener.onResponse("successful sign up");
                }else{
                    onSuccessfulRegistrationListener.onFailure( e.getMessage());
                }
            }
        });

    }

    public void updateIsLeaderFlagInParseDb(final User userToUpdate){
        ParseQuery<ParseUser> parseUserParseQuery = ParseUser.getQuery();
        parseUserParseQuery.fromLocalDatastore();
        parseUserParseQuery.getInBackground(userToUpdate.getParseId(), new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (e == null){
                    parseUser.put("isLeader", userToUpdate.getIsLeader());
                    parseUser.pinInBackground();
                    parseUser.saveEventually();
                }else {
                    Log.d(TAG, "Error Occurred: " + e.getMessage());
                }
            }
        });
    }

    public void loginUserToParseDb(User registeredUser, final OnSuccessfulLoginListener onSuccessfulLoginListener){
        ParseUser.logInInBackground(registeredUser.getUserName(), registeredUser.getPassword(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null){
                    onSuccessfulLoginListener.onResponse("successful login");

                }else {
                    onSuccessfulLoginListener.onFailure(e.getMessage());
                    Log.d(TAG, "Error: " + e.getMessage());
                }

            }
        });
    }
}
