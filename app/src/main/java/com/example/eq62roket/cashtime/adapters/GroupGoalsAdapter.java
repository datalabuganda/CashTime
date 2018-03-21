package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 2/28/18.
 */

public class GroupGoalsAdapter extends RecyclerView.Adapter<GroupGoalsAdapter.GroupGoalsViewHolder> {

    public interface OnGoalClickListener {
        void onGoalClick(GroupGoals groupGoals);
    }

    private List<GroupGoals> groupGoalsList;
    private final OnGoalClickListener mOnGoalClickListener;


    public class GroupGoalsViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, amount;

        public GroupGoalsViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            date = (TextView) view.findViewById(R.id.endDate);
            amount = (TextView) view.findViewById(R.id.amount);
        }

        public void bind(final GroupGoals groupGoals, final OnGoalClickListener onGoalClickListener){
            name.setText(groupGoals.getName());
            amount.setText(groupGoals.getAmount());
            date.setText(groupGoals.getDueDate());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onGoalClickListener.onGoalClick(groupGoals);
                }
            });
        }
    }


    public GroupGoalsAdapter(List<GroupGoals> groupGoalsList, OnGoalClickListener listener) {
        this.groupGoalsList = groupGoalsList;
        mOnGoalClickListener = listener;

    }

    @Override
    public GroupGoalsAdapter.GroupGoalsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_goals_row, parent, false);

        return new GroupGoalsAdapter.GroupGoalsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupGoalsAdapter.GroupGoalsViewHolder holder, int position) {
       holder.bind(groupGoalsList.get(position), mOnGoalClickListener);
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