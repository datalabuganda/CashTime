package com.example.eq62roket.cashtime.Interfaces;

import com.example.eq62roket.cashtime.Models.Group;

import java.util.List;

/**
 * Created by etwin on 3/29/18.
 */

public interface OnReturnedGroupsListener {
    void onResponse(List<Group> groupsList);
    void onFailure(String error);
}
