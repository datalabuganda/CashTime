package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by eq62roket on 3/2/18.
 */
@ParseClassName("ct2_MemberGoals")
public class MembersGoals extends ParseObject {
    String localUniqueID, memberName,memberLocalUniqueID, memberGoalName, completeDate,
            memberGoalAmount, memberGoalDueDate, memberGoalStatus, memberGoalNotes;

    public MembersGoals() {
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public String getMemberLocalUniqueID() {
        return memberLocalUniqueID;
    }

    public void setMemberLocalUniqueID(String memberLocalUniqueID) {
        this.memberLocalUniqueID = memberLocalUniqueID;
    }

    public String getLocalUniqueID() {
        return localUniqueID;
    }

    public void setLocalUniqueID(String localUniqueID) {
        this.localUniqueID = localUniqueID;
    }

    public String getMemberGoalNotes() {
        return memberGoalNotes;
    }

    public void setMemberGoalNotes(String memberGoalNotes) {
        this.memberGoalNotes = memberGoalNotes;
    }

    public String getMemberGoalStatus() {
        return memberGoalStatus;
    }

    public void setMemberGoalStatus(String memberGoalStatus) {
        this.memberGoalStatus = memberGoalStatus;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberGoalName() {
        return memberGoalName;
    }

    public void setMemberGoalName(String memberGoalName) {
        this.memberGoalName = memberGoalName;
    }

    public String getMemberGoalAmount() {
        return memberGoalAmount;
    }

    public void setMemberGoalAmount(String memberGoalAmount) {
        this.memberGoalAmount = memberGoalAmount;
    }

    public String getMemberGoalDueDate() {
        return memberGoalDueDate;
    }

    public void setMemberGoalDueDate(String memberGoalDueDate) {
        this.memberGoalDueDate = memberGoalDueDate;
    }
}
