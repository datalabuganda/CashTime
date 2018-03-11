package com.example.eq62roket.cashtime.Models;

/**
 * Created by etwin on 3/11/18.
 */

public class Tip {
    private String goalName;
    private String introText;

    public Tip(String goalName, String introText) {
        this.goalName = goalName;
        this.introText = introText;
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
}
