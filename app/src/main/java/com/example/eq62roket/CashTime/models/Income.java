package com.example.eq62roket.CashTime.models;

/**
 * Created by eq62roket on 8/5/17.
 */

public class Income {
    private long id;
    private int amount;
    private String createdDate;
    private String endDate;
    private int salary;
    private int loan;
    private int investment;
    private int other;

    public Income(String createdDate, int salary, int loan, int investment, int other) {
        this.createdDate = createdDate;
        this.salary = salary;
        this.loan = loan;
        this.investment = investment;
        this.other = other;
    }

    public Income(long id, int amount, String createdDate, String endDate, int salary, int loan, int investment, int other) {
        this.id = id;
        this.amount = amount;
        this.createdDate = createdDate;
        this.endDate = endDate;
        this.salary = salary;
        this.loan = loan;
        this.investment = investment;
        this.other = other;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getLoan() {
        return loan;
    }

    public void setLoan(int loan) {
        this.loan = loan;
    }

    public int getInvestment() {
        return investment;
    }

    public void setInvestment(int investment) {
        this.investment = investment;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }
}
