package com.example.eq62roket.cashtime.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by etwin on 3/31/18.
 */

@ParseClassName("ct2_GroupMembers")
public class GroupMember extends ParseObject {
    private String memberUsername, memberPhoneNumber, memberHousehold,
            memberBusiness, memberGender, memberEducationLevel, groupName,
            memberNationality, memberLocation, memberGroupLocalUniqueId,
            localUniqueID;
    private long memberPoints;

    public GroupMember() {
    }

    public String getMemberGroupLocalUniqueId() {
        return memberGroupLocalUniqueId;
    }

    public void setMemberGroupLocalUniqueId(String memberGroupLocalUniqueId) {
        this.memberGroupLocalUniqueId = memberGroupLocalUniqueId;
    }

    public String getLocalUniqueID() {
        return localUniqueID;
    }

    public void setLocalUniqueID(String localUniqueID) {
        this.localUniqueID = localUniqueID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMemberUsername() {
        return memberUsername;
    }

    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
    }

    public String getMemberPhoneNumber() {
        return memberPhoneNumber;
    }

    public void setMemberPhoneNumber(String memberPhoneNumber) {
        this.memberPhoneNumber = memberPhoneNumber;
    }

    public String getMemberHousehold() {
        return memberHousehold;
    }

    public void setMemberHousehold(String memberHousehold) {
        this.memberHousehold = memberHousehold;
    }

    public String getMemberBusiness() {
        return memberBusiness;
    }

    public void setMemberBusiness(String memberBusiness) {
        this.memberBusiness = memberBusiness;
    }

    public String getMemberGender() {
        return memberGender;
    }

    public void setMemberGender(String memberGender) {
        this.memberGender = memberGender;
    }

    public String getMemberEducationLevel() {
        return memberEducationLevel;
    }

    public void setMemberEducationLevel(String memberEducationLevel) {
        this.memberEducationLevel = memberEducationLevel;
    }

    public String getMemberNationality() {
        return memberNationality;
    }

    public void setMemberNationality(String memberNationality) {
        this.memberNationality = memberNationality;
    }

    public String getMemberLocation() {
        return memberLocation;
    }

    public void setMemberLocation(String memberLocation) {
        this.memberLocation = memberLocation;
    }

    public long getMemberPoints() {
        return memberPoints;
    }

    public void setMemberPoints(long memberPoints) {
        this.memberPoints = memberPoints;
    }
}
