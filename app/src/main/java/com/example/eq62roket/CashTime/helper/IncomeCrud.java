package com.example.eq62roket.CashTime.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eq62roket.CashTime.models.Income;

import java.util.ArrayList;

import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_INCOME_ID;
import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_INCOME_LOAN;

/**
 * Created by CASHTIME on 8/5/17.
 */

public class IncomeCrud {

    private static final String TAG = "IncomeCrud";

    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private Context context;

    public IncomeCrud(Context context) {
        this.context = context;
        database = DatabaseHelper.getInstance(context);
    }


    public void updateSyncIncome(){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_INCOME_SYNCSTATUS, 1);
        database.update(DatabaseHelper.TABLE_INCOME, values, null, null);
        //database.close();
    }

    public int getSyncStatus(){
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_SYNCSTATUS + " FROM " + DatabaseHelper.TABLE_INCOME + " WHERE " + DatabaseHelper.COLUMN_INCOME_SYNCSTATUS + " IS NOT 1 ";
        Cursor cursor = database.rawQuery(query, null);
        int syncStatus = 1;
        if (cursor.moveToFirst()){
            syncStatus = 0;
        }
        cursor.close();
        return  syncStatus;
    }


    public boolean insertPhpId(int phpId){
        ContentValues phpIdValue = new ContentValues();
        phpIdValue.put(DatabaseHelper.COLUMN_INCOME_PHPID, phpId);
        long result = database.insert(DatabaseHelper.TABLE_INCOME, null, phpIdValue);
        if (result == -1)
            return false;
        else
            return true;
    }

    public int getPhpID(){
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_PHPID + " FROM " + DatabaseHelper.TABLE_INCOME + " WHERE " + DatabaseHelper.COLUMN_INCOME_PHPID + " IS NOT NULL ";
        Cursor cursor = database.rawQuery(query, null);
        int phpId = 0;
        if (cursor.moveToFirst()){
            phpId = cursor.getInt(0);
        }
        cursor.close();
        return  phpId;
    }
    public boolean insertSalary(int salary){
        ContentValues salaryValues = new ContentValues();
        salaryValues.put(DatabaseHelper.COLUMN_INCOME_SALARY, salary);
        long result = database.insert(DatabaseHelper.TABLE_INCOME, null, salaryValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public Cursor getSalary(){
        String query = "SELECT rowid _id,* FROM " + DatabaseHelper.TABLE_INCOME + " WHERE " + DatabaseHelper.COLUMN_INCOME_SALARY + " IS NOT NULL ";
        Cursor data = database.rawQuery(query, null);
        return  data;
    }

    public ArrayList<Income> getAllSalary(){
        ArrayList<Income> salaryArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_INCOME + " WHERE " + DatabaseHelper.COLUMN_INCOME_SALARY + " IS NOT NULL ";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() > 0){
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                Income income = new Income();
                income.setId(cursor.getLong(0));
                income.setSalary(cursor.getInt(3));
                income.setCreatedDate(cursor.getString(7));

                salaryArrayList.add(income);

            }
        }
        return salaryArrayList;

    }


    public Cursor getSalaryID(String salary){
        String query = "SELECT " + COLUMN_INCOME_ID + " FROM " + DatabaseHelper.TABLE_INCOME + " WHERE " + DatabaseHelper.COLUMN_INCOME_SALARY + " = '" + salary + "'";
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    public void updateSalary(String newSalary, int id, int oldSalary){
        String query = "UPDATE " + DatabaseHelper.TABLE_INCOME + " SET " + DatabaseHelper.COLUMN_INCOME_SALARY + " = '" + newSalary + "' WHERE " + COLUMN_INCOME_ID + " = '" + id + "'" + " AND " + DatabaseHelper.COLUMN_INCOME_SALARY + " = '" + oldSalary + "'";

        Log.d(TAG, "updateSalary: query: " + query);
        Log.d(TAG, "updateSalary: Setting salary to " + newSalary);
        database.execSQL(query);
    }



    public boolean insertLoan(int loan){
        ContentValues loanValues = new ContentValues();
        loanValues.put(DatabaseHelper.COLUMN_INCOME_LOAN, loan);
        long result = database.insert(DatabaseHelper.TABLE_INCOME, null, loanValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<Income> getAllLoan(){
        ArrayList<Income> loanArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_INCOME + " WHERE " + DatabaseHelper.COLUMN_INCOME_LOAN + " IS NOT NULL ";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() > 0){
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                Income income = new Income();
                income.setId(cursor.getLong(0));
                income.setLoan(cursor.getInt(4));
                income.setCreatedDate(cursor.getString(7));

                loanArrayList.add(income);

            }
        }
        return loanArrayList;

    }

    public Cursor getLoan(){
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_LOAN + " FROM " + DatabaseHelper.TABLE_INCOME + " WHERE " + DatabaseHelper.COLUMN_INCOME_LOAN + " IS NOT NULL ";
        Cursor data = database.rawQuery(query, null);
        return  data;
    }

    public Cursor getLoanID(String loan){
        String query = "SELECT rowid _id, ID FROM " + DatabaseHelper.TABLE_INCOME + " WHERE " + DatabaseHelper.COLUMN_INCOME_LOAN + " = '" + loan + "'";        Cursor data = database.rawQuery(query, null);
        return data;
    }


    public void updateLoan(String newLoan, int id, int oldLoan){
        String query = "UPDATE " + DatabaseHelper.TABLE_INCOME + " SET " + DatabaseHelper.COLUMN_INCOME_LOAN + " = '" + newLoan + "' WHERE " + COLUMN_INCOME_ID + " = '" + id + "'" + " AND " + COLUMN_INCOME_LOAN + " = '" + oldLoan + "'";
        Log.d(TAG, "updateLoan: query: " + query);
        Log.d(TAG, "updateLoan: Setting salary to " + newLoan);
        database.execSQL(query);
    }



    public boolean insertInvestment(int investment){
        ContentValues investmentValues = new ContentValues();
        investmentValues.put(DatabaseHelper.COLUMN_INCOME_INVESTMENT, investment);
        long result = database.insert(DatabaseHelper.TABLE_INCOME, null, investmentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<Income> getAllInvestment(){
        ArrayList<Income> investmentArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_INCOME + " WHERE " + DatabaseHelper.COLUMN_INCOME_INVESTMENT + " IS NOT NULL ";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() > 0){
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                Income income = new Income();
                income.setId(cursor.getLong(0));
                income.setInvestment(cursor.getInt(5));
                income.setCreatedDate(cursor.getString(7));

                investmentArrayList.add(income);

            }
        }
        return investmentArrayList;

    }

    public Cursor getInvestment(){
        String query = "SELECT rowid _id,* FROM " + DatabaseHelper.TABLE_INCOME + " WHERE " + DatabaseHelper.COLUMN_INCOME_INVESTMENT + " IS NOT NULL ";
        Cursor data = database.rawQuery(query, null);
        return  data;
    }

    public Cursor getInvestmentID(String investment){
        String query = "SELECT " + COLUMN_INCOME_ID + " FROM " + DatabaseHelper.TABLE_INCOME + " WHERE " + DatabaseHelper.COLUMN_INCOME_INVESTMENT + " = '" + investment + "'";
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    public void updateInvestment(String newInvestment, long id, int oldInvestment){
        String query = "UPDATE " + DatabaseHelper.TABLE_INCOME + " SET " + DatabaseHelper.COLUMN_INCOME_INVESTMENT + " = '" + newInvestment + "' WHERE " + COLUMN_INCOME_ID + " = '" + id + "'" + " AND " + DatabaseHelper.COLUMN_INCOME_INVESTMENT + " = '" + oldInvestment + "'";

        Log.d(TAG, "updateInvestment: query: " + query);
        Log.d(TAG, "updateInvestment: Setting salary to " + newInvestment);
        database.execSQL(query);
    }



    public boolean insertOthers(int others){
        ContentValues othersValues = new ContentValues();
        othersValues.put(DatabaseHelper.COLUMN_INCOME_OTHERS, others);
        long result = database.insert(DatabaseHelper.TABLE_INCOME, null, othersValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<Income> getAllOthers(){
        ArrayList<Income> othersArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_INCOME + " WHERE " + DatabaseHelper.COLUMN_INCOME_OTHERS + " IS NOT NULL ";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() > 0){
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                Income income = new Income();
                income.setId(cursor.getLong(0));
                income.setOther(cursor.getInt(6));
                income.setCreatedDate(cursor.getString(7));

                othersArrayList.add(income);

            }
        }
        return othersArrayList;

    }


    public Cursor getOthers(){
        String query = "SELECT rowid _id,* FROM " + DatabaseHelper.TABLE_INCOME + " WHERE " + DatabaseHelper.COLUMN_INCOME_OTHERS + " IS NOT NULL ";
       Cursor data = database.rawQuery(query, null);
        return  data;
    }

    public Cursor getOthersID(String others){
        String query = "SELECT " + COLUMN_INCOME_ID + " FROM " + DatabaseHelper.TABLE_INCOME + " WHERE " + DatabaseHelper.COLUMN_INCOME_OTHERS + " = '" + others + "'";
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    public void updateOthers(String newOthers, long id, int oldOthers){
        String query = "UPDATE " + DatabaseHelper.TABLE_INCOME + " SET " + DatabaseHelper.COLUMN_INCOME_OTHERS + " = '" + newOthers + "' WHERE " + COLUMN_INCOME_ID + " = '" + id + "'" + " AND " + DatabaseHelper.COLUMN_INCOME_OTHERS + " = '" + oldOthers + "'";

        Log.d(TAG, "updateOthers: query: " + query);
        Log.d(TAG, "updateOthers: Setting others to " + newOthers);
        database.execSQL(query);
    }



    public int addAllLoan(){
        int totalIncome = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(" + (DatabaseHelper.COLUMN_INCOME_LOAN) + ") FROM " + DatabaseHelper.TABLE_INCOME, null);
        if (cursor.moveToFirst()){
            totalIncome = cursor.getInt(0);
        }
        cursor.close();
        return totalIncome;
    }

    public int addAllSalary(){
        int totalSalary = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(" + (DatabaseHelper.COLUMN_INCOME_SALARY) + ") FROM " + DatabaseHelper.TABLE_INCOME, null);
        if (cursor.moveToFirst()){
            totalSalary = cursor.getInt(0);
        }
        cursor.close();
        return totalSalary;
    }

    public int addAllInvestment(){
        int totalInvestment = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(" + (DatabaseHelper.COLUMN_INCOME_INVESTMENT) + ") FROM " + DatabaseHelper.TABLE_INCOME, null);
        if (cursor.moveToFirst()){
            totalInvestment = cursor.getInt(0);
        }
        cursor.close();
        return totalInvestment;
    }

    public int addAllOthers(){
        int totalOthers = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(" + (DatabaseHelper.COLUMN_INCOME_OTHERS) + ") FROM " + DatabaseHelper.TABLE_INCOME, null);
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
