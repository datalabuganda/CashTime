package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by etwin on 3/28/18.
 */

@ParseClassName("Groups")
public class Group extends ParseObject{
    private String locationOfGroup, groupLeaderId, groupMemberId;
    private int groupMemberCount;
    private String groupParseId, groupName, groupCentreName, dateCreated;

    public Group() {
    }

    public int getGroupMemberCount() {
        return groupMemberCount;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setGroupMemberCount(int groupMemberCount) {
        this.groupMemberCount += groupMemberCount;
    }

    public String getGroupParseId() {
        return groupParseId;
    }

    public void setGroupParseId(String groupParseId) {
        this.groupParseId = groupParseId;
    }

    public String getGroupCentreName() {
        return groupCentreName;
    }

    public void setGroupCentreName(String groupCentreName) {
        this.groupCentreName = groupCentreName;
    }

    public String getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(String groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getLocationOfGroup() {
        return locationOfGroup;
    }

    public void setLocationOfGroup(String locationOfGroup) {
        this.locationOfGroup = locationOfGroup;
    }

    public String getGroupLeaderId() {
        return groupLeaderId;
    }

    public void setGroupLeaderId(String groupLeaderId) {
        this.groupLeaderId = groupLeaderId;
    }
}
