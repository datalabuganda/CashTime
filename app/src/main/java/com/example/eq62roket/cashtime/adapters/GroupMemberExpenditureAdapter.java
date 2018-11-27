package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Helper.CashTimeUtils;
import com.example.eq62roket.cashtime.Models.GroupMemberExpenditure;
import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 3/28/18.
 */

public class GroupMemberExpenditureAdapter extends RecyclerView.Adapter<GroupMemberExpenditureAdapter.GroupMembersViewHolder> {

    private static String TAG = "GroupMemberExpenditureAdapter";

    public interface OnGoalClickListener {
    void onGoalClick(GroupMemberExpenditure groupMemberExpenditure);
}

    private List<GroupMemberExpenditure> groupMemberExpenditureList;
    private final GroupMemberExpenditureAdapter.OnGoalClickListener mOnGoalClickListener;

    public GroupMemberExpenditureAdapter(List<GroupMemberExpenditure> groupMembersExpenditureList, GroupMemberExpenditureAdapter.OnGoalClickListener listener) {
        this.groupMemberExpenditureList = groupMembersExpenditureList;
        mOnGoalClickListener = listener;

    }


public class GroupMembersViewHolder extends RecyclerView.ViewHolder {
    public TextView category, date, amount, username, notes;
    public ImageView collapse, expand;


    public GroupMembersViewHolder(View view) {
        super(view);
        category = (TextView) view.findViewById(R.id.groupMembersExpenditureCategory);
        notes = (TextView) view.findViewById(R.id.groupMembersExpenditureNotes);
        amount = (TextView) view.findViewById(R.id.groupMembersExpenditureAmount);
        username =(TextView)view.findViewById(R.id.groupMembersExpenditureName);
        date = (TextView)view.findViewById(R.id.groupMembersExpenditureDate);
        collapse = view.findViewById(R.id.collapse);
        expand = view.findViewById(R.id.expand);
    }

    public void bind(final GroupMemberExpenditure groupMemberExpenditure, final GroupMemberExpenditureAdapter.OnGoalClickListener onGoalClickListener){
        category.setText(groupMemberExpenditure.getCategory());
        amount.setText(new CashTimeUtils().currencyFormatter(groupMemberExpenditure.getAmount()));
        date.setText(groupMemberExpenditure.getDate());
        notes.setText(groupMemberExpenditure.getNotes());
        username.setText(groupMemberExpenditure.getMemberUserName());

        Log.d(TAG, "username" + notes);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGoalClickListener.onGoalClick(groupMemberExpenditure);
            }
        });
    }
}


    @Override
    public GroupMemberExpenditureAdapter.GroupMembersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_members_expenditure_row, parent, false);

        return new GroupMemberExpenditureAdapter.GroupMembersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final GroupMemberExpenditureAdapter.GroupMembersViewHolder holder, int position) {
        holder.bind(groupMemberExpenditureList.get(position), mOnGoalClickListener);

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
        if (groupMemberExpenditureList.size() > 0){
            return groupMemberExpenditureList.size();
        }
        return 0;
    }

    public void setFilter(ArrayList<GroupMemberExpenditure> newList) {
        groupMemberExpenditureList = new ArrayList<>();
        groupMemberExpenditureList.addAll(newList);
        notifyDataSetChanged();
    }
}
