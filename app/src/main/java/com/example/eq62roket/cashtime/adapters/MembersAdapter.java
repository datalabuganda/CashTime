package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Members;
import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 2/28/18.
 */

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MyViewHolder>{

    private List<Members> membersList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            phone = (TextView) view.findViewById(R.id.phone);
        }
    }


    public MembersAdapter(List<Members> membersList) {
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
        Members members = membersList.get(position);
        holder.name.setText(members.getName());
        holder.phone.setText(members.getPhone());
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

