package com.cashtime.helper;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cashtime.cashtimefinal.R;
import com.cashtime.models.Goal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by probuse on 8/5/17.
 */

public class GoalListAdapter extends ArrayAdapter<Goal> {

    private static final String TAG = "GoalListAdapter";

    private Context mContext;
    int mResource;

    public GoalListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Goal> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get the user goal information
        String goal_name = getItem(position).getName();
        int goal_amount = getItem(position).getAmount();
        String goal_endDate = getItem(position).getEndDate();

       /* // create Goal object with above information
        Goal goal = new Goal(goal_name, goal_amount, goal_endDate);*/

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvGoalName = (TextView) convertView.findViewById(R.id.tvGoalName);
        TextView tvEndDate = (TextView) convertView.findViewById(R.id.tvEndDate);
        TextView tvGoalAmount = (TextView) convertView.findViewById(R.id.tvGoalAmount);

        tvGoalName.setText(goal_name);
        tvEndDate.setText(goal_endDate);
        tvGoalAmount.setText(goal_amount+"");

        return convertView;
    }
}
