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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.activities.GoalDetailActivity;
import com.example.eq62roket.CashTime.helper.GoalCrud;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Expenditure;
import com.example.eq62roket.CashTime.models.Goal;
import com.example.eq62roket.CashTime.models.Income;
import com.example.eq62roket.CashTime.models.User;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by probuse on 8/5/17.
 */

public class GoalListAdapter extends ArrayAdapter<Goal> {

    private static final String TAG = "GoalListAdapter";

    private Context mContext;
    int mResource;

    DecimalFormat formatter;

    private UserCrud userCrud;
    private GoalCrud goalCrud;
    private SQLiteHelper sqLiteHelper;
    private Expenditure expenditure;
    private User user;
    private Goal goal;

    public GoalListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Goal> objects) {
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

        final String goal_name = getItem(position).getName();
        final int goal_amount = getItem(position).getAmount();
        final String goal_endDate = getItem(position).getEndDate();
        final long goal_id = getItem(position).getId();
        final long st = getItem(position).getCompleteStatus();
        //goal = getItem(position);
        goal = goalCrud.getGoalById(goal_id);


        // get user points
        final long user_points = userCrud.getLastUserInserted().getPoints();
        int goalAmount = goal.getAmount();
        //int savedAmount = sqLiteHelper.addAllSavings();


       /* // create Goal object with above information
        Goal goal = new Goal(goal_name, goal_amount, goal_endDate);*/


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvGoalName = (TextView) convertView.findViewById(R.id.tvGoalName);
        TextView tvEndDate = (TextView) convertView.findViewById(R.id.tvEndDate);
        TextView tvGoalAmount = (TextView) convertView.findViewById(R.id.tvGoalAmount);
        TextView tvUserPoints = (TextView) convertView.findViewById(R.id.tvUserPoints);
        ImageView imgCompleted = (ImageView) convertView.findViewById(R.id.imgCompleted);

        tvGoalName.setText(goal_name);
        tvEndDate.setText("By: " + goal_endDate);
        tvGoalAmount.setText("Shs: " + formatter.format(goal_amount));

        /*Log.d(TAG, "goal status  " + goal.getCompleteStatus());
        Log.d(TAG, "goal saved hack  " + sqLiteHelper.addAllSavings(goal.getStartDate()));
        Log.d(TAG, "goal saved  " + sqLiteHelper.addAllSavings(null));
        Log.d(TAG, "goal id  " + goal_id);
        Log.d(TAG, "goal start date " + goal.getStartDate());*/
        if (goal.getCompleteStatus() == 1){
            imgCompleted.setVisibility(convertView.VISIBLE);
        }
        else {

            // goal click listener
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openGoalDetail(goal_name, goal_amount, goal_endDate, goal_id, user_points);
                }
            });
        }

        return convertView;
    }

    private void openGoalDetail(String goal_name, int goal_amount, String goal_endDate, long goal_id, long user_points){
        Intent intent = new Intent(mContext, GoalDetailActivity.class);
        // PACK DATA
        intent.putExtra("GOAL_NAME", goal_name);
        intent.putExtra("GOAL_AMOUNT", goal_amount);
        intent.putExtra("GOAL_ENDDATE", goal_endDate);
        intent.putExtra("GOAL_ID", goal_id);
        intent.putExtra("USER_POINTS", user_points);

        // OPEN ACTIVITY
        mContext.startActivity(intent);
    }
}
