package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Models.GroupMemberExpenditure;
import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 3/28/18.
 */

public class GroupMemberExpenditureTransactionsAdapter extends RecyclerView.Adapter<GroupMemberExpenditureTransactionsAdapter.MyViewHolder>{

    public interface OnGroupMemberClickListener {
        void onGroupMemberClick(GroupMemberExpenditure groupMemberExpenditure);
    }

    private List<GroupMemberExpenditure> mGroupMemberExpenditure;
    private final GroupMemberExpenditureTransactionsAdapter.OnGroupMemberClickListener mOnGroupMemberClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView category, amount, notes, date;

        public MyViewHolder(View view) {
            super(view);
            category = (TextView) view.findViewById(R.id.groupMembersExpenditureCategory);
            amount = (TextView) view.findViewById(R.id. groupMembersExpenditureAmount);

        }

        public void bind(final GroupMemberExpenditure groupMemberExpenditure, final GroupMemberExpenditureTransactionsAdapter.OnGroupMemberClickListener onGroupMemberClickListener){
            category.setText(groupMemberExpenditure.getCategory());
            amount.setText(groupMemberExpenditure.getAmount());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onGroupMemberClickListener.onGroupMemberClick(groupMemberExpenditure);
                }
            });
        }

    }


    public GroupMemberExpenditureTransactionsAdapter(List<GroupMemberExpenditure> groupMemberExpenditures, GroupMemberExpenditureTransactionsAdapter.OnGroupMemberClickListener onGroupMemberClickListener) {
        this.mGroupMemberExpenditure = groupMemberExpenditures;
        this.mOnGroupMemberClickListener = onGroupMemberClickListener;
    }

    @Override
    public GroupMemberExpenditureTransactionsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.members_row, parent, false);

        return new GroupMemberExpenditureTransactionsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupMemberExpenditureTransactionsAdapter.MyViewHolder holder, int position) {
        holder.bind(mGroupMemberExpenditure.get(position), mOnGroupMemberClickListener);
    }

    @Override
    public int getItemCount() {
        if (mGroupMemberExpenditure.size() > 0){
            return mGroupMemberExpenditure.size();
        }
        return 0;
    }

    public void setFilter(ArrayList<GroupMemberExpenditure> groupMemberExpenditure){
        mGroupMemberExpenditure = new ArrayList<>();
        mGroupMemberExpenditure.addAll(groupMemberExpenditure);
        notifyDataSetChanged();
    }
}

