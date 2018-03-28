package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Models.Group;
import com.example.eq62roket.cashtime.R;

import java.util.List;

/**
 * Created by etwin on 3/11/18.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    public interface OnGroupClickListener{
        void onGroupSelected(Group group);
    }

    List<Group> mGroupsList;
    OnGroupClickListener mOnGroupClickListener;


    public GroupAdapter(List<Group> groupsList, OnGroupClickListener onGroupClickListener) {
        mGroupsList = groupsList;
        mOnGroupClickListener = onGroupClickListener;
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder{
        private TextView groupName, numOfGroupMembers, dateGroupCreated;

        public GroupViewHolder(View view) {
            super(view);
            groupName = (TextView) view.findViewById(R.id.groupName);
            numOfGroupMembers = (TextView) view.findViewById(R.id.numOfGroupMembers);
            dateGroupCreated = (TextView) view.findViewById(R.id.dateGroupCreated);
        }

        public void bind(final Group group, final OnGroupClickListener onGroupClickListener){
            groupName.setText(group.getGroupName());
            numOfGroupMembers.setText(String.valueOf(group.getGroupMemberCount()));
            dateGroupCreated.setText(group.getDateCreated());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onGroupClickListener.onGroupSelected(group);
                }
            });
        }
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.group_row, parent, false
        );
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
       holder.bind(mGroupsList.get(position), mOnGroupClickListener);
    }

    @Override
    public int getItemCount() {
        return mGroupsList.size();
    }




}
