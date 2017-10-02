package com.example.eq62roket.CashTime.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by eq62roket on 10/1/17.
 */

public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"ALARM....", Toast.LENGTH_LONG).show();
    }
}
