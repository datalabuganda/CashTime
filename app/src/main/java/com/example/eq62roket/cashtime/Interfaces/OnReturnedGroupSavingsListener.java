package com.example.eq62roket.cashtime.Interfaces;

import com.example.eq62roket.cashtime.Models.GroupSavings;

import java.util.List;

/**
 * Created by etwin on 3/28/18.
 */

public interface OnReturnedGroupSavingsListener {
    void onResponse(List<GroupSavings> groupSavingsList);
    void onFailure(String error);
}
