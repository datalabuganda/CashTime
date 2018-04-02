package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Models.IncomeSources;
import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 4/2/18.
 */

public class IncomeSourceAdapter extends RecyclerView.Adapter<IncomeSourceAdapter.MyViewHolder>{

    public interface OnIncomeClickListener {
        void onIncomeClick(IncomeSources incomeSources);
    }

    private List<IncomeSources> mIncomeSources;
    private final IncomeSourceAdapter.OnIncomeClickListener mOnIncomeClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.incomeSource);
        }

        public void bind(final IncomeSources incomeSource, final IncomeSourceAdapter.OnIncomeClickListener onIncomeClickListener){
            name.setText(incomeSource.getName());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onIncomeClickListener.onIncomeClick(incomeSource);
                }
            });
        }

    }

    public IncomeSourceAdapter(List<IncomeSources> incomeSource, IncomeSourceAdapter.OnIncomeClickListener onIncomeClickListener) {
        this.mIncomeSources = incomeSource;
        this.mOnIncomeClickListener = onIncomeClickListener;
    }

    @Override
    public IncomeSourceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.income_source_row, parent, false);

        return new IncomeSourceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IncomeSourceAdapter.MyViewHolder holder, int position) {
        holder.bind(mIncomeSources.get(position), mOnIncomeClickListener);
    }

    @Override
    public int getItemCount() {
        if (mIncomeSources.size() > 0){
            return mIncomeSources.size();
        }
        return 0;
    }

    public void setFilter(ArrayList<IncomeSources> incomeSource){
        mIncomeSources = new ArrayList<>();
        mIncomeSources.addAll(incomeSource);
        notifyDataSetChanged();
    }
}