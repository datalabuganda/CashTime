package com.example.eq62roket.cashtime.Interfaces;

import com.example.eq62roket.cashtime.Models.MembersGoals;

import java.util.List;

/**
 * Created by etwin on 3/27/18.
 */

public interface OnReturnedMemberGoalListener {
    void onResponse(List<MembersGoals> membersGoalsList);
    void onFailure(String error);
}
