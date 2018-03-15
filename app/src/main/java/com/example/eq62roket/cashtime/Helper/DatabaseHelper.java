package com.example.eq62roket.cashtime.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.jar.Attributes;

/**
 * Created by eq62roket on 3/11/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

//    public DatabaseHelper DBHelper;
    public SQLiteDatabase db;

    //Database Version
    public static final int DATABASE_VERSION = 6;

    //Database Name
    public static final String DATABASE_NAME = "CashTime.db";

    //Members_table
    public static final String TABLE_NAME = "members_table";
    public static final String ROW_ID = "id";
    public static final String Name = "name";
    public static final String Phone = "phone";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME+" (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, phone INTEGER NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

//    public boolean insertData(String name, String phone){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(Name, name);
//        contentValues.put(Phone, phone);
//        long result = db.insert(TABLE_NAME,null,contentValues);
//        if (result == -1){
//            return false;
//        }else {
//            return true;
//        }
//    }

    public Cursor getAllMembers(){
        db = this.getWritableDatabase();
//        String[] projection = {ROW_ID,Name, Phone};
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME,null);
        return cursor;
    }

//    public Cursor getAllGroupMembers(){
//        String[] columns = {DatabaseHelper.ROW_ID,DatabaseHelper.Name,DatabaseHelper.Phone};
//        return db.query(DatabaseHelper.TABLE_NAME,columns,null,null,null,null,null);
//    }

}
