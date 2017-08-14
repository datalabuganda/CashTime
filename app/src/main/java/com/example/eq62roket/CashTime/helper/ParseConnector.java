package com.example.eq62roket.CashTime.helper;

import android.content.Context;
import android.util.Log;

import com.example.eq62roket.CashTime.models.Goal;
import com.example.eq62roket.CashTime.models.User;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

/**
 * Created by probuse on 8/14/17.
 */

public class ParseConnector {

    private static final String TAG = "ParseConnector";
    UserCrud userCrud;
    GoalCrud goalCrud;
    Context context;


    public ParseConnector(Context context) {
        this.context = context;
        userCrud = new UserCrud(context);
        goalCrud = new GoalCrud(context);
    }

    public ParseObject addUser(){
        // Get the last inserted user
        final User lastInsertedUser = userCrud.getLastUserInserted();

        // add details of this user to a ParseObject
        final ParseObject user = new ParseObject("Users");
        user.put("HouseHoldComposition", lastInsertedUser.getHousehold());
        user.put("Age", lastInsertedUser.getAge());
        user.put("Sex", lastInsertedUser.getSex());
        user.put("CountryOfBirth", lastInsertedUser.getNationality());
        user.put("Points", lastInsertedUser.getPoints());
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    String userParseId = user.getObjectId();
                    lastInsertedUser.setParseId(userParseId);
                    userCrud.updateUser(lastInsertedUser);
                }
            }
        });
        return user;

    }

    public void addGoal(){
        // get the user back from the database
        final String userParseId = userCrud.getLastUserInserted().getParseId();
        final Goal lastInsertedGoal = goalCrud.getLastInsertedGoal();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");
        query.getInBackground(userParseId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null){
                    // put user id in Users table
                    object.put("UserId", userParseId);

                    // create a Goal table
                    final ParseObject goal = new ParseObject("Goal");
                    goal.put("GoalName", goalCrud.getLastInsertedGoal().getName());
                    goal.put("GoalAmount", goalCrud.getLastInsertedGoal().getAmount());
                    goal.put("GoalStartDate", goalCrud.getLastInsertedGoal().getStartDate());
                    goal.put("GoalEndDate", goalCrud.getLastInsertedGoal().getEndDate());

                    // link the user to this goal table
                    goal.put("parent", object);

                    goal.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                String goalParseId = goal.getObjectId();
                                lastInsertedGoal.setParseId(goalParseId);
                                goalCrud.updateGoal(lastInsertedGoal);

                            }
                        }
                    });

                }
            }
        });

    }

   /* public void addExpenditure(){
        final String goalParseId = goalCrud.getLastInsertedGoal().getParseId();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Goal");
        query.getInBackground(goalParseId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null){
                    object.put("GoalId", goalParseId);
                }
            }
        });
    }*/
}
