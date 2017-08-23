package com.example.eq62roket.CashTime.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.IncomeSQLiteHelper;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;

import java.text.DecimalFormat;

/**
 * Created by eq62roket on 8/21/17.
 */

public class IncomeTabbedActivity extends Fragment {
    TextView txtSalary, txtInvestment, txtLoan, txtOthers, txtTotal;
    IncomeSQLiteHelper myHelper;
    Button btnGraph;
    DecimalFormat formatter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.income_tabbed_report, container, false);
        txtSalary = (TextView) rootView.findViewById(R.id.txtSalary);
        txtInvestment = (TextView) rootView.findViewById(R.id.txtInvestment);
        txtLoan = (TextView) rootView.findViewById(R.id.txtLoans);
        txtOthers = (TextView) rootView.findViewById(R.id.txtOtherIncome);
        txtTotal = (TextView) rootView.findViewById(R.id.txtIncome);

        formatter = new DecimalFormat("#,###,###");
        myHelper = new IncomeSQLiteHelper(getActivity());

        sumSalary();
        sumInvestment();
        sumLoan();
        sumOthers();
        sumTotal();


        return rootView;
    }

    public void sumSalary(){
        int sumSalary = myHelper.addAllSalary();
        txtSalary.setText(formatter.format(sumSalary));
    }

    public void sumInvestment(){
        int sumInvestment = myHelper.addAllInvestment();
        txtInvestment.setText(formatter.format(sumInvestment));
    }

    public void sumLoan(){
        int sumLoan = myHelper.addAllLoan();
        txtLoan.setText(formatter.format(sumLoan));
    }

    public void sumOthers(){
        int sumOthers = myHelper.addAllOthers();
        txtOthers.setText(formatter.format(sumOthers));
    }


    public void sumTotal(){
        int sumTotal = myHelper.addAllIncome();
        txtTotal.setText(formatter.format(sumTotal));
    }


}
