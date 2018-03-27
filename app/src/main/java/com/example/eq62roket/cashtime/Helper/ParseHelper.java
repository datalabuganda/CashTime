package com.example.eq62roket.cashtime.Helper;

import android.content.Context;
import android.util.Log;

import com.example.eq62roket.cashtime.Models.GroupGoals;
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
    private final List<GroupGoals> groupGoalList = new ArrayList<>();


    private Context mContext;
    private OnReturnedGroupGoalsListener mOnReturnedGroupGoalsListener;


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
                    Log.d(TAG, "Should delete now: ");
                    groupGoal.deleteInBackground();
                }else {
                    Log.d(TAG, "Error Occured: " + e.getMessage());
                }
            }
        });
    }



}
