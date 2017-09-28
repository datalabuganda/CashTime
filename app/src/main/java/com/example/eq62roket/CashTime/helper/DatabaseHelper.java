package com.example.eq62roket.CashTime.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by cashTime on 8/5/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper";
    private static DatabaseHelper mInstance = null;
    private static SQLiteDatabase db_instance = null;

    private static final String DATABASE_NAME = "cashTime.db";
    private static final int DATABASE_VERSION = 6;

    public static SQLiteDatabase getInstance(Context ctx) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }

        if (db_instance == null) {
            db_instance = mInstance.getWritableDatabase();
        }
        return db_instance;
    }


    // Columns of User table
    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_HOUSEHOLD = "household_composition";
    public static final String COLUMN_USER_SEX = "sex";
    public static final String COLUMN_USER_AGE = "age";
    public static final String COLUMN_USER_LEVEL_EDUCATION = "level_of_education";
    public static final String COLUMN_USER_NATIONALITY = "nationality";
    public static final String COLUMN_USER_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_USER_PARSE_ID = "parse_id";
    public static final String COLUMN_USER_POINTS = "points";
    public static final String COLUMN_USER_SYNCED = "user_synced";


    // goal table
    public static final String TABLE_GOAL = "goal";
    public static final String COLUMN_GOAL_ID = "goal_id";
    public static final String COLUMN_GOAL_NAME = "goal_name";
    public static final String COLUMN_GOAL_AMOUNT = "goal_amount";
    public static final String COLUMN_GOAL_STARTDATE = "goal_startDate";
    public static final String COLUMN_GOAL_ENDDATE = "goal_endDate  ";
    public static final String COLUMN_GOAL_ACTUALCOMPLETIONDATE = "goal_actualCompletionDate ";
    public static final String COLUMN_GOAL_PARSE_ID = "goal_parseId";
    public static final String COLUMN_GOAL_USER_ID = "goal_user_id";
    public static final String COLUMN_GOAL_SYNCED = "goal_synced";
    public static final String COLUMN_GOAL_COMPLETED = "goal_completed";
    public static final String COLUMN_GOAL_POINTS = "goal_points";
    public static final String COLUMN_GOAL_SURPLUS = "goal_amount_saved";



    // expenditure table
    public static final String TABLE_EXPENDITURE = "EXPENDITURETABLE";
    public static final String COLUMN_EXPENDITURE_ID = "ID";
    public static final String COLUMN_EXPENDITURE_AMOUNT = "AMOUNT";
    public static final String COLUMN_EXPENDITURE_TRANSPORT = "TRANSPORT";
    public static final String COLUMN_EXPENDITURE_EDUCATION = "EDUCATION";
    public static final String COLUMN_EXPENDITURE_HEALTH = "HEALTH";
    public static final String COLUMN_EXPENDITURE_SAVINGS = "SAVINGS";
    public static final String COLUMN_EXPENDITURE_OTHERS = "OTHERS";
    public static final String COLUMN_EXPENDITURE_HOMENEEDS = "HOMENEEDS";
    public static final String COLUMN_EXPENDITURE_INSERTDATE = "DATEINSERTED";
    public static final String COLUMN_EXPENDITURE_SYNCSTATUS = "SYNCSTATUS";
    public static final String COLUMN_EXPENDITURE_PARSEID = "PARSEID";



    // income table
    public static final String TABLE_INCOME = "INCOMETABLE";
    public static final String COLUMN_INCOME_ID = "ID";
    public static final String COLUMN_INCOME_SYNCSTATUS = "SYNC_STATUS";
    public static final String COLUMN_INCOME_SALARY = "SALARY";
    public static final String COLUMN_INCOME_LOAN = "LOAN";
    public static final String COLUMN_INCOME_INVESTMENT = "INVESTMENT";
    public static final String COLUMN_INCOME_OTHERS = "OTHERS";
    public static final String COLUMN_INCOME_CREATEDATE = "CREATED_DATE";
    public static final String COLUMN_INCOME_PARSEID = "PARSEID";



    // Variable to create User_table
    private static final String SQL_CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_HOUSEHOLD + " INTEGER NOT NULL, "
            + COLUMN_USER_SEX + " TEXT NOT NULL, "
            + COLUMN_USER_AGE + " INTEGER NOT NULL, "
            + COLUMN_USER_LEVEL_EDUCATION + " TEXT NOT NULL, "
            + COLUMN_USER_NATIONALITY + " TEXT NOT NULL, "
            + COLUMN_USER_PHONE_NUMBER + " TEXT NOT NULL, "
            + COLUMN_USER_POINTS + " REAL NOT NULL, "
            + COLUMN_USER_PARSE_ID + " TEXT, "
            + COLUMN_USER_SYNCED + " INTEGER "
            + ");";

    // Variable to create Goal_table
    private static final String SQL_CREATE_TABLE_GOAL = "CREATE TABLE " + TABLE_GOAL + "("
            + COLUMN_GOAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_GOAL_NAME + " TEXT NOT NULL, "
            + COLUMN_GOAL_AMOUNT + " INTEGER NOT NULL, "
            + COLUMN_GOAL_STARTDATE + " TIMESTAMP DEFAULT (datetime('now', 'localtime')), "
            + COLUMN_GOAL_ENDDATE + " DATE NOT NULL, "
            + COLUMN_GOAL_PARSE_ID + " TEXT, "
            + COLUMN_GOAL_USER_ID + " INTEGER, "
            + COLUMN_GOAL_SYNCED + " INTEGER, "
            + COLUMN_GOAL_COMPLETED + " INTEGER, "
            + COLUMN_GOAL_POINTS + " INTEGER, "
            + COLUMN_GOAL_SURPLUS + " INTEGER, "
            + COLUMN_GOAL_ACTUALCOMPLETIONDATE + " TEXT "
            + ");";

    // Variable to create Expenditure_table
    private static final String SQL_CREATE_TABLE_EXPENDITURE = "create table " + TABLE_EXPENDITURE +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT INTEGER DEFAULT 0, TRANSPORT INTEGER DEFAULT 0, EDUCATION INTEGER DEFAULT 0, HEALTH INTEGER DEFAULT 0, SAVINGS INTEGER DEFAULT 0, OTHERS INTEGER DEFAULT 0, HOMENEEDS INTEGER DEFAULT 0, DATEINSERTED TIMESTAMP DEFAULT (datetime('now', 'localtime')), SYNCSTATUS INTEGER DEFAULT 0, PARSEID INTEGER)";


    // Variable to create Income_table


    private static final String SQL_CREATE_TABLE_INCOME = "create table "+ TABLE_INCOME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, SYNC_STATUS INTEGER DEFAULT 0, AMOUNT INTEGER DEFAULT 0, SALARY INTEGER DEFAULT 0, LOAN INTEGER DEFAULT 0, INVESTMENT INTEGER DEFAULT 0, OTHERS INTEGER DEFAULT 0, CREATED_DATE TIMESTAMP DEFAULT (datetime('now', 'localtime')), PARSEID INTEGER)";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate started");

        db.execSQL(SQL_CREATE_TABLE_USER);
        db.execSQL(SQL_CREATE_TABLE_GOAL);
        db.execSQL(SQL_CREATE_TABLE_EXPENDITURE);
        db.execSQL(SQL_CREATE_TABLE_INCOME);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        // clear all data in database
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENDITURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);

        // recreate the table
        onCreate(db);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
}

