package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Helper.ParseGroupHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupMemberListener;
import com.example.eq62roket.cashtime.Models.GroupMember;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.MembersAdapter;

import java.util.List;

public class GroupMembersIncomeListActivity extends AppCompatActivity {
    private static String TAG = "GroupMembersIncomeListActivity";
    private List<GroupMember> mGroupMemberUsers = null;
    private RecyclerView mRecyclerView;
    private MembersAdapter mMembersAdapter;
    private TextView emptyView;
    FloatingActionButton fabAddGroupMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members_income_list);
        mRecyclerView = (RecyclerView)findViewById(R.id.group_member_income_list_recycler_view);
        emptyView = (TextView)findViewById(R.id.empty_view);
        new ParseGroupHelper(this).getAllMembersFromParseDb(new OnReturnedGroupMemberListener() {
            @Override
            public void onResponse(List<GroupMember> memberList) {
                if (memberList.isEmpty()){
                    mRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }else {
                    emptyView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    mMembersAdapter = new MembersAdapter(memberList, new MembersAdapter.OnGroupMemberClickListener() {
                        @Override
                        public void onGroupMemberClick(GroupMember groupMember) {
                            Intent editUserIntent = new Intent(GroupMembersIncomeListActivity.this, AddGroupMembersIncomeActivity.class);
                            editUserIntent.putExtra("userName", groupMember.getMemberUsername());
                            editUserIntent.putExtra("groupMemberLocalUniqueID", groupMember.getLocalUniqueID());

                            startActivity(editUserIntent);
                            finish();
                        }

                    });

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                    mRecyclerView.setAdapter(mMembersAdapter);
                }

            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG, "onFailure: " + error);
            }
        });

    }
}
