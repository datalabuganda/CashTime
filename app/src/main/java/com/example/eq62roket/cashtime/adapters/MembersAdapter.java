package com.example.eq62roket.cashtime.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Models.Members;
import com.example.eq62roket.cashtime.Activities.GroupMembersDetailActivity;
import com.example.eq62roket.cashtime.Interfaces.ItemClickListener;
import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 2/28/18.
 */

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MyViewHolder>{
    Context c;
    private List<Members> membersList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name, phone;
        ItemClickListener itemClickListener;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            phone = (TextView) view.findViewById(R.id.phone);

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


    public MembersAdapter(Context ctx, List<Members> membersList) {
        this.c=ctx;
        this.membersList = membersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.members_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Members members = membersList.get(position);
        holder.name.setText(members.getName());
        holder.phone.setText(members.getPhone());

        //Handle itemclicks
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //onclick open detail activity and pass data
                Intent detailIntent = new Intent(c, GroupMembersDetailActivity.class);

                //Load data
                detailIntent.putExtra("Name",membersList.get(pos).getName());
                detailIntent.putExtra("Phone", membersList.get(pos).getPhone());
                detailIntent.putExtra("Id", membersList.get(pos).getId());

                //start activity
                c.startActivity(detailIntent);
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

