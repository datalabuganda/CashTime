package com.example.eq62roket.cashtime.Models;

/**
 * Created by eq62roket on 2/28/18.
 */

public class Members {
    String name, phone;
    int id;

    public Members() {
    }

    public Members(String name, String phone, int id) {
        this.name = name;
        this.phone = phone;
        this.id = id;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
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
