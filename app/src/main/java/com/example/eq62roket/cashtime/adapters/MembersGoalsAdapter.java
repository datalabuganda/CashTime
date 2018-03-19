package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Models.MembersGoals;
import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 2/28/18.
 */

public class MembersGoalsAdapter extends RecyclerView.Adapter<MembersGoalsAdapter.MembersGoalsViewHolder> {

    private List<MembersGoals> membersGoalsList;

    public class MembersGoalsViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, amount, goal;

        public MembersGoalsViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.membersGoalsName);
            date = (TextView) view.findViewById(R.id.membersGoalsDate);
            amount = (TextView) view.findViewById(R.id.membersGoalsAmount);
            goal = (TextView) view.findViewById(R.id.membersGoals);
        }
    }


    public MembersGoalsAdapter(List<MembersGoals> membersGoalsList) {
        this.membersGoalsList = membersGoalsList;
    }

    @Override
    public MembersGoalsAdapter.MembersGoalsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.members_goals_row, parent, false);

        return new MembersGoalsAdapter.MembersGoalsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MembersGoalsAdapter.MembersGoalsViewHolder holder, int position) {
        MembersGoals membersGoals = membersGoalsList.get(position);
        holder.name.setText(membersGoals.getName());
        holder.date.setText(membersGoals.getDate());
        holder.amount.setText(membersGoals.getAmount());
    }

    @Override
    public int getItemCount() {
        return membersGoalsList.size();
    }

    public void setFilter(ArrayList<MembersGoals> newList) {
        membersGoalsList = new ArrayList<>();
        membersGoalsList.addAll(newList);
        notifyDataSetChanged();
    }
}
