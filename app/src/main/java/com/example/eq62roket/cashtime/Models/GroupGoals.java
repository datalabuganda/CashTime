package com.example.eq62roket.cashtime.Models;

/**
 * Created by eq62roket on 2/28/18.
 */

public class GroupGoals {
    String name, date, amount;

    public GroupGoals() {
    }

    public GroupGoals(String name, String date, String amount) {
        this.name = name;
        this.date = date;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
