package com.example.eq62roket.cashtime.Models;

/**
 * Created by probuse on 3/3/18.
 */

public class GroupSavings {
    private String goalName, period, incomeSource, notes;
    private String amount;

    public GroupSavings(String goalName, String period, String incomeSource, String notes, String amount) {
        this.goalName = goalName;
        this.period = period;
        this.incomeSource = incomeSource;
        this.notes = notes;
        this.amount = amount;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount += amount;
    }
}
