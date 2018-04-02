package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by etwin on 3/21/18.
 */

@ParseClassName("GroupMember")
public class User extends ParseObject {
    private String parseId, groupLeaderId, userName, phoneNumber, business, gender, educationLevel, nationality, location,
            household;
    private boolean isGroupMember, isLeader;
    private long points;

    public User() {
        super();
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
            String household,
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

    public String getGroupLeaderId() {
        return groupLeaderId;
    }

    public void setGroupLeaderId(String groupLeaderId) {
        this.groupLeaderId = groupLeaderId;
    }

    public String getParseId() {
        return parseId;
    }

    public void setParseId(String parseId) {
        this.parseId = parseId;
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

    public boolean isGroupMember(String isGroupMember, int i) {
        return this.isGroupMember;
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
        this.points = points;
    }
}
