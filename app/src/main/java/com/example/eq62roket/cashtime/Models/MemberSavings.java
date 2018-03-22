package com.example.eq62roket.cashtime.Models;

/**
 * Created by probuse on 3/3/18.
 * modiified by etwin
 */

public class MemberSavings {
    private String memberName, goalName, period, incomeSource, dateAdded;
    private long savingAmount;

    public MemberSavings() {
    }

    public MemberSavings(
            String memberName,
            String goalName,
            String period,
            String incomeSource,
            String dateAdded,
            long savingAmount) {

        this.memberName = memberName;
        this.goalName = goalName;
        this.period = period;
        this.incomeSource = incomeSource;
        this.dateAdded = dateAdded;
        this.savingAmount = savingAmount;
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
