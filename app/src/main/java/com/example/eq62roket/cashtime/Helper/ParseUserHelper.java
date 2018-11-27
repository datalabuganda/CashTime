package com.example.eq62roket.cashtime.Helper;


import android.content.Context;
import android.util.Log;

import com.example.eq62roket.cashtime.Models.GroupMember;
import com.example.eq62roket.cashtime.Models.User;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 3/28/18.
 */

@ParseClassName("GroupMember")
public class ParseUserHelper {
    public interface OnReturnedUserListener{
        void onResponse(List<User> usersList);
        void onFailure(String error);
    }


    private static final String TAG = "ParseUserHelper";
    private final List<User> userList = new ArrayList<>();


    private Context mContext;
    private ParseUserHelper.OnReturnedUserListener mOnReturnedUserListener;


    public ParseUserHelper(Context context){
        mContext = context;
    }

    public void saveUserToParseDb(User user){
        User newUser = new User();
        newUser.put("userName", user.getUserName());
        newUser.put("phoneNumber", user.getPhoneNumber());
        newUser.put("household", user.getHousehold());
        newUser.put("business", user.getBusiness());
//        newUser.put("gender", user.getGender());
//        newUser.put("educationLevel", user.getEducationLevel());
//        newUser.put("nationality", user.getNationality());
        newUser.put("location", user.getLocation());
        newUser.saveInBackground();

    }

    public void getUserFromParseDb(final ParseUserHelper.OnReturnedUserListener onReturnedUserListener){
        ParseQuery<GroupMember> userQuery = ParseQuery.getQuery("GroupMember");
        userQuery.addDescendingOrder("updatedAt");
        userQuery.findInBackground(new FindCallback<GroupMember>() {
            @Override
            public void done(List<GroupMember> parseUser, ParseException e) {
                if (e == null){
                    for (GroupMember retrievedUser: parseUser){
                        User newUser = new User();
                        newUser.setUserName(retrievedUser.get("userName").toString());
                        newUser.setPhoneNumber(retrievedUser.get("phoneNumber").toString());
                        newUser.setHousehold(retrievedUser.get("household").toString());
                        newUser.setBusiness(retrievedUser.get("business").toString());
                        newUser.setGender(retrievedUser.get("gender").toString());
                        newUser.setEducationLevel(retrievedUser.get("educationLevel").toString());
                        newUser.setNationality(retrievedUser.get("nationality").toString());
                        newUser.setLocation(retrievedUser.get("location").toString());
                        newUser.setParseId(retrievedUser.getObjectId());

                        userList.add(newUser);
                    }
                    if (onReturnedUserListener != null){
                        onReturnedUserListener.onResponse(userList);
                    }
                }else {
                    onReturnedUserListener.onFailure(e.getMessage());
                }
            }

        });

    }


    public void updateUserInParseDb(final User userToUpdate){
        ParseQuery<User> userQuery = ParseQuery.getQuery("User");
        userQuery.fromLocalDatastore();
        userQuery.whereEqualTo("objectId", userToUpdate.getParseId());
        userQuery.getInBackground(userToUpdate.getParseId(), new GetCallback<User>() {
            @Override
            public void done(User user, ParseException e) {
                if (e == null) {
                    user.put("username", userToUpdate.getUserName());
                    user.put("userPhone", userToUpdate.getPhoneNumber());
                    user.put("userHousehold", userToUpdate.getHousehold());
                    user.put("userBusiness", userToUpdate.getBusiness());
                    user.put("userGender", userToUpdate.getGender());
                    user.put("userEducationLevel", userToUpdate.getEducationLevel());
                    user.put("userNationality", userToUpdate.getNationality());
                    user.put("userLocation", userToUpdate.getLocation());
                    user.saveInBackground();

                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }

        });
    }

    public void deleteUserFromParseDb(User userToDelete){
        ParseQuery<User> userQuery = ParseQuery.getQuery("User");
        userQuery.getInBackground(userToDelete.getParseId(), new GetCallback<User>() {
            @Override
            public void done(User user, ParseException e) {
                if (e == null) {
                    Log.d(TAG, "Should delete now: ");
                    user.deleteInBackground();
                }else {
                    Log.d(TAG, "Error Occured: " + e.getMessage());
                }
            }
        });
    }

}
