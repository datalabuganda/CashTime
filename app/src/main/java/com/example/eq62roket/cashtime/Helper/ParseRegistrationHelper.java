package com.example.eq62roket.cashtime.Helper;

import android.content.Context;

import com.example.eq62roket.cashtime.Interfaces.OnSuccessfulLoginListener;
import com.example.eq62roket.cashtime.Interfaces.OnSuccessfulRegistrationListener;
import com.example.eq62roket.cashtime.Models.User;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by etwin on 3/28/18.
 */

public class ParseRegistrationHelper {

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
        parseUser.put("isGroupMember", newUser.getGroupMember());
        parseUser.put("userPoints", newUser.getPoints());
        parseUser.put("groupId", newUser.getGroupId());
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

    public void loginUserToParseDb(User registeredUser, final OnSuccessfulLoginListener onSuccessfulLoginListener){
        ParseUser.logInInBackground(registeredUser.getUserName(), registeredUser.getPassword(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null){
                    onSuccessfulLoginListener.onResponse("successful login");

                }else {
                    onSuccessfulLoginListener.onFailure(e.getMessage());
                }

            }
        });
    }
}
