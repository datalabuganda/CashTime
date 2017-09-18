package com.example.eq62roket.CashTime.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.activities.UpdateInvestmentActivity;
import com.example.eq62roket.CashTime.helper.GoalCrud;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Expenditure;
import com.example.eq62roket.CashTime.models.Income;
import com.example.eq62roket.CashTime.models.User;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by eq62roket on 9/4/17.
 */

public class InvestmentAdapter extends ArrayAdapter<Income> {
    private static final String TAG = "InvestmentAdapter";
    private Context mContext;
    int mResource;

    DecimalFormat formatter;

    private UserCrud userCrud;
    private GoalCrud goalCrud;
    private SQLiteHelper sqLiteHelper;
    private User user;
    private Income income;

    private Date currentDate;
    private Date goalEndDate;

    public InvestmentAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Income> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        userCrud = new UserCrud(context);
        goalCrud = new GoalCrud(context);
        sqLiteHelper = new SQLiteHelper(context);
        income = new Income();
        user = new User();
        //goal = new Goal();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get the user goal information

        formatter = new DecimalFormat("#,###,###");



        final long investment_id = getItem(position).getId();
        final int investment_amount = getItem(position).getInvestment();
        final String investment_created_date = getItem(position).getCreatedDate();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvamount = (TextView) convertView.findViewById(R.id.txtInvestmentAmount);
        TextView tvdatecreated = (TextView) convertView.findViewById(R.id.txtInvestmentDate);


        tvamount.setText("Shs: " + formatter.format(investment_amount));
        tvdatecreated.setText(investment_created_date);
        Log.d(TAG, "date: investment " + investment_created_date);



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInvestmentDetail(investment_amount, investment_id);
                Log.d(TAG, "investment amount" + investment_amount);
                Log.d(TAG, "investment id" + investment_id);
            }
        });
        return convertView;
    }

    private void openInvestmentDetail(int investment_amount, long investment_id){
        Intent intent = new Intent(mContext, UpdateInvestmentActivity.class);
        // PACK DATA
        intent.putExtra("INVESTMENT_AMOUNT", investment_amount);
        intent.putExtra("INVESTMENT_ID", investment_id);

        // OPEN ACTIVITY
        mContext.startActivity(intent);
    }
}
