package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by probuse on 3/3/18.
 */
@ParseClassName("ct2_GroupSavings")
public class GroupSavings extends ParseObject {
    private String localUniqueID, goalName, period, incomeSource, notes, dateAdded;
    private String amount, groupLocalUniqueID, userId, groupGoalLocalUniqueID;

    public GroupSavings() {
    }

    public String getLocalUniqueID() {
        return localUniqueID;
    }

    public void setLocalUniqueID(String localUniqueID) {
        this.localUniqueID = localUniqueID;
    }

    public String getGroupLocalUniqueID() {
        return groupLocalUniqueID;
    }

    public void setGroupLocalUniqueID(String groupLocalUniqueID) {
        this.groupLocalUniqueID = groupLocalUniqueID;
    }

    public String getUserId() {
        return userId;
    }

    public String getGroupGoalLocalUniqueID() {
        return groupGoalLocalUniqueID;
    }

    public void setGroupGoalLocalUniqueID(String groupGoalLocalUniqueID) {
        this.groupGoalLocalUniqueID = groupGoalLocalUniqueID;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getIncomeSource() {
        return incomeSource;
    }

    public void setIncomeSource(String incomeSource) {
        this.incomeSource = incomeSource;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
