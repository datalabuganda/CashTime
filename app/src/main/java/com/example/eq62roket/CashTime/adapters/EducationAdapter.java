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
import com.example.eq62roket.CashTime.activities.UpdateEducationActivity;
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

public class EducationAdapter extends ArrayAdapter<Expenditure> {
    private static final String TAG = "Education";
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

    public EducationAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Expenditure> objects) {
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



        final long education_id = getItem(position).getId();
        final int education_amount = getItem(position).getEducation();
        final String education_created_date = getItem(position).getExpenditureDate();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvamount = (TextView) convertView.findViewById(R.id.txtEducationAmount);
        TextView tvdatecreated = (TextView) convertView.findViewById(R.id.txtEducationDate);


        tvamount.setText("" + education_amount);
        tvdatecreated.setText(education_created_date);
        Log.d(TAG, "date: education " + education_created_date);



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEducationDetail(education_amount, education_id);
                Log.d(TAG, "education amount" + education_amount);
                Log.d(TAG, "education id" + education_id);
            }
        });
        return convertView;
    }

    private void openEducationDetail(int education_amount, long education_id){
        Intent intent = new Intent(mContext, UpdateEducationActivity.class);
        // PACK DATA
        intent.putExtra("EDUCATION_AMOUNT", education_amount);
        intent.putExtra("EDUCATION_ID", education_id);

        // OPEN ACTIVITY
        mContext.startActivity(intent);
    }
}
