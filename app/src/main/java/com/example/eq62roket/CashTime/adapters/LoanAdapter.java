package com.example.eq62roket.CashTime.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.activities.GoalDetailActivity;
import com.example.eq62roket.CashTime.activities.UpdateLoanActivity;
import com.example.eq62roket.CashTime.activities.UpdateSalaryActivity;
import com.example.eq62roket.CashTime.helper.GoalCrud;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Expenditure;
import com.example.eq62roket.CashTime.models.Goal;
import com.example.eq62roket.CashTime.models.Income;
import com.example.eq62roket.CashTime.models.User;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by eq62roket on 9/4/17.
 */

public class LoanAdapter extends ArrayAdapter<Income>{
    private static final String TAG = "LoanAdapter";
    private Context mContext;
    int mResource;

    DecimalFormat formatter;

    private UserCrud userCrud;
    private GoalCrud goalCrud;
    private SQLiteHelper sqLiteHelper;
    private Expenditure expenditure;
    private User user;
    private Income income;

    private Date currentDate;
    private Date goalEndDate;

    public LoanAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Income> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        userCrud = new UserCrud(context);
        goalCrud = new GoalCrud(context);
        sqLiteHelper = new SQLiteHelper(context);
        expenditure = new Expenditure();
        income = new Income();
        user = new User();
        //goal = new Goal();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get the user goal information

        formatter = new DecimalFormat("#,###,###");



        final long loan_id = getItem(position).getId();
        final int loan_amount = getItem(position).getLoan();
        final String loan_created_date = getItem(position).getCreatedDate();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvamount = (TextView) convertView.findViewById(R.id.txtLoanAmount);
        TextView tvdatecreated = (TextView) convertView.findViewById(R.id.txtLoanDate);


        tvamount.setText("Shs: " + formatter.format(loan_amount));
        tvdatecreated.setText(loan_created_date);
        Log.d(TAG, "date: loan " + loan_created_date);



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoanDetail(loan_amount, loan_id);
                Log.d(TAG, "loan amount" + loan_amount);
                Log.d(TAG, "loan id" + loan_id);
            }
        });
        return convertView;
    }

    private void openLoanDetail(int loan_amount, long loan_id){
        Intent intent = new Intent(mContext, UpdateLoanActivity.class);
        // PACK DATA
        intent.putExtra("LOAN_AMOUNT", loan_amount);
        intent.putExtra("LOAN_ID", loan_id);

        // OPEN ACTIVITY
        mContext.startActivity(intent);
    }

}


//public class LoanAdapter extends CursorAdapter {
//    private Cursor cursor;
//    public LoanAdapter(Context context, Cursor cursor) {
//        super(context, cursor, 0);
//    }
//
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        return LayoutInflater.from(context).inflate(R.layout.loan_list_adapter, parent,false);
//    }
//
//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//        TextView tvid = (TextView) view.findViewById(R.id.txtId);
//        TextView tvamount = (TextView) view.findViewById(R.id.txtLoanAmount);
//        TextView tvdatecreated = (TextView) view.findViewById(R.id.txtLoanDate);
//
//        String ID = cursor.getString(cursor.getColumnIndexOrThrow("ID"));
//        String CREATED_DATE = cursor.getString(cursor.getColumnIndexOrThrow("CREATED_DATE"));
//        int LOAN = cursor.getInt(cursor.getColumnIndexOrThrow("LOAN"));
//
//
//        tvid.setText(ID);
//        tvdatecreated.setText(CREATED_DATE);
//        tvamount.setText(String.valueOf(LOAN));
//    }
//
//}