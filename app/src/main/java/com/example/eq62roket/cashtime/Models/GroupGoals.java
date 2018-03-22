package com.example.eq62roket.cashtime.Models;

/**
 * Created by eq62roket on 2/28/18.
 * modified by etwin 22/3/18
 */

public class GroupGoals {
    String name, dueDate, amount, notes, groupGoalStatus;

    public GroupGoals() {
    }

    public GroupGoals(
            String name,
            String startDate,
            String amount,
            String notes,
            String groupGoalStatus) {
        this.name = name;
        this.dueDate = startDate;
        this.amount = amount;
        this.notes = notes;
        this.groupGoalStatus = groupGoalStatus;
    }

    public String getGroupGoalStatus() {
        return groupGoalStatus;
    }

    public void setGroupGoalStatus(String groupGoalStatus) {
        this.groupGoalStatus = groupGoalStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
