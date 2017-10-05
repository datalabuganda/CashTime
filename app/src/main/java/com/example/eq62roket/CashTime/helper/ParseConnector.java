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
        user.put("LevelOfEducation", lastInsertedUser.getEducationlevel());
        user.put("Points", lastInsertedUser.getPoints());
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    String userParseId = user.getObjectId();
                    lastInsertedUser.setParseId(userParseId);
                    lastInsertedUser.setSyncStatus(1);
                    userCrud.updateUser(lastInsertedUser);
                }
                else{
                    // Error occured
                    Log.d(TAG, "done user: " + e);
                }
            }
        });


    }

    public void addGoalToParse(){
        // get the user back from the database
        final String userParseId = userCrud.getLastUserInserted().getParseId();
        Log.d(TAG, "userParseId: " + userParseId);

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
                    goal.put("ActualCompletionDate", lastInsertedGoal.getActualCompletionDate());

                    // link the user to this goal table
                    goal.put("parent", object);

                    goal.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                String goalParseId = goal.getObjectId();
                                lastInsertedGoal.setParseId(goalParseId);
                                lastInsertedGoal.setSyncStatus(1);
                                goalCrud.updateGoal(lastInsertedGoal);

                            }
                        }
                    });

                }
                else{
                    // Error occured
                    Log.d(TAG, "done goal: " + e);
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
                    expenditure.put("TransportCost", String.valueOf(expenditureCrud.getLastInsertedTransport()));
                    expenditure.put("TransportCostInsertDate", String.valueOf(expenditureCrud.getTransportDate()));
                    expenditure.put("EducationCost", String.valueOf(expenditureCrud.getLastInsertedEducation()));
                    expenditure.put("EducationCostInsertDate", String.valueOf(expenditureCrud.geteEducationDate()));
                    expenditure.put("HealthCost", String.valueOf(expenditureCrud.getLastInsertedHealth()));
                    expenditure.put("HealthCostInsertDate", String.valueOf(expenditureCrud.getHealthDate()));
                    expenditure.put("SavingCost", String.valueOf(expenditureCrud.getLastInsertedSavings()));
                    expenditure.put("SavingCostInsertDate", String.valueOf(expenditureCrud.getSavingsDate()));
                    expenditure.put("OthersCost", String.valueOf(expenditureCrud.getLastInsertedOthers()));
                    expenditure.put("OthersCostInsertDate", String.valueOf(expenditureCrud.getOthersDate()));
                    expenditure.put("HomeNeedsCost", String.valueOf(expenditureCrud.getLastInserteHomeneeds()));
                    expenditure.put("HomeNeedsCostInsertDate", String.valueOf(expenditureCrud.getHomeneedsDate()));
                    expenditure.put("ctUserId", lastInsertedUser.getParseId());

                    // create a relationship between goal table and expenditure table
                    expenditure.put("parent", object);

                    expenditure.saveInBackground();
                }
                else{
                    // Error occured
                    Log.d(TAG, "done expenditure: " + e);
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
                    income.put("LoanIncome", String.valueOf(incomeCrud.getLastInserteLoan()));
                    income.put("LoanIncomeInsertDate", String.valueOf(incomeCrud.getLoanDate()));
                    income.put("LoanIncomePeriod", String.valueOf(incomeCrud.getLoanPeriod()));
                    income.put("SalaryIncome", String.valueOf(incomeCrud.getLastInsertedSalary()));
                    income.put("SalaryIncomeInsertDate", String.valueOf(incomeCrud.getSalaryDate()));
                    income.put("SalaryIncomePeriod", String.valueOf(incomeCrud.getSalaryPeriod()));
                    income.put("InvestmentIncome", String.valueOf(incomeCrud.getLastInserteInvestment()));
                    income.put("InvestmentIncomeInsertDate", String.valueOf(incomeCrud.getInvestmentDate()));
                    income.put("InvestmentIncomePeriod", String.valueOf(incomeCrud.getInvestmentPeriod()));
                    income.put("OthersIncome", String.valueOf(incomeCrud.getLastInsertedOthers()));
                    income.put("OthersIncomeInsertDate", String.valueOf(incomeCrud.getOthersDate()));
                    income.put("OthersIncomePeriod", String.valueOf(incomeCrud.getOthersPeriod()));

                    // create a relationship between user table and income table
                    income.put("parent", object);

                    income.saveInBackground();
                }
                else{
                    // error occured
                    Log.d(TAG, "done income: " + e);
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
                    user.put("LevelOfEducation", lastInsertedUser.getEducationlevel());
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
                    goal.put("ActualCompletionDate", lastInsertedGoal.getActualCompletionDate());

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
                    expenditure.put("TransportCost", String.valueOf(expenditureCrud.getLastInsertedTransport()));
                    expenditure.put("TransportCostInsertDate", String.valueOf(expenditureCrud.getTransportDate()));
                    expenditure.put("EducationCost", String.valueOf(expenditureCrud.getLastInsertedEducation()));
                    expenditure.put("EducationCostInsertDate", String.valueOf(expenditureCrud.geteEducationDate()));
                    expenditure.put("HealthCost", String.valueOf(expenditureCrud.getLastInsertedHealth()));
                    expenditure.put("HealthCostInsertDate", String.valueOf(expenditureCrud.getHealthDate()));
                    expenditure.put("SavingCost", String.valueOf(expenditureCrud.getLastInsertedSavings()));
                    expenditure.put("SavingCostInsertDate", String.valueOf(expenditureCrud.getSavingsDate()));
                    expenditure.put("OthersCost", String.valueOf(expenditureCrud.getLastInsertedOthers()));
                    expenditure.put("OthersCostInsertDate", String.valueOf(expenditureCrud.getOthersDate()));
                    expenditure.put("HomeNeedsCost", String.valueOf(expenditureCrud.getLastInserteHomeneeds()));
                    expenditure.put("HomeNeedsCostInsertDate", String.valueOf(expenditureCrud.getHomeneedsDate()));
                    expenditure.put("ctUserId", lastInsertedUser.getParseId());

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
                    income.put("LoanIncome", String.valueOf(incomeCrud.getLastInserteLoan()));
                    income.put("LoanIncomeInsertDate", String.valueOf(incomeCrud.getLoanDate()));
                    income.put("LoanIncomePeriod", String.valueOf(incomeCrud.getLoanPeriod()));
                    income.put("SalaryIncome", String.valueOf(incomeCrud.getLastInsertedSalary()));
                    income.put("SalaryIncomeInsertDate", String.valueOf(incomeCrud.getSalaryDate()));
                    income.put("SalaryIncomePeriod", String.valueOf(incomeCrud.getSalaryPeriod()));
                    income.put("InvestmentIncome", String.valueOf(incomeCrud.getLastInserteInvestment()));
                    income.put("InvestmentIncomeInsertDate", String.valueOf(incomeCrud.getInvestmentDate()));
                    income.put("InvestmentIncomePeriod", String.valueOf(incomeCrud.getInvestmentPeriod()));
                    income.put("OthersIncome", String.valueOf(incomeCrud.getLastInsertedOthers()));
                    income.put("OthersIncomeInsertDate", String.valueOf(incomeCrud.getOthersDate()));
                    income.put("OthersIncomePeriod", String.valueOf(incomeCrud.getOthersPeriod()));

                    income.saveInBackground();
                }
            }
        });
    }

}
