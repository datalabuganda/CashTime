package com.example.eq62roket.cashtime.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Activities.AddGroupMembersIncomeActivity;
import com.example.eq62roket.cashtime.Interfaces.ItemClickListener;
import com.example.eq62roket.cashtime.Models.Members;
import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 3/17/18.
 */

public class GroupMembersListIncomeAdapter extends RecyclerView.Adapter<GroupMembersListIncomeAdapter.MyViewHolder>{
    Context c;
    private List<Members> membersList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name, phone;
        ItemClickListener itemClickListener;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.group_member_name_income);
            phone = (TextView) view.findViewById(R.id.group_member_phone_income);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view,getLayoutPosition());

        }

        public void setItemClickListener(ItemClickListener ic){
            this.itemClickListener=ic;
        }
    }


    public GroupMembersListIncomeAdapter(Context ctx, List<Members> membersList) {
        this.c=ctx;
        this.membersList = membersList;
    }

    @Override
    public GroupMembersListIncomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_members_list_income_row, parent, false);

        return new GroupMembersListIncomeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupMembersListIncomeAdapter.MyViewHolder holder, int position) {
        final Members members = membersList.get(position);
        holder.name.setText(members.getName());
        holder.phone.setText(members.getPhone());

        //Handle itemclicks
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                Intent Intent = new Intent(c, AddGroupMembersIncomeActivity.class);

                //Load data
                Intent.putExtra("Name",membersList.get(pos).getName());
//                Intent.putExtra("Phone", membersList.get(pos).getPhone());
//                Intent.putExtra("Id", membersList.get(pos).getId());

                //start activity
                c.startActivity(Intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return membersList.size();
    }

    public void setFilter(ArrayList<Members> newList){
        membersList = new ArrayList<>();
        membersList.addAll(newList);
        notifyDataSetChanged();
    }
}
