package com.example.eq62roket.CashTime.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by eq62roket on 8/2/17.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "EXPENDITURE";
    public static final String TABLE_NAME1 = "EXPENDITURETABLE";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "AMOUNT";
    public static final String COL_3 = "TRANSPORT";
    public static final String COL_4 = "EDUCATION";
    public static final String COL_6 = "HEALTH";
    public static final String COL_8 = "SAVINGS";
    public static final String COL_10 = "OTHERS";
    public static final String COL_11 = "HOMENEEDS";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME1 +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT INTEGER, TRANSPORT INTEGER, EDUCATION INTEGER, HEALTH INTEGER, SAVINGS INTEGER, OTHERS INTEGER, HOMENEEDS INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME1);
        onCreate(db);
    }


    public boolean insertTransport(int transport){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3, transport);
        long result = db.insert(TABLE_NAME1, null, contentValues);
        if (result == 1)
            return false;
        else
            return true;
    }

    public boolean insertEducation(int amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues educationValues = new ContentValues();
        educationValues.put(COL_4, amount);
        long result = db.insert(TABLE_NAME1, null, educationValues);
        if (result == 1)
            return false;
        else
            return true;
    }

    public boolean insertHealth(int health){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues healthValues = new ContentValues();
        healthValues.put(COL_6, health);
        long result = db.insert(TABLE_NAME1, null, healthValues);
        if (result == 1)
            return false;
        else
            return true;
    }

    public boolean insertSavings(int savings){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues savingsValues = new ContentValues();
        savingsValues.put(COL_8, savings);
        long result = db.insert(TABLE_NAME1, null, savingsValues);
        if (result == 1)
            return false;
        else
            return true;
    }

    public boolean insertOthers(int others){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues othersValues = new ContentValues();
        othersValues.put(COL_10, others);
        long result = db.insert(TABLE_NAME1, null, othersValues);
        if (result == 1)
            return false;
        else
            return true;
    }

    public boolean insertHomeneeds(int homeneeds){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues homeneedsValues = new ContentValues();
        homeneedsValues.put(COL_11, homeneeds);
        long result = db.insert(TABLE_NAME1, null, homeneedsValues);
        if (result == 1)
            return false;
        else
            return true;
    }


    public int addAllTransport(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalTransport = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COL_3) + ") FROM " + TABLE_NAME1, null);
        if (cursor.moveToFirst()){
            totalTransport = cursor.getInt(0);
        }
        cursor.close();
        return totalTransport;
    }

    public int addAllEducation(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalEducation = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COL_4) + ") FROM " + TABLE_NAME1, null);
        if (cursor.moveToFirst()){
            totalEducation = cursor.getInt(0);
        }
        cursor.close();
        return totalEducation;
    }

    public int addAllHealth(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalHealth = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COL_6) + ") FROM " + TABLE_NAME1, null);
        if (cursor.moveToFirst()){
            totalHealth = cursor.getInt(0);
        }
        cursor.close();
        return totalHealth;
    }

    public int addAllSavings(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalSavings = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COL_8) + ") FROM " + TABLE_NAME1, null);
        if (cursor.moveToFirst()){
            totalSavings = cursor.getInt(0);
        }
        cursor.close();
        return totalSavings;
    }

    public int addAllOthers(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalOthers = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COL_10) + ") FROM " + TABLE_NAME1, null);
        if (cursor.moveToFirst()){
            totalOthers = cursor.getInt(0);
        }
        cursor.close();
        return totalOthers;
    }

    public int addAllHomeneeds(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalHomeneeds = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COL_11) + ") FROM " + TABLE_NAME1, null);
        if (cursor.moveToFirst()){
            totalHomeneeds = cursor.getInt(0);
        }
        cursor.close();
        return totalHomeneeds;
    }


    public  int addAllCategories(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        int totalOfAllCategories = 0;
//        Cursor cursor = db.rawQuery("SELECT SUM(" + (COL_3) + ") + SUM(" + (COL_4) + ") + SUM(" + (COL_6) + ") + SUM(" + (COL_8) + ") + SUM(" + (COL_10) + ") + SUM(" + (COL_11) + ") FROM " + TABLE_NAME1, null);
//        if (cursor.moveToFirst()){
//            totalOfAllCategories = cursor.getInt(0);
//        }
//        cursor.close();
//        return totalOfAllCategories;
        int Savings = addAllSavings();
        int Transport = addAllTransport();
        int Medical = addAllHealth();
        int Others = addAllOthers();
        int Homeneeds = addAllHomeneeds();
        int Education = addAllEducation();

        int totalCategory = Savings + Transport + Medical + Others + Homeneeds + Education;

        return totalCategory;
    }

}
