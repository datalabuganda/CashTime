package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Models.MembersIncome;
import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 3/28/18.
 */

public class GroupMembersIncomeTransactionsAdapter extends RecyclerView.Adapter<GroupMembersIncomeTransactionsAdapter.MyViewHolder>{

    public interface OnGroupMemberClickListener {
        void onGroupMemberClick(MembersIncome groupMemberIncome);
    }

    private List<MembersIncome> mGroupMemberIncomes;
    private final GroupMembersIncomeTransactionsAdapter.OnGroupMemberClickListener mOnGroupMemberClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView source, amount, duedate;

        public MyViewHolder(View view) {
            super(view);
            source = (TextView) view.findViewById(R.id.memberIncomeSource);
            amount = (TextView) view.findViewById(R.id. memberIncomeAmount);
            duedate = (TextView) view.findViewById(R.id.memberIncomeDate);

        }

        public void bind(final MembersIncome groupMemberIncome, final GroupMembersIncomeTransactionsAdapter.OnGroupMemberClickListener onGroupMemberClickListener){
            source.setText(groupMemberIncome.getSource());
            amount.setText(groupMemberIncome.getAmount());
            duedate.setText(groupMemberIncome.getDueDate());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onGroupMemberClickListener.onGroupMemberClick(groupMemberIncome);
                }
            });
        }

    }


    public GroupMembersIncomeTransactionsAdapter(List<MembersIncome> groupMemberIncomes, GroupMembersIncomeTransactionsAdapter.OnGroupMemberClickListener onGroupMemberClickListener) {
        this.mGroupMemberIncomes = groupMemberIncomes;
        this.mOnGroupMemberClickListener = onGroupMemberClickListener;
    }

    @Override
    public GroupMembersIncomeTransactionsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_members_income_transaction_row, parent, false);

        return new GroupMembersIncomeTransactionsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupMembersIncomeTransactionsAdapter.MyViewHolder holder, int position) {
        holder.bind(mGroupMemberIncomes.get(position), mOnGroupMemberClickListener);
    }

    @Override
    public int getItemCount() {
        if (mGroupMemberIncomes.size() > 0){
            return mGroupMemberIncomes.size();
        }
        return 0;
    }

    public void setFilter(ArrayList<MembersIncome> groupMemberIncomes){
        mGroupMemberIncomes = new ArrayList<>();
        mGroupMemberIncomes.addAll(groupMemberIncomes);
        notifyDataSetChanged();
    }
}
