package com.cashtime.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

        // open the database
        try {
            open();
        } catch (SQLException e){
            Log.e(TAG, "SQLException on opening the database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException{
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void close(){
        mDatabaseHelper.close();
    }

    public User createPerson(int household, String sex, int age, String educationLevel, String nationality, String phoneNumber, long points){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_HOUSEHOLD, household);
        values.put(DatabaseHelper.COLUMN_USER_SEX, sex);
        values.put(DatabaseHelper.COLUMN_USER_AGE, age);
        values.put(DatabaseHelper.COLUMN_USER_LEVEL_EDUCATION, educationLevel);
        values.put(DatabaseHelper.COLUMN_USER_NATIONALITY, nationality);
        values.put(DatabaseHelper.COLUMN_USER_PHONE_NUMBER, phoneNumber);
        values.put(DatabaseHelper.COLUMN_USER_POINTS, points);

        long insertedId = mDatabase.insert(DatabaseHelper.TABLE_USER, null, values);
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_USER,
                mAllColumns, DatabaseHelper.COLUMN_USER_ID + " = " + insertedId, null, null, null, null);
        cursor.moveToFirst();
        User newUser = cursorToPerson(cursor);
        cursor.close();
        return newUser;
    }



    public void deletePerson(User user){
        long id = user.getId();

        // delete all information about this user
        // ....

        System.out.println("the deleted user has the id: " + id);
        mDatabase.delete(DatabaseHelper.TABLE_USER, DatabaseHelper.COLUMN_USER_ID
                + " = " + id, null );

    }

    public List<User> getAllPeople(){
        List<User> listPeople = new ArrayList<User>();

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_USER, mAllColumns,
                null, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                User user = cursorToPerson(cursor);
                listPeople.add(user);
                cursor.moveToNext();
            }
            // close cursor
            cursor.close();
        }
        return listPeople;
    }

    public User getPersonById(long id){
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_USER, mAllColumns,
                DatabaseHelper.COLUMN_USER_ID + " ? =",
                new String[] {String.valueOf(id)}, null, null, null);
        if (cursor != null){
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

