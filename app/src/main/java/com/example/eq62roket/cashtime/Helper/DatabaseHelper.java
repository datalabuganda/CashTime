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
    public static final int DATABASE_VERSION = 8;

    //Database Name
    public static final String DATABASE_NAME = "CashTime.db";

    //TABLES
    public static final String TABLE_NAME = "members_table";
    public static final String INCOME_TABLE = "income";

    public static final String ROW_ID = "id";
    public static final String Name = "name";
    public static final String Phone = "phone";

    //MembersIncome
    public static final String INCOME_ID = "id";
    public static final String INCOME_SOURCE = "income_source";
    public static final String INCOME_AMOUNT = "income_amount";
    public static final String INCOME_DATE = "income_date";
    public static final String INCOME_PERIOD = "income_period";
    public static final String INCOME_NOTES = "income_notes";
    public static final String MEMBER_ID = "member_id";


    private static final String CREATE_TABLE_MEMBERS = "CREATE TABLE "
            + TABLE_NAME + "(" + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Name
            + " TEXT NOT NULL," + Phone + " INTEGER NOT NULL" + ")";

    public static final String CREATE_TABLE_INCOME = "CREATE TABLE "
            + INCOME_TABLE + "(" + INCOME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + INCOME_SOURCE + " TEXT NOT NULL, " + INCOME_AMOUNT + " DOUBLE NOT NULL, "
            + INCOME_DATE + " DATE NOT NULL, "  + INCOME_PERIOD + " DATE NOT NULL, " + INCOME_NOTES + " TEXT NOT NULL, "
            + MEMBER_ID + " INT, "
            + "FOREIGN KEY(" + MEMBER_ID + ") REFERENCES "
            + TABLE_NAME + "(id) " + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MEMBERS);
        db.execSQL(CREATE_TABLE_INCOME);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+INCOME_TABLE);
        onCreate(db);
    }


    public Cursor getAllMembers(){
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME,null);
        return cursor;
    }



}
