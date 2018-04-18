package com.example.eq62roket.cashtime.Helper;

import android.content.Context;
import android.util.Log;

import com.example.eq62roket.cashtime.Interfaces.DeleteBarrierAndTipListener;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupBarrierListener;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupSavingsListener;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupSavingsSumListener;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedMemberGoalListener;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedMemberSavingsListener;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedMemberSavingsSumListener;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedTipsListener;
import com.example.eq62roket.cashtime.Interfaces.SaveBarrierAndTipListener;
import com.example.eq62roket.cashtime.Interfaces.UpdateBarrierAndTipListener;
import com.example.eq62roket.cashtime.Models.Barrier;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.Models.GroupSavings;
import com.example.eq62roket.cashtime.Models.MemberSavings;
import com.example.eq62roket.cashtime.Models.MembersGoals;
import com.example.eq62roket.cashtime.Models.Tip;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etwin on 3/26/18.
 */

public class ParseHelper {

    public interface OnReturnedGroupGoalsListener{
        void onResponse(List<GroupGoals> groupGoalsList);
        void onFailure(String error);
    }


    private static final String TAG = "ParseHelper";
    private Context mContext;
    private String currentUserId;

    public ParseHelper(Context context){
        mContext = context;
        currentUserId = ParseUser.getCurrentUser().getObjectId();
    }

    public void saveGroupGoalsToParseDb(GroupGoals groupGoals){
        GroupGoals newGroupGoal = new GroupGoals();
        newGroupGoal.put("userId", currentUserId);
        newGroupGoal.put("localUniqueID", new CashTimeUtils().getUUID());
        newGroupGoal.put("goalName", groupGoals.getName());
        newGroupGoal.put("goalAmount", groupGoals.getAmount());
        newGroupGoal.put("goalText", groupGoals.getNotes());
        newGroupGoal.put("goalStatus", groupGoals.getGroupGoalStatus());
        newGroupGoal.put("goalEndDate", groupGoals.getDueDate());
        newGroupGoal.put("groupLocalUniqueID", groupGoals.getGroupLocalUniqueID());
        newGroupGoal.put("groupName", groupGoals.getGroupName());
        newGroupGoal.put("completedDate", groupGoals.getCompletedDate());
        newGroupGoal.pinInBackground();
        newGroupGoal.saveEventually();

    }

    public void getGroupGoalsFromParseDb(final OnReturnedGroupGoalsListener onReturnedGroupGoalsListener){
        final List<GroupGoals> groupGoalList = new ArrayList<>();
        ParseQuery<GroupGoals> groupGoalsQuery = ParseQuery.getQuery("ct2_GroupGoals");
        groupGoalsQuery.fromLocalDatastore();
        groupGoalsQuery.whereEqualTo("userId", currentUserId);
        groupGoalsQuery.addDescendingOrder("updatedAt");
        groupGoalsQuery.findInBackground(new FindCallback<GroupGoals>() {
            @Override
            public void done(List<GroupGoals> parseGroupGoals, ParseException e) {
                if (e == null){
                    for (GroupGoals retrievedGroupGoal: parseGroupGoals){
                        GroupGoals newGroupGoal = new GroupGoals();
                        newGroupGoal.setName(retrievedGroupGoal.get("goalName").toString());
                        newGroupGoal.setAmount(retrievedGroupGoal.get("goalAmount").toString());
                        newGroupGoal.setNotes(retrievedGroupGoal.get("goalText").toString());
                        newGroupGoal.setGroupGoalStatus(retrievedGroupGoal.get("goalStatus").toString());
                        newGroupGoal.setDueDate(retrievedGroupGoal.get("goalEndDate").toString());
                        newGroupGoal.setGroupLocalUniqueID(retrievedGroupGoal.get("groupLocalUniqueID").toString());
                        newGroupGoal.setGroupName(retrievedGroupGoal.get("groupName").toString());
                        newGroupGoal.setLocalUniqueID(retrievedGroupGoal.get("localUniqueID").toString());

                        groupGoalList.add(newGroupGoal);
                    }
                    if (onReturnedGroupGoalsListener != null){
                        onReturnedGroupGoalsListener.onResponse(groupGoalList);
                    }
                }else {
                    onReturnedGroupGoalsListener.onFailure(e.getMessage());
                }
            }
        });

    }

    public void getIncompleteGroupGoalsFromParseDb(final OnReturnedGroupGoalsListener onReturnedGroupGoalsListener){
        final List<GroupGoals> groupGoalList = new ArrayList<>();
        ParseQuery<GroupGoals> groupGoalsQuery = ParseQuery.getQuery("ct2_GroupGoals");
        groupGoalsQuery.fromLocalDatastore();
        groupGoalsQuery.whereEqualTo("userId", currentUserId);
        groupGoalsQuery.whereEqualTo("goalStatus", "incomplete");
        groupGoalsQuery.addDescendingOrder("updatedAt");
        groupGoalsQuery.findInBackground(new FindCallback<GroupGoals>() {
            @Override
            public void done(List<GroupGoals> parseGroupGoals, ParseException e) {
                if (e == null){
                    for (GroupGoals retrievedGroupGoal: parseGroupGoals){
                        GroupGoals newGroupGoal = new GroupGoals();
                        newGroupGoal.setName(retrievedGroupGoal.get("goalName").toString());
                        newGroupGoal.setAmount(retrievedGroupGoal.get("goalAmount").toString());
                        newGroupGoal.setNotes(retrievedGroupGoal.get("goalText").toString());
                        newGroupGoal.setGroupGoalStatus(retrievedGroupGoal.get("goalStatus").toString());
                        newGroupGoal.setDueDate(retrievedGroupGoal.get("goalEndDate").toString());
                        newGroupGoal.setGroupLocalUniqueID(retrievedGroupGoal.get("groupLocalUniqueID").toString());
                        newGroupGoal.setGroupName(retrievedGroupGoal.get("groupName").toString());
                        newGroupGoal.setLocalUniqueID(retrievedGroupGoal.get("localUniqueID").toString());

                        groupGoalList.add(newGroupGoal);
                    }
                    if (onReturnedGroupGoalsListener != null){
                        onReturnedGroupGoalsListener.onResponse(groupGoalList);
                    }
                }else {
                    onReturnedGroupGoalsListener.onFailure(e.getMessage());
                }
            }
        });

    }

    public void getAllFailedGroupGoalsFromParseDb(final OnReturnedGroupGoalsListener onReturnedGroupGoalsListener){
        final List<GroupGoals> groupGoalList = new ArrayList<>();
        ParseQuery<GroupGoals> groupGoalsQuery = ParseQuery.getQuery("ct2_GroupGoals");
        groupGoalsQuery.fromLocalDatastore();
        groupGoalsQuery.whereEqualTo("userId", currentUserId);
        groupGoalsQuery.whereEqualTo("goalStatus", "failed");
        groupGoalsQuery.addDescendingOrder("updatedAt");
        groupGoalsQuery.findInBackground(new FindCallback<GroupGoals>() {
            @Override
            public void done(List<GroupGoals> parseGroupGoals, ParseException e) {
                if (e == null){
                    for (GroupGoals retrievedGroupGoal: parseGroupGoals){
                        GroupGoals newGroupGoal = new GroupGoals();
                        newGroupGoal.setName(retrievedGroupGoal.get("goalName").toString());
                        newGroupGoal.setAmount(retrievedGroupGoal.get("goalAmount").toString());
                        newGroupGoal.setNotes(retrievedGroupGoal.get("goalText").toString());
                        newGroupGoal.setGroupGoalStatus(retrievedGroupGoal.get("goalStatus").toString());
                        newGroupGoal.setDueDate(retrievedGroupGoal.get("goalEndDate").toString());
                        newGroupGoal.setGroupLocalUniqueID(retrievedGroupGoal.get("groupLocalUniqueID").toString());
                        newGroupGoal.setGroupName(retrievedGroupGoal.get("groupName").toString());
                        newGroupGoal.setLocalUniqueID(retrievedGroupGoal.get("localUniqueID").toString());

                        groupGoalList.add(newGroupGoal);
                    }
                    if (onReturnedGroupGoalsListener != null){
                        onReturnedGroupGoalsListener.onResponse(groupGoalList);
                    }
                }else {
                    onReturnedGroupGoalsListener.onFailure(e.getMessage());
                }
            }
        });

    }

    public void updateGroupGoalInParseDb(final GroupGoals groupGoalToUpdate){
        ParseQuery<GroupGoals> groupGoalQuery = ParseQuery.getQuery("ct2_GroupGoals");
        groupGoalQuery.fromLocalDatastore();
        groupGoalQuery.whereEqualTo("localUniqueID", groupGoalToUpdate.getLocalUniqueID());
        groupGoalQuery.findInBackground(new FindCallback<GroupGoals>() {
            @Override
            public void done(List<GroupGoals> groupGoals, ParseException e) {
                if (e == null) {
                    if (groupGoals.size() == 1){
                        groupGoals.get(0).put("goalName", groupGoalToUpdate.getName());
                        groupGoals.get(0).put("goalAmount", groupGoalToUpdate.getAmount());
                        groupGoals.get(0).put("goalText", groupGoalToUpdate.getNotes());
                        groupGoals.get(0).put("goalEndDate", groupGoalToUpdate.getDueDate());
                        groupGoals.get(0).pinInBackground();
                        groupGoals.get(0).saveEventually();
                    }

                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void updateGroupGoalCompleteStatusInParseDb(final GroupGoals completedGroupGoal){
        ParseQuery<GroupGoals> groupGoalQuery = ParseQuery.getQuery("ct2_GroupGoals");
        groupGoalQuery.fromLocalDatastore();
        groupGoalQuery.whereEqualTo("localUniqueID", completedGroupGoal.getLocalUniqueID());
        groupGoalQuery.findInBackground(new FindCallback<GroupGoals>() {
            @Override
            public void done(List<GroupGoals> groupGoals, ParseException e) {
                if (e == null) {
                    if (groupGoals.size() == 1){
                        groupGoals.get(0).put("goalStatus", completedGroupGoal.getGroupGoalStatus());
                        groupGoals.get(0).put("completedDate", completedGroupGoal.getCompletedDate());
                        groupGoals.get(0).pinInBackground();
                        groupGoals.get(0).saveEventually();
                    }

                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void deleteGroupGoalFromParseDb(GroupGoals groupGoalToDelete){
        ParseQuery<GroupGoals> groupGoalQuery = ParseQuery.getQuery("ct2_GroupGoals");
        groupGoalQuery.fromLocalDatastore();
        groupGoalQuery.whereEqualTo("localUniqueID", groupGoalToDelete.getLocalUniqueID());
        groupGoalQuery.findInBackground(new FindCallback<GroupGoals>() {
            @Override
            public void done(List<GroupGoals> groupGoals, ParseException e) {
                if (e == null) {
                    if (groupGoals.size() == 1){
                        groupGoals.get(0).unpinInBackground();
                        groupGoals.get(0).deleteEventually();
                    }
                }else {
                    Log.d(TAG, "Error Occurred: " + e.getMessage());
                }
            }
        });
    }

    public void saveMemberGoalsToParseDb(MembersGoals membersGoal){
        MembersGoals newMemberGoal = new MembersGoals();
        newMemberGoal.put("localUniqueID", new CashTimeUtils().getUUID());
        newMemberGoal.put("memberUsernames", membersGoal.getMemberName());
        newMemberGoal.put("memberGoalName", membersGoal.getMemberGoalName());
        newMemberGoal.put("memberGoalAmount", membersGoal.getMemberGoalAmount());
        newMemberGoal.put("memberGoalDeadline", membersGoal.getMemberGoalDueDate());
        newMemberGoal.put("memberGoalStatus", membersGoal.getMemberGoalStatus());
        newMemberGoal.put("memberGoalNotes", membersGoal.getMemberGoalNotes());
        newMemberGoal.put("memberLocalUniqueID", membersGoal.getMemberLocalUniqueID());
        newMemberGoal.put("completeDate", membersGoal.getCompleteDate());
        newMemberGoal.put("savingCreatorId", currentUserId);
        newMemberGoal.pinInBackground();
        newMemberGoal.saveEventually();

    }

    public void getAllMemberGoalsFromParseDb(final OnReturnedMemberGoalListener onReturnedMemberGoalListener){
        final List<MembersGoals> membersGoalsList = new ArrayList<>();
        ParseQuery<MembersGoals> membersGoalsParseQuery = ParseQuery.getQuery("ct2_MemberGoals");
        membersGoalsParseQuery.fromLocalDatastore();
        membersGoalsParseQuery.whereEqualTo("savingCreatorId", currentUserId);
        membersGoalsParseQuery.addDescendingOrder("updatedAt");
        membersGoalsParseQuery.findInBackground(new FindCallback<MembersGoals>() {
            @Override
            public void done(List<MembersGoals> parseMemberGoals, ParseException e) {
                if (e == null){
                    for (MembersGoals retrievedMemberGoal: parseMemberGoals){
                        MembersGoals memberGoal = new MembersGoals();
                        memberGoal.setMemberGoalName(retrievedMemberGoal.get("memberGoalName").toString());
                        memberGoal.setMemberGoalAmount(retrievedMemberGoal.get("memberGoalAmount").toString());
                        memberGoal.setMemberGoalNotes(retrievedMemberGoal.get("memberGoalNotes").toString());
                        memberGoal.setMemberGoalStatus(retrievedMemberGoal.get("memberGoalStatus").toString());
                        memberGoal.setMemberGoalDueDate(retrievedMemberGoal.get("memberGoalDeadline").toString());
                        memberGoal.setMemberName(retrievedMemberGoal.get("memberUsernames").toString());
                        memberGoal.setMemberLocalUniqueID(retrievedMemberGoal.get("memberLocalUniqueID").toString());
                        memberGoal.setCompleteDate(retrievedMemberGoal.get("completeDate").toString());
                        memberGoal.setLocalUniqueID(retrievedMemberGoal.get("localUniqueID").toString());

                        membersGoalsList.add(memberGoal);
                    }
                    if (onReturnedMemberGoalListener != null){
                        onReturnedMemberGoalListener.onResponse(membersGoalsList);
                    }
                }else {
                    onReturnedMemberGoalListener.onFailure(e.getMessage());
                }
            }
        });
    }

    public void getIncompleteMemberGoalsFromParseDb(final OnReturnedMemberGoalListener onReturnedMemberGoalListener){
        final List<MembersGoals> membersGoalsList = new ArrayList<>();
        ParseQuery<MembersGoals> membersGoalsParseQuery = ParseQuery.getQuery("ct2_MemberGoals");
        membersGoalsParseQuery.fromLocalDatastore();
        membersGoalsParseQuery.whereEqualTo("savingCreatorId", currentUserId);
        membersGoalsParseQuery.whereEqualTo("memberGoalStatus", "incomplete");
        membersGoalsParseQuery.addDescendingOrder("updatedAt");
        membersGoalsParseQuery.findInBackground(new FindCallback<MembersGoals>() {
            @Override
            public void done(List<MembersGoals> parseMemberGoals, ParseException e) {
                if (e == null){
                    for (MembersGoals retrievedMemberGoal: parseMemberGoals){
                        MembersGoals memberGoal = new MembersGoals();
                        memberGoal.setMemberGoalName(retrievedMemberGoal.get("memberGoalName").toString());
                        memberGoal.setMemberGoalAmount(retrievedMemberGoal.get("memberGoalAmount").toString());
                        memberGoal.setMemberGoalNotes(retrievedMemberGoal.get("memberGoalNotes").toString());
                        memberGoal.setMemberGoalStatus(retrievedMemberGoal.get("memberGoalStatus").toString());
                        memberGoal.setMemberGoalDueDate(retrievedMemberGoal.get("memberGoalDeadline").toString());
                        memberGoal.setMemberName(retrievedMemberGoal.get("memberUsernames").toString());
                        memberGoal.setMemberLocalUniqueID(retrievedMemberGoal.get("memberLocalUniqueID").toString());
                        memberGoal.setCompleteDate(retrievedMemberGoal.get("completeDate").toString());
                        memberGoal.setLocalUniqueID(retrievedMemberGoal.get("localUniqueID").toString());

                        membersGoalsList.add(memberGoal);
                    }
                    if (onReturnedMemberGoalListener != null){
                        onReturnedMemberGoalListener.onResponse(membersGoalsList);
                    }
                }else {
                    onReturnedMemberGoalListener.onFailure(e.getMessage());
                }
            }
        });
    }


    public void updateMemberGoalInParseDb(final MembersGoals membersGoalToUpdate){
        ParseQuery<MembersGoals> membersGoalsParseQuery = ParseQuery.getQuery("ct2_MemberGoals");
        membersGoalsParseQuery.fromLocalDatastore();
        membersGoalsParseQuery.whereEqualTo("localUniqueID", membersGoalToUpdate.getLocalUniqueID());
        membersGoalsParseQuery.findInBackground(new FindCallback<MembersGoals>() {
            @Override
            public void done(List<MembersGoals> membersGoals, ParseException e) {
                if (e == null) {
                    if (membersGoals.size() == 1){
                        membersGoals.get(0).put("memberGoalName", membersGoalToUpdate.getMemberGoalName());
                        membersGoals.get(0).put("memberGoalAmount", membersGoalToUpdate.getMemberGoalAmount());
                        membersGoals.get(0).put("memberGoalNotes", membersGoalToUpdate.getMemberGoalNotes());
                        membersGoals.get(0).put("memberGoalDeadline", membersGoalToUpdate.getMemberGoalDueDate());
                        membersGoals.get(0).pinInBackground();
                        membersGoals.get(0).saveEventually();
                    }
                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void updateMemberGoalCompleteStatusInParseDb(final MembersGoals completedMemberGoal){
        ParseQuery<MembersGoals> memberGoalQuery = ParseQuery.getQuery("ct2_MemberGoals");
        memberGoalQuery.fromLocalDatastore();
        memberGoalQuery.whereEqualTo("localUniqueID", completedMemberGoal.getLocalUniqueID());
        memberGoalQuery.findInBackground(new FindCallback<MembersGoals>() {
            @Override
            public void done(List<MembersGoals> memberGoals, ParseException e) {
                if (e == null) {
                    if (memberGoals.size() == 1){
                        memberGoals.get(0).put("memberGoalStatus", completedMemberGoal.getMemberGoalStatus());
                        memberGoals.get(0).put("completeDate", completedMemberGoal.getCompleteDate());
                        memberGoals.get(0).pinInBackground();
                        memberGoals.get(0).saveEventually();
                    }
                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void deleteMemberGoalFromParseDb(MembersGoals membersGoalToDelete){
        ParseQuery<MembersGoals> membersGoalsParseQuery = ParseQuery.getQuery("ct2_MemberGoals");
        membersGoalsParseQuery.fromLocalDatastore();
        membersGoalsParseQuery.whereEqualTo("localUniqueID", membersGoalToDelete.getLocalUniqueID());
        membersGoalsParseQuery.findInBackground(new FindCallback<MembersGoals>() {
            @Override
            public void done(List<MembersGoals> membersGoals, ParseException e) {
                if (e == null) {
                    if (membersGoals.size() == 1){
                        membersGoals.get(0).unpinInBackground();
                        membersGoals.get(0).deleteEventually();
                    }
                }else {
                    Log.d(TAG, "Error Occurred: " + e.getMessage());
                }
            }
        });
    }


    public void saveGroupSavingsToParseDb(GroupSavings groupSaving){
        GroupSavings newGroupSaving = new GroupSavings();
        newGroupSaving.put("userId", currentUserId);
        newGroupSaving.put("localUniqueID", new CashTimeUtils().getUUID());
        newGroupSaving.put("groupSavingAmount", groupSaving.getAmount());
        newGroupSaving.put("groupSavingGoalName",groupSaving.getGoalName());
        newGroupSaving.put("groupSavingIncomeSource", groupSaving.getIncomeSource());
        newGroupSaving.put("groupSavingPeriod", groupSaving.getPeriod());
        newGroupSaving.put("groupSavingNotes", groupSaving.getNotes());
        newGroupSaving.put("groupSavingDateAdded", groupSaving.getDateAdded());
        newGroupSaving.put("groupLocalUniqueID", groupSaving.getGroupLocalUniqueID());
        newGroupSaving.put("groupGoalLocalUniqueID", groupSaving.getGroupGoalLocalUniqueID());
        newGroupSaving.pinInBackground();
        newGroupSaving.saveEventually();
    }

    public void getGroupSavingsFromParseDb(final OnReturnedGroupSavingsListener onReturnedGroupSavingsListener){
        final List<GroupSavings> groupSavingsList = new ArrayList<>();
        ParseQuery<GroupSavings> groupSavingsParseQuery = ParseQuery.getQuery("ct2_GroupSavings");
        groupSavingsParseQuery.fromLocalDatastore();
        groupSavingsParseQuery.whereEqualTo("userId", currentUserId);
        groupSavingsParseQuery.addDescendingOrder("updatedAt");
        groupSavingsParseQuery.findInBackground(new FindCallback<GroupSavings>() {
            @Override
            public void done(List<GroupSavings> parseGroupSavings, ParseException e) {
                if (e == null){
                    for (GroupSavings retrievedGroupSavings: parseGroupSavings){
                        GroupSavings groupSaving = new GroupSavings();
                        groupSaving.setAmount(retrievedGroupSavings.get("groupSavingAmount").toString());
                        groupSaving.setGoalName(retrievedGroupSavings.get("groupSavingGoalName").toString());
                        groupSaving.setIncomeSource(retrievedGroupSavings.get("groupSavingIncomeSource").toString());
                        groupSaving.setPeriod(retrievedGroupSavings.get("groupSavingPeriod").toString());
                        groupSaving.setNotes(retrievedGroupSavings.get("groupSavingNotes").toString());
                        groupSaving.setDateAdded(retrievedGroupSavings.get("groupSavingDateAdded").toString());
                        groupSaving.setLocalUniqueID(retrievedGroupSavings.get("localUniqueID").toString());

                        groupSavingsList.add(groupSaving);
                    }
                    if (onReturnedGroupSavingsListener != null){
                        onReturnedGroupSavingsListener.onResponse(groupSavingsList);
                    }
                }else {
                    onReturnedGroupSavingsListener.onFailure(e.getMessage());
                }
            }
        });
    }

    public void getTotalGroupSavingsFromParseDb(GroupGoals groupGoal, final OnReturnedGroupSavingsSumListener onReturnedGroupSavingsSumListener){
        ParseQuery<GroupSavings> groupSavingsParseQuery = ParseQuery.getQuery("ct2_GroupSavings");
        groupSavingsParseQuery.fromLocalDatastore();
        groupSavingsParseQuery.whereEqualTo("groupLocalUniqueID", groupGoal.getGroupLocalUniqueID());
        groupSavingsParseQuery.whereEqualTo("groupGoalLocalUniqueID", groupGoal.getLocalUniqueID());
        groupSavingsParseQuery.findInBackground(new FindCallback<GroupSavings>() {
            @Override
            public void done(List<GroupSavings> parseGroupSavings, ParseException e) {
                if (e == null){
                    int groupGoalTotalSavings = 0;
                    for (GroupSavings groupSaving : parseGroupSavings){
                        groupGoalTotalSavings += Integer.valueOf(groupSaving.getString("groupSavingAmount"));
                    }
                    onReturnedGroupSavingsSumListener.onResponse(groupGoalTotalSavings);
                    Log.d(TAG, "groupGoalTotalSavings: " + groupGoalTotalSavings);
                }else {
                    onReturnedGroupSavingsSumListener.onFailure(e.getMessage());
                }
            }
        });
    }


    public void updateGroupSavingInParseDb(final GroupSavings groupSavingToUpdate){
        ParseQuery<GroupSavings> groupSavingsParseQuery = ParseQuery.getQuery("ct2_GroupSavings");
        groupSavingsParseQuery.fromLocalDatastore();
        groupSavingsParseQuery.whereEqualTo("localUniqueID", groupSavingToUpdate.getLocalUniqueID());
        groupSavingsParseQuery.findInBackground(new FindCallback<GroupSavings>() {
            @Override
            public void done(List<GroupSavings> groupSavings, ParseException e) {
                if (e == null) {
                    if (groupSavings.size() == 1){
                        groupSavings.get(0).put("groupSavingGoalName", groupSavingToUpdate.getGoalName());
                        groupSavings.get(0).put("groupSavingAmount", groupSavingToUpdate.getAmount());
                        groupSavings.get(0).put("groupSavingIncomeSource", groupSavingToUpdate.getIncomeSource());
                        groupSavings.get(0).put("groupSavingPeriod", groupSavingToUpdate.getPeriod());
                        groupSavings.get(0).put("groupSavingNotes", groupSavingToUpdate.getNotes());
                        groupSavings.get(0).pinInBackground();
                        groupSavings.get(0).saveEventually();
                    }
                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void deleteGroupSavingFromParseDb(GroupSavings groupsSavingToDelete){
        ParseQuery<GroupSavings> groupSavingsParseQuery = ParseQuery.getQuery("ct2_GroupSavings");
        groupSavingsParseQuery.fromLocalDatastore();
        groupSavingsParseQuery.whereEqualTo("localUniqueID", groupsSavingToDelete.getLocalUniqueID());
        groupSavingsParseQuery.findInBackground(new FindCallback<GroupSavings>() {
            @Override
            public void done(List<GroupSavings> groupSavings, ParseException e) {
                if (e == null) {
                    if (groupSavings.size() == 1){
                        groupSavings.get(0).unpinInBackground();
                        groupSavings.get(0).deleteEventually();
                    }
                }else {
                    Log.d(TAG, "Error Occurred: " + e.getMessage());
                }
            }
        });
    }

    public void saveMemberSavingsToParseDb(MemberSavings memberSavingToSave){
        MemberSavings newMemberSaving = new MemberSavings();
        newMemberSaving.put("memberSavingLocalUniqueID", new CashTimeUtils().getUUID());
        newMemberSaving.put("memberUserName", memberSavingToSave.getMemberName());
        newMemberSaving.put("memberSavingAmount", memberSavingToSave.getSavingAmount());
        newMemberSaving.put("memberSavingGoalName", memberSavingToSave.getGoalName());
        newMemberSaving.put("memberSavingIncomeSource", memberSavingToSave.getIncomeSource());
        newMemberSaving.put("memberSavingPeriod", memberSavingToSave.getPeriod());
        newMemberSaving.put("memberSavingNotes", memberSavingToSave.getSavingNote());
        newMemberSaving.put("memberSavingDateAdded", memberSavingToSave.getDateAdded());
        newMemberSaving.put("memberLocalUniqueID", memberSavingToSave.getMemberLocalUniqueID());
        newMemberSaving.put("memberGoalParseId", memberSavingToSave.getGoalParseId());
        newMemberSaving.put("savingCreatorId", currentUserId);
        newMemberSaving.pinInBackground();
        newMemberSaving.saveEventually();
    }

    public void getMemberSavingsFromParseDb(final OnReturnedMemberSavingsListener onReturnedMemberSavingsListener){
        final List<MemberSavings> memberSavingsList = new ArrayList<>();
        ParseQuery<MemberSavings> memberSavingsParseQuery = ParseQuery.getQuery("ct2_GroupMemberSavings");
        memberSavingsParseQuery.fromLocalDatastore();
        memberSavingsParseQuery.whereEqualTo("savingCreatorId", currentUserId);
        memberSavingsParseQuery.addDescendingOrder("updatedAt");
        memberSavingsParseQuery.findInBackground(new FindCallback<MemberSavings>() {
            @Override
            public void done(List<MemberSavings> parseMemberSavings, ParseException e) {
                if (e == null){
                    for (MemberSavings retrievedMemberSavings: parseMemberSavings){
                        MemberSavings memberSaving = new MemberSavings();
                        memberSaving.setMemberName(retrievedMemberSavings.get("memberUserName").toString());
                        memberSaving.setSavingAmount(retrievedMemberSavings.get("memberSavingAmount").toString());
                        memberSaving.setGoalName(retrievedMemberSavings.get("memberSavingGoalName").toString());
                        memberSaving.setIncomeSource(retrievedMemberSavings.get("memberSavingIncomeSource").toString());
                        memberSaving.setPeriod(retrievedMemberSavings.get("memberSavingPeriod").toString());
                        memberSaving.setSavingNote(retrievedMemberSavings.get("memberSavingNotes").toString());
                        memberSaving.setDateAdded(retrievedMemberSavings.get("memberSavingDateAdded").toString());
                        memberSaving.setGoalParseId(retrievedMemberSavings.get("memberGoalParseId").toString());
                        memberSaving.setLocalUniqueID(retrievedMemberSavings.get("memberSavingLocalUniqueID").toString());

                        memberSavingsList.add(memberSaving);
                    }
                    if (onReturnedMemberSavingsListener != null){
                        onReturnedMemberSavingsListener.onResponse(memberSavingsList);
                    }
                }else {
                    onReturnedMemberSavingsListener.onFailure(e.getMessage());
                }
            }
        });
    }

    public void getTotalMemberSavingsFromParseDb(MembersGoals memberGoal, final OnReturnedMemberSavingsSumListener onReturnedMemberSavingsSumListener){
        ParseQuery<MemberSavings> memberSavingsParseQuery = ParseQuery.getQuery("ct2_GroupMemberSavings");
        memberSavingsParseQuery.fromLocalDatastore();
        memberSavingsParseQuery.whereEqualTo("memberLocalUniqueID", memberGoal.getMemberLocalUniqueID());
        memberSavingsParseQuery.whereEqualTo("memberGoalParseId", memberGoal.getLocalUniqueID());
        memberSavingsParseQuery.findInBackground(new FindCallback<MemberSavings>() {
            @Override
            public void done(List<MemberSavings> parseMemberSavings, ParseException e) {
                if (e == null){
                    int memberGoalTotalSavings = 0;
                    for (MemberSavings memberSaving : parseMemberSavings){
                        memberGoalTotalSavings += Integer.valueOf(memberSaving.getString("memberSavingAmount"));
                    }
                    onReturnedMemberSavingsSumListener.onResponse(memberGoalTotalSavings);
                }else {
                    onReturnedMemberSavingsSumListener.onFailure(e.getMessage());
                }
            }
        });
    }

    public void updateMemberSavingInParseDb(final MemberSavings memberSavingToUpdate){
        ParseQuery<MemberSavings> memberSavingsParseQuery = ParseQuery.getQuery("ct2_GroupMemberSavings");
        memberSavingsParseQuery.fromLocalDatastore();
        memberSavingsParseQuery.whereEqualTo("memberSavingLocalUniqueID", memberSavingToUpdate.getLocalUniqueID());
        memberSavingsParseQuery.findInBackground(new FindCallback<MemberSavings>() {
            @Override
            public void done(List<MemberSavings> memberSavings, ParseException e) {
                if (e == null) {
                    if (memberSavings.size() == 1){
                        memberSavings.get(0).put("memberSavingAmount", memberSavingToUpdate.getSavingAmount());
                        memberSavings.get(0).put("memberSavingIncomeSource", memberSavingToUpdate.getIncomeSource());
                        memberSavings.get(0).put("memberSavingPeriod", memberSavingToUpdate.getPeriod());
                        memberSavings.get(0).put("memberSavingNotes", memberSavingToUpdate.getSavingNote());
                    }

                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void deleteMemberSavingFromParseDb(MemberSavings memberSavingToDelete){
        ParseQuery<MemberSavings> memberSavingsParseQuery = ParseQuery.getQuery("ct2_GroupMemberSavings");
        memberSavingsParseQuery.fromLocalDatastore();
        memberSavingsParseQuery.whereEqualTo("memberSavingLocalUniqueID", memberSavingToDelete.getLocalUniqueID());
        memberSavingsParseQuery.findInBackground(   new FindCallback<MemberSavings>() {
            @Override
            public void done(List<MemberSavings> memberSavings, ParseException e) {
                if (e == null) {
                    if (memberSavings.size() == 1){
                        memberSavings.get(0).unpinInBackground();
                        memberSavings.get(0).deleteEventually();
                    }
                }else {
                    Log.d(TAG, "Error Occurred: " + e.getMessage());
                }
            }
        });
    }

    public void saveGroupBarrierToParseDb(Barrier barrierToSave, final SaveBarrierAndTipListener saveBarrierAndTipListener){
        Barrier newBarrier = new Barrier();
        newBarrier.put("userId", currentUserId);
        newBarrier.put("groupGoalName", barrierToSave.getGoalName());
        newBarrier.put("barrierName", barrierToSave.getBarrierName());
        newBarrier.put("barrierNotes", barrierToSave.getBarrierText());
        newBarrier.put("tipGiven", barrierToSave.isTipGiven());
        newBarrier.put("barrierDateAdded", barrierToSave.getDateAdded());
        newBarrier.put("groupId", barrierToSave.getGroupId());
        newBarrier.put("groupGoalParseId", barrierToSave.getGroupGoalParseId());
        newBarrier.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    saveBarrierAndTipListener.onResponse("saved");
                }else {
                    saveBarrierAndTipListener.onFailure(e.getMessage());
                }
            }
        });
    }

    public void getGroupBarriersFromParseDb(final OnReturnedGroupBarrierListener onReturnedGroupBarrierListener){
        final List<Barrier> barrierList = new ArrayList<>();
        ParseQuery<Barrier> barrierParseQuery = ParseQuery.getQuery("ct2_Barriers");
        barrierParseQuery.whereEqualTo("userId", currentUserId);
        barrierParseQuery.addDescendingOrder("updatedAt");
        barrierParseQuery.findInBackground(new FindCallback<Barrier>() {
            @Override
            public void done(List<Barrier> parseBarriers, ParseException e) {
                if (e == null){
                    for (Barrier retrievedBarrier: parseBarriers){
                        Barrier barrier = new Barrier();
                        barrier.setGoalName(retrievedBarrier.get("groupGoalName").toString());
                        barrier.setBarrierName(retrievedBarrier.get("barrierName").toString());
                        barrier.setBarrierText(retrievedBarrier.get("barrierNotes").toString());
                        barrier.setTipGiven((Boolean) retrievedBarrier.get("tipGiven"));
                        barrier.setDateAdded(retrievedBarrier.get("barrierDateAdded").toString());
                        barrier.setParseId(retrievedBarrier.getObjectId());

                        barrierList.add(barrier);
                    }
                    if (onReturnedGroupBarrierListener != null){
                        onReturnedGroupBarrierListener.onResponse(barrierList);
                    }
                } else {
                    onReturnedGroupBarrierListener.onFailure("Error Occurred");
                }

            }
        });
    }

    public void updateGroupBarriersInParseDb(final Barrier barrierToUpdate, final UpdateBarrierAndTipListener updateBarrierAndTipListener){
        ParseQuery<Barrier> barrierParseQuery = ParseQuery.getQuery("ct2_Barriers");
        barrierParseQuery.getInBackground(barrierToUpdate.getParseId(), new GetCallback<Barrier>() {
            @Override
            public void done(Barrier parseBarrier, ParseException e) {
                if (e == null) {
                    parseBarrier.put("barrierName", barrierToUpdate.getBarrierName());
                    parseBarrier.put("barrierNotes", barrierToUpdate.getBarrierText());
                    parseBarrier.put("tipGiven", barrierToUpdate.isTipGiven());
                    parseBarrier.put("barrierDateAdded", barrierToUpdate.getDateAdded());
                    parseBarrier.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                updateBarrierAndTipListener.onResponse("updated");
                            }else {
                                updateBarrierAndTipListener.onFailure(e.getMessage());
                            }
                        }
                    });

                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void deleteGroupBarrierFromParseDb(Barrier barrierToDelete, final DeleteBarrierAndTipListener deleteBarrierAndTipListener){
        ParseQuery<Barrier> barrierParseQuery = ParseQuery.getQuery("ct2_Barriers");
        barrierParseQuery.getInBackground(barrierToDelete.getParseId(), new GetCallback<Barrier>() {
            @Override
            public void done(Barrier parseBarrier, ParseException e) {
                if (e == null) {
                    parseBarrier.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                deleteBarrierAndTipListener.onResponse("deleted");
                            }else {
                                deleteBarrierAndTipListener.onFailure(e.getMessage());
                            }
                        }
                    });
                }else {
                    Log.d(TAG, "Error Occurred: " + e.getMessage());
                }
            }
        });
    }

    public void saveTipToParseDb(Tip tipToSave, final SaveBarrierAndTipListener saveBarrierAndTipListener){
        Tip newTip = new Tip();
        newTip.put("userId", currentUserId);
        newTip.put("groupGoalName", tipToSave.getGoalName());
        newTip.put("tipNotes", tipToSave.getIntroText());
        newTip.put("dateAdded", tipToSave.getDateAdded());
        newTip.put("dateModified", tipToSave.getDateModified());
        newTip.put("groupParseId", tipToSave.getGroupParseId());
        newTip.put("groupGoalParseId", tipToSave.getGroupGoalParseId());
        newTip.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    saveBarrierAndTipListener.onResponse("saved");
                }else {
                    saveBarrierAndTipListener.onFailure(e.getMessage());
                }
            }
        });
    }

    public void getAllTipsFromParseDb(final OnReturnedTipsListener onReturnedTipsListener){
        final List<Tip> tipList = new ArrayList<>();
        ParseQuery<Tip> tipParseQuery = ParseQuery.getQuery("ct2_Tips");
        tipParseQuery.whereEqualTo("userId", currentUserId);
        tipParseQuery.addDescendingOrder("updatedAt");
        tipParseQuery.findInBackground(new FindCallback<Tip>() {
            @Override
            public void done(List<Tip> parseTips, ParseException e) {
                if (e == null){
                    for (Tip retrievedTip: parseTips){
                        Tip tip = new Tip();
                        tip.setGoalName(retrievedTip.get("groupGoalName").toString());
                        tip.setIntroText(retrievedTip.get("tipNotes").toString());
                        tip.setDateAdded(retrievedTip.get("dateAdded").toString());
                        tip.setDateModified(retrievedTip.get("dateModified").toString());
                        tip.setTipParseId(retrievedTip.getObjectId());

                        tipList.add(tip);
                    }
                    if (onReturnedTipsListener != null){
                        onReturnedTipsListener.onResponse(tipList);
                    }
                } else {
                    onReturnedTipsListener.onFailure("Error Occurred");
                }

            }
        });
    }


    public void getTipsOfParticularGoalFromParseDb(String nameOfGoal, final OnReturnedTipsListener onReturnedTipsListener){
        final List<Tip> tipList = new ArrayList<>();
        ParseQuery<Tip> tipParseQuery = ParseQuery.getQuery("ct2_Tips");
        tipParseQuery.addDescendingOrder("updatedAt");
        tipParseQuery.whereEqualTo("groupGoalName", nameOfGoal);
        tipParseQuery.findInBackground(new FindCallback<Tip>() {
            @Override
            public void done(List<Tip> parseTips, ParseException e) {
                if (e == null){
                    for (Tip retrievedTip: parseTips){
                        Tip tip = new Tip();
                        tip.setGoalName(retrievedTip.get("groupGoalName").toString());
                        tip.setIntroText(retrievedTip.get("tipNotes").toString());
                        tip.setDateAdded(retrievedTip.get("dateAdded").toString());
                        tip.setDateModified(retrievedTip.get("dateModified").toString());
                        tip.setTipParseId(retrievedTip.getObjectId());

                        tipList.add(tip);
                    }
                    if (onReturnedTipsListener != null){
                        onReturnedTipsListener.onResponse(tipList);
                    }
                } else {
                    onReturnedTipsListener.onFailure("Error Occurred");
                }

            }
        });
    }

    public void updateTipsInParseDb(final Tip tipToUpdate, final UpdateBarrierAndTipListener updateBarrierAndTipListener){
        ParseQuery<Tip> tipParseQuery = ParseQuery.getQuery("ct2_Tips");
        tipParseQuery.getInBackground(tipToUpdate.getTipParseId(), new GetCallback<Tip>() {
            @Override
            public void done(Tip parseTip, ParseException e) {
                if (e == null) {
                    parseTip.put("tipNotes", tipToUpdate.getIntroText());
                    parseTip.put("dateModified", tipToUpdate.getDateModified());
                    parseTip.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                updateBarrierAndTipListener.onResponse("updated");
                            }else {
                                updateBarrierAndTipListener.onFailure(e.getMessage());
                            }
                        }
                    });

                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void deleteTipFromParseDb(Tip tipToDelete, final DeleteBarrierAndTipListener deleteBarrierAndTipListener){
        ParseQuery<Tip> tipParseQuery = ParseQuery.getQuery("ct2_Tips");
        tipParseQuery.getInBackground(tipToDelete.getTipParseId(), new GetCallback<Tip>() {
            @Override
            public void done(Tip parseTip, ParseException e) {
                if (e == null) {
                    parseTip.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                deleteBarrierAndTipListener.onResponse("deleted");
                            }else {
                                deleteBarrierAndTipListener.onFailure(e.getMessage());
                            }
                        }
                    });
                }else {
                    Log.d(TAG, "Error Occurred: " + e.getMessage());
                }
            }
        });
    }

}
