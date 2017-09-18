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
import com.example.eq62roket.CashTime.helper.GoalCrud;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Expenditure;
import com.example.eq62roket.CashTime.models.User;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by eq62roket on 8/24/17.
 */

public class HomeNeedsAdapter extends ArrayAdapter<Expenditure> {
    private static final String TAG = "HomeneedsAdapter";
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

    public HomeNeedsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Expenditure> objects) {
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



        final long homeneeds_id = getItem(position).getId();
        final int homeneeds_amount = getItem(position).getHomeneeds();
        final String homeneeds_created_date = getItem(position).getExpenditureDate();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvamount = (TextView) convertView.findViewById(R.id.txtHomeneedsAmount);
        TextView tvdatecreated = (TextView) convertView.findViewById(R.id.txtHomeneedsDate);


        tvamount.setText("" + homeneeds_amount);
        tvdatecreated.setText(homeneeds_created_date);
        Log.d(TAG, "date: homeneeds " + homeneeds_created_date);



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeneedsDetail(homeneeds_amount, homeneeds_id);
                Log.d(TAG, "homeneed amount" + homeneeds_amount);
                Log.d(TAG, "homeneeds id" + homeneeds_id);
            }
        });
        return convertView;
    }

    private void openHomeneedsDetail(int homeneeds_amount, long homeneeds_id){
        Intent intent = new Intent(mContext, UpdateHomeneedsActivity.class);
        // PACK DATA
        intent.putExtra("HOMENEEDS_AMOUNT", homeneeds_amount);
        intent.putExtra("HOMENEEDS_ID", homeneeds_id);

        // OPEN ACTIVITY
        mContext.startActivity(intent);
    }
}
