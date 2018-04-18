package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by eq62roket on 3/28/18.
 */
@ParseClassName("ct2_GroupIncome")
public class GroupIncome extends ParseObject {
    String localUniqueID, source, period, amount, notes, userId, groupName;
    String groupLocalUniqueID;

    public GroupIncome() {
        super();
    }

    public String getGroupLocalUniqueID() {
        return groupLocalUniqueID;
    }

    public void setGroupLocalUniqueID(String groupLocalUniqueID) {
        this.groupLocalUniqueID = groupLocalUniqueID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocalUniqueID() {
        return localUniqueID;
    }

    public void setLocalUniqueID(String localUniqueID) {
        this.localUniqueID = localUniqueID;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
