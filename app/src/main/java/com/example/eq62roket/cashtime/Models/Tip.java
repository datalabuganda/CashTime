package com.example.eq62roket.cashtime.Models;

/**
 * Created by etwin on 3/11/18.
 */

public class Tip {
    private String goalName;
    private String introText;
    private String dateAdded;
    private String dateModified;

    public Tip(String goalName, String introText, String dateAdded) {
        this.goalName = goalName;
        this.introText = introText;
        this.dateAdded = dateAdded;
    }

    public Tip() {
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
