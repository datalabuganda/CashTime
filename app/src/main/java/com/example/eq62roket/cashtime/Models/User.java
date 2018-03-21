package com.example.eq62roket.cashtime.Models;

/**
 * Created by etwin on 3/21/18.
 */

public class User {
    private String userName, phoneNumber, business, gender, educationLevel, nationality, location;
    private boolean isGroupMember, isLeader;
    private int household;
    private long points;

    public User() {
    }

    public User(
            String userName,
            String phoneNumber,
            String business,
            String gender,
            String educationLevel,
            String nationality,
            String location,
            boolean isGroupMember,
            boolean isLeader,
            int household,
            long points) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.business = business;
        this.gender = gender;
        this.educationLevel = educationLevel;
        this.nationality = nationality;
        this.location = location;
        this.isGroupMember = isGroupMember;
        this.isLeader = isLeader;
        this.household = household;
        this.points = points;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isGroupMember() {
        return isGroupMember;
    }

    public void setGroupMember(boolean groupMember) {
        isGroupMember = groupMember;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public int getHousehold() {
        return household;
    }

    public void setHousehold(int household) {
        this.household = household;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }
}
