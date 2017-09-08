package com.example.eq62roket.CashTime.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eq62roket.CashTime.models.Expenditure;

/**
 * Created by cashtime on 8/12/17.
 */

public class ExpenditureCrud {
    private static final String TAG = "ExpenditureCrud";

    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private Context context;

    public ExpenditureCrud(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public void updateSyncExpenditure(){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EXPENDITURE_SYNCSTATUS, 1);
        database.update(DatabaseHelper.TABLE_EXPENDITURE, values, null, null);
        //database.close();
    }

    public int getSyncStatus(){
        String query = "SELECT " + DatabaseHelper.COLUMN_EXPENDITURE_SYNCSTATUS + " FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + DatabaseHelper.COLUMN_EXPENDITURE_SYNCSTATUS + " IS NOT 1 ";
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
        phpIdValue.put(DatabaseHelper.COLUMN_EXPENDITURE_PHPID, phpId);
        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, phpIdValue);
        if (result == -1)
            return false;
        else
            return true;
    }

    public int getPhpID(){
        String query = "SELECT " + DatabaseHelper.COLUMN_EXPENDITURE_PHPID + " FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + DatabaseHelper.COLUMN_EXPENDITURE_PHPID + " IS NOT NULL ";
        Cursor cursor = database.rawQuery(query, null);
        int phpId = 0;
        if (cursor.moveToFirst()){
            phpId = cursor.getInt(0);
        }
        cursor.close();
        return  phpId;
    }

    public Expenditure getLastInsertedSaving() {
        String selectQuery = "select * from " + DatabaseHelper.TABLE_EXPENDITURE +
                " where " + DatabaseHelper.COLUMN_EXPENDITURE_SAVINGS + " <> 0" +
                " order by " + DatabaseHelper.COLUMN_EXPENDITURE_ID + " desc " +
                " limit 1";
        Cursor cursor = database.rawQuery(selectQuery, null);

        Expenditure expenditure = new Expenditure();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            expenditure.setId(cursor.getLong(0));
        }
        return expenditure;

    }

    public boolean insertTransport(int transport){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_EXPENDITURE_TRANSPORT, transport);
        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertEducation(int amount){
        ContentValues educationValues = new ContentValues();
        educationValues.put(DatabaseHelper.COLUMN_EXPENDITURE_EDUCATION, amount);
        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, educationValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertHealth(int health){
        ContentValues healthValues = new ContentValues();
        healthValues.put(DatabaseHelper.COLUMN_EXPENDITURE_HEALTH, health);
        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, healthValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertSavings(int savings){
        ContentValues savingsValues = new ContentValues();
        savingsValues.put(DatabaseHelper.COLUMN_EXPENDITURE_SAVINGS, savings);
        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, savingsValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public void updateSavings(Expenditure expenditure){
        ContentValues savingsValues = new ContentValues();
        savingsValues.put(DatabaseHelper.COLUMN_EXPENDITURE_SAVINGS, expenditure.getSavings());
        database.update(DatabaseHelper.TABLE_EXPENDITURE, savingsValues, null, null);

    }

    public boolean insertOthers(int others){
        ContentValues othersValues = new ContentValues();
        othersValues.put(DatabaseHelper.COLUMN_EXPENDITURE_OTHERS, others);
        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, othersValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertHomeneeds(int homeneeds){
        ContentValues homeneedsValues = new ContentValues();
        homeneedsValues.put(DatabaseHelper.COLUMN_EXPENDITURE_HOMENEEDS, homeneeds);
        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, homeneedsValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public int addAllTransport(){
        int totalTransport = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(" + (DatabaseHelper.COLUMN_EXPENDITURE_TRANSPORT) + ") FROM " + DatabaseHelper.TABLE_EXPENDITURE, null);
        if (cursor.moveToFirst()){
            totalTransport = cursor.getInt(0);
        }
        cursor.close();
        return totalTransport;
    }

    public int addAllEducation(){
        int totalEducation = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(" + (DatabaseHelper.COLUMN_EXPENDITURE_EDUCATION) + ") FROM " + DatabaseHelper.TABLE_EXPENDITURE, null);
        if (cursor.moveToFirst()){
            totalEducation = cursor.getInt(0);
        }
        cursor.close();
        return totalEducation;
    }

    public int addAllHealth(){
        int totalHealth = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(" + (DatabaseHelper.COLUMN_EXPENDITURE_HEALTH) + ") FROM " + DatabaseHelper.TABLE_EXPENDITURE, null);
        if (cursor.moveToFirst()){
            totalHealth = cursor.getInt(0);
        }
        cursor.close();
        return totalHealth;
    }

    public int addAllSavings(String date){
        int totalSavings = 0;

        String where = "";
        if( date != null) {
            where = " WHERE " + DatabaseHelper.COLUMN_EXPENDITURE_INSERTDATE + " >= Datetime('" + date + "')";
        }

        Cursor cursor = database.rawQuery("SELECT SUM(" + (DatabaseHelper.COLUMN_EXPENDITURE_SAVINGS) + ")" +
                " FROM " + DatabaseHelper.TABLE_EXPENDITURE + where , null);
        if (cursor.moveToFirst()){
            totalSavings = cursor.getInt(0);
        }
        cursor.close();
        return totalSavings;
    }

    public int addAllOthers(){
        int totalOthers = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(" + (DatabaseHelper.COLUMN_EXPENDITURE_OTHERS) + ") FROM " + DatabaseHelper.TABLE_EXPENDITURE, null);
        if (cursor.moveToFirst()){
            totalOthers = cursor.getInt(0);
        }
        cursor.close();
        return totalOthers;
    }

    public int addAllHomeneeds(){
        int totalHomeneeds = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(" + (DatabaseHelper.COLUMN_EXPENDITURE_HOMENEEDS) + ") FROM " + DatabaseHelper.TABLE_EXPENDITURE, null);
        if (cursor.moveToFirst()){
            totalHomeneeds = cursor.getInt(0);
        }
        cursor.close();
        return totalHomeneeds;
    }


    public  int addAllCategories(){
        int Savings = addAllSavings(null);
        int Transport = addAllTransport();
        int Medical = addAllHealth();
        int Others = addAllOthers();
        int Homeneeds = addAllHomeneeds();
        int Education = addAllEducation();

        int totalCategory = Savings + Transport + Medical + Others + Homeneeds + Education;

        Log.d(TAG, "Savings: " + Savings);
        return totalCategory;
    }


}
