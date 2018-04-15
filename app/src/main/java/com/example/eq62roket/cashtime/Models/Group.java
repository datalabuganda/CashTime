package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by etwin on 3/28/18.
 */

@ParseClassName("ct2_Groups")
public class Group extends ParseObject{
    private String locationOfGroup, groupLeaderId, groupCreatorId, localUniqueID;
    private int groupMemberCount;
    private String groupName, groupCentreName, dateCreated;

    public Group() {
    }

    public String getLocalUniqueID() {
        return localUniqueID;
    }

    public void setLocalUniqueID(String localUniqueID) {
        this.localUniqueID = localUniqueID;
    }

    public String getGroupCreatorId() {
        return groupCreatorId;
    }

    public void setGroupCreatorId(String groupCreatorId) {
        this.groupCreatorId = groupCreatorId;
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

    public String getGroupCentreName() {
        return groupCentreName;
    }

    public void setGroupCentreName(String groupCentreName) {
        this.groupCentreName = groupCentreName;
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
