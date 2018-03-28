package com.example.eq62roket.cashtime.Models;

/**
 * Created by etwin on 3/21/18.
 */

public class User{
    private String userName, phoneNumber, business, gender, educationLevel, nationality, location;
    private String household;
    private Boolean isGroupMember, isLeader;
    private long points;
    private String password;

    public User() {
    }


    public Boolean getGroupMember() {
        return isGroupMember;
    }

    public void setGroupMember(Boolean groupMember) {
        isGroupMember = groupMember;
    }

    public Boolean getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(Boolean isLeader) {
        this.isLeader = isLeader;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getHousehold() {
        return household;
    }

    public void setHousehold(String household) {
        this.household = household;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points += points;
    }
}
