package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Models.Tip;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.TipsAdapter;

import java.util.ArrayList;
import java.util.List;

public class TipsActivity extends AppCompatActivity {

    private static final String TAG = "TipsActivity";

    List<Tip> mTipList = new ArrayList<>();
    private TipsAdapter mTipsAdapter;

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        mTipsAdapter = new TipsAdapter(mTipList, new TipsAdapter.OnTipClickListener() {
            @Override
            public void onTipSelected(Tip tip) {
                Toast.makeText(TipsActivity.this, "GoalName " + tip.getGoalName(), Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsActivity.this, AddTipsActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mTipsAdapter);

    }

}
