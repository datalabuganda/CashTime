package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Models.GroupTransactions;
import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 3/1/18.
 */

public class GroupTransactionsAdapter extends RecyclerView.Adapter<GroupTransactionsAdapter.GroupTransactionsViewHolder> {

    private List<GroupTransactions> groupTransactionsList;

    public class GroupTransactionsViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, amount, goal;

        public GroupTransactionsViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.groupTransactionName);
            date = (TextView) view.findViewById(R.id.groupTransactionDate);
            amount = (TextView) view.findViewById(R.id.groupTransactionsAmount);
            goal = (TextView) view.findViewById(R.id.groupTransactionGoal);
        }
    }


    public GroupTransactionsAdapter(List<GroupTransactions> groupTransactionsList) {
        this.groupTransactionsList = groupTransactionsList;
    }

    @Override
    public GroupTransactionsAdapter.GroupTransactionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_transactions_row, parent, false);

        return new GroupTransactionsAdapter.GroupTransactionsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupTransactionsAdapter.GroupTransactionsViewHolder holder, int position) {
        GroupTransactions groupTransactions = groupTransactionsList.get(position);
        holder.name.setText(groupTransactions.getName());
        holder.date.setText(groupTransactions.getDate());
        holder.amount.setText(groupTransactions.getAmount());
        holder.goal.setText(groupTransactions.getGoal());
    }

    @Override
    public int getItemCount() {
        return groupTransactionsList.size();
    }

    public void setFilter(ArrayList<GroupTransactions> newList) {
        groupTransactionsList = new ArrayList<>();
        groupTransactionsList.addAll(newList);
        notifyDataSetChanged();
    }
}