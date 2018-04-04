package com.example.eq62roket.cashtime.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.eq62roket.cashtime.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class GroupIncomeAnalysisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_income_analysis);

        totalGroupIncome();
    }

    public void totalGroupIncome(){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GroupIncome");
        query.findInBackground(new FindCallback<ParseObject>() {
            int sum;
            public void done(List<ParseObject> amount, ParseException e) {
                for (ParseObject p: amount){
                    sum += Integer.valueOf(p.getString("amount"));
                    Log.d("Sum", "Summmmm " + sum);
                }
                if (e == null) {

                    Log.d("score", "Retrieved " + amount.size() + "amount");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }


}


