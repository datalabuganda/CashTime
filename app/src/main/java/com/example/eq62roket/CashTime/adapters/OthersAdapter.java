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
import com.example.eq62roket.CashTime.activities.UpdateOthersActivity;
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

public class OthersAdapter extends ArrayAdapter<Expenditure> {
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

    public OthersAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Expenditure> objects) {
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



        final long others_id = getItem(position).getId();
        final int others_amount = getItem(position).getOthers();
        final String others_created_date = getItem(position).getExpenditureDate();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvamount = (TextView) convertView.findViewById(R.id.txtOthersAmount);
        TextView tvdatecreated = (TextView) convertView.findViewById(R.id.txtOthersDate);


        tvamount.setText("Shs: " + formatter.format(others_amount));
        tvdatecreated.setText(others_created_date);
        Log.d(TAG, "date: others " + others_created_date);



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOthersDetail(others_amount, others_id);
                Log.d(TAG, "others amount" + others_amount);
                Log.d(TAG, "others id" + others_id);
            }
        });
        return convertView;
    }

    private void openOthersDetail(int others_amount, long others_id){
        Intent intent = new Intent(mContext, UpdateOthersActivity.class);
        // PACK DATA
        intent.putExtra("OTHERS_AMOUNT", others_amount);
        intent.putExtra("OTHERS_ID", others_id);

        // OPEN ACTIVITY
        mContext.startActivity(intent);
    }
}
