package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Helper.CashTimeUtils;
import com.example.eq62roket.cashtime.Models.GroupIncome;
import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 3/28/18.
 */

public class GroupIncomeAdapter extends RecyclerView.Adapter<GroupIncomeAdapter.MyViewHolder>{

    public interface OnGroupClickListener {
        void onGroupClick(GroupIncome groupIncome);
    }

    private List<GroupIncome> mGroupIncomes;
    private final GroupIncomeAdapter.OnGroupClickListener mOnGroupClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView source, amount, period, notes, groupName;

        public MyViewHolder(View view) {
            super(view);
            source = (TextView) view.findViewById(R.id.groupIncomeSource);
            amount = (TextView) view.findViewById(R.id. groupIncomeAmount);
            period = (TextView) view.findViewById(R.id.groupIncomePeriod);
            notes = (TextView)view.findViewById(R.id.groupIncomeNotes);
            groupName =(TextView)view.findViewById(R.id.groupIncomeName);

        }

        public void bind(final GroupIncome groupIncome, final GroupIncomeAdapter.OnGroupClickListener onGroupClickListener){
            source.setText(groupIncome.getSource());
            amount.setText(new CashTimeUtils().currencyFormatter(groupIncome.getAmount()));
            notes.setText(groupIncome.getNotes());
            period.setText(groupIncome.getPeriod());
            groupName.setText(groupIncome.getGroupName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onGroupClickListener.onGroupClick(groupIncome);
                }
            });
        }

    }


    public GroupIncomeAdapter(List<GroupIncome> groupIncome, GroupIncomeAdapter.OnGroupClickListener onGroupClickListener) {
        this.mGroupIncomes = groupIncome;
        this.mOnGroupClickListener = onGroupClickListener;
    }

    @Override
    public GroupIncomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_income_transactions_row, parent, false);

        return new GroupIncomeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupIncomeAdapter.MyViewHolder holder, int position) {
        holder.bind(mGroupIncomes.get(position), mOnGroupClickListener);
    }

    @Override
    public int getItemCount() {
        if (mGroupIncomes.size() > 0){
            return mGroupIncomes.size();
        }
        return 0;
    }

    public void setFilter(ArrayList<GroupIncome> groupIncome){
        mGroupIncomes = new ArrayList<>();
        mGroupIncomes.addAll(groupIncome);
        notifyDataSetChanged();
    }
}

