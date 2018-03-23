package com.example.eq62roket.cashtime.Models;

/**
 * Created by etwin on 3/23/18.
 */

public class Barrier {
    private String goalName, barrierName, barrierText, dateAdded;
    boolean tipGiven;

    public Barrier() {
    }

    public Barrier(String barrierName, String barrierText, String dateAdded) {
        this.barrierName = barrierName;
        this.barrierText = barrierText;
        this.dateAdded = dateAdded;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getBarrierName() {
        return barrierName;
    }

    public void setBarrierName(String barrierName) {
        this.barrierName = barrierName;
    }

    public String getBarrierText() {
        return barrierText;
    }

    public void setBarrierText(String barrierText) {
        this.barrierText = barrierText;
    }

    public boolean isTipGiven() {
        return tipGiven;
    }

    public void setTipGiven(boolean tipGiven) {
        this.tipGiven = tipGiven;
    }
}
