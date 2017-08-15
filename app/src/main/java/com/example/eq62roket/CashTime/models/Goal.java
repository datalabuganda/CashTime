package com.example.eq62roket.CashTime.models;

/**
 * Created by eq62roket on 8/5/17.
 */

public class Goal {
    private long id;
    private String parseId;
    private String name;
    private int amount, syncStatus;
    private String startDate;
    private String endDate;
    private User user;

    public Goal() {
    }

    public Goal(long id, String name, int amount, String startDate, String endDate, User user, String parseId) {
        this.id = id;
        this.name = name;
        this.parseId = parseId;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParseId() {
        return parseId;
    }

    public void setParseId(String parseId) {
        this.parseId = parseId;
    }

    public int getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
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
        this.amount = amount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
