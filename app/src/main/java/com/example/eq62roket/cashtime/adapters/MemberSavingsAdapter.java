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
        TextView membersName, savingAmount, dateAdded, goalName;


        public MemberSavingViewHolder(View itemView) {
            super(itemView);
            membersName = (TextView) itemView.findViewById(R.id.memberName);
            savingAmount = (TextView) itemView.findViewById(R.id.savingAmount);
            dateAdded = (TextView) itemView.findViewById(R.id.dateAdded);
            goalName = (TextView) itemView.findViewById(R.id.goalName);
        }

        public void bind(final MemberSavings memberSavings, final OnSavingClickListener listener){
            membersName.setText(memberSavings.getMemberName());
            savingAmount.setText(String.valueOf(memberSavings.getsavingAmount()));
            dateAdded.setText(memberSavings.getDateAdded());
            goalName.setText(memberSavings.getGoalName());
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
