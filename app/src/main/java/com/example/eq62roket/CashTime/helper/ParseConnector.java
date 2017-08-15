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

    Context context;

    UserCrud userCrud;
    GoalCrud goalCrud;

    SQLiteHelper sqLiteHelper;
    IncomeSQLiteHelper incomeSQLiteHelper;

    Goal lastInsertedGoal;
    User lastInsertedUser;


    public ParseConnector(Context context) {
        this.context = context;
        userCrud = new UserCrud(context);
        goalCrud = new GoalCrud(context);
        this.lastInsertedGoal = goalCrud.getLastInsertedGoal();
        this.lastInsertedUser = userCrud.getLastUserInserted();

        sqLiteHelper = new SQLiteHelper(context);
        incomeSQLiteHelper = new IncomeSQLiteHelper(context);
    }

    public void addUserToParse(){
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
                else{
                    // Error occured
                }
            }
        });


    }

    public void addGoalToParse(){
        // get the user back from the database
        final String userParseId = userCrud.getLastUserInserted().getParseId();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");
        query.getInBackground(userParseId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null){
                    // create a Goal table
                    final ParseObject goal = new ParseObject("Goal");
                    goal.put("GoalName", lastInsertedGoal.getName());
                    goal.put("GoalAmount", lastInsertedGoal.getAmount());
                    goal.put("GoalStartDate", lastInsertedGoal.getStartDate());
                    goal.put("GoalEndDate", lastInsertedGoal.getEndDate());

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
                else{
                    // Error occured
                }
            }
        });
    }


    public void addExpenditureToParse(){
       String goalParseId = goalCrud.getLastInsertedGoal().getParseId();
        Log.d(TAG, "AddExpenditure: " + goalCrud.getLastInsertedGoal().getParseId());

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Goal");
        query.getInBackground(goalParseId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null){
                    ParseObject expenditure = new ParseObject("Expenditure");
                    expenditure.put("TotalTransportCost", sqLiteHelper.addAllTransport());
                    expenditure.put("TotalEducationCost", sqLiteHelper.addAllEducation());
                    expenditure.put("TotalHealthCost", sqLiteHelper.addAllHealth());
                    expenditure.put("TotalSavingCost", sqLiteHelper.addAllSavings());
                    expenditure.put("TotalOthersCost", sqLiteHelper.addAllOthers());
                    expenditure.put("TotalHomeNeedsCost", sqLiteHelper.addAllHomeneeds());
                    expenditure.put("TotalExpenditureCost", sqLiteHelper.addAllCategories());

                    // create a relationship between goal table and expenditure table
                    expenditure.put("parent", object);

                    expenditure.saveInBackground();
                }
                else{
                    // Error occured
                }
            }
        });
    }

    public void addIncomeToParse(){
        String userParseId = userCrud.getLastUserInserted().getParseId();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");
        query.getInBackground(userParseId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    ParseObject income = new ParseObject("Income");
                    income.put("TotalLoanIncome", incomeSQLiteHelper.addAllLoan());
                    income.put("TotalSalaryIncome", incomeSQLiteHelper.addAllSalary());
                    income.put("TotalInvestmentIncome", incomeSQLiteHelper.addAllInvestment());
                    income.put("TotalOthersIncome", incomeSQLiteHelper.addAllOthers());
                    income.put("TotalIncome", incomeSQLiteHelper.addAllIncome());

                    // create a relationship between user table and income table
                    income.put("parent", object);

                    income.saveInBackground();
                }
                else{
                    // error occured
                }
            }
        });
    }

    public void upDateUser(String objectId){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");

        // Retrieve the object by id
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject user, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data.
                    user.put("HouseHoldComposition", lastInsertedUser.getHousehold());
                    user.put("Age", lastInsertedUser.getAge());
                    user.put("Sex", lastInsertedUser.getSex());
                    user.put("CountryOfBirth", lastInsertedUser.getNationality());
                    user.put("Points", lastInsertedUser.getPoints());

                    user.saveInBackground();
                }
            }
        });
    }

    public void upDateGoal(String objectId){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Goal");

        // Retrieve the object by id
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject goal, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data.
                    goal.put("GoalName", lastInsertedGoal.getName());
                    goal.put("GoalAmount", lastInsertedGoal.getAmount());
                    goal.put("GoalStartDate", lastInsertedGoal.getStartDate());
                    goal.put("GoalEndDate", lastInsertedGoal.getEndDate());

                    goal.saveInBackground();
                }
            }
        });
    }

    public void upDateExpenditure(String objectId){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Expenditure");

        // Retrieve the object by id
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject expenditure, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data.
                    expenditure.put("TotalTransportCost", sqLiteHelper.addAllTransport());
                    expenditure.put("TotalEducationCost", sqLiteHelper.addAllEducation());
                    expenditure.put("TotalHealthCost", sqLiteHelper.addAllHealth());
                    expenditure.put("TotalSavingCost", sqLiteHelper.addAllSavings());
                    expenditure.put("TotalOthersCost", sqLiteHelper.addAllOthers());
                    expenditure.put("TotalHomeNeedsCost", sqLiteHelper.addAllHomeneeds());
                    expenditure.put("TotalExpenditureCost", sqLiteHelper.addAllCategories());

                    expenditure.saveInBackground();
                }
            }
        });
    }

    public void upDateIncome(String objectId){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Income");

        // Retrieve the object by id
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject income, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data.
                    income.put("TotalLoanIncome", incomeSQLiteHelper.addAllLoan());
                    income.put("TotalSalaryIncome", incomeSQLiteHelper.addAllSalary());
                    income.put("TotalInvestmentIncome", incomeSQLiteHelper.addAllInvestment());
                    income.put("TotalOthersIncome", incomeSQLiteHelper.addAllOthers());
                    income.put("TotalIncome", incomeSQLiteHelper.addAllIncome());

                    income.saveInBackground();
                }
            }
        });
    }

}
