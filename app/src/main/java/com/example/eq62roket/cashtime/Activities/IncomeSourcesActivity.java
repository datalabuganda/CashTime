package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.eq62roket.cashtime.Helper.ParseExpenditureCategoryHelper;
import com.example.eq62roket.cashtime.Helper.ParseIncomeSourceHelper;
import com.example.eq62roket.cashtime.Models.ExpenditureCategories;
import com.example.eq62roket.cashtime.Models.IncomeSources;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.ExpenditureCategoryAdapter;
import com.example.eq62roket.cashtime.adapters.IncomeSourceAdapter;

import java.util.List;

public class IncomeSourcesActivity extends AppCompatActivity {

    private static String TAG = "IncomeSourcesActivity";

    List<IncomeSources> incomeSources = null;
    private RecyclerView recyclerView;
    private IncomeSourceAdapter mAdapter;
    ParseIncomeSourceHelper mParseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_sources);

        recyclerView = (RecyclerView)findViewById(R.id.income_sources_recycler_view);


        new ParseIncomeSourceHelper(getApplicationContext()).getSourcesFromParseDb(new ParseIncomeSourceHelper.OnReturnedSourcesListener() {
            @Override
            public void onResponse(List<IncomeSources> incomeSourcesList) {
                incomeSources = incomeSourcesList;

                mAdapter = new IncomeSourceAdapter(incomeSourcesList, new IncomeSourceAdapter.OnIncomeClickListener() {
                    @Override
                    public void onIncomeClick(IncomeSources incomeSources) {
                        Intent editIncomeSourceIntent = new Intent(IncomeSourcesActivity.this, EditIncomeSourceActivity.class);
                        editIncomeSourceIntent.putExtra("incomeName", incomeSources.getName());
                        editIncomeSourceIntent.putExtra("incomeParseId", incomeSources.getParseId());
                        startActivity(editIncomeSourceIntent);
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

            }
        });

    }
}
