package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Helper.CashTimeUtils;
import com.example.eq62roket.cashtime.Models.GroupSavings;
import com.example.eq62roket.cashtime.R;

import java.util.List;

/**
 * Created by probuse on 3/3/18.
 */

public class GroupSavingsAdapter extends RecyclerView.Adapter<GroupSavingsAdapter.GroupSavingsViewHolder> {

    public interface OnGroupSavingClickListener {
        void onGroupSavingClick(GroupSavings groupSavings);
    }
    private static final String TAG = "GroupSavingsAdapter";

    private List<GroupSavings> mGroupSavings;
    private OnGroupSavingClickListener mOnGroupSavingClickListener;

    public GroupSavingsAdapter(List<GroupSavings> groupSavings, OnGroupSavingClickListener onGroupSavingClickListener){
        mGroupSavings = groupSavings;
        mOnGroupSavingClickListener = onGroupSavingClickListener;
    }

    public class GroupSavingsViewHolder extends RecyclerView.ViewHolder{
        private TextView goalName, groupAmount, groupSavingDate;


        public GroupSavingsViewHolder(View view) {
            super(view);
            goalName = (TextView) view.findViewById(R.id.goalName);
            groupAmount = (TextView) view.findViewById(R.id.groupAmount);
            groupSavingDate = (TextView) view.findViewById(R.id.groupSavingDate);
        }

        public void bind(final GroupSavings groupSavings, final OnGroupSavingClickListener onGroupSavingClickListener){
            goalName.setText(groupSavings.getGoalName());
            groupAmount.setText(new CashTimeUtils().currencyFormatter(groupSavings.getAmount()));
            groupSavingDate.setText(groupSavings.getDateAdded());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onGroupSavingClickListener.onGroupSavingClick(groupSavings);
                }
            });
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
       holder.bind(mGroupSavings.get(position), mOnGroupSavingClickListener);


    }

    @Override
    public int getItemCount() {
        return mGroupSavings.size();
    }
}
