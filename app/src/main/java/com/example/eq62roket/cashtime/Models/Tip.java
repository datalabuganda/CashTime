package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by etwin on 3/11/18.
 */

@ParseClassName("ct2_Tips")
public class Tip extends ParseObject{
    private String goalName;
    private String localUniqueID;
    private String introText;
    private String dateAdded;
    private String dateModified, groupGoalLocalUniqueID, groupLocalUniqueID;

    public Tip() {
    }

    public String getGroupGoalLocalUniqueID() {
        return groupGoalLocalUniqueID;
    }

    public void setGroupGoalLocalUniqueID(String groupGoalLocalUniqueID) {
        this.groupGoalLocalUniqueID = groupGoalLocalUniqueID;
    }

    public String getGroupLocalUniqueID() {
        return groupLocalUniqueID;
    }

    public void setGroupLocalUniqueID(String groupLocalUniqueID) {
        this.groupLocalUniqueID = groupLocalUniqueID;
    }

    public String getLocalUniqueID() {
        return localUniqueID;
    }

    public void setLocalUniqueID(String localUniqueID) {
        this.localUniqueID = localUniqueID;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getIntroText() {
        return introText;
    }

    public void setIntroText(String introText) {
        this.introText = introText;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
