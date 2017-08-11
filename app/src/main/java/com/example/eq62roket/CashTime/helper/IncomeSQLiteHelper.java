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
    public static final String TABLE_NAME = "INCOMETABLE";
    public static final int DATABASE_VERSION = 3;
    public static final String COL_1 = "ID";
    public static final String COL_2 = "AMOUNT";
    public static final String COL_3 = "SALARY";
    public static final String COL_4 = "LOAN";
    public static final String COL_6 = "INVESTMENT";
    public static final String COL_8 = "OTHERS";

    public IncomeSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT INTEGER, SALARY INTEGER, LOAN INTEGER, INVESTMENT INTEGER, OTHERS INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertSalary(int salary){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues salaryValues = new ContentValues();
        salaryValues.put(COL_3, salary);
        long result = db.insert(TABLE_NAME, null, salaryValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertLoan(int loan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues loanValues = new ContentValues();
        loanValues.put(COL_4, loan);
        long result = db.insert(TABLE_NAME, null, loanValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertInvestment(int investment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues investmentValues = new ContentValues();
        investmentValues.put(COL_6, investment);
        long result = db.insert(TABLE_NAME, null, investmentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertOthers(int others){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues othersValues = new ContentValues();
        othersValues.put(COL_8, others);
        long result = db.insert(TABLE_NAME, null, othersValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public int addAllLoan(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalIncome = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COL_4) + ") FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()){
            totalIncome = cursor.getInt(0);
        }
        cursor.close();
        return totalIncome;
    }

    public int addAllSalary(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalSalary = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COL_3) + ") FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()){
            totalSalary = cursor.getInt(0);
        }
        cursor.close();
        return totalSalary;
    }

    public int addAllInvestment(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalInvestment = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COL_6) + ") FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()){
            totalInvestment = cursor.getInt(0);
        }
        cursor.close();
        return totalInvestment;
    }

    public int addAllOthers(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalOthers = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COL_8) + ") FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()){
            totalOthers = cursor.getInt(0);
        }
        cursor.close();
        return totalOthers;
    }


    public int addAllIncome(){
        int Salary = addAllSalary();
        int Loan = addAllLoan();
        int Investment = addAllInvestment();
        int Others = addAllOthers();

        int totalIncome = Salary + Loan + Investment + Others;

        return totalIncome;
    }

}
