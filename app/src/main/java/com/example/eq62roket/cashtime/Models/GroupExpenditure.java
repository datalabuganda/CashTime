package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by eq62roket on 3/28/18.
 */
@ParseClassName("GroupExpenditure")
public class GroupExpenditure extends ParseObject {
    String category, categoryId, dueDate, amount, notes, groupName;
    String groupParseId, groupExpenditureParseId, parseId, userId;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupParseId() {
        return groupParseId;
    }

    public void setGroupParseId(String groupParseId) {
        this.groupParseId = groupParseId;
    }

    public String getGroupExpenditureParseId() {
        return groupExpenditureParseId;
    }

    public void setGroupExpenditureParseId(String groupExpenditureParseId) {
        this.groupExpenditureParseId = groupExpenditureParseId;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public GroupExpenditure() {
    }


}
