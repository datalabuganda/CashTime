package com.cashtime.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cashtime.models.Goal;
import com.cashtime.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cashtime on 7/18/17.
 */

public class UserCrud {

    public static final String TAG = "UserCrud";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDatabaseHelper;
    private Context mContext;
    private String[] mAllColumns = {
            DatabaseHelper.COLUMN_USER_ID,
            DatabaseHelper.COLUMN_USER_HOUSEHOLD,
            DatabaseHelper.COLUMN_USER_SEX,
            DatabaseHelper.COLUMN_USER_AGE,
            DatabaseHelper.COLUMN_USER_LEVEL_EDUCATION,
            DatabaseHelper.COLUMN_USER_NATIONALITY,
            DatabaseHelper.COLUMN_USER_PHONE_NUMBER,
            DatabaseHelper.COLUMN_USER_POINTS
    };

    public UserCrud(Context context) {
        this.mContext = context;
        mDatabaseHelper = new DatabaseHelper(context);
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void createUser(User user){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_HOUSEHOLD, user.getHousehold());
        values.put(DatabaseHelper.COLUMN_USER_SEX, user.getSex());
        values.put(DatabaseHelper.COLUMN_USER_AGE, user.getAge());
        values.put(DatabaseHelper.COLUMN_USER_LEVEL_EDUCATION, user.getEducationLevel());
        values.put(DatabaseHelper.COLUMN_USER_NATIONALITY, user.getNationality());
        values.put(DatabaseHelper.COLUMN_USER_PHONE_NUMBER, user.getPhoneNumber());
        values.put(DatabaseHelper.COLUMN_USER_POINTS, user.getPoints());

        mDatabase.insert(DatabaseHelper.TABLE_USER, null, values);
    }

    public void updateUser(User user){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_HOUSEHOLD, user.getHousehold());
        values.put(DatabaseHelper.COLUMN_USER_SEX, user.getSex());
        values.put(DatabaseHelper.COLUMN_USER_AGE, user.getAge());
        values.put(DatabaseHelper.COLUMN_USER_LEVEL_EDUCATION, user.getEducationLevel());
        values.put(DatabaseHelper.COLUMN_USER_NATIONALITY, user.getNationality());
        values.put(DatabaseHelper.COLUMN_USER_PHONE_NUMBER, user.getPhoneNumber());
        values.put(DatabaseHelper.COLUMN_USER_POINTS, user.getPoints());

        mDatabase.update(DatabaseHelper.TABLE_USER,
                values,
                DatabaseHelper.COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        mDatabase.close();
    }

    public void deleteUser(User user){
        mDatabase.delete(DatabaseHelper.TABLE_USER,
                DatabaseHelper.COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        mDatabase.close();
    }

    public ArrayList<User> getAllUsers(){
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_USER, null, null, null, null, null, null);
        ArrayList<User> userArrayList = new ArrayList<>();
        User user;

        if (cursor.getCount() > 0){
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                user = new User();
                user.setId(cursor.getLong(0));
                user.setHousehold(cursor.getInt(1));
                user.setSex(cursor.getString(2));
                user.setAge(cursor.getInt(3));
                user.setEducationLevel(cursor.getString(4));
                user.setNationality(cursor.getString(5));
                user.setPhoneNumber(cursor.getString(6));
                user.setPoints(cursor.getLong(7));

                userArrayList.add(user);
            }
        }
        return userArrayList;

    }


    public User getPersonById(long id){
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_USER, mAllColumns,
                DatabaseHelper.COLUMN_USER_ID + " = ?",
                new String[] {String.valueOf(id)}, null, null, null);

        User user = null;
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            user = cursorToPerson(cursor);
        }
        return user;
    }

    public User getLastUserInserted(){

        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        String selectQuery = "select * from "+DatabaseHelper.TABLE_USER+
                " order by "+DatabaseHelper.COLUMN_USER_ID+" desc "+
                " limit 1";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
        }

        User user = cursorToPerson(cursor);
        return user;
    }
    protected User cursorToPerson(Cursor cursor){
        User user = new User();
        user.setId(cursor.getLong(0));
//        user.setDistrict(cursor.getString(1));
//        user.setArea(cursor.getString(2));
        user.setHousehold(cursor.getInt(1));
        user.setSex(cursor.getString(2));
        user.setAge(cursor.getInt(3));
        user.setEducationLevel(cursor.getString(4));
        user.setNationality(cursor.getString(5));
        user.setPhoneNumber(cursor.getString(6));
        user.setPoints(cursor.getLong(7));

        return user;
    }


}

