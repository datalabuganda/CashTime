package com.example.eq62roket.CashTime.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by eq62roket on 8/3/17.
 */

public class IncomeSQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "INCOME";
    public static final String TABLE_INCOME = "INCOMETABLE";
    public static final int DATABASE_VERSION = 9;
    public static final String COLUMN_INCOME_ID = "ID";
    public static final String COLUMN_INCOME_SYNCSTATUS = "SYNC_STATUS";
    public static final String COLUMN_INCOME_SALARY = "SALARY";
    public static final String COLUMN_INCOME_LOAN = "LOAN";
    public static final String COLUMN_INCOME_INVESTMENT = "INVESTMENT";
    public static final String COLUMN_INCOME_OTHERS = "OTHERS";
    public static final String COLUMN_INCOME_CREATEDATE = "CREATED_DATE";
    public static final String COLUMN_INCOME_PHPID = "PHPID";


    private static final String TAG = "IncomeSQLiteHelper";

    public IncomeSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_INCOME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, SYNC_STATUS INTEGER, AMOUNT INTEGER, SALARY INTEGER, LOAN INTEGER, INVESTMENT INTEGER, OTHERS INTEGER, CREATED_DATE DATETIME, PHPID INTEGER)");

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

    /*public boolean insertSyncStatus(int syncStatus){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues syncStatusValue = new ContentValues();
        syncStatusValue.put(COLUMN_EXPENDITURE_AMOUNT, syncStatus);
        long result = db.insert(TABLE_INCOME, null, syncStatusValue);
        if (result == -1)
            return false;
        else
            return true;
    }

    public int getSyncStatus(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_EXPENDITURE_AMOUNT + " FROM " + TABLE_INCOME + " WHERE " + COLUMN_EXPENDITURE_AMOUNT + " IS NOT NULL ";
        Cursor cursor = db.rawQuery(query, null);
        int syncStatus = 0;
        if (cursor.moveToFirst()){
            syncStatus = cursor.getInt(0);
        }
        cursor.close();
        return  syncStatus;
    }*/

    public boolean insertPhpId(int phpId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues phpIdValue = new ContentValues();
        phpIdValue.put(COLUMN_INCOME_PHPID, phpId);
        long result = db.insert(TABLE_INCOME, null, phpIdValue);
        if (result == -1)
            return false;
        else
            return true;
    }

    public int getPhpID(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_INCOME_PHPID + " FROM " + TABLE_INCOME + " WHERE " + COLUMN_INCOME_PHPID + " IS NOT NULL ";
        Cursor cursor = db.rawQuery(query, null);
        int phpId = 0;
        if (cursor.moveToFirst()){
            phpId = cursor.getInt(0);
        }
        cursor.close();
        return  phpId;
    }
    public boolean insertSalary(int salary){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues salaryValues = new ContentValues();
        salaryValues.put(COLUMN_INCOME_SALARY, salary);
        long result = db.insert(TABLE_INCOME, null, salaryValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public Cursor getSalary(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_INCOME_SALARY + " FROM " + TABLE_INCOME + " WHERE " + COLUMN_INCOME_SALARY + " IS NOT NULL ";
        Cursor data = db.rawQuery(query, null);
        return  data;
    }

    public Cursor getSalaryID(String salary){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_INCOME_ID + " FROM " + TABLE_INCOME + " WHERE " + COLUMN_INCOME_SALARY + " = '" + salary + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateSalary(String newSalary, int id, String oldSalary){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_INCOME + " SET " + COLUMN_INCOME_SALARY + " = '" + newSalary + "' WHERE " + COLUMN_INCOME_ID + " = '" + id + "'" + " AND " + COLUMN_INCOME_SALARY + " = '" + oldSalary + "'";

        Log.d(TAG, "updateSalary: query: " + query);
        Log.d(TAG, "updateSalary: Setting salary to " + newSalary);
        db.execSQL(query);
    }



    public boolean insertLoan(int loan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues loanValues = new ContentValues();
        loanValues.put(COLUMN_INCOME_LOAN, loan);
        long result = db.insert(TABLE_INCOME, null, loanValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getLoan(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_INCOME_LOAN + " FROM " + TABLE_INCOME + " WHERE " + COLUMN_INCOME_LOAN + " IS NOT NULL ";
        Cursor data = db.rawQuery(query, null);
        return  data;
    }

    public Cursor getLoanID(String loan){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_INCOME_ID + " FROM " + TABLE_INCOME + " WHERE " + COLUMN_INCOME_LOAN + " = '" + loan + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateLoan(String newLoan, int id, String oldLoan){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_INCOME + " SET " + COLUMN_INCOME_LOAN + " = '" + newLoan + "' WHERE " + COLUMN_INCOME_ID + " = '" + id + "'" + " AND " + COLUMN_INCOME_LOAN + " = '" + oldLoan + "'";

        Log.d(TAG, "updateLoan: query: " + query);
        Log.d(TAG, "updateLoan: Setting loan to " + newLoan);
        db.execSQL(query);
    }


    public boolean insertInvestment(int investment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues investmentValues = new ContentValues();
        investmentValues.put(COLUMN_INCOME_INVESTMENT, investment);
        long result = db.insert(TABLE_INCOME, null, investmentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getInvestment(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_INCOME_INVESTMENT + " FROM " + TABLE_INCOME + " WHERE " + COLUMN_INCOME_INVESTMENT + " IS NOT NULL ";
        Cursor data = db.rawQuery(query, null);
        return  data;
    }

    public Cursor getInvestmentID(String investment){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_INCOME_ID + " FROM " + TABLE_INCOME + " WHERE " + COLUMN_INCOME_INVESTMENT + " = '" + investment + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateInvestment(String newInvestment, int id, String oldInvestment){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_INCOME + " SET " + COLUMN_INCOME_INVESTMENT + " = '" + newInvestment + "' WHERE " + COLUMN_INCOME_ID + " = '" + id + "'" + " AND " + COLUMN_INCOME_INVESTMENT + " = '" + oldInvestment + "'";

        Log.d(TAG, "updateInvestment: query: " + query);
        Log.d(TAG, "updateInvestment: Setting salary to " + newInvestment);
        db.execSQL(query);
    }



    public boolean insertOthers(int others){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues othersValues = new ContentValues();
        othersValues.put(COLUMN_INCOME_OTHERS, others);
        long result = db.insert(TABLE_INCOME, null, othersValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getOthers(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_INCOME_OTHERS + " FROM " + TABLE_INCOME + " WHERE " + COLUMN_INCOME_OTHERS + " IS NOT NULL ";
        Cursor data = db.rawQuery(query, null);
        return  data;
    }

    public Cursor getOthersID(String others){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_INCOME_ID + " FROM " + TABLE_INCOME + " WHERE " + COLUMN_INCOME_OTHERS + " = '" + others + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateOthers(String newOthers, int id, String oldOthers){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_INCOME + " SET " + COLUMN_INCOME_OTHERS + " = '" + newOthers + "' WHERE " + COLUMN_INCOME_ID + " = '" + id + "'" + " AND " + COLUMN_INCOME_OTHERS + " = '" + oldOthers + "'";

        Log.d(TAG, "updateOthers: query: " + query);
        Log.d(TAG, "updateOthers: Setting others to " + newOthers);
        db.execSQL(query);
    }



    public int addAllLoan(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalIncome = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COLUMN_INCOME_LOAN) + ") FROM " + TABLE_INCOME, null);
        if (cursor.moveToFirst()){
            totalIncome = cursor.getInt(0);
        }
        cursor.close();
        return totalIncome;
    }

    public int addAllSalary(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalSalary = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COLUMN_INCOME_SALARY) + ") FROM " + TABLE_INCOME, null);
        if (cursor.moveToFirst()){
            totalSalary = cursor.getInt(0);
        }
        cursor.close();
        return totalSalary;
    }

    public int addAllInvestment(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalInvestment = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COLUMN_INCOME_INVESTMENT) + ") FROM " + TABLE_INCOME, null);
        if (cursor.moveToFirst()){
            totalInvestment = cursor.getInt(0);
        }
        cursor.close();
        return totalInvestment;
    }

    public int addAllOthers(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalOthers = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COLUMN_INCOME_OTHERS) + ") FROM " + TABLE_INCOME, null);
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
