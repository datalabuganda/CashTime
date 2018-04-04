package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by eq62roket on 2/28/18.
 */

@ParseClassName("GroupMembers")
public class Members extends ParseObject{
    String parseId, name, phone, houseHoldComposition, business, gender, educationLevel, nationality, location;

    public Members() {
        super();
    }

//    public Members(String name, String phone, int id) {
//        this.name = name;
//        this.phone = phone;
//        this.id = id;
//    }


    public String getParseId() {
        return parseId;
    }

    public void setParseId(String parseId) {
        this.parseId = parseId;
    }

    public String getHouseHoldComposition() {
        return houseHoldComposition;
    }

    public void setHouseHoldComposition(String houseHoldComposition) {
        this.houseHoldComposition = houseHoldComposition;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
