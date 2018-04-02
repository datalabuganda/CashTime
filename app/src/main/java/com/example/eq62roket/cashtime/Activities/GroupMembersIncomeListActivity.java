package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.eq62roket.cashtime.Helper.ParseUserHelper;
import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.MembersAdapter;

import java.util.List;

public class GroupMembersIncomeListActivity extends AppCompatActivity {
    private static String TAG = "GroupMembersIncomeListActivity";
    private List<User> mGroupMemberUsers = null;
    private RecyclerView mRecyclerView;
    private MembersAdapter mMembersAdapter;
    FloatingActionButton fabAddGroupMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members_income_list);
        mRecyclerView = (RecyclerView)findViewById(R.id.group_member_income_list_recycler_view);

        new ParseUserHelper(this).getUserFromParseDb(new ParseUserHelper.OnReturnedUserListener() {
            @Override
            public void onResponse(List<User> usersList) {

                mMembersAdapter = new MembersAdapter(usersList, new MembersAdapter.OnGroupMemberClickListener() {
                    @Override
                    public void onGroupMemberClick(User groupMemberUser) {
                        Intent editUserIntent = new Intent(GroupMembersIncomeListActivity.this, AddGroupMembersIncomeActivity.class);
                        editUserIntent.putExtra("userName", groupMemberUser.getUserName());
                        editUserIntent.putExtra("phoneNumber", groupMemberUser.getPhoneNumber());
                        editUserIntent.putExtra("business",groupMemberUser.getBusiness());
                        editUserIntent.putExtra("gender", groupMemberUser.getGender());
                        editUserIntent.putExtra("nationality", groupMemberUser.getNationality());
                        editUserIntent.putExtra("location", groupMemberUser.getLocation());
                        editUserIntent.putExtra("educationLevel", groupMemberUser.getEducationLevel());
                        editUserIntent.putExtra("household",groupMemberUser.getHousehold());
                        editUserIntent.putExtra("parseId", groupMemberUser.getParseId());

                        startActivity(editUserIntent);
                        finish();
                    }

                });

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                mRecyclerView.setAdapter(mMembersAdapter);

            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG, "onFailure: " + error);
            }
        });

    }
}
