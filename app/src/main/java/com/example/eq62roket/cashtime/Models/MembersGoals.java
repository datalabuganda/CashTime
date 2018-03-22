package com.example.eq62roket.cashtime.Models;

/**
 * Created by eq62roket on 3/2/18.
 */

public class MembersGoals {
    String memberName, memberGoalName, memberGoalAmount, memberGoalDueDate, memberGoalStatus, memberGoalNotes;

    public MembersGoals() {
    }

    public MembersGoals(
            String memberName,
            String memberGoalName,
            String memberGoalAmount,
            String memberGoalDueDate,
            String memberGoalStatus,
            String memberGoalNotes) {
        this.memberName = memberName;
        this.memberGoalName = memberGoalName;
        this.memberGoalAmount = memberGoalAmount;
        this.memberGoalDueDate = memberGoalDueDate;
        this.memberGoalStatus = memberGoalStatus;
        this.memberGoalNotes = memberGoalNotes;
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
