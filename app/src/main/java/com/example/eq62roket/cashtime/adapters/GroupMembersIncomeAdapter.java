package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Helper.CashTimeUtils;
import com.example.eq62roket.cashtime.Models.MembersIncome;
import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 3/28/18.
 */

public class GroupMembersIncomeAdapter extends RecyclerView.Adapter<GroupMembersIncomeAdapter.MyViewHolder>{

    public interface OnGroupMemberClickListener {
        void onGroupMemberClick(MembersIncome groupMemberIncome);
    }

    private List<MembersIncome> mGroupMemberIncomes;
    private final GroupMembersIncomeAdapter.OnGroupMemberClickListener mOnGroupMemberClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView source, amount, period, groupMemberUserName, notes;
        public ImageView collapse, expand;

        public MyViewHolder(View view) {
            super(view);
            source = (TextView) view.findViewById(R.id.groupMemberIncomeSource);
            amount = (TextView) view.findViewById(R.id. groupMemberIncomeAmount);
            period = (TextView) view.findViewById(R.id.groupMemberIncomePeriod);
            groupMemberUserName = (TextView) view.findViewById(R.id.groupMemberIncomeName);
            notes = (TextView)view.findViewById(R.id.groupMemberIncomeNotes);
            collapse = view.findViewById(R.id.collapse);
            expand = view.findViewById(R.id.expand);

        }

        public void bind(final MembersIncome groupMemberIncome, final GroupMembersIncomeAdapter.OnGroupMemberClickListener onGroupMemberClickListener){
            source.setText(groupMemberIncome.getSource());
            amount.setText(new CashTimeUtils().currencyFormatter(groupMemberIncome.getAmount()));
            period.setText(groupMemberIncome.getPeriod());
            notes.setText(groupMemberIncome.getNotes());
            groupMemberUserName.setText(groupMemberIncome.getMemberUserName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onGroupMemberClickListener.onGroupMemberClick(groupMemberIncome);
                }
            });
        }

    }


    public GroupMembersIncomeAdapter(List<MembersIncome> groupMemberIncomes, GroupMembersIncomeAdapter.OnGroupMemberClickListener onGroupMemberClickListener) {
        this.mGroupMemberIncomes = groupMemberIncomes;
        this.mOnGroupMemberClickListener = onGroupMemberClickListener;
    }

    @Override
    public GroupMembersIncomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_members_income_transaction_row, parent, false);

        return new GroupMembersIncomeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final GroupMembersIncomeAdapter.MyViewHolder holder, int position) {
        holder.bind(mGroupMemberIncomes.get(position), mOnGroupMemberClickListener);

        holder.notes.setMaxLines(2);

        holder.expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.notes.setMaxLines(100);
                holder.expand.setVisibility(View.GONE);
                holder.collapse.setVisibility(View.VISIBLE);
            }
        });

        holder.collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.notes.setMaxLines(2);
                holder.expand.setVisibility(View.VISIBLE);
                holder.collapse.setVisibility(View.GONE);
            }
        });
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
