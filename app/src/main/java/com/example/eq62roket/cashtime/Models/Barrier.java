package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by etwin on 3/23/18.
 */

@ParseClassName("Barriers")
public class Barrier extends ParseObject{
    private String parseId, goalName, barrierName, barrierText, dateAdded;
    boolean tipGiven;

    public Barrier() {
    }

    public String getParseId() {
        return parseId;
    }

    public void setParseId(String parseId) {
        this.parseId = parseId;
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
