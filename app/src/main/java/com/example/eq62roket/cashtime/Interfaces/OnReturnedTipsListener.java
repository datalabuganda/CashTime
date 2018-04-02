package com.example.eq62roket.cashtime.Interfaces;

import com.example.eq62roket.cashtime.Models.Tip;

import java.util.List;

/**
 * Created by etwin on 3/28/18.
 */

public interface OnReturnedTipsListener {
    void onResponse(List<Tip> tipsList);
    void onFailure(String error);
}
