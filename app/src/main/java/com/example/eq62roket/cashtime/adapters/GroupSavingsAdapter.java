package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Models.GroupSavings;
import com.example.eq62roket.cashtime.R;

import java.util.List;

/**
 * Created by probuse on 3/3/18.
 */

public class GroupSavingsAdapter extends RecyclerView.Adapter<GroupSavingsAdapter.GroupSavingsViewHolder> {

    private static final String TAG = "GroupSavingsAdapter";

    private List<GroupSavings> mGroupSavings;
    public GroupSavingsAdapter(List<GroupSavings> groupSavings){
        mGroupSavings = groupSavings;
    }

    public class GroupSavingsViewHolder extends RecyclerView.ViewHolder{
        private TextView name, amount;


        public GroupSavingsViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_memberName);
            amount = (TextView) view.findViewById(R.id.tv_memberAmount);
        }
    }

    @Override
    public GroupSavingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(
               R.layout.group_savings_row, parent, false
       );
       return new GroupSavingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupSavingsViewHolder holder, int position) {
        GroupSavings savings = mGroupSavings.get(position);
        holder.name.setText(savings.getGoalName());
        holder.amount.setText(String.valueOf(savings.getAmount()));


    }

    @Override
    public int getItemCount() {
        return mGroupSavings.size();
    }
}
