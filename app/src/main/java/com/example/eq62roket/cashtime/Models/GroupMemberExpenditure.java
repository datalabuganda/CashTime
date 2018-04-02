package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by eq62roket on 3/28/18.
 */
@ParseClassName("GroupMembersExpenditure")
public class GroupMemberExpenditure extends ParseObject {
    String parseId, memberParseId, memberUserName, category, dueDate, amount, notes;

    public String getParseId() {
        return parseId;
    }

    public String getMemberParseId() {
        return memberParseId;
    }

    public void setMemberParseId(String memberParseId) {
        this.memberParseId = memberParseId;
    }

    public String getMemberUserName() {
        return memberUserName;
    }

    public void setMemberUserName(String memberUserName) {
        this.memberUserName = memberUserName;
    }

    public void setParseId(String parseId) {
        this.parseId = parseId;
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

    public GroupMemberExpenditure() {
        super();
    }
}

