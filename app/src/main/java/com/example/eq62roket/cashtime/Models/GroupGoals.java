package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by eq62roket on 2/28/18.
 * modified by etwin 22/3/18
 */
@ParseClassName("ct2_GroupGoals")
public class GroupGoals extends ParseObject {
    private String name, dueDate, amount, completedDate,
            notes, groupGoalStatus, groupLocalUniqueID, groupName, localUniqueID;


    public GroupGoals() {
        super();
    }

    public String getLocalUniqueID() {
        return localUniqueID;
    }

    public void setLocalUniqueID(String localUniqueID) {
        this.localUniqueID = localUniqueID;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public String getGroupLocalUniqueID() {
        return groupLocalUniqueID;
    }

    public void setGroupLocalUniqueID(String groupLocalUniqueID) {
        this.groupLocalUniqueID = groupLocalUniqueID;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
