package com.example.eq62roket.cashtime.Helper;

import android.app.Application;

import com.example.eq62roket.cashtime.Models.Barrier;
import com.example.eq62roket.cashtime.Models.Group;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.Models.GroupMember;
import com.example.eq62roket.cashtime.Models.GroupSavings;
import com.example.eq62roket.cashtime.Models.MemberSavings;
import com.example.eq62roket.cashtime.Models.MembersGoals;
import com.example.eq62roket.cashtime.Models.Tip;
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

        ParseObject.registerSubclass(GroupGoals.class);
        ParseObject.registerSubclass(MembersGoals.class);
        ParseObject.registerSubclass(GroupSavings.class);
        ParseObject.registerSubclass(MemberSavings.class);
        ParseObject.registerSubclass(Barrier.class);
        ParseObject.registerSubclass(Tip.class);
        ParseObject.registerSubclass(Group.class);
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(GroupMember.class);
        Parse.initialize(this);
    }
}