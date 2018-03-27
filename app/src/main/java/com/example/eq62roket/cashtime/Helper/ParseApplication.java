package com.example.eq62roket.cashtime.Helper;

import android.app.Application;

import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by etwin on 3/26/18.
 */

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);

        ParseObject.registerSubclass(GroupGoals.class);
        Parse.initialize(this);
    }
}
