package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.eq62roket.cashtime.Helper.ParseExpenditureCategoryHelper;
import com.example.eq62roket.cashtime.Helper.ParseExpenditureHelper;
import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Models.ExpenditureCategories;
import com.example.eq62roket.cashtime.Models.GroupGoals;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.ExpenditureCategoryAdapter;
import com.example.eq62roket.cashtime.adapters.GroupGoalsAdapter;

import java.util.List;

public class ExpenditureCategoriesActivity extends AppCompatActivity {

    private static String TAG = "ExpenditureCategoriesActivity";

    List<ExpenditureCategories> expenditureCategories = null;
    private RecyclerView recyclerView;
    private ExpenditureCategoryAdapter mAdapter;
    ParseExpenditureCategoryHelper mParseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure_categories);
        mParseHelper = new ParseExpenditureCategoryHelper(this);

        recyclerView = (RecyclerView)findViewById(R.id.expenditure_categories_recycler_view);

        new ParseExpenditureCategoryHelper(getApplicationContext()).getCategoriesFromParseDb(new ParseExpenditureCategoryHelper.OnReturnedCategoryListener() {
            @Override
            public void onResponse(List<ExpenditureCategories> expenditureCategoriesList) {
                expenditureCategories = expenditureCategoriesList;

                mAdapter = new ExpenditureCategoryAdapter(expenditureCategoriesList, new ExpenditureCategoryAdapter.OnExpenditureCategoryClickListener() {
                    @Override
                    public void onExpenditureCategoryClick(ExpenditureCategories expenditureCategories) {
                        Intent editExpenditureCategoryIntent = new Intent(ExpenditureCategoriesActivity.this, EditExpenditureCategoryActivity.class);
                        editExpenditureCategoryIntent.putExtra("categoryName", expenditureCategories.getName());
                        editExpenditureCategoryIntent.putExtra("categoryParseId", expenditureCategories.getParseId());
                        startActivity(editExpenditureCategoryIntent);
                        finish();
                    }

                });

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG, "onFailure: " + error);
            }
        });
    }
}
