package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by etwin on 3/23/18.
 */

@ParseClassName("ct2_Barriers")
public class Barrier extends ParseObject{
    private String localUniqueID, goalName, barrierName,
            barrierText, dateAdded, groupLocalUniqueID, groupGoalLocalUniqueID;
    boolean tipGiven;

    public Barrier() {
    }

    public String getGroupLocalUniqueID() {
        return groupLocalUniqueID;
    }

    public void setGroupLocalUniqueID(String groupLocalUniqueID) {
        this.groupLocalUniqueID = groupLocalUniqueID;
    }

    public String getGroupGoalLocalUniqueID() {
        return groupGoalLocalUniqueID;
    }

    public void setGroupGoalLocalUniqueID(String groupGoalLocalUniqueID) {
        this.groupGoalLocalUniqueID = groupGoalLocalUniqueID;
    }

    public String getLocalUniqueID() {
        return localUniqueID;
    }

    public void setLocalUniqueID(String localUniqueID) {
        this.localUniqueID = localUniqueID;
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
