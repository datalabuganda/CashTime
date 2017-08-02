package com.cashtime.cashtimefinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cashtime.helper.PersonCrud;
import com.cashtime.helper.SavingCrud2;
import com.cashtime.models.Person;
import com.cashtime.models.Saving;
import com.numetriclabz.numandroidcharts.ChartData;
import com.numetriclabz.numandroidcharts.FunnelChart;

import java.util.ArrayList;
import java.util.List;

public class FunnelActivity extends AppCompatActivity {

    private static final String TAG = "FunnelActivity";

    FunnelChart funnelChart;
    SavingCrud2 savingCrud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funnel);

        Log.d(TAG, "onCreate started");

       /* funnelChart = (FunnelChart) findViewById(R.id.pyramid);
        savingCrud = new SavingCrud2(this);

        List<Saving> saving = savingCrud.getAllSavings();
        Log.d(TAG, "saving" + saving);
        List<ChartData> newData = new ArrayList<>();


*//*
        Log.d(TAG, "before if statement");
        if (saving != null){
            Log.d(TAG, "before in if statement");
            for (int i = 0; i < saving.size(); i++){
                Log.d(TAG, "before in for statement");
                newData.add(new ChartData((float) saving.get(i).getId()));
//              Toast.makeText(this, "We in for loop", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "for loop runnning" + i);
            }
        }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            funnelChart.setData(newData);*//*



        List<ChartData> values = new ArrayList<>();
        values.add(new ChartData("unique Visits", 100000));
        values.add(new ChartData("Visitors", 3300));
       *//* values.add(new ChartData("Visitors", 4000));
        values.add(new ChartData("constant visits", 23000));
        values.add(new ChartData("constant", 2300));
        values.add(new ChartData("New Visitors", 23000));*//*

        funnelChart.setData(values);
        */
    }
}
