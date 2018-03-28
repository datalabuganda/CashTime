package com.example.eq62roket.cashtime.Models;

/**
 * Created by probuse on 3/3/18.
 * modiified by etwin
 */
public class MemberSavings {
    private String parseId, memberName, goalName, period, incomeSource, dateAdded, savingNote;
    private long savingAmount;

    public MemberSavings() {
    }

    public String getParseId() {
        return parseId;
    }

    public void setParseId(String parseId) {
        this.parseId = parseId;
    }

    public String getSavingNote() {
        return savingNote;
    }

    public void setSavingNote(String savingNote) {
        this.savingNote = savingNote;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
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

    public long getSavingAmount() {
        return savingAmount;
    }

    public void setSavingAmount(long savingAmount) {
        this.savingAmount = savingAmount;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setmemberName(String memberName) {
        this.memberName = memberName;
    }

    public long getsavingAmount() {
        return savingAmount;
    }

    public void setsavingAmount(long savingAmount) {
        this.savingAmount = savingAmount;
    }
}
