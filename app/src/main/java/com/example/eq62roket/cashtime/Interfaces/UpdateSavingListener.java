package com.example.eq62roket.cashtime.Interfaces;

/**
 * Created by etwin on 4/7/18.
 */

public interface UpdateSavingListener {
    void onResponse(String updateMessage);
    void onFailure(String error);
}
