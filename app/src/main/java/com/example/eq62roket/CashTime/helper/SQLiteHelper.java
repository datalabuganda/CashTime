package com.example.eq62roket.CashTime.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.eq62roket.CashTime.models.Expenditure;

/**
 * Created by eq62roket on 8/2/17.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLiteHelper";

    public static final String DATABASE_NAME = "EXPENDITURE";
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
    public static final String COLUMN_EXPENDITURE_PHPID = "PHPID";



    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 9);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_EXPENDITURE +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT INTEGER, TRANSPORT INTEGER, EDUCATION INTEGER, HEALTH INTEGER, SAVINGS INTEGER, OTHERS INTEGER, HOMENEEDS INTEGER, DATEINSERTED TIMESTAMP DEFAULT (datetime('now', 'localtime')), SYNCSTATUS INTEGER DEFAULT 0, PHPID INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EXPENDITURE);
        onCreate(db);
    }

    public void updateSyncExpenditure(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXPENDITURE_SYNCSTATUS, 1);
        db.update(TABLE_EXPENDITURE, values, null, null);
        //database.close();
    }

    public int getSyncStatus(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_EXPENDITURE_SYNCSTATUS + " FROM " + TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_SYNCSTATUS + " IS NOT 1 ";
        Cursor cursor = db.rawQuery(query, null);
        int syncStatus = 1;
        if (cursor.moveToFirst()){
            syncStatus = 0;
        }
        cursor.close();
        return  syncStatus;
    }

    public boolean insertPhpId(int phpId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues phpIdValue = new ContentValues();
        phpIdValue.put(COLUMN_EXPENDITURE_PHPID, phpId);
        long result = db.insert(TABLE_EXPENDITURE, null, phpIdValue);
        if (result == -1)
            return false;
        else
            return true;
    }

    public int getPhpID(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_EXPENDITURE_PHPID + " FROM " + TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_PHPID + " IS NOT NULL ";
        Cursor cursor = db.rawQuery(query, null);
        int phpId = 0;
        if (cursor.moveToFirst()){
            phpId = cursor.getInt(0);
        }
        cursor.close();
        return  phpId;
    }

    public Expenditure getLastInsertedSaving() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "select * from " + TABLE_EXPENDITURE +
                " where " + COLUMN_EXPENDITURE_SAVINGS + " <> 0" +
                " order by " + COLUMN_EXPENDITURE_ID + " desc " +
                " limit 1";
        Cursor cursor = db.rawQuery(selectQuery, null);

        Expenditure expenditure = new Expenditure();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            expenditure.setId(cursor.getLong(0));
        }
        return expenditure;

    }

    public boolean insertTransport(int transport){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EXPENDITURE_TRANSPORT, transport);
        long result = db.insert(TABLE_EXPENDITURE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertEducation(int amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues educationValues = new ContentValues();
        educationValues.put(COLUMN_EXPENDITURE_EDUCATION, amount);
        long result = db.insert(TABLE_EXPENDITURE, null, educationValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getEducation(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_EXPENDITURE_EDUCATION + " FROM " + TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_EDUCATION + " IS NOT NULL ";
        Cursor data = db.rawQuery(query, null);
        return  data;
    }

    public Cursor getEducationID(String education){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_EXPENDITURE_ID + " FROM " + TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_EDUCATION + " = '" + education + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateEducation(String newEducation, long id, int oldEducation){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_EXPENDITURE + " SET " + COLUMN_EXPENDITURE_EDUCATION + " = '" + newEducation + "' WHERE " + COLUMN_EXPENDITURE_ID + " = '" + id + "'" + " AND " + COLUMN_EXPENDITURE_EDUCATION + " = '" + oldEducation + "'";

        Log.d(TAG, "updateEducation: query: " + query);
        Log.d(TAG, "updateEducation: Setting education to " + newEducation);
        db.execSQL(query);
    }


    public boolean insertHealth(int health){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues healthValues = new ContentValues();
        healthValues.put(COLUMN_EXPENDITURE_HEALTH, health);
        long result = db.insert(TABLE_EXPENDITURE, null, healthValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getHealth(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_EXPENDITURE_HEALTH + " FROM " + TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_HEALTH + " IS NOT NULL ";
        Cursor data = db.rawQuery(query, null);
        return  data;
    }

    public Cursor getSavings(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_EXPENDITURE_SAVINGS + " FROM " + TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_SAVINGS + " IS NOT NULL ";
        Cursor data = db.rawQuery(query, null);
        return  data;
    }

    public Cursor getSavingsID(String savings){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_EXPENDITURE_ID + " FROM " + TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_SAVINGS + " = '" + savings + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    public void updateSavings(String newSavings, int id, String oldSavings){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_EXPENDITURE + " SET " + COLUMN_EXPENDITURE_SAVINGS + " = '" + newSavings + "' WHERE " + COLUMN_EXPENDITURE_ID + " = '" + id + "'" + " AND " + COLUMN_EXPENDITURE_SAVINGS + " = '" + oldSavings + "'";

        Log.d(TAG, "updateSavings: query: " + query);
        Log.d(TAG, "updateSavings: Setting savings to " + newSavings);
        db.execSQL(query);
    }

    public Cursor getHealthID(String health){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_EXPENDITURE_ID + " FROM " + TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_HEALTH + " = '" + health + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateHealth(String newHealth, int id, String oldHealth){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_EXPENDITURE + " SET " + COLUMN_EXPENDITURE_HEALTH + " = '" + newHealth + "' WHERE " + COLUMN_EXPENDITURE_ID + " = '" + id + "'" + " AND " + COLUMN_EXPENDITURE_HEALTH + " = '" + oldHealth + "'";

        Log.d(TAG, "updateHealth: query: " + query);
        Log.d(TAG, "updateHealth: Setting health to " + newHealth);
        db.execSQL(query);
    }

    public boolean insertSavings(int savings){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues savingsValues = new ContentValues();
        savingsValues.put(COLUMN_EXPENDITURE_SAVINGS, savings);
        long result = db.insert(TABLE_EXPENDITURE, null, savingsValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public void updateSavings(Expenditure expenditure){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues savingsValues = new ContentValues();
        savingsValues.put(COLUMN_EXPENDITURE_SAVINGS, expenditure.getSavings());
        db.update(TABLE_EXPENDITURE, savingsValues, null, null);

    }

    public boolean insertOthers(int others){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues othersValues = new ContentValues();
        othersValues.put(COLUMN_EXPENDITURE_OTHERS, others);
        long result = db.insert(TABLE_EXPENDITURE, null, othersValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getOthers(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_EXPENDITURE_OTHERS + " FROM " + TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_OTHERS + " IS NOT NULL ";
        Cursor data = db.rawQuery(query, null);
        return  data;
    }

    public Cursor getOthersID(String transport){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_EXPENDITURE_ID + " FROM " + TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_TRANSPORT + " = '" + transport + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateOthers(String newOthers, int id, String oldOthers){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_EXPENDITURE + " SET " + COLUMN_EXPENDITURE_OTHERS + " = '" + newOthers + "' WHERE " + COLUMN_EXPENDITURE_ID + " = '" + id + "'" + " AND " + COLUMN_EXPENDITURE_OTHERS + " = '" + oldOthers + "'";

        Log.d(TAG, "updateOthers: query: " + query);
        Log.d(TAG, "updateOthers: Setting others to " + newOthers);
        db.execSQL(query);
    }

    public boolean insertHomeneeds(int homeneeds){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues homeneedsValues = new ContentValues();
        homeneedsValues.put(COLUMN_EXPENDITURE_HOMENEEDS, homeneeds);
        long result = db.insert(TABLE_EXPENDITURE, null, homeneedsValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getHomeneeds(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_EXPENDITURE_HOMENEEDS + " FROM " + TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_HOMENEEDS + " IS NOT NULL ";
        Cursor data = db.rawQuery(query, null);
        return  data;
    }

    public Cursor getHomeneedsID(String homeneeds){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_EXPENDITURE_ID + " FROM " + TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_HOMENEEDS + " = '" + homeneeds + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateHomeneeds(String newHomeneeds, int id, String oldHomeneeds){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_EXPENDITURE + " SET " + COLUMN_EXPENDITURE_HOMENEEDS + " = '" + newHomeneeds + "' WHERE " + COLUMN_EXPENDITURE_ID + " = '" + id + "'" + " AND " + COLUMN_EXPENDITURE_HOMENEEDS + " = '" + oldHomeneeds + "'";

        Log.d(TAG, "updateHomeneeds: query: " + query);
        Log.d(TAG, "updateHomeneeds: Setting homeneeds to " + newHomeneeds);
        db.execSQL(query);
    }



    public int addAllTransport(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalTransport = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COLUMN_EXPENDITURE_TRANSPORT) + ") FROM " + TABLE_EXPENDITURE, null);
        if (cursor.moveToFirst()){
            totalTransport = cursor.getInt(0);
        }
        cursor.close();
        return totalTransport;
    }

    public Cursor getTransport(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_EXPENDITURE_TRANSPORT + " FROM " + TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_TRANSPORT + " IS NOT NULL ";
        Cursor data = db.rawQuery(query, null);
        return  data;
    }

    public Cursor getTransportID(String transport){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_EXPENDITURE_ID + " FROM " + TABLE_EXPENDITURE + " WHERE " + COLUMN_EXPENDITURE_TRANSPORT + " = '" + transport + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateTransport(String newTransport, int id, String oldTransport){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_EXPENDITURE + " SET " + COLUMN_EXPENDITURE_TRANSPORT + " = '" + newTransport + "' WHERE " + COLUMN_EXPENDITURE_ID + " = '" + id + "'" + " AND " + COLUMN_EXPENDITURE_TRANSPORT + " = '" + oldTransport + "'";

        Log.d(TAG, "updateTransport: query: " + query);
        Log.d(TAG, "updateTransport: Setting transport to " + newTransport);
        db.execSQL(query);
    }

    public int addAllEducation(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalEducation = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COLUMN_EXPENDITURE_EDUCATION) + ") FROM " + TABLE_EXPENDITURE, null);
        if (cursor.moveToFirst()){
            totalEducation = cursor.getInt(0);
        }
        cursor.close();
        return totalEducation;
    }

    public int addAllHealth(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalHealth = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COLUMN_EXPENDITURE_HEALTH) + ") FROM " + TABLE_EXPENDITURE, null);
        if (cursor.moveToFirst()){
            totalHealth = cursor.getInt(0);
        }
        cursor.close();
        return totalHealth;
    }

    public int addAllSavings(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalSavings = 0;

        String where = "";
        if( date != null) {
            where = " WHERE " + COLUMN_EXPENDITURE_INSERTDATE + " >= Datetime('" + date + "')";
        }

        Cursor cursor = db.rawQuery("SELECT SUM(" + (COLUMN_EXPENDITURE_SAVINGS) + ")" +
                " FROM " + TABLE_EXPENDITURE + where , null);
        if (cursor.moveToFirst()){
            totalSavings = cursor.getInt(0);
        }
        cursor.close();
        return totalSavings;
    }

    public int addAllOthers(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalOthers = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COLUMN_EXPENDITURE_OTHERS) + ") FROM " + TABLE_EXPENDITURE, null);
        if (cursor.moveToFirst()){
            totalOthers = cursor.getInt(0);
        }
        cursor.close();
        return totalOthers;
    }

    public int addAllHomeneeds(){
        SQLiteDatabase db = this.getReadableDatabase();
        int totalHomeneeds = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + (COLUMN_EXPENDITURE_HOMENEEDS) + ") FROM " + TABLE_EXPENDITURE, null);
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
