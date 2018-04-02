package com.example.eq62roket.cashtime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Models.ExpenditureCategories;
import com.example.eq62roket.cashtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 4/2/18.
 */

public class ExpenditureCategoryAdapter extends RecyclerView.Adapter<ExpenditureCategoryAdapter.MyViewHolder>{

    public interface OnExpenditureCategoryClickListener {
        void onExpenditureCategoryClick(ExpenditureCategories expenditureCategories);
    }

    private List<ExpenditureCategories> mExpenditureCategory;
    private final ExpenditureCategoryAdapter.OnExpenditureCategoryClickListener mOnExpenditureCategoryClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.expenditureCategory);

        }

        public void bind(final ExpenditureCategories expenditureCategory, final ExpenditureCategoryAdapter.OnExpenditureCategoryClickListener onExpenditureCategoryClickListener){
            name.setText(expenditureCategory.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onExpenditureCategoryClickListener.onExpenditureCategoryClick(expenditureCategory);
                }
            });
        }

    }


    public ExpenditureCategoryAdapter(List<ExpenditureCategories> expenditureCategory, ExpenditureCategoryAdapter.OnExpenditureCategoryClickListener onExpenditureCategoryClickListener) {
        this.mExpenditureCategory = expenditureCategory;
        this.mOnExpenditureCategoryClickListener = onExpenditureCategoryClickListener;
    }

    @Override
    public ExpenditureCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expenditure_category_row, parent, false);

        return new ExpenditureCategoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExpenditureCategoryAdapter.MyViewHolder holder, int position) {
        holder.bind(mExpenditureCategory.get(position), mOnExpenditureCategoryClickListener);
    }

    @Override
    public int getItemCount() {
        if (mExpenditureCategory.size() > 0){
            return mExpenditureCategory.size();
        }
        return 0;
    }

    public void setFilter(ArrayList<ExpenditureCategories> expenditureCategory){
        mExpenditureCategory = new ArrayList<>();
        mExpenditureCategory.addAll(expenditureCategory);
        notifyDataSetChanged();
    }
}