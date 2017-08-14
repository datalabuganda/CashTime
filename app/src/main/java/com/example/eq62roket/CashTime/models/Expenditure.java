package com.example.eq62roket.CashTime.models;

/**
 * Created by eq62roket on 8/5/17.
 */

public class Expenditure {
    private long id;
    private long goal_id;
    private String expenditureDate;
    private int education;
    private int transport;
    private int health;
    private int others;
    private int savings;
    private int homeneeds;

    public Expenditure() {
    }

    public Expenditure(long id, long goal_id, String expenditureDate, int education, int transport, int health, int others, int savings, int homeneeds) {
        this.id = id;
        this.goal_id = goal_id;
        this.expenditureDate = expenditureDate;
        this.education = education;
        this.transport = transport;
        this.health = health;
        this.others = others;
        this.savings = savings;
        this.homeneeds = homeneeds;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGoal_id() {
        return goal_id;
    }

    public void setGoal_id(long goal_id) {
        this.goal_id = goal_id;
    }

    public String getExpenditureDate() {
        return expenditureDate;
    }

    public void setExpenditureDate(String expenditureDate) {
        this.expenditureDate = expenditureDate;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public int getTransport() {
        return transport;
    }

    public void setTransport(int transport) {
        this.transport = transport;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getOthers() {
        return others;
    }

    public void setOthers(int others) {
        this.others = others;
    }

    public int getSavings() {
        return savings;
    }

    public void setSavings(int savings) {
        this.savings = savings;
    }

    public int getHomeneeds() {
        return homeneeds;
    }

    public void setHomeneeds(int homeneeds) {
        this.homeneeds = homeneeds;
    }
}
