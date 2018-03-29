package com.example.eq62roket.cashtime.Interfaces;

import com.example.eq62roket.cashtime.Models.User;

import java.util.List;

/**
 * Created by etwin on 3/29/18.
 */

public interface OnReturnedGroupMemberListener {
    void onResponse(List<User> userList);
    void onFailure(String error);
}
