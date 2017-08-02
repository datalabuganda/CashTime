package com.cashtime.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cashtime.helper.DatabaseHelper;
import com.cashtime.models.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cashtime on 7/18/17.
 */

public class PersonCrud {

    public static final String TAG = "PersonCrud";

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

    public PersonCrud(Context context) {
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

    public Person createPerson(int household, String sex, int age, String educationLevel, String nationality, String phoneNumber, long points){
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
        Person newPerson = cursorToPerson(cursor);
        cursor.close();
        return newPerson;
    }



    public void deletePerson(Person person){
        long id = person.getId();

        // delete all information about this person
        // ....

        System.out.println("the deleted person has the id: " + id);
        mDatabase.delete(DatabaseHelper.TABLE_USER, DatabaseHelper.COLUMN_USER_ID
                + " = " + id, null );

    }

    public List<Person> getAllPeople(){
        List<Person> listPeople = new ArrayList<Person>();

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_USER, mAllColumns,
                null, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Person person = cursorToPerson(cursor);
                listPeople.add(person);
                cursor.moveToNext();
            }
            // close cursor
            cursor.close();
        }
        return listPeople;
    }

    public Person getPersonById(long id){
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_USER, mAllColumns,
                DatabaseHelper.COLUMN_USER_ID + " ? =",
                new String[] {String.valueOf(id)}, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }

        Person person = cursorToPerson(cursor);
        return person;
    }

    protected Person cursorToPerson(Cursor cursor){
        Person person = new Person();
        person.setId(cursor.getLong(0));
//        person.setDistrict(cursor.getString(1));
//        person.setArea(cursor.getString(2));
        person.setHousehold(cursor.getInt(1));
        person.setSex(cursor.getString(2));
        person.setAge(cursor.getInt(3));
        person.setEducationLevel(cursor.getString(4));
        person.setNationality(cursor.getString(5));
        person.setPhoneNumber(cursor.getString(6));
        person.setPoints(cursor.getLong(7));

        return person;
    }


}

