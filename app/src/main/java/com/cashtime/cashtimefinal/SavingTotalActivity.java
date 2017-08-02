package com.cashtime.cashtimefinal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.cashtime.helper.DatabaseHelper;
import com.cashtime.helper.SavingCrud;
import com.cashtime.helper.SavingCrud2;
import com.cashtime.models.Saving;

import java.util.ArrayList;

public class SavingTotalActivity extends AppCompatActivity {

    private int totalSaving = 0;
    private SavingCrud2 mSavingCrud;

    private SQLiteOpenHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_total);

       /* databaseHelper = new DatabaseHelper(this);
        mSavingCrud = new SavingCrud2(this);

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_SAVING, null, null, null, null, null, null);

        ArrayList<Saving> savings = mSavingCrud.getAllSavings();
        if (cursor.getCount() > 0){
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                totalSaving+= savings.get(i).getTotalSaving();
            }
        }
        TextView tvTotalSaving = (TextView) findViewById(R.id.tvTotalSaving);

        tvTotalSaving.setText(String.valueOf(totalSaving));

*/
    }
}
