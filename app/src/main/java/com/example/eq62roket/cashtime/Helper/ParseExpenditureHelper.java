package com.example.eq62roket.cashtime.Helper;

import android.content.Context;
import android.util.Log;

import com.example.eq62roket.cashtime.Models.GroupExpenditure;

import com.example.eq62roket.cashtime.Models.GroupMemberExpenditure;
import com.parse.FindCallback;
import com.parse.ParseException;
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

    public interface OnReturnedGroupSumOfExpenditureListener{
        void onResponse(int sumOfGroupExpenditure);
        void onFailure(String error);
    }

    private static final String TAG = "ParseExpenditureHelper";
    private final List<GroupExpenditure> groupExpenditureList = new ArrayList<>();
    private final List<GroupMemberExpenditure> groupMembersExpenditureList = new ArrayList<>();


    private Context mContext;
    private String currentUserID;
    private ParseExpenditureHelper.OnReturnedGroupExpenditureListener mOnReturnedGroupExpenditureListener;
    private ParseExpenditureHelper.OnReturnedGroupMembersExpenditureListener mOnReturnedGroupMembersExpenditureListener;

    public ParseExpenditureHelper(Context context){
        mContext = context;
        currentUserID = ParseUser.getCurrentUser().getObjectId();
    }

    /**********************************  Group Expenditure Parse Helper *****************************/
    public void saveGroupExpenditureToParseDb(GroupExpenditure groupExpenditure){
        GroupExpenditure newGroupExpenditure = new GroupExpenditure();
        newGroupExpenditure.put("groupExpenditureLocalUniqueID", new CashTimeUtils().getUUID());
        newGroupExpenditure.put("groupExpenditureCategory", groupExpenditure.getCategory());
        newGroupExpenditure.put("groupExpenditureAmount", groupExpenditure.getAmount());
        newGroupExpenditure.put("groupExpenditureNotes", groupExpenditure.getNotes());
        newGroupExpenditure.put("groupExpenditureDate", groupExpenditure.getDate());
        newGroupExpenditure.put("groupName", groupExpenditure.getGroupName());
        newGroupExpenditure.put("groupLocalUniqueID", groupExpenditure.getGroupLocalUniqueID());
        newGroupExpenditure.put("createdById", groupExpenditure.getUserId());
        newGroupExpenditure.pinInBackground();
        newGroupExpenditure.saveEventually();

    }

    public void getGroupExpenditureFromParseDb(final ParseExpenditureHelper.OnReturnedGroupExpenditureListener onReturnedGroupExpenditureListener){
        ParseQuery<GroupExpenditure> groupExpenditureQuery = ParseQuery.getQuery("ct2_GroupExpenditure");
        groupExpenditureQuery.fromLocalDatastore();
        groupExpenditureQuery.addDescendingOrder("updatedAt");
        groupExpenditureQuery.whereEqualTo("createdById", currentUserID);
        groupExpenditureQuery.findInBackground(new FindCallback<GroupExpenditure>() {
            @Override
            public void done(List<GroupExpenditure> parseGroupExpenditure, ParseException e) {
                if (e == null){
                    for (GroupExpenditure retrievedGroupExpenditure: parseGroupExpenditure){
                        GroupExpenditure newGroupExpenditure = new GroupExpenditure();
                        newGroupExpenditure.setCategory(retrievedGroupExpenditure.get("groupExpenditureCategory").toString());
                        newGroupExpenditure.setAmount(retrievedGroupExpenditure.get("groupExpenditureAmount").toString());
                        newGroupExpenditure.setNotes(retrievedGroupExpenditure.get("groupExpenditureNotes").toString());
                        newGroupExpenditure.setDate(retrievedGroupExpenditure.get("groupExpenditureDate").toString());
                        newGroupExpenditure.setGroupName(retrievedGroupExpenditure.get("groupName").toString());
                        newGroupExpenditure.setLocalUniqueID(retrievedGroupExpenditure.get("groupExpenditureLocalUniqueID").toString());

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
        ParseQuery<GroupExpenditure> groupExpenditureQuery = ParseQuery.getQuery("ct2_GroupExpenditure");
        groupExpenditureQuery.fromLocalDatastore();
        groupExpenditureQuery.whereEqualTo("groupExpenditureLocalUniqueID", groupExpenditureToUpdate.getLocalUniqueID());
        groupExpenditureQuery.findInBackground(new FindCallback<GroupExpenditure>() {
            @Override
            public void done(List<GroupExpenditure> groupExpenditures, ParseException e) {
                if (e == null) {
                    if (groupExpenditures.size() == 1){
                        groupExpenditures.get(0).put("groupExpenditureCategory", groupExpenditureToUpdate.getCategory());
                        groupExpenditures.get(0).put("groupExpenditureAmount", groupExpenditureToUpdate.getAmount());
                        groupExpenditures.get(0).put("groupExpenditureNotes", groupExpenditureToUpdate.getNotes());
                        groupExpenditures.get(0).put("groupExpenditureDate", groupExpenditureToUpdate.getDate());
                        groupExpenditures.get(0).pinInBackground();
                        groupExpenditures.get(0).saveEventually();
                    }
                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void deleteGroupExpenditureFromParseDb(GroupExpenditure groupExpenditureToDelete){
        ParseQuery<GroupExpenditure> groupExpenditureQuery = ParseQuery.getQuery("ct2_GroupExpenditure");
        groupExpenditureQuery.fromLocalDatastore();
        groupExpenditureQuery.whereEqualTo("groupExpenditureLocalUniqueID", groupExpenditureToDelete.getLocalUniqueID());
        groupExpenditureQuery.findInBackground(new FindCallback<GroupExpenditure>() {
            @Override
            public void done(List<GroupExpenditure> groupExpenditures, ParseException e) {
                if (e == null) {
                    if (groupExpenditures.size() == 1){
                        groupExpenditures.get(0).unpinInBackground();
                        groupExpenditures.get(0).deleteEventually();
                    }
                }else {
                    Log.d(TAG, "Error Occurred: " + e.getMessage());
                }
            }
        });
    }

    /******************************** Members Expenditure Parse Helper ******************************/
    public void saveGroupMembersExpenditureToParseDb(GroupMemberExpenditure groupMembersExpenditure){
        GroupMemberExpenditure newGroupMembersExpenditure = new GroupMemberExpenditure();
        newGroupMembersExpenditure.put("memberExpenditureLocalUniqueID", new CashTimeUtils().getUUID());
        newGroupMembersExpenditure.put("memberExpenditureCategory", groupMembersExpenditure.getCategory());
        newGroupMembersExpenditure.put("memberExpenditureAmount", groupMembersExpenditure.getAmount());
        newGroupMembersExpenditure.put("memberExpenditureNotes", groupMembersExpenditure.getNotes());
        newGroupMembersExpenditure.put("memberExpenditureDate", groupMembersExpenditure.getDate());
        newGroupMembersExpenditure.put("memberUsername", groupMembersExpenditure.getMemberUserName());
        newGroupMembersExpenditure.put("memberLocalUniqueID", groupMembersExpenditure.getMemberLocalUniqueID());
        newGroupMembersExpenditure.put("createdById", groupMembersExpenditure.getUserId());
        newGroupMembersExpenditure.pinInBackground();
        newGroupMembersExpenditure.saveEventually();
    }

    public void getGroupMembersExpenditureFromParseDb(final ParseExpenditureHelper.OnReturnedGroupMembersExpenditureListener onReturnedGroupMembersExpenditureListener){
        ParseQuery<GroupMemberExpenditure> groupMemberExpenditureQuery = ParseQuery.getQuery("ct2_MemberExpenditure");
        groupMemberExpenditureQuery.fromLocalDatastore();
        groupMemberExpenditureQuery.addDescendingOrder("updatedAt");
        groupMemberExpenditureQuery.whereEqualTo("createdById", currentUserID);
        groupMemberExpenditureQuery.findInBackground(new FindCallback<GroupMemberExpenditure>() {
            @Override
            public void done(List<GroupMemberExpenditure> parseGroupMemberExpenditure, ParseException e) {
                if (e == null){
                    for (GroupMemberExpenditure retrievedGroupMemberExpenditure: parseGroupMemberExpenditure){
                        GroupMemberExpenditure newGroupMembersExpenditure = new GroupMemberExpenditure();
                        newGroupMembersExpenditure.setCategory(retrievedGroupMemberExpenditure.get("memberExpenditureCategory").toString());
                        newGroupMembersExpenditure.setAmount(retrievedGroupMemberExpenditure.get("memberExpenditureAmount").toString());
                        newGroupMembersExpenditure.setNotes(retrievedGroupMemberExpenditure.get("memberExpenditureNotes").toString());
                        newGroupMembersExpenditure.setDate(retrievedGroupMemberExpenditure.get("memberExpenditureDate").toString());
                        newGroupMembersExpenditure.setMemberUserName(retrievedGroupMemberExpenditure.get("memberUsername").toString());
                        newGroupMembersExpenditure.setLocalUniqueID(retrievedGroupMemberExpenditure.get("memberExpenditureLocalUniqueID").toString());

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
        ParseQuery<GroupMemberExpenditure> groupMembersExpenditureQuery = ParseQuery.getQuery("ct2_MemberExpenditure");
        groupMembersExpenditureQuery.fromLocalDatastore();
        groupMembersExpenditureQuery.whereEqualTo("memberExpenditureLocalUniqueID", groupMembersExpenditureToUpdate.getLocalUniqueID());
        groupMembersExpenditureQuery.findInBackground(new FindCallback<GroupMemberExpenditure>() {
            @Override
            public void done(List<GroupMemberExpenditure> groupMemberExpenditures, ParseException e) {
                if (e == null) {
                    if (groupMemberExpenditures.size() == 1){
                        groupMemberExpenditures.get(0).put("memberExpenditureCategory", groupMembersExpenditureToUpdate.getCategory());
                        groupMemberExpenditures.get(0).put("memberExpenditureAmount", groupMembersExpenditureToUpdate.getAmount());
                        groupMemberExpenditures.get(0).put("memberExpenditureNotes", groupMembersExpenditureToUpdate.getNotes());
                        groupMemberExpenditures.get(0).put("memberExpenditureDate", groupMembersExpenditureToUpdate.getDate());
                        groupMemberExpenditures.get(0).pinInBackground();
                        groupMemberExpenditures.get(0).saveEventually();
                    }
                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void deleteGroupMembersExpenditureFromParseDb(GroupMemberExpenditure groupMembersExpenditureToDelete){
        ParseQuery<GroupMemberExpenditure> groupMemberExpenditureQuery = ParseQuery.getQuery("ct2_MemberExpenditure");
        groupMemberExpenditureQuery.fromLocalDatastore();
        groupMemberExpenditureQuery.whereEqualTo("memberExpenditureLocalUniqueID", groupMembersExpenditureToDelete.getLocalUniqueID());
        groupMemberExpenditureQuery.findInBackground(new FindCallback<GroupMemberExpenditure>() {
            @Override
            public void done(List<GroupMemberExpenditure> groupMemberExpenditures, ParseException e) {
                if (e == null) {
                    if (groupMemberExpenditures.size() == 1){
                        groupMemberExpenditures.get(0).unpinInBackground();
                        groupMemberExpenditures.get(0).deleteEventually();
                    }
                }else {
                    Log.d(TAG, "Error Occurred: " + e.getMessage());
                }
            }
        });
    }

}
