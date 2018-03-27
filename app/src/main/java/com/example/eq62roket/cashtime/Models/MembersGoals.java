package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by eq62roket on 3/2/18.
 */
@ParseClassName("MemberGoals")
public class MembersGoals extends ParseObject {
    String parseId, memberName, memberGoalName, memberGoalAmount, memberGoalDueDate, memberGoalStatus, memberGoalNotes;

    public MembersGoals() {
    }

    public String getParseId() {
        return parseId;
    }

    public void setParseId(String parseId) {
        this.parseId = parseId;
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
