package com.example.eq62roket.cashtime.Interfaces;

import com.example.eq62roket.cashtime.Models.MemberSavings;

import java.util.List;

/**
 * Created by etwin on 3/28/18.
 */

public interface OnReturnedMemberSavingsListener {
    void onResponse(List<MemberSavings> memberSavingsList);
    void onFailure(String error);
}
