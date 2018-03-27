package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by probuse on 3/3/18.
 */
@ParseClassName("GroupSavings")
public class GroupSavings extends ParseObject {
    private String goalName, period, incomeSource, notes, dateAdded;
    private String amount;

    public GroupSavings() {
    }

//    public GroupSavings(String goalName, String period, String incomeSource, String notes, String dateAdded, String amount) {
//        this.goalName = goalName;
//        this.period = period;
//        this.incomeSource = incomeSource;
//        this.notes = notes;
//        this.dateAdded = dateAdded;
//        this.amount = amount;
//    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
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

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
