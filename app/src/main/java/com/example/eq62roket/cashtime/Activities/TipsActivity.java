package com.example.eq62roket.cashtime.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.eq62roket.cashtime.Models.Tip;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.TipsAdapter;

import java.util.ArrayList;
import java.util.List;

public class TipsActivity extends AppCompatActivity {

    List<Tip> mTipList = new ArrayList<>();
    private TipsAdapter mTipsAdapter;

    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        mTipsAdapter = new TipsAdapter(mTipList);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mTipsAdapter);

        // prepare tip data
        prepareTipData();

    }

    private void prepareTipData(){
        Tip tip = null;
        tip = new Tip(
                "Buy BeeHive",
                "It is advisable to buy new beehives....this is because new beehives are new."
        );
        mTipList.add(tip);

        tip = new Tip(
                "Buy a goat",
                "Female goats are good goats because...female goats have breasts that give sweet milk."
        );
        mTipList.add(tip);

        tip = new Tip(
                "Increase Honey Sales",
                "Bees make more honey if the conditions are favorable...bees need water...flowers"
        );
        mTipList.add(tip);

        tip = new Tip(
                "Buy a goat",
                "Female goats are good goats because...female goats have breasts that give sweet milk."
        );
        mTipList.add(tip);


        tip = new Tip(
                "Buy BeeHive",
                "It is advisable to buy new beehives....this is because new beehives are new."
        );
        mTipList.add(tip);

        tip = new Tip(
                "Buy a goat",
                "Female goats are good goats because...female goats have breasts that give sweet milk."
        );
        mTipList.add(tip);

        tip = new Tip(
                "Buy BeeHive",
                "It is advisable to buy new beehives....this is because new beehives are new."
        );
        mTipList.add(tip);

        tip = new Tip(
                "Increase Honey Sales",
                "Bees make more honey if the conditions are favorable...bees need water...flowers"
        );
        mTipList.add(tip);

        mTipsAdapter.notifyDataSetChanged();
    }
}
