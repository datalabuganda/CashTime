package com.example.eq62roket.CashTime.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.eq62roket.CashTime.R;

/**
 * Created by eq62roket on 9/4/17.
 */

public class OtherIncome extends CursorAdapter{
    public OtherIncome(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.otherincome_list_adapter, parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvamount = (TextView) view.findViewById(R.id.txtOtherIncomeAmount);
        TextView tvdatecreated = (TextView) view.findViewById(R.id.txtOtherIncomeDate);

        String CREATED_DATE = cursor.getString(cursor.getColumnIndexOrThrow("CREATED_DATE"));
        int OTHERS = cursor.getInt(cursor.getColumnIndexOrThrow("OTHERS"));

        tvdatecreated.setText(CREATED_DATE);
        tvamount.setText(String.valueOf(OTHERS));
    }
}
