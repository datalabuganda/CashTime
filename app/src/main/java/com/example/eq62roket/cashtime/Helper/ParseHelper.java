package com.example.eq62roket.cashtime.Helper;

import android.content.Context;
import android.util.Log;

import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupBarrierListener;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupSavingsListener;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedMemberGoalListener;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedMemberSavingsListener;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedTipsListener;
import com.example.eq62roket.cashtime.Models.Barrier;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.Models.GroupSavings;
import com.example.eq62roket.cashtime.Models.MemberSavings;
import com.example.eq62roket.cashtime.Models.MembersGoals;
import com.example.eq62roket.cashtime.Models.Tip;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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
        newGroupGoal.put("goalName", groupGoals.getName());
        newGroupGoal.put("goalAmount", groupGoals.getAmount());
        newGroupGoal.put("goalText", groupGoals.getNotes());
        newGroupGoal.put("goalStatus", groupGoals.getGroupGoalStatus());
        newGroupGoal.put("goalEndDate", groupGoals.getDueDate());
        newGroupGoal.put("groupParseId", groupGoals.getGroupId());
        newGroupGoal.put("groupName", groupGoals.getGroupName());
        newGroupGoal.saveInBackground();

    }

    public void getGroupGoalsFromParseDb(final OnReturnedGroupGoalsListener onReturnedGroupGoalsListener){
        final List<GroupGoals> groupGoalList = new ArrayList<>();
        ParseQuery<GroupGoals> groupGoalsQuery = ParseQuery.getQuery("GroupGoals");
        groupGoalsQuery.addDescendingOrder("updatedAt");
//        groupGoalsQuery.whereEqualTo("userId", currentUserId);
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
                        newGroupGoal.setGroupId(retrievedGroupGoal.get("groupParseId").toString());
                        newGroupGoal.setGroupName(retrievedGroupGoal.get("groupName").toString());
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

        Log.d(TAG, "Current User Id" + currentUserId);
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
        newMemberGoal.put("userId", currentUserId);
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
//        membersGoalsParseQuery.whereEqualTo("userId", currentUserId);
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
        newGroupSaving.put("userId", currentUserId);
        newGroupSaving.put("groupSavingAmount", groupSaving.getAmount());
        newGroupSaving.put("groupSavingGoalName",groupSaving.getGoalName());
        newGroupSaving.put("groupSavingIncomeSource", groupSaving.getIncomeSource());
        newGroupSaving.put("groupSavingPeriod", groupSaving.getPeriod());
        newGroupSaving.put("groupSavingNotes", groupSaving.getNotes());
        newGroupSaving.put("groupSavingDateAdded", groupSaving.getDateAdded());
        newGroupSaving.saveInBackground();
    }

    public void getGroupSavingsFromParseDb(final OnReturnedGroupSavingsListener onReturnedGroupSavingsListener){
        final List<GroupSavings> groupSavingsList = new ArrayList<>();
        ParseQuery<GroupSavings> groupSavingsParseQuery = ParseQuery.getQuery("GroupSavings");
//        groupSavingsParseQuery.whereEqualTo("userId", currentUserId);
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
                        groupSaving.setParseId(retrievedGroupSavings.getObjectId());

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

    public void updateGroupSavingInParseDb(final GroupSavings groupSavingToUpdate){
        ParseQuery<GroupSavings> groupSavingsParseQuery = ParseQuery.getQuery("GroupSavings");
        groupSavingsParseQuery.getInBackground(groupSavingToUpdate.getParseId(), new GetCallback<GroupSavings>() {
            @Override
            public void done(GroupSavings groupSaving, ParseException e) {
                if (e == null) {
                    groupSaving.put("groupSavingGoalName", groupSavingToUpdate.getGoalName());
                    groupSaving.put("groupSavingAmount", groupSavingToUpdate.getAmount());
                    groupSaving.put("groupSavingIncomeSource", groupSavingToUpdate.getIncomeSource());
                    groupSaving.put("groupSavingPeriod", groupSavingToUpdate.getPeriod());
                    groupSaving.put("groupSavingNotes", groupSavingToUpdate.getNotes());
                    groupSaving.put("groupSavingDateAdded", groupSavingToUpdate.getDateAdded());
                    groupSaving.saveInBackground();

                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void deleteGroupSavingFromParseDb(GroupSavings groupsSavingToDelete){
        ParseQuery<GroupSavings> groupGoalQuery = ParseQuery.getQuery("GroupSavings");
        groupGoalQuery.getInBackground(groupsSavingToDelete.getParseId(), new GetCallback<GroupSavings>() {
            @Override
            public void done(GroupSavings groupSaving, ParseException e) {
                if (e == null) {
                    groupSaving.deleteInBackground();
                }else {
                    Log.d(TAG, "Error Occured: " + e.getMessage());
                }
            }
        });
    }

    public void saveMemberSavingsToParseDb(MemberSavings memberSavingToSave){
        MemberSavings newMemberSaving = new MemberSavings();
        newMemberSaving.put("userId", currentUserId);
        newMemberSaving.put("memberUserName", memberSavingToSave.getMemberName());
        newMemberSaving.put("memberSavingAmount", memberSavingToSave.getSavingAmount());
        newMemberSaving.put("memberSavingGoalName", memberSavingToSave.getGoalName());
        newMemberSaving.put("memberSavingIncomeSource", memberSavingToSave.getIncomeSource());
        newMemberSaving.put("memberSavingPeriod", memberSavingToSave.getPeriod());
        newMemberSaving.put("memberSavingNotes", memberSavingToSave.getSavingNote());
        newMemberSaving.put("memberSavingDateAdded", memberSavingToSave.getDateAdded());
        newMemberSaving.saveInBackground();
    }

    public void getMemberSavingsFromParseDb(final OnReturnedMemberSavingsListener onReturnedMemberSavingsListener){
        final List<MemberSavings> memberSavingsList = new ArrayList<>();
        ParseQuery<MemberSavings> memberSavingsParseQuery = ParseQuery.getQuery("GroupMemberSavings");
        memberSavingsParseQuery.whereEqualTo("userId", currentUserId);
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
                        memberSaving.setParseId(retrievedMemberSavings.getObjectId());

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

    public void updateMemberSavingInParseDb(final MemberSavings memberSavingToUpdate){
        ParseQuery<MemberSavings> memberSavingsParseQuery = ParseQuery.getQuery("GroupMemberSavings");
        memberSavingsParseQuery.getInBackground(memberSavingToUpdate.getParseId(), new GetCallback<MemberSavings>() {
            @Override
            public void done(MemberSavings memberSaving, ParseException e) {
                if (e == null) {
                    memberSaving.put("memberSavingAmount", memberSavingToUpdate.getSavingAmount());
                    memberSaving.put("memberSavingIncomeSource", memberSavingToUpdate.getIncomeSource());
                    memberSaving.put("memberSavingPeriod", memberSavingToUpdate.getPeriod());
                    memberSaving.put("memberSavingNotes", memberSavingToUpdate.getSavingNote());
                    memberSaving.put("memberSavingDateAdded", memberSavingToUpdate.getDateAdded());
                    memberSaving.saveInBackground();

                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void deleteMemberSavingFromParseDb(MemberSavings memberSavingToDelete){
        ParseQuery<MemberSavings> groupGoalQuery = ParseQuery.getQuery("GroupMemberSavings");
        groupGoalQuery.getInBackground(memberSavingToDelete.getParseId(), new GetCallback<MemberSavings>() {
            @Override
            public void done(MemberSavings memberSaving, ParseException e) {
                if (e == null) {
                    memberSaving.deleteInBackground();
                }else {
                    Log.d(TAG, "Error Occured: " + e.getMessage());
                }
            }
        });
    }

    public void saveGroupBarrierToParseDb(Barrier barrierToSave){
        Barrier newBarrier = new Barrier();
        newBarrier.put("userId", currentUserId);
        newBarrier.put("groupGoalName", barrierToSave.getGoalName());
        newBarrier.put("barrierName", barrierToSave.getBarrierName());
        newBarrier.put("barrierNotes", barrierToSave.getBarrierText());
        newBarrier.put("tipGiven", barrierToSave.isTipGiven());
        newBarrier.put("barrierDateAdded", barrierToSave.getDateAdded());
        newBarrier.saveInBackground();
    }

    public void getGroupBarriersFromParseDb(final OnReturnedGroupBarrierListener onReturnedGroupBarrierListener){
        final List<Barrier> barrierList = new ArrayList<>();
        ParseQuery<Barrier> barrierParseQuery = ParseQuery.getQuery("Barriers");
//        barrierParseQuery.whereEqualTo("userId", currentUserId);
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
                    onReturnedGroupBarrierListener.onFailure("Error Occured");
                }

            }
        });
    }

    public void updateGroupBarriersInParseDb(final Barrier barrierToUpdate){
        ParseQuery<Barrier> barrierParseQuery = ParseQuery.getQuery("Barriers");
        barrierParseQuery.getInBackground(barrierToUpdate.getParseId(), new GetCallback<Barrier>() {
            @Override
            public void done(Barrier parseBarrier, ParseException e) {
                if (e == null) {
                    parseBarrier.put("barrierName", barrierToUpdate.getBarrierName());
                    parseBarrier.put("barrierNotes", barrierToUpdate.getBarrierText());
                    parseBarrier.put("tipGiven", barrierToUpdate.isTipGiven());
                    parseBarrier.put("barrierDateAdded", barrierToUpdate.getDateAdded());

                    parseBarrier.saveInBackground();

                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void deleteGroupBarrierFromParseDb(Barrier barrierToDelete){
        ParseQuery<Barrier> barrierParseQuery = ParseQuery.getQuery("Barriers");
        barrierParseQuery.getInBackground(barrierToDelete.getParseId(), new GetCallback<Barrier>() {
            @Override
            public void done(Barrier parseBarrier, ParseException e) {
                if (e == null) {
                    parseBarrier.deleteInBackground();
                }else {
                    Log.d(TAG, "Error Occured: " + e.getMessage());
                }
            }
        });
    }

    public void saveTipToParseDb(Tip tipToSave){
        Tip newTip = new Tip();
        newTip.put("userId", currentUserId);
        newTip.put("groupGoalName", tipToSave.getGoalName());
        newTip.put("tipNotes", tipToSave.getIntroText());
        newTip.put("dateAdded", tipToSave.getDateAdded());
        newTip.put("dateModified", tipToSave.getDateModified());
        newTip.saveInBackground();
    }

    public void getAllTipsFromParseDb(final OnReturnedTipsListener onReturnedTipsListener){
        final List<Tip> tipList = new ArrayList<>();
        ParseQuery<Tip> tipParseQuery = ParseQuery.getQuery("Tips");
//        tipParseQuery.whereEqualTo("userId", currentUserId);
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
                    onReturnedTipsListener.onFailure("Error Occured");
                }

            }
        });
    }


    public void getTipsOfParticularGoalFromParseDb(String nameOfGoal, final OnReturnedTipsListener onReturnedTipsListener){
        final List<Tip> tipList = new ArrayList<>();
        ParseQuery<Tip> tipParseQuery = ParseQuery.getQuery("Tips");
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
                    onReturnedTipsListener.onFailure("Error Occured");
                }

            }
        });
    }

    public void updateTipsInParseDb(final Tip tipToUpdate){
        ParseQuery<Tip> tipParseQuery = ParseQuery.getQuery("Tips");
        tipParseQuery.getInBackground(tipToUpdate.getTipParseId(), new GetCallback<Tip>() {
            @Override
            public void done(Tip parseTip, ParseException e) {
                if (e == null) {
                    parseTip.put("tipNotes", tipToUpdate.getIntroText());
                    parseTip.put("dateModified", tipToUpdate.getDateModified());
                    parseTip.saveInBackground();

                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void deleteTipFromParseDb(Tip tipToDelete){
        ParseQuery<Tip> tipParseQuery = ParseQuery.getQuery("Tips");
        tipParseQuery.getInBackground(tipToDelete.getTipParseId(), new GetCallback<Tip>() {
            @Override
            public void done(Tip parseTip, ParseException e) {
                if (e == null) {
                    parseTip.deleteInBackground();
                }else {
                    Log.d(TAG, "Error Occured: " + e.getMessage());
                }
            }
        });
    }

}
