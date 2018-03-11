package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Models.Tip;
import com.example.eq62roket.cashtime.R;

import java.util.List;

/**
 * Created by etwin on 3/11/18.
 */

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.TipViewHolder> {

    List<Tip> mTipsList;

    public TipsAdapter(List<Tip> tipsList) {
        mTipsList = tipsList;
    }

    public class TipViewHolder extends RecyclerView.ViewHolder{
        private TextView goalName, tipIntroText;

        public TipViewHolder(View view) {
            super(view);
            goalName = (TextView) view.findViewById(R.id.tip_goalName);
            tipIntroText = (TextView) view.findViewById(R.id.tip_introText);
        }
    }

    @Override
    public TipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.tips_row, parent, false
        );
        return new TipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TipViewHolder holder, int position) {
        Tip tip = mTipsList.get(position);
        holder.goalName.setText(tip.getGoalName());
        holder.tipIntroText.setText(tip.getIntroText());
    }

    @Override
    public int getItemCount() {
        return mTipsList.size();
    }




}
