package com.cashtime.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cashtime.models.Saving;

import java.util.ArrayList;

/**
 * Created by cashTime on 7/29/17.
 */

public class SavingCrud2 {
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private Context mContext;

    /*public SavingCrud2(Context context) {
        this.mContext = context;
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getReadableDatabase();
    }

    public void createSaving(Saving saving){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TOTAL_SAVING, saving.getTotalSaving());

        database.insert(DatabaseHelper.TABLE_SAVING, null, values);
        //database.close();
    }

    public void updateSaving(Saving saving){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TOTAL_SAVING, saving.getTotalSaving());

        database.update(
                DatabaseHelper.TABLE_SAVING,
                values,
                DatabaseHelper.COLUMN_SAVING_ID + " = ?",
                new String[]{String.valueOf(saving.getId())});
        database.close();
    }

    public void deleteSaving(Saving saving){
        database.delete(
                DatabaseHelper.TABLE_SAVING,
                DatabaseHelper.COLUMN_SAVING_ID + " = ?",
                new String[]{String.valueOf(saving.getId())});
        database.close();
    }

    public ArrayList<Saving> getAllSavings(){
        Cursor cursor = database.query(DatabaseHelper.TABLE_SAVING, null, null, null, null, null, null, null);

        ArrayList<Saving> savings = new ArrayList<Saving>();
        Saving saving;
        if (cursor.getCount() > 0){
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                saving = new Saving();
                saving.setId(cursor.getInt(0));
                saving.setTotalSaving(cursor.getInt(1));

                savings.add(saving);
            }
        }
        cursor.close();
        database.close();
        return savings;
    }*/
}