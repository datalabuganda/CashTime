package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by etwin on 3/11/18.
 */

@ParseClassName("ct2_Tips")
public class Tip extends ParseObject{
    private String goalName;
    private String tipParseId;
    private String introText;
    private String dateAdded;
    private String dateModified, groupGoalParseId, groupParseId;

    public Tip() {
    }

    public String getGroupGoalParseId() {
        return groupGoalParseId;
    }

    public void setGroupGoalParseId(String groupGoalParseId) {
        this.groupGoalParseId = groupGoalParseId;
    }

    public String getGroupParseId() {
        return groupParseId;
    }

    public void setGroupParseId(String groupParseId) {
        this.groupParseId = groupParseId;
    }

    public String getTipParseId() {
        return tipParseId;
    }

    public void setTipParseId(String tipParseId) {
        this.tipParseId = tipParseId;
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
