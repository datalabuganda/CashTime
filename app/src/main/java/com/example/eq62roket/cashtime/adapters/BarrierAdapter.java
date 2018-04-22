package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Models.Barrier;
import com.example.eq62roket.cashtime.Models.Tip;
import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etwin on 3/11/18.
 */

public class BarrierAdapter extends RecyclerView.Adapter<BarrierAdapter.TipViewHolder> {

    public interface OnBarrierClickListener{
        void onBarrierSelected(Barrier barrier);
    }

    List<Barrier> mBarrierList;
    OnBarrierClickListener mOnBarrierClickListener;


    public BarrierAdapter(List<Barrier> barriersList, OnBarrierClickListener onBarrierClickListener) {
        mBarrierList = barriersList;
        mOnBarrierClickListener = onBarrierClickListener;
    }

    public class TipViewHolder extends RecyclerView.ViewHolder{
        private TextView barrierName, barrierNotes, dateAdded;

        public TipViewHolder(View view) {
            super(view);
            barrierName = (TextView) view.findViewById(R.id.barrierName);
            barrierNotes = (TextView) view.findViewById(R.id.barrierNotes);
            dateAdded = (TextView) view.findViewById(R.id.dateAdded);
        }

        public void bind(final Barrier barrier, final OnBarrierClickListener onBarrierClickListener){
            barrierName.setText(barrier.getBarrierName());
            barrierNotes.setText(barrier.getBarrierText());
            dateAdded.setText(barrier.getDateAdded());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBarrierClickListener.onBarrierSelected(barrier);
                }
            });
        }
    }

    @Override
    public TipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.barrier_row, parent, false
        );
        return new TipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TipViewHolder holder, int position) {
       holder.bind(mBarrierList.get(position),mOnBarrierClickListener);
    }

    @Override
    public int getItemCount() {
        return mBarrierList.size();
    }

    public void setFilter(ArrayList<Barrier> newList) {
        mBarrierList = new ArrayList<>();
        mBarrierList.addAll(newList);
        notifyDataSetChanged();
    }
}
