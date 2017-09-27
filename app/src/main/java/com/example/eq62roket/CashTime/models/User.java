package com.example.eq62roket.CashTime.models;

/**
 * Created by eq62roket on 8/5/17.
 */

public class User {
    long id, points;
    int household, age, syncStatus;
    String sex, educationlevel, nationality, phonenumber, parseId;

    public User() {
    }

    public User(long points) {
        this.points = points;
    }

    public User(long points, int household, int age, String sex, String educationlevel, String nationality) {
        this.points = points;
        this.household = household;
        this.age = age;
        this.sex = sex;
        this.educationlevel = educationlevel;
        this.nationality = nationality;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
    }

    public void setParseId(String parseId) {
        this.parseId = parseId;
    }

    public String getParseId() {
        return parseId;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points += points;
    }

    public void resetPoints(long points){ this.points = points; }

    public int getHousehold() {
        return household;
    }

    public void setHousehold(int household) {
        this.household = household;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEducationlevel() {
        return educationlevel;
    }

    public void setEducationlevel(String educationlevel) {
        this.educationlevel = educationlevel;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
