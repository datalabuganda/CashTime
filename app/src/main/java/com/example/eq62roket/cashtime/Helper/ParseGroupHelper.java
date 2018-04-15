package com.example.eq62roket.cashtime.Helper;

import android.content.Context;
import android.util.Log;

import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupMemberListener;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupsListener;
import com.example.eq62roket.cashtime.Interfaces.OnSuccessfulRegistrationListener;
import com.example.eq62roket.cashtime.Models.Group;
import com.example.eq62roket.cashtime.Models.GroupMember;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etwin on 3/28/18.
 */

public class ParseGroupHelper {

    public interface SaveGroupListener{
        void onResponse(String saveMessage);
        void onFailure(String error);
    }

    public interface UpdateGroupListener{
        void onResponse(String updateMessage);
        void onFailure(String error);
    }

    public interface DeleteGroupListener{
        void onResponse(String updateMessage);
        void onFailure(String error);
    }

    public interface UpdateGroupMemberListener{
        void onResponse(String updateMessage);
        void onFailure(String error);
    }

    private String currentUserId;
    private static final String TAG = "ParseGroupHelper";
    private Context mContext;

    public ParseGroupHelper(Context context){
        mContext = context;
        currentUserId = ParseUser.getCurrentUser().getObjectId();
    }

    public void saveNewGroupToParseDb(Group groupToSave) {
        Group newGroup = new Group();
        newGroup.put("localUniqueID", new CashTimeUtils().getUUID());
        newGroup.put("groupName", groupToSave.getGroupName());
        newGroup.put("groupLocation", groupToSave.getLocationOfGroup());
        newGroup.put("groupCentreName", groupToSave.getGroupCentreName());
        newGroup.put("groupCreatorId", groupToSave.getGroupCreatorId());
        newGroup.put("groupLeaderId", groupToSave.getGroupLeaderId());
        newGroup.put("groupMemberCount", groupToSave.getGroupMemberCount());
        newGroup.put("groupDateCreated", groupToSave.getDateCreated());
        newGroup.pinInBackground();
        newGroup.saveEventually();
    }

    public void getAllGroupsFromParseDb(final OnReturnedGroupsListener onReturnedGroupsListener){
        final List<Group> groupList = new ArrayList<>();
        ParseQuery<Group> groupParseQuery = ParseQuery.getQuery("ct2_Groups");
        groupParseQuery.fromLocalDatastore();
        groupParseQuery.whereEqualTo("groupCreatorId", currentUserId);
        groupParseQuery.addDescendingOrder("updatedAt");
        groupParseQuery.findInBackground(new FindCallback<Group>() {
            @Override
            public void done(List<Group> parseGroupList, ParseException e) {
                if (e == null){
                    for (Group returnedGroup: parseGroupList){
                        Group group = new Group();
                        group.setGroupName(returnedGroup.get("groupName").toString());
                        group.setLocationOfGroup(returnedGroup.get("groupLocation").toString());
                        group.setGroupCentreName(returnedGroup.get("groupCentreName").toString());
                        group.setGroupCreatorId(returnedGroup.get("groupCreatorId").toString());
                        group.setGroupLeaderId(returnedGroup.get("groupLeaderId").toString());
                        group.setGroupMemberCount(returnedGroup.getInt("groupMemberCount"));
                        group.setDateCreated(returnedGroup.get("groupDateCreated").toString());
                        group.setLocalUniqueID(returnedGroup.get("localUniqueID").toString());

                        groupList.add(group);
                    }
                    onReturnedGroupsListener.onResponse(groupList);
                }else {
                    onReturnedGroupsListener.onFailure(e.getMessage());
                }
            }
        });
    }

    public void getParticularGroupFromParseDb(String groupId, final OnReturnedGroupsListener onReturnedGroupsListener){
        final List<Group> groupList = new ArrayList<>();
        ParseQuery<Group> groupParseQuery = ParseQuery.getQuery("ct2_Groups");
        groupParseQuery.whereEqualTo("groupCreatorId", currentUserId);
        groupParseQuery.whereEqualTo("objectId", groupId);
        groupParseQuery.addDescendingOrder("updatedAt");
        groupParseQuery.findInBackground(new FindCallback<Group>() {
            @Override
            public void done(List<Group> parseGroupList, ParseException e) {
                if (e == null){
                    for (Group returnedGroup: parseGroupList){
                        Group group = new Group();
                        group.setGroupName(returnedGroup.get("groupName").toString());
                        group.setLocationOfGroup(returnedGroup.get("groupLocation").toString());
                        group.setGroupCentreName(returnedGroup.get("groupCentreName").toString());
                        group.setGroupCreatorId(returnedGroup.get("groupCreatorId").toString());
                        group.setGroupLeaderId(returnedGroup.get("groupLeaderId").toString());
                        group.setGroupMemberCount(returnedGroup.getInt("groupMemberCount"));
                        group.setDateCreated(returnedGroup.get("groupDateCreated").toString());
                        group.setLocalUniqueID(returnedGroup.get("localUniqueID").toString());

                        groupList.add(group);
                    }
                    onReturnedGroupsListener.onResponse(groupList);
                }else {
                    onReturnedGroupsListener.onFailure(e.getMessage());
                }
            }
        });
    }

    public void updateGroupInParseDb(final Group groupToUpdate){
        ParseQuery<Group> groupParseQuery = ParseQuery.getQuery("ct2_Groups");
        groupParseQuery.fromLocalDatastore();
        groupParseQuery.whereContains("localUniqueID", groupToUpdate.getLocalUniqueID());
        groupParseQuery.findInBackground(new FindCallback<Group>() {
            @Override
            public void done(List<Group> group, ParseException e) {
                if (e == null){
                    if (group.size() == 1){
                        group.get(0).put("groupName", groupToUpdate.getGroupName());
                        group.get(0).put("groupLocation", groupToUpdate.getLocationOfGroup());
                        group.get(0).put("groupCentreName", groupToUpdate.getGroupCentreName());
                        group.get(0).pinInBackground();
                        group.get(0).saveEventually();
                    }
                }else {
                    Log.d(TAG, "Error Occured: " + e.getMessage());
                }
            }
        });
    }

    public void deleteGroupFromParseDb(final Group groupToUpdate){
        ParseQuery<Group> groupParseQuery = ParseQuery.getQuery("ct2_Groups");
        groupParseQuery.fromLocalDatastore();
        groupParseQuery.whereContains("localUniqueID", groupToUpdate.getLocalUniqueID());
        groupParseQuery.findInBackground(new FindCallback<Group>() {
            @Override
            public void done(List<Group> group, ParseException e) {
                if (e == null){
                    if (group.size() == 1){
                        group.get(0).unpinInBackground();
                        group.get(0).deleteEventually();
                    }
                }else {
                    Log.d(TAG, "Error Occurred: " + e.getMessage());
                }
            }
        });
    }

    public void incrementGroupMemberCount(Group groupToUpdate) {
        ParseQuery<Group> groupParseQuery = ParseQuery.getQuery("ct2_Groups");
        groupParseQuery.getInBackground(groupToUpdate.getLocalUniqueID(), new GetCallback<Group>() {
            @Override
            public void done(Group parseGroup, ParseException e) {
                if (e == null){
                    parseGroup.increment("groupMemberCount");
                    parseGroup.saveInBackground();
                }else {
                    Log.d(TAG, "An Error Occurred: " + e.getMessage());
                }
            }
        });
    }

    public void decrementGroupMemberCount(Group groupToUpdate) {
        long oldGroupMemberCount = groupToUpdate.getGroupMemberCount();
        final long newGroupMemberCount = oldGroupMemberCount - 1;
        ParseQuery<Group> groupParseQuery = ParseQuery.getQuery("ct2_Groups");
        groupParseQuery.getInBackground(groupToUpdate.getLocalUniqueID(), new GetCallback<Group>() {
            @Override
            public void done(Group parseGroup, ParseException e) {
                if (e == null){
                    parseGroup.put("groupMemberCount", newGroupMemberCount);
                    parseGroup.saveInBackground();
                }else {
                    Log.d(TAG, "An Error Occurred: " + e.getMessage());
                }
            }
        });
    }

    public void saveGroupMemberUserToParseDb(GroupMember groupMemberToSave, final OnSuccessfulRegistrationListener onSuccessfulRegistrationListener){
        GroupMember newGroupMember = new GroupMember();
        newGroupMember.put("memberUsername", groupMemberToSave.getMemberUsername());
        newGroupMember.put("memberPhoneNumber", groupMemberToSave.getMemberPhoneNumber());
        newGroupMember.put("memberHousehold", groupMemberToSave.getMemberHousehold());
        newGroupMember.put("memberGender", groupMemberToSave.getMemberGender());
        newGroupMember.put("memberBusiness", groupMemberToSave.getMemberBusiness());
        newGroupMember.put("memberEducationLevel", groupMemberToSave.getMemberEducationLevel());
        newGroupMember.put("memberNationality", groupMemberToSave.getMemberNationality());
        newGroupMember.put("memberLocation", groupMemberToSave.getMemberLocation());
        newGroupMember.put("memberPoints", groupMemberToSave.getMemberPoints());
        newGroupMember.put("memberGroupId", groupMemberToSave.getMemberGroupLocalUniqueId());
//        newGroupMember.put("localUniqueID", new CashTimeUtils().getUUID());
        newGroupMember.put("memberCreatorId", currentUserId);
//        newGroupMember.put("groupName", groupMemberToSave.getGroupName());
        newGroupMember.pinInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    onSuccessfulRegistrationListener.onResponse("registered");
                }else{
                    onSuccessfulRegistrationListener.onFailure( e.getMessage());
                }
            }
        });

    }

    public void getGroupMembersFromParseDb(String groupId, final OnReturnedGroupMemberListener onReturnedGroupMemberListener){
        final List<GroupMember> groupMemberList = new ArrayList<>();
        ParseQuery<GroupMember> groupMemberParseQuery = ParseQuery.getQuery("ct2_GroupMembers");
        groupMemberParseQuery.fromLocalDatastore();
        groupMemberParseQuery.whereEqualTo("memberCreatorId", currentUserId);
        groupMemberParseQuery.whereEqualTo("memberGroupId", groupId);
        groupMemberParseQuery.addDescendingOrder("updatedAt");

        groupMemberParseQuery.findInBackground(new FindCallback<GroupMember>() {
            @Override
            public void done(List<GroupMember> parseGroupMemberList, ParseException e) {
                if (e == null){
                    for (GroupMember returnedGroupMember: parseGroupMemberList){
                        GroupMember groupMember = new GroupMember();
//                        groupMember.setLocalUniqueID(returnedGroupMember.get("localUniqueID").toString());
                        groupMember.setMemberUsername(returnedGroupMember.get("memberUsername").toString());
                        groupMember.setMemberPhoneNumber(returnedGroupMember.get("memberPhoneNumber").toString());
                        groupMember.setMemberHousehold(returnedGroupMember.get("memberHousehold").toString());
                        groupMember.setMemberGender(returnedGroupMember.get("memberGender").toString());
                        groupMember.setMemberBusiness(returnedGroupMember.get("memberBusiness").toString());
                        groupMember.setMemberEducationLevel(returnedGroupMember.get("memberEducationLevel").toString());
                        groupMember.setMemberNationality(returnedGroupMember.get("memberNationality").toString());
                        groupMember.setMemberLocation(returnedGroupMember.get("memberLocation").toString());
                        groupMember.setMemberPoints(Long.parseLong(String.valueOf(returnedGroupMember.get("memberPoints"))));
                        groupMember.setMemberGroupLocalUniqueId(returnedGroupMember.get("memberGroupId").toString());
//                        groupMember.setGroupName(returnedGroupMember.get("groupName").toString());
                        groupMember.setMemberParseId(returnedGroupMember.getObjectId());

                        groupMemberList.add(groupMember);
                    }
                    onReturnedGroupMemberListener.onResponse(groupMemberList);
                }else {
                    onReturnedGroupMemberListener.onFailure(e.getMessage());
                }
            }
        });
    }

    public void getAllMembersFromParseDb(final OnReturnedGroupMemberListener onReturnedGroupMemberListener){
        final List<GroupMember> groupMemberList = new ArrayList<>();
        ParseQuery<GroupMember> groupMemberParseQuery = ParseQuery.getQuery("ct2_GroupMembers");
        groupMemberParseQuery.fromLocalDatastore();
        groupMemberParseQuery.whereEqualTo("memberCreatorId", currentUserId);
        groupMemberParseQuery.addDescendingOrder("updatedAt");

        groupMemberParseQuery.findInBackground(new FindCallback<GroupMember>() {
            @Override
            public void done(List<GroupMember> parseGroupMemberList, ParseException e) {
                if (e == null){
                    for (GroupMember returnedGroupMember: parseGroupMemberList){
                        GroupMember groupMember = new GroupMember();
//                        groupMember.setLocalUniqueID(returnedGroupMember.get("localUniqueID").toString());
                        groupMember.setMemberUsername(returnedGroupMember.get("memberUsername").toString());
                        groupMember.setMemberPhoneNumber(returnedGroupMember.get("memberPhoneNumber").toString());
                        groupMember.setMemberHousehold(returnedGroupMember.get("memberHousehold").toString());
                        groupMember.setMemberGender(returnedGroupMember.get("memberGender").toString());
                        groupMember.setMemberBusiness(returnedGroupMember.get("memberBusiness").toString());
                        groupMember.setMemberEducationLevel(returnedGroupMember.get("memberEducationLevel").toString());
                        groupMember.setMemberNationality(returnedGroupMember.get("memberNationality").toString());
                        groupMember.setMemberLocation(returnedGroupMember.get("memberLocation").toString());
                        groupMember.setMemberPoints(Long.parseLong(String.valueOf(returnedGroupMember.get("memberPoints"))));
                        groupMember.setMemberGroupLocalUniqueId(returnedGroupMember.get("memberGroupId").toString());
//                        groupMember.setGroupName(returnedGroupMember.get("groupName").toString());
                        groupMember.setMemberParseId(returnedGroupMember.getObjectId());

                        groupMemberList.add(groupMember);
                    }
                    onReturnedGroupMemberListener.onResponse(groupMemberList);
                }else {
                    onReturnedGroupMemberListener.onFailure(e.getMessage());
                }
            }
        });
    }

    public void getMemberUserFromParseDb(String groupMemberParseId, final OnReturnedGroupMemberListener onReturnedGroupMemberListener){
        final List<GroupMember> groupMemberList = new ArrayList<>();
        ParseQuery<GroupMember> groupMemberParseQuery = ParseQuery.getQuery("ct2_GroupMembers");
        groupMemberParseQuery.fromLocalDatastore();
        groupMemberParseQuery.whereEqualTo("objectId", groupMemberParseId);
        groupMemberParseQuery.addDescendingOrder("updatedAt");

        groupMemberParseQuery.findInBackground(new FindCallback<GroupMember>() {
            @Override
            public void done(List<GroupMember> parseGroupMemberList, ParseException e) {
                if (e == null){
                    for (GroupMember returnedGroupMember: parseGroupMemberList){
                        GroupMember groupMember = new GroupMember();
//                        groupMember.setLocalUniqueID(returnedGroupMember.get("localUniqueID").toString());
                        groupMember.setMemberUsername(returnedGroupMember.get("memberUsername").toString());
                        groupMember.setMemberPhoneNumber(returnedGroupMember.get("memberPhoneNumber").toString());
                        groupMember.setMemberHousehold(returnedGroupMember.get("memberHousehold").toString());
                        groupMember.setMemberGender(returnedGroupMember.get("memberGender").toString());
                        groupMember.setMemberBusiness(returnedGroupMember.get("memberBusiness").toString());
                        groupMember.setMemberEducationLevel(returnedGroupMember.get("memberEducationLevel").toString());
                        groupMember.setMemberNationality(returnedGroupMember.get("memberNationality").toString());
                        groupMember.setMemberLocation(returnedGroupMember.get("memberLocation").toString());
                        groupMember.setMemberPoints(Long.parseLong(String.valueOf(returnedGroupMember.get("memberPoints"))));
                        groupMember.setMemberGroupLocalUniqueId(returnedGroupMember.get("memberGroupId").toString());
                        groupMember.setMemberParseId(returnedGroupMember.getObjectId());

                        groupMemberList.add(groupMember);
                    }
                    onReturnedGroupMemberListener.onResponse(groupMemberList);
                    Log.d(TAG, "memberUserList: " +groupMemberList.size());
                }else {
                    onReturnedGroupMemberListener.onFailure(e.getMessage());
                }
            }
        });
    }

    public void updateGroupMemberInParseDb(final GroupMember groupMemberToUpdate, final UpdateGroupMemberListener updateGroupMemberListener){
        ParseQuery<GroupMember> groupMemberParseQuery = ParseQuery.getQuery("ct2_GroupMembers");
        groupMemberParseQuery.getInBackground(groupMemberToUpdate.getMemberParseId(), new GetCallback<GroupMember>() {
            @Override
            public void done(GroupMember parseGroupMember, ParseException e) {
                if (e == null) {
                    parseGroupMember.put("memberUsername", groupMemberToUpdate.getMemberUsername());
                    parseGroupMember.put("memberPhoneNumber", groupMemberToUpdate.getMemberPhoneNumber());
                    parseGroupMember.put("memberHousehold", groupMemberToUpdate.getMemberHousehold());
                    parseGroupMember.put("memberGender", groupMemberToUpdate.getMemberHousehold());
                    parseGroupMember.put("memberBusiness", groupMemberToUpdate.getMemberBusiness());
                    parseGroupMember.put("memberEducationLevel", groupMemberToUpdate.getMemberEducationLevel());
                    parseGroupMember.put("memberNationality", groupMemberToUpdate.getMemberNationality());
                    parseGroupMember.put("memberLocation", groupMemberToUpdate.getMemberLocation());
                    parseGroupMember.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                updateGroupMemberListener.onResponse("updated");
                            }else {
                                updateGroupMemberListener.onFailure(e.getMessage());
                            }
                        }
                    });
                }else {
                    Log.d(TAG, "Error Occurred: " + e.getMessage());
                }
            }
        });
    }

    public void deleteGroupMemberFromParseDb(GroupMember groupMemberToDelete){
        ParseQuery<GroupMember> groupMemberParseQuery = ParseQuery.getQuery("ct2_GroupMembers");
        groupMemberParseQuery.getInBackground(groupMemberToDelete.getMemberParseId(), new GetCallback<GroupMember>() {
            @Override
            public void done(GroupMember groupMember, ParseException e) {
                if (e == null){
                    groupMember.deleteInBackground();
                }else {
                    Log.d(TAG, "Error Occured: " + e.getMessage());
                }
            }
        });

    }

    public void deleteAllGroupMembersFromParseDb(String groupId){
        ParseQuery<GroupMember> groupParseQuery = ParseQuery.getQuery("ct2_GroupMembers");
        groupParseQuery.whereEqualTo("memberGroupId", groupId);
        groupParseQuery.findInBackground(new FindCallback<GroupMember>() {
            @Override
            public void done(List<GroupMember> parseGroupMembersToDelete, ParseException e) {
                if (e == null){
                    for (GroupMember groupMemberToDelete: parseGroupMembersToDelete){
                        groupMemberToDelete.unpinInBackground();
                        groupMemberToDelete.deleteEventually();
                    }
                }else {
                    Log.d(TAG, "An Error Occurred: " + e.getMessage());
                }
            }
        });
    }



}
