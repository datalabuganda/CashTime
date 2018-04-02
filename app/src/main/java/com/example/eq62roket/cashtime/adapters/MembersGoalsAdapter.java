package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Helper.CashTimeUtils;
import com.example.eq62roket.cashtime.Models.MembersGoals;
import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 2/28/18.
 * modified by etwin
 */

public class MembersGoalsAdapter extends RecyclerView.Adapter<MembersGoalsAdapter.MembersGoalsViewHolder> {

    public interface OnMemberGoalClickListener {
        void onMemberGoalClick(MembersGoals membersGoals);
    }

    private List<MembersGoals> membersGoalsList;
    private OnMemberGoalClickListener mOnMemberGoalClickListener;

    public class MembersGoalsViewHolder extends RecyclerView.ViewHolder {
        public TextView memberName, memberGoalDueDate, memberGoalAmount, memberGoalName;

        public MembersGoalsViewHolder(View view) {
            super(view);
            memberName = (TextView) view.findViewById(R.id.memberName);
            memberGoalDueDate = (TextView) view.findViewById(R.id.memberGoalDueDate);
            memberGoalAmount = (TextView) view.findViewById(R.id.memberGoalAmount);
            memberGoalName = (TextView) view.findViewById(R.id.memberGoalName);
        }

        public void bind(final MembersGoals membersGoals, final OnMemberGoalClickListener memberGoalClickListener){
            memberName.setText(membersGoals.getMemberName());
            memberGoalDueDate.setText(membersGoals.getMemberGoalDueDate());
            memberGoalAmount.setText(new CashTimeUtils().currencyFormatter(membersGoals.getMemberGoalAmount()));
            memberGoalName.setText(membersGoals.getMemberGoalName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    memberGoalClickListener.onMemberGoalClick(membersGoals);
                }
            });
        }
    }


    public MembersGoalsAdapter(List<MembersGoals> membersGoalsList, OnMemberGoalClickListener onMemberGoalClickListener) {
        this.membersGoalsList = membersGoalsList;
        mOnMemberGoalClickListener = onMemberGoalClickListener;
    }

    @Override
    public MembersGoalsAdapter.MembersGoalsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.members_goals_row, parent, false);

        return new MembersGoalsAdapter.MembersGoalsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MembersGoalsAdapter.MembersGoalsViewHolder holder, int position) {
        holder.bind(membersGoalsList.get(position), mOnMemberGoalClickListener);
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
