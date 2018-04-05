package com.example.eq62roket.cashtime.Interfaces;

/**
 * Created by etwin on 4/4/18.
 */

public interface OnReturnedMemberSavingsSumListener {
    void onResponse(int memberGoalTotalSavings);
    void onFailure(String error);
}
