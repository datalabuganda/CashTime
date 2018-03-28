package com.example.eq62roket.cashtime.Interfaces;

import com.example.eq62roket.cashtime.Models.Barrier;

import java.util.List;

/**
 * Created by etwin on 3/28/18.
 */

public interface OnReturnedGroupBarrierListener {
    void onResponse(List<Barrier> barrierList);
    void onFailure(String error);
}
