package com.cashtime.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cashtime.models.Saving;

/**
 * Created by cashTime on 7/29/17.
 */

public class SavingCrud {
    private static final String TAG = "SavingCrud";

    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDatabaseHelper;
    private Context mContext;
    /*private String[] mAllColumns = {
            DatabaseHelper.COLUMN_SAVING_ID,
            DatabaseHelper.COLUMN_TOTAL_SAVING,
            DatabaseHelper.COLUMN_SAVING_GOAL_ID
    };

    public SavingCrud(Context context) {
        this.mContext = context;
        mDatabaseHelper = new DatabaseHelper(context);

        // open the database
        try {
            open();
        } catch (SQLException e){
            Log.e(TAG, "SQLException on opening the database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException{
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void close(){
        mDatabaseHelper.close();
    }

    public Saving createSaving(int total){
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_TOTAL_SAVING, total);

        long insertedId = mDatabase.insert(DatabaseHelper.TABLE_SAVING, null, values);
        Cursor cursor = mDatabase.query(mDatabaseHelper.TABLE_SAVING,
                mAllColumns, mDatabaseHelper.COLUMN_SAVING_ID + " = " + insertedId, null, null, null, null);

        cursor.moveToFirst();
        Saving newSaving = cursorToSaving(cursor);
        cursor.close();
        return newSaving;

    }

    public void deleteSaving(Saving saving ){
        long id = saving.getId();
        // delete all information about this saving
        // .......

        System.out.println("The deleted Saving has an id " + id);
        mDatabase.delete(DatabaseHelper.TABLE_SAVING, DatabaseHelper.COLUMN_SAVING_ID + " = " + id, null);
    }

    public Saving getSavingById(long id){
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_SAVING, mAllColumns,
                DatabaseHelper.COLUMN_SAVING_ID + " ? = ", new String[] {String.valueOf(id)},
                null, null, null);
        if (cursor != null ){
            cursor.moveToFirst();
        }

        Saving saving = cursorToSaving(cursor);
        return saving;

    }

    protected Saving cursorToSaving(Cursor cursor){
        Saving saving = new Saving();
        saving.setId(cursor.getInt(0));
        saving.setTotalSaving(cursor.getInt(1));

        return saving;
    }*/
}
