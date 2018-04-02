package com.example.eq62roket.cashtime.Helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by etwin on 3/28/18.
 */

public class CashTimeSharedPreferences {

    private Context mContext;
    private SharedPreferences mSharedPreferences;

    public CashTimeSharedPreferences(Context context){
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences("cashtimeSharedPreference", Context.MODE_PRIVATE);
    }

    public void writeToSharedPreference(String key, String value){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String readValueFromSharedPreferences(String key){
        String value = mSharedPreferences.getString(key, "Key Not Found");
        return  value;
    }

    public void removeValueFromSharedPreferences(String key){
        mSharedPreferences.edit().remove(key).apply();
    }
}
