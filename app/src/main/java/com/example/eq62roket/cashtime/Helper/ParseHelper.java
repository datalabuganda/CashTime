package com.example.eq62roket.cashtime.Helper;

import android.content.Context;
import android.util.Log;

import com.example.eq62roket.cashtime.Interfaces.OnReturnedMemberGoalListener;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.Models.GroupSavings;
import com.example.eq62roket.cashtime.Models.MembersGoals;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

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


    public ParseHelper(Context context){
        mContext = context;
    }

    public void saveGroupGoalsToParseDb(GroupGoals groupGoals){
        GroupGoals newGroupGoal = new GroupGoals();
        newGroupGoal.put("goalName", groupGoals.getName());
        newGroupGoal.put("goalAmount", groupGoals.getAmount());
        newGroupGoal.put("goalText", groupGoals.getNotes());
        newGroupGoal.put("goalStatus", groupGoals.getGroupGoalStatus());
        newGroupGoal.put("goalEndDate", groupGoals.getDueDate());
        newGroupGoal.saveInBackground();

    }

    public void getGroupGoalsFromParseDb(final OnReturnedGroupGoalsListener onReturnedGroupGoalsListener){
        final List<GroupGoals> groupGoalList = new ArrayList<>();
        ParseQuery<GroupGoals> groupGoalsQuery = ParseQuery.getQuery("GroupGoals");
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
                        newGroupGoal.setParseId(retrievedGroupGoal.getObjectId());

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
        ParseQuery<GroupGoals> groupGoalQuery = ParseQuery.getQuery("GroupGoals");
        groupGoalQuery.getInBackground(groupGoalToUpdate.getParseId(), new GetCallback<GroupGoals>() {
            @Override
            public void done(GroupGoals groupGoal, ParseException e) {
                if (e == null) {
                    groupGoal.put("goalName", groupGoalToUpdate.getName());
                    groupGoal.put("goalAmount", groupGoalToUpdate.getAmount());
                    groupGoal.put("goalText", groupGoalToUpdate.getNotes());
                    groupGoal.put("goalEndDate", groupGoalToUpdate.getDueDate());
                    groupGoal.saveInBackground();

                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void deleteGroupGoalFromParseDb(GroupGoals groupGoalToDelete){
        ParseQuery<GroupGoals> groupGoalQuery = ParseQuery.getQuery("GroupGoals");
        groupGoalQuery.getInBackground(groupGoalToDelete.getParseId(), new GetCallback<GroupGoals>() {
            @Override
            public void done(GroupGoals groupGoal, ParseException e) {
                if (e == null) {
                    groupGoal.deleteInBackground();
                }else {
                    Log.d(TAG, "Error Occured: " + e.getMessage());
                }
            }
        });

    }

    public void saveMemberGoalsToParseDb(MembersGoals membersGoal){
        MembersGoals newMemberGoal = new MembersGoals();
        newMemberGoal.put("memberUsernames", membersGoal.getMemberName());
        newMemberGoal.put("memberGoalName", membersGoal.getMemberGoalName());
        newMemberGoal.put("memberGoalAmount", membersGoal.getMemberGoalAmount());
        newMemberGoal.put("memberGoalDeadline", membersGoal.getMemberGoalDueDate());
        newMemberGoal.put("memberGoalStatus", membersGoal.getMemberGoalStatus());
        newMemberGoal.put("memberGoalNotes", membersGoal.getMemberGoalNotes());
        newMemberGoal.saveInBackground();

    }

    public void getMemberGoalsFromParseDb(final OnReturnedMemberGoalListener onReturnedMemberGoalListener){
        final List<MembersGoals> membersGoalsList = new ArrayList<>();
        ParseQuery<MembersGoals> membersGoalsParseQuery = ParseQuery.getQuery("MemberGoals");
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
                        memberGoal.setParseId(retrievedMemberGoal.getObjectId());

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
        ParseQuery<MembersGoals> membersGoalsParseQuery = ParseQuery.getQuery("MemberGoals");
        membersGoalsParseQuery.getInBackground(membersGoalToUpdate.getParseId(), new GetCallback<MembersGoals>() {
            @Override
            public void done(MembersGoals membersGoal, ParseException e) {
                if (e == null) {
                    membersGoal.put("memberGoalName", membersGoalToUpdate.getMemberGoalName());
                    membersGoal.put("memberGoalAmount", membersGoalToUpdate.getMemberGoalAmount());
                    membersGoal.put("memberGoalNotes", membersGoalToUpdate.getMemberGoalNotes());
                    membersGoal.put("memberGoalDeadline", membersGoalToUpdate.getMemberGoalDueDate());
                    membersGoal.saveInBackground();

                    Log.d(TAG, "done:>>>>>>>>>>>>>>>>> ");

                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void deleteMemberGoalFromParseDb(MembersGoals membersGoalToDelete){
        ParseQuery<MembersGoals> membersGoalsParseQuery = ParseQuery.getQuery("MemberGoals");
        membersGoalsParseQuery.getInBackground(membersGoalToDelete.getParseId(), new GetCallback<MembersGoals>() {
            @Override
            public void done(MembersGoals membersGoal, ParseException e) {
                if (e == null) {
                    membersGoal.deleteInBackground();
                }else {
                    Log.d(TAG, "Error Occured: " + e.getMessage());
                }
            }
        });

    }


    public void saveGroupSavingsToParseDb(GroupSavings groupSaving){
        GroupSavings newGroupSaving = new GroupSavings();
        newGroupSaving.put("groupSavingAmount", groupSaving.getAmount());
        newGroupSaving.put("groupSavingGoalName",groupSaving.getGoalName());
        newGroupSaving.put("groupSavingIncomeSource", groupSaving.getIncomeSource());
        newGroupSaving.put("groupSavingPeriod", groupSaving.getPeriod());
        newGroupSaving.put("groupSavingNotes", groupSaving.getNotes());
        newGroupSaving.saveInBackground();

    }
}
