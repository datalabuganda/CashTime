package com.example.eq62roket.cashtime.Helper;

import android.content.Context;
import android.util.Log;

import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupMemberListener;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupsListener;
import com.example.eq62roket.cashtime.Models.Group;
import com.example.eq62roket.cashtime.Models.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etwin on 3/28/18.
 */

public class ParseGroupHelper {

    private static final String TAG = "ParseGroupHelper";
    private Context mContext;

    public ParseGroupHelper(Context context){
        mContext = context;
    }

    public void saveNewGroupToParseDb(Group groupToSave) {
        Group newGroup = new Group();
        newGroup.put("groupName", groupToSave.getGroupName());
        newGroup.put("groupLocation", groupToSave.getLocationOfGroup());
        newGroup.put("groupCentreName", groupToSave.getGroupCentreName());
        newGroup.put("groupCreatorId", groupToSave.getGroupCreatorId());
        newGroup.put("groupLeaderId", groupToSave.getGroupLeaderId());
        newGroup.put("groupMemberCount", groupToSave.getGroupMemberCount());
        newGroup.put("groupDateCreated", groupToSave.getDateCreated());

        newGroup.saveInBackground();
    }

    public void getAllGroupsFromParseDb(final OnReturnedGroupsListener onReturnedGroupsListener){
        final List<Group> groupList = new ArrayList<>();
        ParseQuery<Group> groupParseQuery = ParseQuery.getQuery("Groups");
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
                        group.setGroupParseId(returnedGroup.getObjectId());

                        groupList.add(group);
                    }
                    onReturnedGroupsListener.onResponse(groupList);
                }else {
                    onReturnedGroupsListener.onFailure(e.getMessage());
                }
            }
        });
    }

    public void getGroupMembersFromParseDb(String groupId, final OnReturnedGroupMemberListener onReturnedGroupMemberListener){
        final List<User> userList = new ArrayList<>();
        ParseQuery<ParseUser> userParseQuery = ParseUser.getQuery();
        userParseQuery.whereEqualTo("groupId", groupId);
        userParseQuery.addDescendingOrder("updatedAt");

        userParseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> parseUserList, ParseException e) {
                if (e == null){
                    for (ParseUser returnedUser: parseUserList){
                        User user = new User();
                        user.setUserName(returnedUser.get("username").toString());
                        user.setPhoneNumber(returnedUser.get("userPhone").toString());
                        user.setHousehold(returnedUser.get("userHousehold").toString());
                        user.setGender(returnedUser.get("userGender").toString());
                        user.setBusiness(returnedUser.get("userBusiness").toString());
                        user.setEducationLevel(returnedUser.get("userEducationLevel").toString());
                        user.setNationality(returnedUser.get("userNationality").toString());
                        user.setLocation(returnedUser.get("userLocation").toString());
                        user.setIsLeader((Boolean) returnedUser.get("isLeader"));
                        user.setGroupMember((Boolean) returnedUser.get("isGroupMember"));
                        user.setPoints(Long.parseLong(String.valueOf(returnedUser.get("userPoints"))));

                        userList.add(user);
                    }
                    onReturnedGroupMemberListener.onResponse(userList);
                }else {
                    onReturnedGroupMemberListener.onFailure(e.getMessage());
                }
            }
        });
    }


}
