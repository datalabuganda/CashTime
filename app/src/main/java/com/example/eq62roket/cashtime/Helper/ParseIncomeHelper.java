package com.example.eq62roket.cashtime.Helper;

import android.content.Context;
import android.util.Log;

import com.example.eq62roket.cashtime.Models.GroupIncome;

import com.example.eq62roket.cashtime.Models.MembersIncome;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 3/28/18.
 */

public class ParseIncomeHelper {


    public interface OnReturnedGroupMemberIncomeListener{
        void onResponse(List<MembersIncome> groupMemberIncomeList);
        void onFailure(String error);
    }

    public interface OnReturnedGroupIncomeListener{
        void onResponse(List<GroupIncome> groupIncomeList);
        void onFailure(String error);
    }

    public interface OnReturnedGroupSumOfIncomeListener{
        void onResponse(int sumOfGroupIncome);
        void onFailure(String error);
    }

    public interface OnReturnedMemberSumOfIncomeListener{
        void onResponse(int sumOfMemberIncome);
        void onFailure(String error);
    }

    public interface ReturnedTotalIncomeAmountListener{
        void onResponse(int totalIncome);
        void onFailure(String error);
    }


    private static final String TAG = "ParseIncomeHelper";
    private final List<MembersIncome> groupMemberIncomeList = new ArrayList<>();
    private final List<GroupIncome> groupIncomeList = new ArrayList<>();


    private Context mContext;
    private String currentUserId;
    private ParseIncomeHelper.OnReturnedGroupMemberIncomeListener mOnReturnedGroupMemberIncomeListener;
    private ParseIncomeHelper.OnReturnedGroupIncomeListener mOnReturnedGroupIncomeListener;

    public ParseIncomeHelper(Context context){
        mContext = context;
        currentUserId = ParseUser.getCurrentUser().getObjectId();
    }

    public void saveGroupMemberIncomeToParseDb(MembersIncome groupMemberIncome){
        MembersIncome newGroupMemberIncome = new MembersIncome();
        newGroupMemberIncome.put("memberIncomeLocalUniqueID", new CashTimeUtils().getUUID());
        newGroupMemberIncome.put("memberIncomeSource", groupMemberIncome.getSource());
        newGroupMemberIncome.put("memberIncomeAmount", groupMemberIncome.getAmount());
        newGroupMemberIncome.put("memberIncomeNotes", groupMemberIncome.getNotes());
        newGroupMemberIncome.put("memberIncomePeriod", groupMemberIncome.getPeriod());
        newGroupMemberIncome.put("memberUsername", groupMemberIncome.getMemberUserName());
        newGroupMemberIncome.put("memberLocalUniqueID", groupMemberIncome.getMemberLocalUniqueID());
        newGroupMemberIncome.put("createdById", groupMemberIncome.getUserId());
        newGroupMemberIncome.pinInBackground();
        newGroupMemberIncome.saveEventually();

    }

    public void getGroupMemberIncomeMemberFromParseDb(final ParseIncomeHelper.OnReturnedGroupMemberIncomeListener onReturnedGroupMemberIncomeListener){
        ParseQuery<MembersIncome> groupMemberIncomeQuery = ParseQuery.getQuery("ct2_MemberIncome");
        groupMemberIncomeQuery.fromLocalDatastore();
        groupMemberIncomeQuery.addDescendingOrder("updatedAt");
        groupMemberIncomeQuery.whereEqualTo("createdById", currentUserId);
        groupMemberIncomeQuery.findInBackground(new FindCallback<MembersIncome>() {
            @Override
            public void done(List<MembersIncome> parseGroupMemberIncome, ParseException e) {
                if (e == null){
                    for (MembersIncome retrievedGroupMemberIncome: parseGroupMemberIncome){
                        MembersIncome newGroupMemberIncome = new MembersIncome();
                        newGroupMemberIncome.setSource(retrievedGroupMemberIncome.get("memberIncomeSource").toString());
                        newGroupMemberIncome.setAmount(retrievedGroupMemberIncome.get("memberIncomeAmount").toString());
                        newGroupMemberIncome.setNotes(retrievedGroupMemberIncome.get("memberIncomeNotes").toString());
                        newGroupMemberIncome.setPeriod(retrievedGroupMemberIncome.get("memberIncomePeriod").toString());
                        newGroupMemberIncome.setMemberUserName(retrievedGroupMemberIncome.get("memberUsername").toString());
                        newGroupMemberIncome.setUserId(retrievedGroupMemberIncome.get("createdById").toString());
                        newGroupMemberIncome.setLocalUniqueID(retrievedGroupMemberIncome.get("memberIncomeLocalUniqueID").toString());

                        groupMemberIncomeList.add(newGroupMemberIncome);
                    }
                    if (onReturnedGroupMemberIncomeListener != null){
                        onReturnedGroupMemberIncomeListener.onResponse(groupMemberIncomeList);
                    }
                }else {
                    onReturnedGroupMemberIncomeListener.onFailure(e.getMessage());
                }
            }

        });

    }

    public void updateGroupMemberIncomeInParseDb(final MembersIncome groupMemberIncomeToUpdate){
        ParseQuery<MembersIncome> groupMemberIncomeQuery = ParseQuery.getQuery("ct2_MemberIncome");
        groupMemberIncomeQuery.fromLocalDatastore();
        groupMemberIncomeQuery.whereEqualTo("memberIncomeLocalUniqueID", groupMemberIncomeToUpdate.getLocalUniqueID());
        groupMemberIncomeQuery.findInBackground(new FindCallback<MembersIncome>() {
            @Override
            public void done(List<MembersIncome> groupMemberIncomes, ParseException e) {
                if (e == null) {
                    if (groupMemberIncomes.size() == 1){
                        groupMemberIncomes.get(0).put("memberIncomeSource", groupMemberIncomeToUpdate.getSource());
                        groupMemberIncomes.get(0).put("memberIncomeAmount", groupMemberIncomeToUpdate.getAmount());
                        groupMemberIncomes.get(0).put("memberIncomeNotes", groupMemberIncomeToUpdate.getNotes());
                        groupMemberIncomes.get(0).put("memberIncomePeriod", groupMemberIncomeToUpdate.getPeriod());
                        groupMemberIncomes.get(0).pinInBackground();
                        groupMemberIncomes.get(0).saveEventually();
                    }
                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void deleteGroupMemberIncomeFromParseDb(MembersIncome groupMemberIncomeToDelete){
        final ParseQuery<MembersIncome> groupMemberIncomeQuery = ParseQuery.getQuery("ct2_MemberIncome");
        groupMemberIncomeQuery.fromLocalDatastore();
        groupMemberIncomeQuery.whereEqualTo("memberIncomeLocalUniqueID", groupMemberIncomeToDelete.getLocalUniqueID());
        groupMemberIncomeQuery.findInBackground(new FindCallback<MembersIncome>() {
            @Override
            public void done(List<MembersIncome> groupMemberIncomes, ParseException e) {
                if (e == null) {
                    if (groupMemberIncomes.size() == 1){
                        groupMemberIncomes.get(0).unpinInBackground();
                        groupMemberIncomes.get(0).deleteEventually();
                    }
                }else {
                    Log.d(TAG, "Error Occurred: " + e.getMessage());
                }
            }
        });
    }

    /******************************** Group Income **********************************/

    public void saveGroupIncomeToParseDb(GroupIncome groupIncome){
        GroupIncome newGroupIncome = new GroupIncome();
        newGroupIncome.put("groupIncomeLocalUniqueID", new CashTimeUtils().getUUID());
        newGroupIncome.put("groupIncomeSource", groupIncome.getSource());
        newGroupIncome.put("groupIncomeAmount", groupIncome.getAmount());
        newGroupIncome.put("groupIncomeNotes", groupIncome.getNotes());
        newGroupIncome.put("groupIncomePeriod", groupIncome.getPeriod());
        newGroupIncome.put("groupName", groupIncome.getGroupName());
        newGroupIncome.put("groupLocalUniqueID", groupIncome.getGroupLocalUniqueID());
        newGroupIncome.put("createdById", groupIncome.getUserId());
        newGroupIncome.pinInBackground();
        newGroupIncome.saveEventually();

    }


    public void updateGroupIncomeInParseDb(final GroupIncome groupIncomeToUpdate){
        final ParseQuery<GroupIncome> groupIncomeQuery = ParseQuery.getQuery("ct2_GroupIncome");
        groupIncomeQuery.fromLocalDatastore();
        groupIncomeQuery.whereEqualTo("groupIncomeLocalUniqueID", groupIncomeToUpdate.getLocalUniqueID());
        groupIncomeQuery.findInBackground(new FindCallback<GroupIncome>() {
            @Override
            public void done(List<GroupIncome> groupMemberIncomes, ParseException e) {
                if (e == null) {
                    if (groupMemberIncomes.size() == 1){
                        groupMemberIncomes.get(0).put("groupIncomeSource", groupIncomeToUpdate.getSource());
                        groupMemberIncomes.get(0).put("groupIncomeAmount", groupIncomeToUpdate.getAmount());
                        groupMemberIncomes.get(0).put("groupIncomeNotes", groupIncomeToUpdate.getNotes());
                        groupMemberIncomes.get(0).put("groupIncomePeriod", groupIncomeToUpdate.getPeriod());
                        groupMemberIncomes.get(0).put("createdById", currentUserId);
                        groupMemberIncomes.get(0).pinInBackground();
                        groupMemberIncomes.get(0).saveEventually();
                    }
                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void getGroupIncomeFromParseDb(final ParseIncomeHelper.OnReturnedGroupIncomeListener onReturnedGroupIncomeListener){
        ParseQuery<GroupIncome> groupIncomeQuery = ParseQuery.getQuery("ct2_GroupIncome");
        groupIncomeQuery.fromLocalDatastore();
        groupIncomeQuery.addDescendingOrder("updatedAt");
        groupIncomeQuery.whereEqualTo("createdById", currentUserId);
        groupIncomeQuery.findInBackground(new FindCallback<GroupIncome>() {
            @Override
            public void done(List<GroupIncome> parseGroupIncome, ParseException e) {
                if (e == null){
                    for (GroupIncome retrievedGroupIncome: parseGroupIncome){
                        GroupIncome newGroupIncome = new GroupIncome();
                        newGroupIncome.setSource(retrievedGroupIncome.get("groupIncomeSource").toString());
                        newGroupIncome.setAmount(retrievedGroupIncome.get("groupIncomeAmount").toString());
                        newGroupIncome.setNotes(retrievedGroupIncome.get("groupIncomeNotes").toString());
                        newGroupIncome.setPeriod(retrievedGroupIncome.get("groupIncomePeriod").toString());
                        newGroupIncome.setGroupName(retrievedGroupIncome.get("groupName").toString());
                        newGroupIncome.setLocalUniqueID(retrievedGroupIncome.get("groupIncomeLocalUniqueID").toString());

                        groupIncomeList.add(newGroupIncome);
                    }
                    if (onReturnedGroupIncomeListener != null){
                        onReturnedGroupIncomeListener.onResponse(groupIncomeList);
                    }
                }else {
                    onReturnedGroupIncomeListener.onFailure(e.getMessage());
                }
            }

        });

    }

    public void getTotalGroupIncomeFromParseDb(String groupLocalUniqueID){
        ParseQuery<GroupIncome> groupIncomeParseQuery = ParseQuery.getQuery("ct2_GroupIncome");
        groupIncomeParseQuery.fromLocalDatastore();
        groupIncomeParseQuery.whereEqualTo("createdById", currentUserId);
        groupIncomeParseQuery.whereEqualTo("groupLocalUniqueID", groupLocalUniqueID);
        groupIncomeParseQuery.addDescendingOrder("updatedAt");
        groupIncomeParseQuery.findInBackground(new FindCallback<GroupIncome>() {
            @Override
            public void done(List<GroupIncome> groupParseIncomes, ParseException e) {
                if (e == null){
                    int groupTotalIncome = 0;
                    for (GroupIncome groupIncome : groupParseIncomes){
                        groupTotalIncome += Integer.valueOf(groupIncome.getString("groupIncomeAmount"));
                    }
                    Log.e(TAG, "groupTotalIncome: " + groupTotalIncome);
                }else {
                    Log.d(TAG, "Error occurred " + e.getMessage());
                }
            }
        });
    }



    public void deleteGroupIncomeFromParseDb(GroupIncome groupIncomeToDelete){
        ParseQuery<GroupIncome> groupIncomeQuery = ParseQuery.getQuery("ct2_GroupIncome");
        groupIncomeQuery.fromLocalDatastore();
        groupIncomeQuery.whereEqualTo("groupIncomeLocalUniqueID", groupIncomeToDelete.getLocalUniqueID());
        groupIncomeQuery.findInBackground(new FindCallback<GroupIncome>() {
            @Override
            public void done(List<GroupIncome> groupIncomes, ParseException e) {
                if (e == null) {
                    if (groupIncomes.size() == 1){
                        Log.d(TAG, "deleted: " );
                        groupIncomes.get(0).unpinInBackground();
                        groupIncomes.get(0).deleteEventually();
                    }
                }else {
                    Log.d(TAG, "Error Occurred: " + e.getMessage());
                }
            }
        });
    }

}
