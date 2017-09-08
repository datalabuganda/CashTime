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

public class SalaryAdapter extends CursorAdapter{
    public SalaryAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.salary_list_adapter, parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvamount = (TextView) view.findViewById(R.id.txtSalaryAmount);
        TextView tvdatecreated = (TextView) view.findViewById(R.id.txtSalaryDate);


        String CREATED_DATE = cursor.getString(cursor.getColumnIndexOrThrow("CREATED_DATE"));
        int SALARY = cursor.getInt(cursor.getColumnIndexOrThrow("SALARY"));

        tvdatecreated.setText(CREATED_DATE);
        tvamount.setText(String.valueOf(SALARY));
    }
    @Override
    protected void onContentChanged() {
        // TODO Auto-generated method stub
        super.onContentChanged();
        notifyDataSetChanged();
    }
}
