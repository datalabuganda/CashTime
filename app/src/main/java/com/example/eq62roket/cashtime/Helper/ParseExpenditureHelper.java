package com.example.eq62roket.cashtime.Helper;

import android.content.Context;
import android.util.Log;

import com.example.eq62roket.cashtime.Models.GroupExpenditure;
import com.example.eq62roket.cashtime.Models.GroupIncome;
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

    public interface OnReturnedGroupSumOfExpenditureListener{
        void onResponse(int sumOfGroupExpenditure);
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
        newGroupExpenditure.put("groupExpenditureDate", groupExpenditure.getDate());
        newGroupExpenditure.put("groupName", groupExpenditure.getGroupName());
        newGroupExpenditure.put("groupParseId", groupExpenditure.getGroupParseId());
        newGroupExpenditure.put("createdById", groupExpenditure.getUserId());
        newGroupExpenditure.saveInBackground();

    }

    public void getGroupExpenditureFromParseDb(final ParseExpenditureHelper.OnReturnedGroupExpenditureListener onReturnedGroupExpenditureListener){
        ParseQuery<GroupExpenditure> groupExpenditureQuery = ParseQuery.getQuery("ct2_GroupExpenditure");
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
                        newGroupExpenditure.setDate(retrievedGroupExpenditure.get("groupExpenditureDate").toString());
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

    public void getTotalGroupExpenditureFromParseDb(GroupExpenditure groupExpenditure, final ParseExpenditureHelper.OnReturnedGroupSumOfExpenditureListener onReturnedGroupSumOfExpenditureListener){
        ParseQuery<GroupExpenditure> groupExpenditureParseQuery = ParseQuery.getQuery("ct2_GroupExpenditure");
        groupExpenditureParseQuery.whereEqualTo("groupParseId", groupExpenditure.getGroupParseId());
        groupExpenditureParseQuery.whereEqualTo("groupExpenditureParseId", groupExpenditure.getParseId());
        groupExpenditureParseQuery.findInBackground(new FindCallback<GroupExpenditure>() {
            @Override
            public void done(List<GroupExpenditure> parseGroupExpenditure, ParseException e) {
                if (e == null){
                    int totalGroupExpenditure = 0;
                    for (GroupExpenditure groupExpenditure : parseGroupExpenditure){
                        totalGroupExpenditure += Integer.valueOf(groupExpenditure.getString("groupExpenditureAmount"));
                    }onReturnedGroupSumOfExpenditureListener.onResponse(totalGroupExpenditure);
                }else {
                    onReturnedGroupSumOfExpenditureListener.onFailure(e.getMessage());
                }
            }
        });
    }

    public int totalGroupExpenditure(GroupExpenditure groupExpenditure, final ParseExpenditureHelper.OnReturnedGroupSumOfExpenditureListener onReturnedGroupSumOfExpenditureListener){
        int sumOfExpenditure = 0;
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ct2_GroupExpenditure");
        query.whereEqualTo("groupParseId", groupExpenditure.getGroupParseId());
        query.whereEqualTo("groupExpenditureParseId", groupExpenditure.getParseId());
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++){
                sumOfExpenditure += Integer.parseInt(results.get(i).getString("groupExpenditureAmount"));
            }onReturnedGroupSumOfExpenditureListener.onResponse(sumOfExpenditure);
        } catch (ParseException e) {
            onReturnedGroupSumOfExpenditureListener.onFailure(e.getMessage());
            e.printStackTrace();
        }
        return sumOfExpenditure;
    }

    public void updateGroupExpenditureInParseDb(final GroupExpenditure groupExpenditureToUpdate){
        ParseQuery<GroupExpenditure> groupExpenditureQuery = ParseQuery.getQuery("ct2_GroupExpenditure");
        groupExpenditureQuery.getInBackground(groupExpenditureToUpdate.getParseId(), new GetCallback<GroupExpenditure>() {
            @Override
            public void done(GroupExpenditure groupExpenditure, ParseException e) {
                if (e == null) {
                    groupExpenditure.put("groupExpenditureCategory", groupExpenditureToUpdate.getCategory());
                    groupExpenditure.put("groupExpenditureAmount", groupExpenditureToUpdate.getAmount());
                    groupExpenditure.put("groupExpenditureNotes", groupExpenditureToUpdate.getNotes());
                    groupExpenditure.put("groupExpenditureDate", groupExpenditureToUpdate.getDate());
                    groupExpenditure.saveInBackground();

                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }

        });
    }

    public void deleteGroupExpenditureFromParseDb(GroupExpenditure groupExpenditureToDelete){
        ParseQuery<GroupExpenditure> groupExpenditureQuery = ParseQuery.getQuery("ct2_GroupExpenditure");
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
        newGroupMembersExpenditure.put("memberExpenditureCategory", groupMembersExpenditure.getCategory());
        newGroupMembersExpenditure.put("memberExpenditureAmount", groupMembersExpenditure.getAmount());
        newGroupMembersExpenditure.put("memberExpenditureNotes", groupMembersExpenditure.getNotes());
        newGroupMembersExpenditure.put("memberExpenditureDate", groupMembersExpenditure.getDate());
        newGroupMembersExpenditure.put("memberUsername", groupMembersExpenditure.getMemberUserName());
        newGroupMembersExpenditure.put("memberParseId", groupMembersExpenditure.getMemberParseId());
        newGroupMembersExpenditure.put("createdById", groupMembersExpenditure.getUserId());
        newGroupMembersExpenditure.saveInBackground();
    }

    public void getGroupMembersExpenditureFromParseDb(final ParseExpenditureHelper.OnReturnedGroupMembersExpenditureListener onReturnedGroupMembersExpenditureListener){
        ParseQuery<GroupMemberExpenditure> groupMemberExpenditureQuery = ParseQuery.getQuery("ct2_MemberExpenditure");
        String currentUser = ParseUser.getCurrentUser().getObjectId();
        groupMemberExpenditureQuery.addDescendingOrder("updatedAt");
        groupMemberExpenditureQuery.whereEqualTo("createdById", currentUser);
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
        ParseQuery<GroupMemberExpenditure> groupMembersExpenditureQuery = ParseQuery.getQuery("ct2_MemberExpenditure");
        groupMembersExpenditureQuery.getInBackground(groupMembersExpenditureToUpdate.getParseId(), new GetCallback<GroupMemberExpenditure>() {
            @Override
            public void done(GroupMemberExpenditure groupMemberExpenditure, ParseException e) {
                if (e == null) {
                    groupMemberExpenditure.put("memberExpenditureCategory", groupMembersExpenditureToUpdate.getCategory());
                    groupMemberExpenditure.put("memberExpenditureAmount", groupMembersExpenditureToUpdate.getAmount());
                    groupMemberExpenditure.put("memberExpenditureNotes", groupMembersExpenditureToUpdate.getNotes());
                    groupMemberExpenditure.put("memberExpenditureDate", groupMembersExpenditureToUpdate.getDate());
                    groupMemberExpenditure.saveInBackground();

                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }

        });
    }

    public void deleteGroupMembersExpenditureFromParseDb(GroupMemberExpenditure groupMembersExpenditureToDelete){
        ParseQuery<GroupMemberExpenditure> groupMemberExpenditureQuery = ParseQuery.getQuery("ct2_MemberExpenditure");
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
