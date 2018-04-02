package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.eq62roket.cashtime.Helper.ParseGroupHelper;
import com.example.eq62roket.cashtime.Helper.ParseUserHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupMemberListener;
import com.example.eq62roket.cashtime.Models.GroupMember;
import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.MembersAdapter;

import java.util.List;

public class GroupMembersIncomeListActivity extends AppCompatActivity {
    private static String TAG = "GroupMembersIncomeListActivity";
    private List<GroupMember> mGroupMemberUsers = null;
    private RecyclerView mRecyclerView;
    private MembersAdapter mMembersAdapter;
    FloatingActionButton fabAddGroupMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members_income_list);
        mRecyclerView = (RecyclerView)findViewById(R.id.group_member_income_list_recycler_view);

        new ParseGroupHelper(this).getAllMembersFromParseDb(new OnReturnedGroupMemberListener() {
            @Override
            public void onResponse(List<GroupMember> memberList) {

                mMembersAdapter = new MembersAdapter(memberList, new MembersAdapter.OnGroupMemberClickListener() {
                    @Override
                    public void onGroupMemberClick(GroupMember groupMember) {
                        Intent editUserIntent = new Intent(GroupMembersIncomeListActivity.this, AddGroupMembersIncomeActivity.class);
                        editUserIntent.putExtra("userName", groupMember.getMemberUsername());
                        editUserIntent.putExtra("phoneNumber", groupMember.getMemberPhoneNumber());
                        editUserIntent.putExtra("business",groupMember.getMemberBusiness());
                        editUserIntent.putExtra("gender", groupMember.getMemberGender());
                        editUserIntent.putExtra("nationality", groupMember.getMemberNationality());
                        editUserIntent.putExtra("location", groupMember.getMemberLocation());
                        editUserIntent.putExtra("educationLevel", groupMember.getMemberEducationLevel());
                        editUserIntent.putExtra("household",groupMember.getMemberHousehold());
                        editUserIntent.putExtra("parseId", groupMember.getMemberParseId());

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
