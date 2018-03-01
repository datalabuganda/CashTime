package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 2/28/18.
 */

public class GroupGoalsAdapter extends RecyclerView.Adapter<GroupGoalsAdapter.GroupGoalsViewHolder> {

    private List<GroupGoals> groupGoalsList;

    public class GroupGoalsViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, amount;

        public GroupGoalsViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            date = (TextView) view.findViewById(R.id.date);
            amount = (TextView) view.findViewById(R.id.amount);
        }
    }


    public GroupGoalsAdapter(List<GroupGoals> groupGoalsList) {
        this.groupGoalsList = groupGoalsList;
    }

    @Override
    public GroupGoalsAdapter.GroupGoalsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_goals_row, parent, false);

        return new GroupGoalsAdapter.GroupGoalsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupGoalsAdapter.GroupGoalsViewHolder holder, int position) {
        GroupGoals groupGoals = groupGoalsList.get(position);
        holder.name.setText(groupGoals.getName());
        holder.date.setText(groupGoals.getDate());
        holder.amount.setText(groupGoals.getAmount());
    }

    @Override
    public int getItemCount() {
        return groupGoalsList.size();
    }

    public void setFilter(ArrayList<GroupGoals> newList) {
        groupGoalsList = new ArrayList<>();
        groupGoalsList.addAll(newList);
        notifyDataSetChanged();
    }
}