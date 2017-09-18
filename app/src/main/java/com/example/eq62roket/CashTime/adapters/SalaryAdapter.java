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
import com.example.eq62roket.CashTime.activities.UpdateSalaryActivity;
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

public class SalaryAdapter extends ArrayAdapter<Income> {
    private static final String TAG = "SalaryAdapter";
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

    public SalaryAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Income> objects) {
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



        final long salary_id = getItem(position).getId();
        final int salary_amount = getItem(position).getSalary();
        final String salary_created_date = getItem(position).getCreatedDate();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvamount = (TextView) convertView.findViewById(R.id.txtSalaryAmount);
        TextView tvdatecreated = (TextView) convertView.findViewById(R.id.txtSalaryDate);


        tvamount.setText("" + salary_amount);
        tvdatecreated.setText(salary_created_date);
        Log.d(TAG, "date: salary " + salary_created_date);



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSalaryDetail(salary_amount, salary_id);
                Log.d(TAG, "salary amount" + salary_amount);
                Log.d(TAG, "salry id" + salary_id);
            }
        });
        return convertView;
    }

    private void openSalaryDetail(int salary_amount, long salary_id){
        Intent intent = new Intent(mContext, UpdateSalaryActivity.class);
        // PACK DATA
        intent.putExtra("SALARY_AMOUNT", salary_amount);
        intent.putExtra("SALARY_ID", salary_id);

        // OPEN ACTIVITY
        mContext.startActivity(intent);
    }
}
