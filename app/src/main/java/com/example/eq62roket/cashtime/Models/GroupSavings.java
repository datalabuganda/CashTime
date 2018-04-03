package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by probuse on 3/3/18.
 */
@ParseClassName("ct2_GroupSavings")
public class GroupSavings extends ParseObject {
    private String parseId, goalName, period, incomeSource, notes, dateAdded;
    private String amount, groupParseId, userId, groupGoalParseId;

    public GroupSavings() {
    }

    public String getGroupParseId() {
        return groupParseId;
    }

    public void setGroupParseId(String groupParseId) {
        this.groupParseId = groupParseId;
    }

    public String getUserId() {
        return userId;
    }

    public String getGroupGoalParseId() {
        return groupGoalParseId;
    }

    public void setGroupGoalParseId(String groupGoalParseId) {
        this.groupGoalParseId = groupGoalParseId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getParseId() {
        return parseId;
    }

    public void setParseId(String parseId) {
        this.parseId = parseId;
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
