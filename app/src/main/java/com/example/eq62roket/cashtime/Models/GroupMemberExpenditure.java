package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by eq62roket on 3/28/18.
 */
@ParseClassName("ct2_MemberExpenditure")
public class GroupMemberExpenditure extends ParseObject {
    String localUniqueID, memberParseId, memberUserName, category, date, amount, notes, userId, memberLocalUniqueID;

    public String getMemberLocalUniqueID() {
        return memberLocalUniqueID;
    }

    public void setMemberLocalUniqueID(String memberLocalUniqueID) {
        this.memberLocalUniqueID = memberLocalUniqueID;
    }

    public String getUserId() {
        return userId;
    }

    public String getLocalUniqueID() {
        return localUniqueID;
    }

    public void setLocalUniqueID(String localUniqueID) {
        this.localUniqueID = localUniqueID;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

