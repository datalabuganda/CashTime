package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by eq62roket on 3/28/18.
 */
@ParseClassName("GroupIncome")
public class GroupIncome extends ParseObject {
    String parseId, source, period, amount, notes, userId, groupName;
    String groupParseId, groupIncomeParseId;

    public String getGroupParseId() {
        return groupParseId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupParseId(String groupParseId) {
        this.groupParseId = groupParseId;
    }

    public String getGroupIncomeParseId() {
        return groupIncomeParseId;
    }

    public void setGroupIncomeParseId(String groupIncomeParseId) {
        this.groupIncomeParseId = groupIncomeParseId;
    }

    public String getUserId() {
        return userId;
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

    public GroupIncome() {
        super();
    }
}
