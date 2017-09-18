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
import com.example.eq62roket.CashTime.activities.UpdateTransportActivity;
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

public class TransportAdapter extends ArrayAdapter<Expenditure> {
    private static final String TAG = "TransportAdapter";
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

    public TransportAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Expenditure> objects) {
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



        final long transport_id = getItem(position).getId();
        final int transport_amount = getItem(position).getTransport();
        final String transport_created_date = getItem(position).getExpenditureDate();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvamount = (TextView) convertView.findViewById(R.id.txtTransportAmount);
        TextView tvdatecreated = (TextView) convertView.findViewById(R.id.txtTransportDate);


        tvamount.setText("Shs: " + formatter.format(transport_amount));
        tvdatecreated.setText(transport_created_date);
        Log.d(TAG, "date: transport " + transport_created_date);



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTransportDetail(transport_amount, transport_id);
                Log.d(TAG, "transport amount" + transport_amount);
                Log.d(TAG, "transport id" + transport_id);
            }
        });
        return convertView;
    }

    private void openTransportDetail(int transport_amount, long transport_id){
        Intent intent = new Intent(mContext, UpdateTransportActivity.class);
        // PACK DATA
        intent.putExtra("TRANSPORT_AMOUNT", transport_amount);
        intent.putExtra("TRANSPORT_ID", transport_id);

        // OPEN ACTIVITY
        mContext.startActivity(intent);
    }
}
