package com.example.eq62roket.CashTime.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.eq62roket.CashTime.models.Goal;
import com.example.eq62roket.CashTime.models.User;

import java.util.ArrayList;

/**
 * Created by cashtime on 8/5/17.
 */

public class GoalCrud {
    private static final String TAG = "GoalCrud";

    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private Context context;

    public GoalCrud(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getReadableDatabase();
    }

    public void createGoal(Goal goal){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_GOAL_NAME, goal.getName());
        values.put(DatabaseHelper.COLUMN_GOAL_AMOUNT, goal.getAmount());
        values.put(DatabaseHelper.COLUMN_GOAL_STARTDATE, goal.getStartDate());
        values.put(DatabaseHelper.COLUMN_GOAL_ENDDATE, goal.getEndDate());

        database.insert(DatabaseHelper.TABLE_GOAL, null, values);
//        database.close();
    }

    public void updateGoal(Goal goal){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_GOAL_NAME, goal.getName());
        values.put(DatabaseHelper.COLUMN_GOAL_AMOUNT, goal.getAmount());
        values.put(DatabaseHelper.COLUMN_GOAL_STARTDATE, goal.getStartDate());
        values.put(DatabaseHelper.COLUMN_GOAL_ENDDATE, goal.getEndDate());
        values.put(DatabaseHelper.COLUMN_GOAL_PARSE_ID, goal.getParseId());

        database.update(DatabaseHelper.TABLE_GOAL, values, DatabaseHelper.COLUMN_GOAL_ID + " = ?", new String[]{String.valueOf(goal.getId())});
        database.close();
    }

    public void deleteGoal(Goal goal){
        database.delete(DatabaseHelper.TABLE_GOAL, DatabaseHelper.COLUMN_GOAL_ID + " = ?", new String[]{String.valueOf(goal.getId())});
        database.close();
    }

    public ArrayList<Goal> getAllGoals(){
        Cursor cursor = database.query(DatabaseHelper.TABLE_GOAL, null, null, null, null, null, null);
        ArrayList<Goal> goalArrayList = new ArrayList<>();
        Goal goal;

        if (cursor.getCount() > 0){
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                goal = new Goal();
                goal.setId(cursor.getLong(0));
                goal.setName(cursor.getString(1));
                goal.setAmount(cursor.getInt(2));
                goal.setStartDate(cursor.getString(3));
                goal.setEndDate(cursor.getString(4));
//                goal.setUser(cursor.getColumnIndex(DatabaseHelper.COLUMN_GOAL_USER_ID));

                // get user id
                long userId = cursor.getLong(5);

                UserCrud userCrud = new UserCrud(context);
                User user = userCrud.getPersonById(userId);
                if (user != null){
                    goal.setUser(user);
                }
                goalArrayList.add(goal);

            }
        }
        return goalArrayList;
    }

    public Goal getLastInsertedGoal() {
        String selectQuery = "select * from " + DatabaseHelper.TABLE_GOAL +
                " order by " + DatabaseHelper.COLUMN_GOAL_ID + " desc " +
                " limit 1";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
        }

        Goal goal = cursorToGoal(cursor);
        return goal;

    }

    protected Goal cursorToGoal(Cursor cursor){
        Goal goal = new Goal();
        goal.setId(cursor.getLong(0));
        goal.setName(cursor.getString(1));
        goal.setAmount(cursor.getInt(2));
        goal.setStartDate(cursor.getString(3));
        goal.setEndDate(cursor.getString(4));
        goal.setParseId(cursor.getString(5));


        // get user id
        long userId = cursor.getLong(6);
        UserCrud userCrud = new UserCrud(context);
        User user = userCrud.getPersonById(userId);
        if (user != null){
            goal.setUser(user);
        }
        return goal;
    }

}
