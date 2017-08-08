package com.example.eq62roket.CashTime.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.activities.GoalDetailActivity;
import com.example.eq62roket.CashTime.helper.GoalCrud;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.Goal;

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

    public GoalListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Goal> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        userCrud = new UserCrud(context);
        goalCrud = new GoalCrud(context);

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

        // get user points
        final String user_points = String.valueOf(userCrud.getLastUserInserted().getPoints());

       /* // create Goal object with above information
        Goal goal = new Goal(goal_name, goal_amount, goal_endDate);*/


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvGoalName = (TextView) convertView.findViewById(R.id.tvGoalName);
        TextView tvEndDate = (TextView) convertView.findViewById(R.id.tvEndDate);
        TextView tvGoalAmount = (TextView) convertView.findViewById(R.id.tvGoalAmount);


        tvGoalName.setText(goal_name + goal_id);
        tvEndDate.setText("By: " + goal_endDate);
        tvGoalAmount.setText("Shs: " + formatter.format(goal_amount));

        // goal click listener
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoalDetail(goal_name, goal_amount, goal_endDate, goal_id);
            }
        });

        return convertView;
    }

    private void openGoalDetail(String goal_name, int goal_amount, String goal_endDate, long goal_id){
        Intent intent = new Intent(mContext, GoalDetailActivity.class);
        // PACK DATA
        intent.putExtra("GOAL_NAME", goal_name);
        intent.putExtra("GOAL_AMOUNT", goal_amount);
        intent.putExtra("GOAL_ENDDATE", goal_endDate);
        intent.putExtra("GOAL_ID", goal_id);

        // OPEN ACTIVITY
        mContext.startActivity(intent);
    }
}
