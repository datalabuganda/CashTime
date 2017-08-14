package com.example.eq62roket.CashTime.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.eq62roket.CashTime.models.Income;

/**
 * Created by eq62roket on 8/5/17.
 */

public class IncomeCrud {

    private static final String TAG = "IncomeCrud";

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private Context context;

    public IncomeCrud(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getReadableDatabase();
    }

    public boolean insertSalary(Income income){
        ContentValues salaryValues = new ContentValues();
        salaryValues.put(DatabaseHelper.COLUMN_INCOME_SALARY, income.getSalary());

        long result = database.insert(DatabaseHelper.TABLE_INCOME, null, salaryValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertLoan(Income income){
        ContentValues loanValues = new ContentValues();
        loanValues.put(DatabaseHelper.COLUMN_INCOME_LOAN, income.getLoan());

        long result = database.insert(DatabaseHelper.TABLE_INCOME, null, loanValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertInvestment(Income income){
        ContentValues investmentValues = new ContentValues();
        investmentValues.put(DatabaseHelper.COLUMN_INCOME_INVESTMENT, income.getInvestment());

        long result = database.insert(DatabaseHelper.TABLE_INCOME, null, investmentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertOthers(Income income){
        ContentValues othersValues = new ContentValues();
        othersValues.put(DatabaseHelper.COLUMN_INCOME_OTHERS, income.getOther());

        long result = database.insert(DatabaseHelper.TABLE_EXPENDITURE, null, othersValues);
        if (result == -1)
            return false;
        else
            return true;
    }
}
