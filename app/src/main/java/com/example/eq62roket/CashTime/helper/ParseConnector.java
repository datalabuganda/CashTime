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

    ExpenditureCrud expenditureCrud;
    IncomeCrud incomeCrud;

    Goal lastInsertedGoal;
    User lastInsertedUser;


    public ParseConnector(Context context) {
        this.context = context;
        userCrud = new UserCrud(context);
        goalCrud = new GoalCrud(context);
        this.lastInsertedGoal = goalCrud.getLastInsertedGoal();
        this.lastInsertedUser = userCrud.getLastUserInserted();

        expenditureCrud = new ExpenditureCrud(context);
        incomeCrud = new IncomeCrud(context);
    }

    public void addUserToParse(){
        // add details of this user to a ParseObject
        final ParseObject user = new ParseObject("ctUser");
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

        ParseQuery<ParseObject> query = ParseQuery.getQuery("ctUser");
        query.getInBackground(userParseId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null){
                    // create a Goal table
                    final ParseObject goal = new ParseObject("ctUserGoals");
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

        ParseQuery<ParseObject> query = ParseQuery.getQuery("ctUserGoals");
        query.getInBackground(goalParseId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null){
                    ParseObject expenditure = new ParseObject("ctUserExpenditures");
                    expenditure.put("TotalTransportCost", expenditureCrud.addAllTransport());
                    expenditure.put("TotalEducationCost", expenditureCrud.addAllEducation());
                    expenditure.put("TotalHealthCost", expenditureCrud.addAllHealth());
                    expenditure.put("TotalSavingCost", expenditureCrud.addAllSavings(null));
                    expenditure.put("TotalOthersCost", expenditureCrud.addAllOthers());
                    expenditure.put("TotalHomeNeedsCost", expenditureCrud.addAllHomeneeds());
                    expenditure.put("TotalExpenditureCost", expenditureCrud.addAllCategories());

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

        ParseQuery<ParseObject> query = ParseQuery.getQuery("ctUser");
        query.getInBackground(userParseId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    ParseObject income = new ParseObject("ctUserIncomes");
                    income.put("TotalLoanIncome", incomeCrud.addAllLoan());
                    income.put("TotalSalaryIncome", incomeCrud.addAllSalary());
                    income.put("TotalInvestmentIncome", incomeCrud.addAllInvestment());
                    income.put("TotalOthersIncome", incomeCrud.addAllOthers());
                    income.put("TotalIncome", incomeCrud.addAllIncome());

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
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ctUser");

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
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ctUserGoals");

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
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ctUserExpenditures");

        // Retrieve the object by id
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject expenditure, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data.
                    expenditure.put("TotalTransportCost", expenditureCrud.addAllTransport());
                    expenditure.put("TotalEducationCost", expenditureCrud.addAllEducation());
                    expenditure.put("TotalHealthCost", expenditureCrud.addAllHealth());
                    expenditure.put("TotalSavingCost", expenditureCrud.addAllSavings(null));
                    expenditure.put("TotalOthersCost", expenditureCrud.addAllOthers());
                    expenditure.put("TotalHomeNeedsCost", expenditureCrud.addAllHomeneeds());
                    expenditure.put("TotalExpenditureCost", expenditureCrud.addAllCategories());

                    expenditure.saveInBackground();
                }
            }
        });
    }

    public void upDateIncome(String objectId){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ctUserIncomes");

        // Retrieve the object by id
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject income, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data.
                    income.put("TotalLoanIncome", incomeCrud.addAllLoan());
                    income.put("TotalSalaryIncome", incomeCrud.addAllSalary());
                    income.put("TotalInvestmentIncome", incomeCrud.addAllInvestment());
                    income.put("TotalOthersIncome", incomeCrud.addAllOthers());
                    income.put("TotalIncome", incomeCrud.addAllIncome());

                    income.saveInBackground();
                }
            }
        });
    }

}
