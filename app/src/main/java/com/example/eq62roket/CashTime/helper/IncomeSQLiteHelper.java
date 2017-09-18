package com.example.eq62roket.CashTime.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by eq62roket on 8/3/17.
 */

public class IncomeSQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "INCOME";
    public static final String TABLE_INCOME = "INCOMETABLE";
    public static final String COLUMN_INCOME_ID = "ID";
    public static final String COLUMN_INCOME_SYNCSTATUS = "SYNC_STATUS";
    public static final String COLUMN_INCOME_SALARY = "SALARY";
    public static final String COLUMN_INCOME_LOAN = "LOAN";
    public static final String COLUMN_INCOME_INVESTMENT = "INVESTMENT";
    public static final String COLUMN_INCOME_OTHERS = "OTHERS";
    public static final String COLUMN_INCOME_CREATEDATE = "CREATED_DATE";
    public static final String COLUMN_INCOME_PHPID = "PHPID";


    public IncomeSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_INCOME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT INTEGER, SALARY INTEGER, LOAN INTEGER, INVESTMENT INTEGER, OTHERS INTEGER, SYNC_STATUS INTEGER, CREATED_DATE TIMESTAMP DEFAULT (date('now')))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        onCreate(db);
    }

    public void updateSyncIncome(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_INCOME_SYNCSTATUS, 1);
        db.update(TABLE_INCOME, values, null, null);
        //database.close();
    }

    public int getSyncStatus(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_INCOME_SYNCSTATUS + " FROM " + TABLE_INCOME + " WHERE " + COLUMN_INCOME_SYNCSTATUS + " IS NOT 1 ";
        Cursor cursor = db.rawQuery(query, null);
        int syncStatus = 1;
        if (cursor.moveToFirst()){
            syncStatus = 0;
        }
        cursor.close();
        return  syncStatus;
    }

}
