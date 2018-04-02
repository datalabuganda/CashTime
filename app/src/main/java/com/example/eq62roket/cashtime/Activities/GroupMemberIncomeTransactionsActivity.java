package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Helper.ParseIncomeHelper;
import com.example.eq62roket.cashtime.Models.MembersIncome;

import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupMembersIncomeAdapter;

import java.util.List;

public class GroupMemberIncomeTransactionsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private List<MembersIncome> membersList = null;
    private RecyclerView recyclerView;
    private GroupMembersIncomeAdapter mAdapter;
    private FloatingActionButton gmitfab;
    private TextView membersText;

    private static String TAG = "GroupMemberIncomeTransactionsActivity";

    private String groupMemberIncomeParseId = "";
    private ParseIncomeHelper mParseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member_income_transactions);
        mParseHelper = new ParseIncomeHelper(this);

        gmitfab = (FloatingActionButton) findViewById(R.id.gmitfab);
        recyclerView = (RecyclerView) findViewById(R.id.group_member_list_income_recycler_view);

        Intent intent = getIntent();
        groupMemberIncomeParseId = intent.getStringExtra("groupMembersParseId");


        gmitfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupMemberIncomeTransactionsActivity.this, AddGroupMembersIncomeActivity.class);
                startActivity(intent);
            }
        });

        new ParseIncomeHelper(this).getGroupMemberIncomeMemberFromParseDb(new ParseIncomeHelper.OnReturnedGroupMemberIncomeListener() {
            @Override
            public void onResponse(List<MembersIncome> incomeList) {

                mAdapter = new GroupMembersIncomeAdapter(incomeList, new GroupMembersIncomeAdapter.OnGroupMemberClickListener() {
                    @Override
                    public void onGroupMemberClick(MembersIncome groupMemberIncome) {
                        Intent incomeIntent = new Intent(GroupMemberIncomeTransactionsActivity.this, AddGroupMembersIncomeActivity.class);
                        incomeIntent.putExtra("source", groupMemberIncome.getSource());
                        incomeIntent.putExtra("amount", groupMemberIncome.getAmount());

                        startActivity(incomeIntent);
                        finish();
                    }


                });

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG, "onFailure: " + error);
            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
