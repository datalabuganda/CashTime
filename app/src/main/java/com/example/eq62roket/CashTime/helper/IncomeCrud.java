package com.example.eq62roket.CashTime.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.eq62roket.CashTime.models.Income;

import java.util.ArrayList;


import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_INCOME_ID;
import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_INCOME_INVESTMENT;
import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_INCOME_LOAN;
import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_INCOME_OTHERS;
import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_INCOME_SALARY;

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
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_SYNCSTATUS +
                " FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + DatabaseHelper.COLUMN_INCOME_SYNCSTATUS + " IS NOT 1 ";
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
        phpIdValue.put(DatabaseHelper.COLUMN_INCOME_PARSEID, phpId);
        long result = database.insert(DatabaseHelper.TABLE_INCOME, null, phpIdValue);
        if (result == -1)
            return false;
        else
            return true;
    }

    public int getPhpID(){
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_PARSEID +
                " FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + DatabaseHelper.COLUMN_INCOME_PARSEID + " IS NOT 0 ";

        Cursor cursor = database.rawQuery(query, null);
        int phpId = 0;
        if (cursor.moveToFirst()){
            phpId = cursor.getInt(0);
        }
        cursor.close();
        return  phpId;
    }
    public boolean insertSalary(int salary, String period){
        ContentValues salaryValues = new ContentValues();
        salaryValues.put(DatabaseHelper.COLUMN_INCOME_PERIOD, period);
        salaryValues.put(DatabaseHelper.COLUMN_INCOME_SALARY, salary);
        long result = database.insert(DatabaseHelper.TABLE_INCOME, null, salaryValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getSalary(){
        String query = "SELECT rowid _id,* FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + DatabaseHelper.COLUMN_INCOME_SALARY + " IS NOT NULL ";
        Cursor data = database.rawQuery(query, null);
        return  data;
    }

    public ArrayList<Income> getAllSalary(){
        ArrayList<Income> salaryArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + DatabaseHelper.COLUMN_INCOME_SALARY + " IS NOT 0 " +
                " order by " + DatabaseHelper.COLUMN_INCOME_ID + " desc ";
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
        String query = "SELECT " + COLUMN_INCOME_ID +
                " FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + DatabaseHelper.COLUMN_INCOME_SALARY + " = '" + salary + "'";
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    public void updateSalary(String newSalary, int id, int oldSalary){
        String query = "UPDATE " + DatabaseHelper.TABLE_INCOME +
                " SET " + DatabaseHelper.COLUMN_INCOME_SALARY + " = '" + newSalary +
                "' WHERE " + COLUMN_INCOME_ID + " = '" + id + "'" +
                " AND " + DatabaseHelper.COLUMN_INCOME_SALARY + " = '" + oldSalary + "'";
        database.execSQL(query);
    }


    public boolean insertLoan(int loan, String period){
        ContentValues loanValues = new ContentValues();
        loanValues.put(DatabaseHelper.COLUMN_INCOME_LOAN, loan);
        loanValues.put(DatabaseHelper.COLUMN_INCOME_PERIOD, period);
        long result = database.insert(DatabaseHelper.TABLE_INCOME, null, loanValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<Income> getAllLoan(){
        ArrayList<Income> loanArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + DatabaseHelper.COLUMN_INCOME_LOAN + " IS NOT 0 " +
                " order by " + DatabaseHelper.COLUMN_INCOME_ID + " desc ";
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
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_LOAN +
                " FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + DatabaseHelper.COLUMN_INCOME_LOAN + " IS NOT NULL ";
        Cursor data = database.rawQuery(query, null);
        return  data;
    }

    public Cursor getLoanID(String loan){
        String query = "SELECT rowid _id, ID FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + DatabaseHelper.COLUMN_INCOME_LOAN + " = '" + loan + "'";
        Cursor data = database.rawQuery(query, null);
        return data;
    }


    public void updateLoan(String newLoan, int id, int oldLoan){
        String query = "UPDATE " + DatabaseHelper.TABLE_INCOME +
                " SET " + DatabaseHelper.COLUMN_INCOME_LOAN + " = '" + newLoan +
                "' WHERE " + COLUMN_INCOME_ID + " = '" + id + "'" +
                " AND " + COLUMN_INCOME_LOAN + " = '" + oldLoan + "'";
        database.execSQL(query);
    }



    public boolean insertInvestment(int investment, String period){
        ContentValues investmentValues = new ContentValues();
        investmentValues.put(DatabaseHelper.COLUMN_INCOME_PERIOD, period);
        investmentValues.put(DatabaseHelper.COLUMN_INCOME_INVESTMENT, investment);
        long result = database.insert(DatabaseHelper.TABLE_INCOME, null, investmentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<Income> getAllInvestment(){
        ArrayList<Income> investmentArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + DatabaseHelper.COLUMN_INCOME_INVESTMENT + " IS NOT 0 " +
                " order by " + DatabaseHelper.COLUMN_INCOME_ID + " desc ";
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
        String query = "SELECT rowid _id,* FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + DatabaseHelper.COLUMN_INCOME_INVESTMENT + " IS NOT NULL ";
        Cursor data = database.rawQuery(query, null);
        return  data;
    }

    public Cursor getInvestmentID(String investment){
        String query = "SELECT " + COLUMN_INCOME_ID + " FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + DatabaseHelper.COLUMN_INCOME_INVESTMENT + " = '" + investment + "'";
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    public void updateInvestment(String newInvestment, long id, int oldInvestment){
        String query = "UPDATE " + DatabaseHelper.TABLE_INCOME +
                " SET " + DatabaseHelper.COLUMN_INCOME_INVESTMENT + " = '" + newInvestment +
                "' WHERE " + COLUMN_INCOME_ID + " = '" + id + "'" +
                " AND " + DatabaseHelper.COLUMN_INCOME_INVESTMENT + " = '" + oldInvestment + "'";
        database.execSQL(query);
    }



    public boolean insertOthers(int others, String period){
        ContentValues othersValues = new ContentValues();
        othersValues.put(DatabaseHelper.COLUMN_INCOME_OTHERS, others);
        othersValues.put(DatabaseHelper.COLUMN_INCOME_PERIOD, period);
        long result = database.insert(DatabaseHelper.TABLE_INCOME, null, othersValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<Income> getAllOthers(){
        ArrayList<Income> othersArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + DatabaseHelper.COLUMN_INCOME_OTHERS + " IS NOT 0 " +
                " order by " + DatabaseHelper.COLUMN_INCOME_ID + " desc ";
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
        String query = "SELECT rowid _id,* FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + DatabaseHelper.COLUMN_INCOME_OTHERS + " IS NOT 0 ";
       Cursor data = database.rawQuery(query, null);
        return  data;
    }

    public Cursor getOthersID(String others){
        String query = "SELECT " + COLUMN_INCOME_ID + " FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + DatabaseHelper.COLUMN_INCOME_OTHERS + " = '" + others + "'";
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    public void updateOthers(String newOthers, long id, int oldOthers){
        String query = "UPDATE " + DatabaseHelper.TABLE_INCOME +
                " SET " + DatabaseHelper.COLUMN_INCOME_OTHERS + " = '" + newOthers +
                "' WHERE " + COLUMN_INCOME_ID + " = '" + id + "'" +
                " AND " + DatabaseHelper.COLUMN_INCOME_OTHERS + " = '" + oldOthers + "'";
        database.execSQL(query);
    }

    public int addAllLoan(){
        int totalIncome = 0;
        Cursor cursor = database.rawQuery(
                "SELECT SUM(" + (DatabaseHelper.COLUMN_INCOME_LOAN) + ") " +
                        "FROM " + DatabaseHelper.TABLE_INCOME, null);
        if (cursor.moveToFirst()){
            totalIncome = cursor.getInt(0);
        }
        cursor.close();
        return totalIncome;
    }

    public int addAllSalary(){
        int totalSalary = 0;
        Cursor cursor = database.rawQuery(
                "SELECT SUM(" + (DatabaseHelper.COLUMN_INCOME_SALARY) + ") " +
                        "FROM " + DatabaseHelper.TABLE_INCOME, null);
        if (cursor.moveToFirst()){
            totalSalary = cursor.getInt(0);
        }
        cursor.close();
        return totalSalary;
    }

    public int addAllInvestment(){
        int totalInvestment = 0;
        Cursor cursor = database.rawQuery(
                "SELECT SUM(" + (DatabaseHelper.COLUMN_INCOME_INVESTMENT) + ") " +
                        "FROM " + DatabaseHelper.TABLE_INCOME, null);
        if (cursor.moveToFirst()){
            totalInvestment = cursor.getInt(0);
        }
        cursor.close();
        return totalInvestment;
    }

    public int addAllOthers(){
        int totalOthers = 0;
        Cursor cursor = database.rawQuery(
                "SELECT SUM(" + (DatabaseHelper.COLUMN_INCOME_OTHERS) + ") " +
                        "FROM " + DatabaseHelper.TABLE_INCOME, null);
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


    /////////////// Methods to retrieve last inserted dates in each column ////////////////////

    public String getSalaryDate(){
        String lastSalaryInsertedDate;
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_CREATEDATE +
                " FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + COLUMN_INCOME_SALARY + " IS NOT 0 " +
                " order by " + DatabaseHelper.COLUMN_INCOME_ID + " desc " + " limit 1";
        Cursor data = database.rawQuery(query, null);
        if (data.moveToLast()) {
            lastSalaryInsertedDate = data.getString(0);
            return lastSalaryInsertedDate;
        }
        else
            return null;
    }

    public String getSalaryPeriod(){
        String lastSalaryInsertedDate;
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_PERIOD +
                " FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + COLUMN_INCOME_SALARY + " IS NOT 0 " +
                " order by " + DatabaseHelper.COLUMN_INCOME_ID + " desc " + " limit 1";
        Cursor data = database.rawQuery(query, null);
        if (data.moveToLast()) {
            lastSalaryInsertedDate = data.getString(0);
            return lastSalaryInsertedDate;
        }
        else
            return null;
    }

    public String getInvestmentDate(){
        String lastInvestmentInsertedDate;
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_CREATEDATE +
                " FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + COLUMN_INCOME_INVESTMENT + " IS NOT 0 " +
                " order by " + DatabaseHelper.COLUMN_INCOME_ID + " desc " + " limit 1";
        Cursor data = database.rawQuery(query, null);
        if (data.moveToLast()) {
            lastInvestmentInsertedDate = data.getString(0);
            return lastInvestmentInsertedDate;
        }
        else
            return null;
    }

    public String getInvestmentPeriod(){
        String lastInvestmentInsertedDate;
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_PERIOD +
                " FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + COLUMN_INCOME_INVESTMENT + " IS NOT 0 " +
                " order by " + DatabaseHelper.COLUMN_INCOME_ID + " desc " + " limit 1";
        Cursor data = database.rawQuery(query, null);
        if (data.moveToLast()) {
            lastInvestmentInsertedDate = data.getString(0);
            return lastInvestmentInsertedDate;
        }
        else
            return null;
    }

    public String getLoanDate(){
        String lastLoanInsertedDate;
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_CREATEDATE +
                " FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + COLUMN_INCOME_LOAN + " IS NOT 0 " +
                " order by " + DatabaseHelper.COLUMN_INCOME_ID + " desc " + " limit 1";
        Cursor data = database.rawQuery(query, null);
        if (data.moveToLast()) {
            lastLoanInsertedDate = data.getString(0);
            return lastLoanInsertedDate;
        }
        else
            return null;

    }

    public String getLoanPeriod(){
        String lastLoanInsertedDate;
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_PERIOD +
                " FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + COLUMN_INCOME_LOAN + " IS NOT 0 " +
                " order by " + DatabaseHelper.COLUMN_INCOME_ID + " desc " + " limit 1";
        Cursor data = database.rawQuery(query, null);
        if (data.moveToLast()) {
            lastLoanInsertedDate = data.getString(0);
            return lastLoanInsertedDate;
        }
        else
            return null;
    }

    public String getOthersDate(){
        String lastOthersInsertedDate;
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_CREATEDATE +
                " FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + COLUMN_INCOME_OTHERS + " IS NOT 0 " +
                " order by " + DatabaseHelper.COLUMN_INCOME_ID + " desc " + " limit 1";
        Cursor data = database.rawQuery(query, null);
        if (data.moveToLast()) {
            lastOthersInsertedDate = data.getString(0);
            return lastOthersInsertedDate;
        }
        else
            return null;
    }

    public String getOthersPeriod(){
        String lastOthersInsertedDate;
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_PERIOD +
                " FROM " + DatabaseHelper.TABLE_INCOME +
                " WHERE " + COLUMN_INCOME_OTHERS + " IS NOT 0 " +
                " order by " + DatabaseHelper.COLUMN_INCOME_ID + " desc " + " limit 1";
        Cursor data = database.rawQuery(query, null);
        if (data.moveToLast()) {
            lastOthersInsertedDate = data.getString(0);
            return lastOthersInsertedDate;
        }
        else
            return null;
    }


    /////////////// Methods to retrieve last inserted items in each column ////////////////////

    public int getLastInsertedOthers(){
        int lastInsertedOthers;
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_OTHERS + " FROM " + DatabaseHelper.TABLE_INCOME + " order by " + DatabaseHelper.COLUMN_INCOME_ID + " desc " + " limit 1";
        Cursor data = database.rawQuery(query, null);
        data.moveToLast();
        lastInsertedOthers = data.getInt(0);
        return  lastInsertedOthers;
    }

    public int getLastInsertedSalary(){
        int lastInsertedSalary;
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_SALARY + " FROM " + DatabaseHelper.TABLE_INCOME + " order by " + DatabaseHelper.COLUMN_INCOME_ID + " desc " + " limit 1";
        Cursor data = database.rawQuery(query, null);
        data.moveToLast();
        lastInsertedSalary = data.getInt(0);
        return  lastInsertedSalary;
    }

    public String getLastInsertedSalaryPeriod(){
        String lastInsertedSalaryPeriod;
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_PERIOD + " FROM " + DatabaseHelper.TABLE_INCOME + " order by " + DatabaseHelper.COLUMN_INCOME_ID + " desc " + " limit 1";
        Cursor data = database.rawQuery(query, null);
        data.moveToLast();
        lastInsertedSalaryPeriod = data.getString(0);
        return  lastInsertedSalaryPeriod;
    }

    public int getLastInserteInvestment(){
        int lastInsertedInvestment;
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_INVESTMENT + " FROM " + DatabaseHelper.TABLE_INCOME + " order by " + DatabaseHelper.COLUMN_INCOME_ID + " desc " + " limit 1";
        Cursor data = database.rawQuery(query, null);
        data.moveToLast();
        lastInsertedInvestment = data.getInt(0);
        return  lastInsertedInvestment;
    }

    public int getLastInserteLoan(){
        int lastInsertedLoan;
        String query = "SELECT " + DatabaseHelper.COLUMN_INCOME_LOAN + " FROM " + DatabaseHelper.TABLE_INCOME + " order by " + DatabaseHelper.COLUMN_INCOME_ID + " desc " + " limit 1";
        Cursor data = database.rawQuery(query, null);
        data.moveToLast();
        lastInsertedLoan = data.getInt(0);
        return  lastInsertedLoan;
    }

}
