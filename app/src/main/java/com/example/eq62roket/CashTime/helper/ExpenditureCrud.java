package com.example.eq62roket.CashTime.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eq62roket.CashTime.models.Expenditure;

import java.util.ArrayList;

import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_EXPENDITURE_EDUCATION;
import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_EXPENDITURE_HEALTH;
import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_EXPENDITURE_ID;
import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_EXPENDITURE_OTHERS;
import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_EXPENDITURE_SAVINGS;
import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_EXPENDITURE_TRANSPORT;

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

    public void updateSyncExpenditure(int statusFlag){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EXPENDITURE_SYNCSTATUS, statusFlag);
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
                " where " + COLUMN_EXPENDITURE_SAVINGS + " <> 0" +
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
        contentValues.put(COLUMN_EXPENDITURE_TRANSPORT, transport);
        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

//    public void updateTransport(String newTransport, int id, int oldTransport){
//        String query = "UPDATE " + DatabaseHelper.TABLE_EXPENDITURE + " SET " + COLUMN_EXPENDITURE_TRANSPORT + " = '" + newTransport + "' WHERE " + COLUMN_INCOME_ID + " = '" + id + "'" + " AND " + COLUMN_EXPENDITURE_TRANSPORT + " = '" + oldTransport + "'";
//        Log.d(TAG, "updateTransport: query: " + query);
//        Log.d(TAG, "updateTransport: Setting salary to " + newTransport);
//        database.execSQL(query);
//    }

    public ArrayList<Expenditure> getAllTransport(){
        ArrayList<Expenditure> transportArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_TRANSPORT + " IS NOT NULL ";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() > 0){
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                Expenditure expenditure = new Expenditure();
                expenditure.setId(cursor.getLong(0));
                expenditure.setTransport(cursor.getInt(2));
                expenditure.setExpenditureDate(cursor.getString(8));

                transportArrayList.add(expenditure);

            }
        }
        return transportArrayList;

    }

    public boolean insertEducation(int amount){
        ContentValues educationValues = new ContentValues();
        educationValues.put(COLUMN_EXPENDITURE_EDUCATION, amount);
        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, educationValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public void updateEducation(String newEducation, int id, int oldEducation){
        String query = "UPDATE " + DatabaseHelper.TABLE_EXPENDITURE + " SET " + COLUMN_EXPENDITURE_EDUCATION + " = '" + newEducation + "' WHERE " + COLUMN_EXPENDITURE_ID + " = '" + id + "'" + " AND " + COLUMN_EXPENDITURE_EDUCATION + " = '" + oldEducation + "'";
        Log.d(TAG, "updateEducation: query: " + query);
        Log.d(TAG, "updateEducation: Setting salary to " + newEducation);
        database.execSQL(query);
    }


    public ArrayList<Expenditure> getAllEducation(){
        ArrayList<Expenditure> educationArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_EDUCATION + " IS NOT NULL ";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() > 0){
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                Expenditure expenditure = new Expenditure();
                expenditure.setId(cursor.getLong(0));
                expenditure.setEducation(cursor.getInt(3));
                expenditure.setExpenditureDate(cursor.getString(8));

                educationArrayList.add(expenditure);

            }
        }
        return educationArrayList;

    }

    public Cursor getEducation(){
        String query = "SELECT " + COLUMN_EXPENDITURE_EDUCATION + " FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_EDUCATION + " IS NOT NULL ";
        Cursor data = database.rawQuery(query, null);
        return  data;
    }

    public Cursor getEducationID(String education){
        String query = "SELECT " + DatabaseHelper.COLUMN_EXPENDITURE_ID + " FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_EDUCATION + " = '" + education + "'";
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    public void updateEducation(String newEducation, int id, String oldEducation){
        String query = "UPDATE " + DatabaseHelper.TABLE_EXPENDITURE + " SET " + COLUMN_EXPENDITURE_EDUCATION + " = '" + newEducation + "' WHERE " + DatabaseHelper.COLUMN_EXPENDITURE_ID + " = '" + id + "'" + " AND " + COLUMN_EXPENDITURE_EDUCATION + " = '" + oldEducation + "'";

        Log.d(TAG, "updateEducation: query: " + query);
        Log.d(TAG, "updateEducation: Setting education to " + newEducation);
        database.execSQL(query);
    }


    public boolean insertHealth(int health){
        ContentValues healthValues = new ContentValues();
        healthValues.put(COLUMN_EXPENDITURE_HEALTH, health);
        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, healthValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<Expenditure> getAllHealth(){
        ArrayList<Expenditure> healthArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_HEALTH + " IS NOT NULL ";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() > 0){
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                Expenditure expenditure = new Expenditure();
                expenditure.setId(cursor.getLong(0));
                expenditure.setHealth(cursor.getInt(4));
                expenditure.setExpenditureDate(cursor.getString(8));

                healthArrayList.add(expenditure);

            }
        }
        return healthArrayList;

    }

    public Cursor getHealth(){
        String query = "SELECT " + COLUMN_EXPENDITURE_HEALTH + " FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_HEALTH + " IS NOT NULL ";
        Cursor data = database.rawQuery(query, null);
        return  data;
    }

    public Cursor getSavings(){
        String query = "SELECT " + COLUMN_EXPENDITURE_SAVINGS + " FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_SAVINGS + " IS NOT NULL ";
        Cursor data = database.rawQuery(query, null);
        return  data;
    }

    public void updateSavings(String newSavings, int id, int oldSavings){
        String query = "UPDATE " + DatabaseHelper.TABLE_EXPENDITURE + " SET " + COLUMN_EXPENDITURE_SAVINGS + " = '" + newSavings + "' WHERE " + COLUMN_EXPENDITURE_ID + " = '" + id + "'" + " AND " + COLUMN_EXPENDITURE_SAVINGS + " = '" + oldSavings + "'";
        Log.d(TAG, "updateSavings: query: " + query);
        Log.d(TAG, "updateSavings: Setting salary to " + newSavings);
        database.execSQL(query);
    }

    public ArrayList<Expenditure> getAllSavings(){
        ArrayList<Expenditure> savingsArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_SAVINGS + " IS NOT NULL ";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() > 0){
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                Expenditure expenditure = new Expenditure();
                expenditure.setId(cursor.getLong(0));
                expenditure.setSavings(cursor.getInt(5));
                expenditure.setExpenditureDate(cursor.getString(8));

                savingsArrayList.add(expenditure);

            }
        }
        return savingsArrayList;

    }

    public Cursor getSavingsID(String savings){
        String query = "SELECT " + DatabaseHelper.COLUMN_EXPENDITURE_ID + " FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_SAVINGS + " = '" + savings + "'";
        Cursor data = database.rawQuery(query, null);
        return data;
    }


//    public void updateSavings(String newSavings, int id, String oldSavings){
//        String query = "UPDATE " + DatabaseHelper.TABLE_EXPENDITURE + " SET " + COLUMN_EXPENDITURE_SAVINGS + " = '" + newSavings + "' WHERE " + DatabaseHelper.COLUMN_EXPENDITURE_ID + " = '" + id + "'" + " AND " + COLUMN_EXPENDITURE_SAVINGS + " = '" + oldSavings + "'";
//
//        Log.d(TAG, "updateSavings: query: " + query);
//        Log.d(TAG, "updateSavings: Setting savings to " + newSavings);
//        database.execSQL(query);
//    }

    public Cursor getHealthID(String health){
        String query = "SELECT " + DatabaseHelper.COLUMN_EXPENDITURE_ID + " FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_HEALTH + " = '" + health + "'";
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    public void updateHealth(String newHealth, long id, int oldHealth){
        String query = "UPDATE " + DatabaseHelper.TABLE_EXPENDITURE + " SET " + COLUMN_EXPENDITURE_HEALTH + " = '" + newHealth + "' WHERE " + DatabaseHelper.COLUMN_EXPENDITURE_ID + " = '" + id + "'" + " AND " + COLUMN_EXPENDITURE_HEALTH + " = '" + oldHealth + "'";

        Log.d(TAG, "updateHealth: query: " + query);
        Log.d(TAG, "updateHealth: Setting health to " + newHealth);
        database.execSQL(query);
    }

    public boolean insertSavings(int savings){
        ContentValues savingsValues = new ContentValues();
        savingsValues.put(COLUMN_EXPENDITURE_SAVINGS, savings);
        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, savingsValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public boolean insertOthers(int others){
        ContentValues othersValues = new ContentValues();
        othersValues.put(COLUMN_EXPENDITURE_OTHERS, others);
        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, othersValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public ArrayList<Expenditure> getAllOthers(){
        ArrayList<Expenditure> othersArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_OTHERS + " IS NOT NULL ";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() > 0){
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                Expenditure expenditure = new Expenditure();
                expenditure.setId(cursor.getLong(0));
                expenditure.setOthers(cursor.getInt(6));
                expenditure.setExpenditureDate(cursor.getString(8));

                othersArrayList.add(expenditure);

            }
        }
        return othersArrayList;

    }

    public Cursor getOthers(){
        String query = "SELECT " + COLUMN_EXPENDITURE_OTHERS + " FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_OTHERS + " IS NOT NULL ";
        Cursor data = database.rawQuery(query, null);
        return  data;
    }

    public Cursor getOthersID(String transport){
        String query = "SELECT " + DatabaseHelper.COLUMN_EXPENDITURE_ID + " FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_TRANSPORT + " = '" + transport + "'";
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    public void updateOthers(String newOthers, int id, int oldOthers){
        String query = "UPDATE " + DatabaseHelper.TABLE_EXPENDITURE + " SET " + COLUMN_EXPENDITURE_OTHERS + " = '" + newOthers + "' WHERE " + DatabaseHelper.COLUMN_EXPENDITURE_ID + " = '" + id + "'" + " AND " + COLUMN_EXPENDITURE_OTHERS + " = '" + oldOthers + "'";

        Log.d(TAG, "updateOthers: query: " + query);
        Log.d(TAG, "updateOthers: Setting others to " + newOthers);
        database.execSQL(query);
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


    public ArrayList<Expenditure> getAllHomeneeds(){
        ArrayList<Expenditure> homeneedsArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + DatabaseHelper.COLUMN_EXPENDITURE_HOMENEEDS + " IS NOT NULL ";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() > 0){
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                Expenditure expenditure = new Expenditure();
                expenditure.setId(cursor.getLong(0));
                expenditure.setHomeneeds(cursor.getInt(7));
                expenditure.setExpenditureDate(cursor.getString(8));

                homeneedsArrayList.add(expenditure);

            }
        }
        return homeneedsArrayList;

    }

    public Cursor getHomeneeds(){
        String query = "SELECT " + DatabaseHelper.COLUMN_EXPENDITURE_HOMENEEDS + " FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + DatabaseHelper.COLUMN_EXPENDITURE_HOMENEEDS + " IS NOT NULL ";
        Cursor data = database.rawQuery(query, null);
        return  data;
    }

    public Cursor getHomeneedsID(String homeneeds){
        String query = "SELECT " + DatabaseHelper.COLUMN_EXPENDITURE_ID + " FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + DatabaseHelper.COLUMN_EXPENDITURE_HOMENEEDS + " = '" + homeneeds + "'";
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    public void updateHomeneeds(String newHomeneeds, int id, int oldHomeneeds){
        String query = "UPDATE " + DatabaseHelper.TABLE_EXPENDITURE + " SET " + DatabaseHelper.COLUMN_EXPENDITURE_HOMENEEDS + " = '" + newHomeneeds + "' WHERE " + DatabaseHelper.COLUMN_EXPENDITURE_ID + " = '" + id + "'" + " AND " + DatabaseHelper.COLUMN_EXPENDITURE_HOMENEEDS + " = '" + oldHomeneeds + "'";

        Log.d(TAG, "updateHomeneeds: query: " + query);
        Log.d(TAG, "updateHomeneeds: Setting homeneeds to " + newHomeneeds);
        database.execSQL(query);
    }



    public int addAllTransport(){
        int totalTransport = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(" + (COLUMN_EXPENDITURE_TRANSPORT) + ") FROM " + DatabaseHelper.TABLE_EXPENDITURE, null);
        if (cursor.moveToFirst()){
            totalTransport = cursor.getInt(0);
        }
        cursor.close();
        return totalTransport;
    }

    public Cursor getTransport(){
        String query = "SELECT " + COLUMN_EXPENDITURE_TRANSPORT + " FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_TRANSPORT + " IS NOT NULL ";
        Cursor data = database.rawQuery(query, null);
        return  data;
    }

    public Cursor getTransportID(String transport){
        String query = "SELECT " + DatabaseHelper.COLUMN_EXPENDITURE_ID + " FROM " + DatabaseHelper.TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_TRANSPORT + " = '" + transport + "'";
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    public void updateTransport(String newTransport, int id, int oldTransport){
        String query = "UPDATE " + DatabaseHelper.TABLE_EXPENDITURE + " SET " + COLUMN_EXPENDITURE_TRANSPORT + " = '" + newTransport + "' WHERE " + DatabaseHelper.COLUMN_EXPENDITURE_ID + " = '" + id + "'" + " AND " + COLUMN_EXPENDITURE_TRANSPORT + " = '" + oldTransport + "'";

        Log.d(TAG, "updateTransport: query: " + query);
        Log.d(TAG, "updateTransport: Setting transport to " + newTransport);
        database.execSQL(query);
    }

    public int addAllEducation(){
        int totalEducation = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(" + (COLUMN_EXPENDITURE_EDUCATION) + ") FROM " + DatabaseHelper.TABLE_EXPENDITURE, null);
        if (cursor.moveToFirst()){
            totalEducation = cursor.getInt(0);
        }
        cursor.close();
        return totalEducation;
    }

    public int addAllHealth(){
        int totalHealth = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(" + (COLUMN_EXPENDITURE_HEALTH) + ") FROM " + DatabaseHelper.TABLE_EXPENDITURE, null);
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

        Cursor cursor = database.rawQuery("SELECT SUM(" + (COLUMN_EXPENDITURE_SAVINGS) + ")" +
                " FROM " + DatabaseHelper.TABLE_EXPENDITURE + where , null);
        if (cursor.moveToFirst()){
            totalSavings = cursor.getInt(0);
        }
        cursor.close();
        return totalSavings;
    }

    public int addAllOthers(){
        int totalOthers = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(" + (COLUMN_EXPENDITURE_OTHERS) + ") FROM " + DatabaseHelper.TABLE_EXPENDITURE, null);
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
