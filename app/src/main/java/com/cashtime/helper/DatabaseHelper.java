package com.cashtime.helper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by cashtime on 7/18/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper";

    // Columns of User table
    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_HOUSEHOLD = "household_composition";
    public static final String COLUMN_USER_SEX = "sex";
    public static final String COLUMN_USER_AGE = "age";
    public static final String COLUMN_USER_LEVEL_EDUCATION = "level_of_education";
    public static final String COLUMN_USER_NATIONALITY = "nationality";
    public static final String COLUMN_USER_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_USER_POINTS = "points";


    // goal table
    public static final String TABLE_GOAL = "goal";
    public static final String COLUMN_GOAL_ID = "goal_id";
    public static final String COLUMN_GOAL_NAME = "goal_name";
    public static final String COLUMN_GOAL_AMOUNT = "goal_amount";
    public static final String COLUMN_GOAL_STARTDATE = "goal_startDate";
    public static final String COLUMN_GOAL_ENDDATE = "goal_endDate  ";
    public static final String COLUMN_GOAL_USER_ID = "goal_user_id";


    // expenditure table
    public static final String TABLE_EXPENDITURE = "expenditure";
    public static final String COLUMN_EXPENDITURE_ID = "expenditure_id";
    public static final String COLUMN_EXPENDITURE_GOAL_ID = "expenditure_goal_id";
    public static final String COLUMN_EXPENDITURE_DATE = "expenditure_date";
    public static final String COLUMN_EXPENDITURE_EDUCATION = "expenditure_education";
    public static final String COLUMN_EXPENDITURE_TRANSPORT = "expenditure_transport";
    public static final String COLUMN_EXPENDITURE_HEALTH = "expenditure_health";
    public static final String COLUMN_EXPENDITURE_HOMENEEDS = "expenditure_home_needs";
    public static final String COLUMN_EXPENDITURE_SAVINGS = "expenditure_savings";
    public static final String COLUMN_EXPENDITURE_OTHERS = "expenditure_others";


    // income table
    public static final String TABLE_INCOME = "income";
    public static final String COLUMN_INCOME_ID = "income_id";
    public static final String COLUMN_INCOME_USER_ID = "income_user_id";
    public static final String COLUMN_INCOME_AMOUNT = "income_amount";
    public static final String COLUMN_INCOME_STARTDATE = "income_startDate";
    public static final String COLUMN_INCOME_ENDDATE = "income_endDate";
    public static final String COLUMN_INCOME_SALARY = "income_salary";
    public static final String COLUMN_INCOME_LOAN = "income_loan";
    public static final String COLUMN_INCOME_INVESTMENT = "income_investment";
    public static final String COLUMN_INCOME_OTHERS = "income_others";


    private static final String DATABASE_NAME = "cashTime.db";
    private static final int DATABASE_VERSION = 6;

    // Variable to create Person_table
    private static final String SQL_CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_HOUSEHOLD + " INTEGER NOT NULL, "
            + COLUMN_USER_SEX + " TEXT NOT NULL, "
            + COLUMN_USER_AGE + " INTEGER NOT NULL, "
            + COLUMN_USER_LEVEL_EDUCATION + " TEXT NOT NULL, "
            + COLUMN_USER_NATIONALITY + " TEXT NOT NULL, "
            + COLUMN_USER_PHONE_NUMBER + " TEXT NOT NULL, "
            + COLUMN_USER_POINTS + " REAL NOT NULL "
            + ");";


    // Variable to create Goal_table
    private static final String SQL_CREATE_TABLE_GOAL = "CREATE TABLE " + TABLE_GOAL + "("
            + COLUMN_GOAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_GOAL_NAME + " TEXT NOT NULL, "
            + COLUMN_GOAL_AMOUNT + " INTEGER NOT NULL, "
            + COLUMN_GOAL_STARTDATE + " DATE NOT NULL, "
            + COLUMN_GOAL_ENDDATE + " DATE NOT NULL, "
            + COLUMN_GOAL_USER_ID + " INTEGER "
            + ");";

    // Variable to create Expenditure_table
    private static final String SQL_CREATE_TABLE_EXPENDITURE = "CREATE TABLE " + TABLE_EXPENDITURE + "("
            + COLUMN_EXPENDITURE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_EXPENDITURE_GOAL_ID + " INTEGER, "
            + COLUMN_EXPENDITURE_DATE + " DATE NOT NULL, "
            + COLUMN_EXPENDITURE_EDUCATION + " INTEGER, "
            + COLUMN_EXPENDITURE_TRANSPORT + " INTEGER, "
            + COLUMN_EXPENDITURE_HEALTH + " INTEGER, "
            + COLUMN_EXPENDITURE_HOMENEEDS + " INTEGER, "
            + COLUMN_EXPENDITURE_SAVINGS + " INTEGER, "
            + COLUMN_EXPENDITURE_OTHERS + " INTEGER "
            + ");";


    // Variable to create Income_table
    private static final String SQL_CREATE_TABLE_INCOME = "CREATE TABLE " + TABLE_INCOME + "("
            + COLUMN_INCOME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_INCOME_USER_ID + " INTEGER NOT NULL, "
            + COLUMN_INCOME_AMOUNT + " INTEGER NOT NULL, "
            + COLUMN_INCOME_STARTDATE + " DATE, "
            + COLUMN_INCOME_ENDDATE + " DATE, "
            + COLUMN_INCOME_SALARY + " INTEGER, "
            + COLUMN_INCOME_LOAN + " INTEGER, "
            + COLUMN_INCOME_INVESTMENT + " INTEGER, "
            + COLUMN_INCOME_OTHERS + " INTEGER "
            + ");";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create tables

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
