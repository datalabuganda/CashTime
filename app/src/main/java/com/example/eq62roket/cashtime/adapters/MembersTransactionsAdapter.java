package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Models.MembersTransactions;
import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 3/1/18.
 */

public class MembersTransactionsAdapter extends RecyclerView.Adapter<MembersTransactionsAdapter.MembersTransactionsViewHolder> {

    private List<MembersTransactions> membersTransactionsList;

    public class MembersTransactionsViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, amount, goal;

        public MembersTransactionsViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.membersTransactionName);
            date = (TextView) view.findViewById(R.id.membersTransactionDate);
            amount = (TextView) view.findViewById(R.id.membersTransactionsAmount);
            goal = (TextView) view.findViewById(R.id.membersTransactionGoal);
        }
    }


    public MembersTransactionsAdapter(List<MembersTransactions> membersTransactionsList) {
        this.membersTransactionsList = membersTransactionsList;
    }

    @Override
    public MembersTransactionsAdapter.MembersTransactionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.members_transactions_row, parent, false);

        return new MembersTransactionsAdapter.MembersTransactionsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MembersTransactionsAdapter.MembersTransactionsViewHolder holder, int position) {
        MembersTransactions membersTransactions = membersTransactionsList.get(position);
        holder.name.setText(membersTransactions.getName());
        holder.date.setText(membersTransactions.getDate());
        holder.amount.setText(membersTransactions.getAmount());
        holder.goal.setText(membersTransactions.getGoal());
    }

    @Override
    public int getItemCount() {
        return membersTransactionsList.size();
    }

    public void setFilter(ArrayList<MembersTransactions> newList) {
        membersTransactionsList = new ArrayList<>();
         membersTransactionsList.addAll(newList);
        notifyDataSetChanged();
    }
}
