package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by eq62roket on 3/28/18.
 */
@ParseClassName("ct2_MemberIncome")
public class MembersIncome extends ParseObject {
    String localUniqueID, memberLocalUniqueID, memberUserName, source, period, amount, notes, userId;

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

    public String getMemberLocalUniqueID() {
        return memberLocalUniqueID;
    }

    public void setMemberLocalUniqueID(String memberLocalUniqueID) {
        this.memberLocalUniqueID = memberLocalUniqueID;
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

    public MembersIncome() {
        super();
    }




}
