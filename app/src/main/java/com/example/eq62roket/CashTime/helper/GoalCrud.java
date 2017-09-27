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
    private Context context;

    public GoalCrud(Context context) {
        this.context = context;
        database = DatabaseHelper.getInstance(context);
    }

    public void createGoal(Goal goal){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_GOAL_NAME, goal.getName());
        values.put(DatabaseHelper.COLUMN_GOAL_AMOUNT, goal.getAmount());
        values.put(DatabaseHelper.COLUMN_GOAL_ENDDATE, goal.getEndDate());
        values.put(DatabaseHelper.COLUMN_GOAL_SYNCED, goal.getSyncStatus());
        values.put(DatabaseHelper.COLUMN_GOAL_COMPLETED, 0);
        values.put(DatabaseHelper.COLUMN_GOAL_POINTS, 0);
        values.put(DatabaseHelper.COLUMN_GOAL_PHP_ID, "0");
        values.put(DatabaseHelper.COLUMN_GOAL_SURPLUS, goal.getSurplus());
        values.put(DatabaseHelper.COLUMN_GOAL_ACTUALCOMPLETIONDATE, goal.getActualCompletionDate());

        database.insert(DatabaseHelper.TABLE_GOAL, null, values);
//        database.close();
    }

    public void updateGoal(Goal goal){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_GOAL_NAME, goal.getName());
        values.put(DatabaseHelper.COLUMN_GOAL_AMOUNT, goal.getAmount());
        values.put(DatabaseHelper.COLUMN_GOAL_ENDDATE, goal.getEndDate());
        values.put(DatabaseHelper.COLUMN_GOAL_PHP_ID, goal.getParseId());
        values.put(DatabaseHelper.COLUMN_GOAL_SYNCED, goal.getSyncStatus());
        values.put(DatabaseHelper.COLUMN_GOAL_COMPLETED, goal.getCompleteStatus());
        values.put(DatabaseHelper.COLUMN_GOAL_POINTS, goal.getTotalPoints());
        values.put(DatabaseHelper.COLUMN_GOAL_SURPLUS, goal.getSurplus());
        values.put(DatabaseHelper.COLUMN_GOAL_ACTUALCOMPLETIONDATE, goal.getActualCompletionDate());

        database.update(DatabaseHelper.TABLE_GOAL, values, DatabaseHelper.COLUMN_GOAL_ID + " = ?", new String[]{String.valueOf(goal.getId())});
        //database.close();
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
                goal.setEndDate(cursor.getString(4));

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

    public Goal getGoalById(long id){
        Cursor cursor = database.query(DatabaseHelper.TABLE_GOAL, null,
                DatabaseHelper.COLUMN_GOAL_ID + " = ?",
                new String[] {String.valueOf(id)}, null, null, null);

        Goal goal = null;
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            goal = cursorToGoal(cursor);
        }
        return goal;
    }

    public Goal getLastInsertedGoal() {
        String selectQuery = "select * from " + DatabaseHelper.TABLE_GOAL +
                " order by " + DatabaseHelper.COLUMN_GOAL_ID + " desc " +
                " limit 1";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
        }
        else {
            Goal goal = null;
            return goal;
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
        goal.setSyncStatus(cursor.getInt(7));
        goal.setCompleteStatus(cursor.getInt(8));
        goal.setTotalPoints(cursor.getLong(9));
        goal.setSurplus(cursor.getInt(10));
        goal.setActualCompletionDate(cursor.getString(11));

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
