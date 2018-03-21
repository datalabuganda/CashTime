package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Models.MemberSavings;
import com.example.eq62roket.cashtime.R;

import java.util.List;

/**
 * Created by probuse on 3/3/18.
 */

public class MemberSavingsAdapter extends RecyclerView.Adapter<MemberSavingsAdapter.MemberSavingViewHolder> {

    public interface OnSavingClickListener {
        void onSavingClick(MemberSavings memberSavings);
    }

    private List<MemberSavings> mMemberSavings;
    private final OnSavingClickListener mOnSavingClickListener;

    public MemberSavingsAdapter(List<MemberSavings> memberSavings, OnSavingClickListener listener){
        mMemberSavings = memberSavings;
        mOnSavingClickListener = listener;
    }

    public class MemberSavingViewHolder extends RecyclerView.ViewHolder{
        TextView name, amount;

        public MemberSavingViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_memberName);
            amount = (TextView) itemView.findViewById(R.id.tv_memberAmount);
        }

        public void bind(final MemberSavings memberSavings, final OnSavingClickListener listener){
            name.setText(memberSavings.getName());
            amount.setText(String.valueOf(memberSavings.getAmount()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onSavingClick(memberSavings);
                }
            });

        }
    }

    @Override
    public MemberSavingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(
               R.layout.member_savings_row, parent, false
       );

       return new MemberSavingViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MemberSavingViewHolder holder, int position) {

        holder.bind(mMemberSavings.get(position), mOnSavingClickListener);
    }

    @Override
    public int getItemCount() {
        return mMemberSavings.size();
    }

}
