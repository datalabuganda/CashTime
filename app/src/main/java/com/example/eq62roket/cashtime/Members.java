package com.example.eq62roket.cashtime;

/**
 * Created by eq62roket on 2/28/18.
 */

public class Members {
    String name, phone;

    public Members() {
    }

    public Members(String name, String phone) {
        this.name = name;
        this.phone = phone;
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
