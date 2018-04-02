package com.example.eq62roket.cashtime.Helper;

import android.app.Application;

import com.example.eq62roket.cashtime.Models.ExpenditureCategories;
import com.example.eq62roket.cashtime.Models.GroupExpenditure;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.Models.GroupIncome;
import com.example.eq62roket.cashtime.Models.GroupMemberExpenditure;
import com.example.eq62roket.cashtime.Models.IncomeSources;
import com.example.eq62roket.cashtime.Models.MembersIncome;
import com.example.eq62roket.cashtime.Models.User;
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

        ParseObject.registerSubclass(ExpenditureCategories.class);
        ParseObject.registerSubclass(IncomeSources.class);
        ParseObject.registerSubclass(GroupGoals.class);
        ParseObject.registerSubclass(MembersIncome.class);
        ParseObject.registerSubclass(GroupIncome.class);
        ParseObject.registerSubclass(GroupExpenditure.class);
        ParseObject.registerSubclass(GroupMemberExpenditure.class);
        ParseObject.registerSubclass(User.class);
        Parse.initialize(this);
    }
}
