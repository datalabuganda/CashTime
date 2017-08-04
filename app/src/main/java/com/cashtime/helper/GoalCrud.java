package com.cashtime.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cashtime.models.Goal;
import com.cashtime.models.User;

import java.util.ArrayList;

/**
 * Created by cashtime on 8/4/17.
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
}
