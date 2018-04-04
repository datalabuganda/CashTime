package com.example.eq62roket.cashtime.Helper;

import android.content.Context;
import android.util.Log;

import com.example.eq62roket.cashtime.Models.GroupExpenditure;
import com.example.eq62roket.cashtime.Models.GroupMember;
import com.example.eq62roket.cashtime.Models.GroupMemberExpenditure;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 3/28/18.
 */

public class ParseExpenditureHelper {
    public interface OnReturnedGroupExpenditureListener{
        void onResponse(List<GroupExpenditure> groupExpendituresList);
        void onFailure(String error);
    }

    public interface OnReturnedGroupMembersExpenditureListener{
        void onResponse(List<GroupMemberExpenditure> groupMembersExpendituresList);
        void onFailure(String error);
    }

    private static final String TAG = "ParseExpenditureHelper";
    private final List<GroupExpenditure> groupExpenditureList = new ArrayList<>();
    private final List<GroupMemberExpenditure> groupMembersExpenditureList = new ArrayList<>();


    private Context mContext;
    private ParseExpenditureHelper.OnReturnedGroupExpenditureListener mOnReturnedGroupExpenditureListener;
    private ParseExpenditureHelper.OnReturnedGroupMembersExpenditureListener mOnReturnedGroupMembersExpenditureListener;

    public ParseExpenditureHelper(Context context){
        mContext = context;
    }

    /**********************************  Group Expenditure Parse Helper *****************************/
    public void saveGroupExpenditureToParseDb(GroupExpenditure groupExpenditure){
        GroupExpenditure newGroupExpenditure = new GroupExpenditure();
        newGroupExpenditure.put("groupExpenditureCategory", groupExpenditure.getCategory());
        newGroupExpenditure.put("groupExpenditureAmount", groupExpenditure.getAmount());
        newGroupExpenditure.put("groupExpenditureNotes", groupExpenditure.getNotes());
        newGroupExpenditure.put("groupExpenditureDueDate", groupExpenditure.getDueDate());
        newGroupExpenditure.put("groupName", groupExpenditure.getGroupName());
        newGroupExpenditure.put("groupParseId", groupExpenditure.getGroupParseId());
        newGroupExpenditure.put("createdById", groupExpenditure.getUserId());
        newGroupExpenditure.saveInBackground();

    }

    public void getGroupExpenditureFromParseDb(final ParseExpenditureHelper.OnReturnedGroupExpenditureListener onReturnedGroupExpenditureListener){
        ParseQuery<GroupExpenditure> groupExpenditureQuery = ParseQuery.getQuery("GroupExpenditure");
        String currentUser = ParseUser.getCurrentUser().getObjectId();
        groupExpenditureQuery.addDescendingOrder("updatedAt");
        groupExpenditureQuery.whereEqualTo("createdById", currentUser);
        groupExpenditureQuery.findInBackground(new FindCallback<GroupExpenditure>() {
            @Override
            public void done(List<GroupExpenditure> parseGroupExpenditure, ParseException e) {
                if (e == null){
                    for (GroupExpenditure retrievedGroupExpenditure: parseGroupExpenditure){
                        GroupExpenditure newGroupExpenditure = new GroupExpenditure();
                        newGroupExpenditure.setCategory(retrievedGroupExpenditure.get("groupExpenditureCategory").toString());
                        newGroupExpenditure.setAmount(retrievedGroupExpenditure.get("groupExpenditureAmount").toString());
                        newGroupExpenditure.setNotes(retrievedGroupExpenditure.get("groupExpenditureNotes").toString());
                        newGroupExpenditure.setDueDate(retrievedGroupExpenditure.get("groupExpenditureDueDate").toString());
                        newGroupExpenditure.setGroupName(retrievedGroupExpenditure.get("groupName").toString());
                        newGroupExpenditure.setParseId(retrievedGroupExpenditure.getObjectId());

                        groupExpenditureList.add(newGroupExpenditure);
                    }
                    if (onReturnedGroupExpenditureListener != null){
                        onReturnedGroupExpenditureListener.onResponse(groupExpenditureList);
                    }
                }else {
                    onReturnedGroupExpenditureListener.onFailure(e.getMessage());
                }
            }

        });
    }

    public void updateGroupExpenditureInParseDb(final GroupExpenditure groupExpenditureToUpdate){
        ParseQuery<GroupExpenditure> groupExpenditureQuery = ParseQuery.getQuery("GroupExpenditure");
        groupExpenditureQuery.getInBackground(groupExpenditureToUpdate.getParseId(), new GetCallback<GroupExpenditure>() {
            @Override
            public void done(GroupExpenditure groupExpenditure, ParseException e) {
                if (e == null) {
                    groupExpenditure.put("groupExpenditureCategory", groupExpenditureToUpdate.getCategory());
                    groupExpenditure.put("groupExpenditureAmount", groupExpenditureToUpdate.getAmount());
                    groupExpenditure.put("groupExpenditureNotes", groupExpenditureToUpdate.getNotes());
                    groupExpenditure.put("groupExpenditureDueDate", groupExpenditureToUpdate.getDueDate());
                    groupExpenditure.saveInBackground();

                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }

        });
    }

    public void deleteGroupExpenditureFromParseDb(GroupExpenditure groupExpenditureToDelete){
        ParseQuery<GroupExpenditure> groupExpenditureQuery = ParseQuery.getQuery("GroupExpenditure");
        groupExpenditureQuery.getInBackground(groupExpenditureToDelete.getParseId(), new GetCallback<GroupExpenditure>() {
            @Override
            public void done(GroupExpenditure groupExpenditure, ParseException e) {
                if (e == null) {
                    Log.d(TAG, "Should delete now: ");
                    groupExpenditure.deleteInBackground();
                }else {
                    Log.d(TAG, "Error Occured: " + e.getMessage());
                }
            }
        });
    }

    /******************************** Members Expenditure Parse Helper ******************************/
    public void saveGroupMembersExpenditureToParseDb(GroupMemberExpenditure groupMembersExpenditure){
        GroupMemberExpenditure newGroupMembersExpenditure = new GroupMemberExpenditure();
        newGroupMembersExpenditure.put("groupMembersExpenditureCategory", groupMembersExpenditure.getCategory());
        newGroupMembersExpenditure.put("groupMembersExpenditureAmount", groupMembersExpenditure.getAmount());
        newGroupMembersExpenditure.put("groupMembersExpenditureNotes", groupMembersExpenditure.getNotes());
        newGroupMembersExpenditure.put("groupMembersExpenditureDate", groupMembersExpenditure.getDueDate());
        newGroupMembersExpenditure.put("groupMemberUsername", groupMembersExpenditure.getMemberUserName());
        newGroupMembersExpenditure.put("groupMemberParseId", groupMembersExpenditure.getMemberParseId());
        newGroupMembersExpenditure.put("createdById", groupMembersExpenditure.getUserId());
        newGroupMembersExpenditure.saveInBackground();
    }

    public void getGroupMembersExpenditureFromParseDb(final ParseExpenditureHelper.OnReturnedGroupMembersExpenditureListener onReturnedGroupMembersExpenditureListener){
        ParseQuery<GroupMemberExpenditure> groupMemberExpenditureQuery = ParseQuery.getQuery("GroupMembersExpenditure");
        String currentUser = ParseUser.getCurrentUser().getObjectId();
        groupMemberExpenditureQuery.addDescendingOrder("updatedAt");
        groupMemberExpenditureQuery.whereEqualTo("createdById", currentUser);
        groupMemberExpenditureQuery.findInBackground(new FindCallback<GroupMemberExpenditure>() {
            @Override
            public void done(List<GroupMemberExpenditure> parseGroupMemberExpenditure, ParseException e) {
                if (e == null){
                    for (GroupMemberExpenditure retrievedGroupMemberExpenditure: parseGroupMemberExpenditure){
                        GroupMemberExpenditure newGroupMembersExpenditure = new GroupMemberExpenditure();
                        newGroupMembersExpenditure.setCategory(retrievedGroupMemberExpenditure.get("groupMembersExpenditureCategory").toString());
                        newGroupMembersExpenditure.setAmount(retrievedGroupMemberExpenditure.get("groupMembersExpenditureAmount").toString());
                        newGroupMembersExpenditure.setNotes(retrievedGroupMemberExpenditure.get("groupMembersExpenditureNotes").toString());
                        newGroupMembersExpenditure.setDueDate(retrievedGroupMemberExpenditure.get("groupMembersExpenditureDate").toString());
                        newGroupMembersExpenditure.setMemberUserName(retrievedGroupMemberExpenditure.get("groupMemberUsername").toString());
                        newGroupMembersExpenditure.setParseId(retrievedGroupMemberExpenditure.getObjectId());

                        groupMembersExpenditureList.add(newGroupMembersExpenditure);
                    }
                    if (onReturnedGroupMembersExpenditureListener != null){
                        onReturnedGroupMembersExpenditureListener.onResponse(groupMembersExpenditureList);
                    }
                }else {
                    onReturnedGroupMembersExpenditureListener.onFailure(e.getMessage());
                }
            }

        });

    }

    public void updateGroupMembersExpenditureInParseDb(final GroupMemberExpenditure groupMembersExpenditureToUpdate){
        ParseQuery<GroupMemberExpenditure> groupMembersExpenditureQuery = ParseQuery.getQuery("GroupMembersExpenditure");
        groupMembersExpenditureQuery.getInBackground(groupMembersExpenditureToUpdate.getParseId(), new GetCallback<GroupMemberExpenditure>() {
            @Override
            public void done(GroupMemberExpenditure groupMemberExpenditure, ParseException e) {
                if (e == null) {
                    groupMemberExpenditure.put("groupMembersExpenditureCategory", groupMembersExpenditureToUpdate.getCategory());
                    groupMemberExpenditure.put("groupMembersExpenditureAmount", groupMembersExpenditureToUpdate.getAmount());
                    groupMemberExpenditure.put("groupMembersExpenditureNotes", groupMembersExpenditureToUpdate.getNotes());
                    groupMemberExpenditure.put("groupMembersExpenditureDate", groupMembersExpenditureToUpdate.getDueDate());
                    groupMemberExpenditure.saveInBackground();

                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }

        });
    }

    public void deleteGroupMembersExpenditureFromParseDb(GroupMemberExpenditure groupMembersExpenditureToDelete){
        ParseQuery<GroupMemberExpenditure> groupMemberExpenditureQuery = ParseQuery.getQuery("GroupMembersExpenditure");
        groupMemberExpenditureQuery.getInBackground(groupMembersExpenditureToDelete.getParseId(), new GetCallback<GroupMemberExpenditure>() {
            @Override
            public void done(GroupMemberExpenditure groupMemberExpenditure, ParseException e) {
                if (e == null) {
                    Log.d(TAG, "Should delete now: ");
                    groupMemberExpenditure.deleteInBackground();
                }else {
                    Log.d(TAG, "Error Occured: " + e.getMessage());
                }
            }
        });
    }

}
