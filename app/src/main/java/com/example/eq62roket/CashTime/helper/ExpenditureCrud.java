package com.example.eq62roket.CashTime.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.eq62roket.CashTime.models.Expenditure;
import com.example.eq62roket.CashTime.models.Goal;

import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_EXPENDITURE_EDUCATION;
import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_EXPENDITURE_HEALTH;
import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_EXPENDITURE_HOMENEEDS;
import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_EXPENDITURE_OTHERS;
import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_EXPENDITURE_SAVINGS;
import static com.example.eq62roket.CashTime.helper.DatabaseHelper.COLUMN_EXPENDITURE_TRANSPORT;

/**
 * Created by eq62roket on 8/12/17.
 */

public class ExpenditureCrud {
    private static final String TAG = "ExpenditureCrud";

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private Context context;

    public ExpenditureCrud(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        //database = databaseHelper.getReadableDatabase();
    }

    public boolean insertTransport(Expenditure expenditure){
        ContentValues transportValues = new ContentValues();
        transportValues.put(COLUMN_EXPENDITURE_TRANSPORT, expenditure.getTransport());

        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, transportValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertEducation(Expenditure expenditure){
        ContentValues educationValues = new ContentValues();
        educationValues.put(DatabaseHelper.COLUMN_EXPENDITURE_EDUCATION, expenditure.getEducation());

        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, educationValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertHealth(Expenditure expenditure){
        ContentValues healthValues = new ContentValues();
        healthValues.put(DatabaseHelper.COLUMN_EXPENDITURE_HEALTH, expenditure.getHealth());

        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, healthValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertHomeneeds(Expenditure expenditure){
        ContentValues homeneedsValues = new ContentValues();
        homeneedsValues.put(DatabaseHelper.COLUMN_EXPENDITURE_HOMENEEDS, expenditure.getHomeneeds());

        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, homeneedsValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertSavings(Expenditure expenditure){
        ContentValues savingsValues = new ContentValues();
        savingsValues.put(DatabaseHelper.COLUMN_EXPENDITURE_SAVINGS, expenditure.getSavings());

        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, savingsValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertOthers(Expenditure expenditure){
        ContentValues othersValues = new ContentValues();
        othersValues.put(DatabaseHelper.COLUMN_EXPENDITURE_OTHERS, expenditure.getOthers());

        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, othersValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public int addAllTransport() {
        int totalTransport = 0;
        Cursor cursor = database.rawQuery("select SUM(" + (COLUMN_EXPENDITURE_TRANSPORT) + ") from " + DatabaseHelper.TABLE_EXPENDITURE, null);

        if (cursor.moveToFirst()) {
            totalTransport = cursor.getInt(0);
        }
        cursor.close();
        return totalTransport;
    }


}
