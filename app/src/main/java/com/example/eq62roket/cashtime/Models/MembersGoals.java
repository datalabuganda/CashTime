package com.example.eq62roket.cashtime.Models;

/**
 * Created by eq62roket on 3/2/18.
 */

public class MembersGoals {
    String memberName, memberGoalName, memberGoalAmount, memberGoalDueDate;

    public MembersGoals() {
    }

    public MembersGoals(String memberName, String memberGoalName, String memberGoalAmount, String memberGoalDueDate) {
        this.memberName = memberName;
        this.memberGoalName = memberGoalName;
        this.memberGoalAmount = memberGoalAmount;
        this.memberGoalDueDate = memberGoalDueDate;
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
