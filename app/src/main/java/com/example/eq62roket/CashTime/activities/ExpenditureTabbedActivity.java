package com.example.eq62roket.CashTime.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;

import java.text.DecimalFormat;

/**
 * Created by eq62roket on 8/21/17.
 */

public class ExpenditureTabbedActivity extends Fragment {

    TextView txtTransport, txtEducation, txtHealth, txtSavings, txtOthers, txtTotal, txtHomeneeds;
    SQLiteHelper mySQLiteHelper;
    Button btnGraph;
    DecimalFormat formatter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.expenditure_tabbed_report, container, false);

        txtEducation = (TextView) rootView.findViewById(R.id.txtEducation);
        txtTransport = (TextView) rootView.findViewById(R.id.txtTransport);
        txtHealth = (TextView) rootView.findViewById(R.id.txtHealth);
        txtSavings = (TextView) rootView.findViewById(R.id.txtSavings);
        txtOthers = (TextView) rootView.findViewById(R.id.txtOthers);
        txtTotal = (TextView) rootView.findViewById(R.id.txtTotal);
        txtHomeneeds = (TextView) rootView.findViewById(R.id.txtHomeneeds);

        formatter = new DecimalFormat("#,###,###");
        mySQLiteHelper = new SQLiteHelper(getActivity());

        sumEducation();
        sumTransport();
        sumHealth();
        sumSavings();
        sumOthers();
        sumTotal();
        sumHomeneeds();


        return rootView;
    }

    public void sumTransport(){
        int sumTransport = mySQLiteHelper.addAllTransport();
        txtTransport.setText(formatter.format(sumTransport));
    }

    public void sumEducation(){
        int sumEducation = mySQLiteHelper.addAllEducation();
        txtEducation.setText(formatter.format(sumEducation));
    }

    public void sumHealth(){
        int sumHealth = mySQLiteHelper.addAllHealth();
        txtHealth.setText(formatter.format(sumHealth));
    }

    public void sumSavings(){
        int sumSavings = mySQLiteHelper.addAllSavings();
        txtSavings.setText(formatter.format(sumSavings));
    }

    public void sumOthers(){
        int sumOthers = mySQLiteHelper.addAllOthers();
        txtOthers.setText(formatter.format(sumOthers));
    }

    public void sumTotal(){
        int sumTotal = mySQLiteHelper.addAllCategories();
        txtTotal.setText(formatter.format(sumTotal));
    }

    public void sumHomeneeds(){
        int sumHomeneeds = mySQLiteHelper.addAllHomeneeds();
        txtHomeneeds.setText(formatter.format(sumHomeneeds));
    }

}
