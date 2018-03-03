package com.example.eq62roket.cashtime.Models;

/**
 * Created by probuse on 3/3/18.
 */

public class MemberSavings {
    private String name;
    private int amount;

    public MemberSavings() {
    }

    public MemberSavings(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount += amount;
    }
}
