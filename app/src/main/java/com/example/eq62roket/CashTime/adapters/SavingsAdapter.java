package com.example.eq62roket.CashTime.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.eq62roket.CashTime.activities.UpdateHomeneedsActivity;
import com.example.eq62roket.CashTime.activities.UpdateSavingsActivity;
import com.example.eq62roket.CashTime.helper.GoalCrud;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Expenditure;
import com.example.eq62roket.CashTime.models.User;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by eq62roket on 9/4/17.
 */

public class SavingsAdapter extends ArrayAdapter<Expenditure> {
    private static final String TAG = "SavingsAdapter";
    private Context mContext;
    int mResource;

    DecimalFormat formatter;

    private UserCrud userCrud;
    private GoalCrud goalCrud;
    private SQLiteHelper sqLiteHelper;
    private Expenditure expenditure;
    private User user;

    private Date currentDate;
    private Date goalEndDate;

    public SavingsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Expenditure> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        userCrud = new UserCrud(context);
        goalCrud = new GoalCrud(context);
        sqLiteHelper = new SQLiteHelper(context);
        expenditure = new Expenditure();
        user = new User();
        //goal = new Goal();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get the user goal information

        formatter = new DecimalFormat("#,###,###");



        final long savings_id = getItem(position).getId();
        final int savings_amount = getItem(position).getSavings();
        final String savings_created_date = getItem(position).getExpenditureDate();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvamount = (TextView) convertView.findViewById(R.id.txtSavingsAmount);
        TextView tvdatecreated = (TextView) convertView.findViewById(R.id.txtSavingsDate);


        tvamount.setText("" + savings_amount);
        tvdatecreated.setText(savings_created_date);
        Log.d(TAG, "date: savings " + savings_created_date);



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSavingsDetail(savings_amount, savings_id);
                Log.d(TAG, "savings amount" + savings_amount);
                Log.d(TAG, "savings id" + savings_id);
            }
        });
        return convertView;
    }

    private void openSavingsDetail(int savings_amount, long savings_id){
        Intent intent = new Intent(mContext, UpdateSavingsActivity.class);
        // PACK DATA
        intent.putExtra("SAVINGS_AMOUNT", savings_amount);
        intent.putExtra("SAVINGS_ID", savings_id);

        // OPEN ACTIVITY
        mContext.startActivity(intent);
    }
}
