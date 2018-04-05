package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by eq62roket on 3/28/18.
 */
@ParseClassName("ct2_MemberIncome")
public class MembersIncome extends ParseObject {
    String parseId, memberParseId, memberUserName, source, dueDate, amount, notes, userId;

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

    public void setSource(String source) {
        this.source = source;
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

    public MembersIncome() {
        super();
    }




}
