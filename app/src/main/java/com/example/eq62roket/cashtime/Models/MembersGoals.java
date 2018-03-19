package com.example.eq62roket.cashtime.Models;

/**
 * Created by eq62roket on 3/2/18.
 */

public class MembersGoals {
    String name, goal, amount, date;

    public MembersGoals() {
    }

    public MembersGoals(String name, String goal, String amount, String date) {
        this.name = name;
        this.goal = goal;
        this.amount = amount;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
