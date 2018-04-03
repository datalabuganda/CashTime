package com.example.eq62roket.cashtime.Interfaces;

/**
 * Created by etwin on 4/3/18.
 */

public interface OnReturnedGroupSavingsSumListener {
    void onResponse(int groupGoalTotalSavings);
    void onFailure(String error);
}
