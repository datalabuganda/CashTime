package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Helper.CashTimeUtils;
import com.example.eq62roket.cashtime.Models.GroupExpenditure;
import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 3/28/18.
 */

public class GroupExpenditureAdapter extends RecyclerView.Adapter<GroupExpenditureAdapter.MyViewHolder>{

    public interface OnGroupExpenditureClickListener {
        void onGroupExpenditureClick(GroupExpenditure groupExpenditure);
    }

    private List<GroupExpenditure> mGroupExpenditure;
    private final OnGroupExpenditureClickListener mOnGroupExpenditureClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView category, amount, duedate, notes, groupName;
        public ImageView collapse, expand;


        public MyViewHolder(View view) {
            super(view);
            category = (TextView) view.findViewById(R.id.groupExpenditureCategory);
            amount = (TextView) view.findViewById(R.id. groupExpenditureAmount);
            notes = (TextView)view.findViewById(R.id.groupExpenditureNotes);
            duedate = (TextView)view.findViewById(R.id.groupExpenditureDate);
            groupName =(TextView)view.findViewById(R.id.groupName);
            collapse = view.findViewById(R.id.collapse);
            expand = view.findViewById(R.id.expand);

        }

        public void bind(final GroupExpenditure groupExpenditure, final OnGroupExpenditureClickListener onGroupExpenditureClickListener){
            category.setText(groupExpenditure.getCategory());
            amount.setText(new CashTimeUtils().currencyFormatter(groupExpenditure.getAmount()));
            notes.setText(groupExpenditure.getNotes());
            duedate.setText(groupExpenditure.getDate());
            groupName.setText(groupExpenditure.getGroupName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onGroupExpenditureClickListener.onGroupExpenditureClick(groupExpenditure);
                }
            });
        }

    }


    public GroupExpenditureAdapter(List<GroupExpenditure> groupExpenditure, OnGroupExpenditureClickListener onGroupExpenditureClickListener) {
        this.mGroupExpenditure = groupExpenditure;
        this.mOnGroupExpenditureClickListener = onGroupExpenditureClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_expenditure_transactions_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.bind(mGroupExpenditure.get(position), mOnGroupExpenditureClickListener);
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
        if (mGroupExpenditure.size() > 0){
            return mGroupExpenditure.size();
        }
        return 0;
    }

    public void setFilter(ArrayList<GroupExpenditure> groupExpenditure){
        mGroupExpenditure = new ArrayList<>();
        mGroupExpenditure.addAll(groupExpenditure);
        notifyDataSetChanged();
    }
}
