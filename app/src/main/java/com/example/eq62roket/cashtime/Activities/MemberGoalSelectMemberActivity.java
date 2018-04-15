package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Helper.ParseGroupHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupMemberListener;
import com.example.eq62roket.cashtime.Models.GroupMember;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.MembersAdapter;

import java.util.ArrayList;
import java.util.List;

public class MemberGoalSelectMemberActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private List<GroupMember> mGroupMemberUsers = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MembersAdapter mMembersAdapter;
    private TextView emptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_goal_select_member);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        emptyView = (TextView) findViewById(R.id.empty_view);

        new ParseGroupHelper(MemberGoalSelectMemberActivity.this)
                .getAllMembersFromParseDb(new OnReturnedGroupMemberListener() {
                    @Override
                    public void onResponse(List<GroupMember> groupMembersList) {

                        if (groupMembersList.isEmpty()){
                            mRecyclerView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                        }else {
                            mGroupMemberUsers = groupMembersList;

                            emptyView.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mMembersAdapter = new MembersAdapter(groupMembersList, new MembersAdapter.OnGroupMemberClickListener() {
                                @Override
                                public void onGroupMemberClick(GroupMember groupMemberUser) {
                                    Intent addMemberGoalIntent = new Intent(MemberGoalSelectMemberActivity.this, AddMembersGoalsActivity.class);
                                    addMemberGoalIntent.putExtra("groupMemberName", groupMemberUser.getMemberUsername());
                                    addMemberGoalIntent.putExtra("groupMemberGroupId", groupMemberUser.getMemberGroupLocalUniqueId());
                                    addMemberGoalIntent.putExtra("groupMemberLocalUniqueID", groupMemberUser.getLocalUniqueID());
                                    startActivity(addMemberGoalIntent);
                                }
                            });

                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            mRecyclerView.setLayoutManager(mLayoutManager);
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mMembersAdapter.notifyDataSetChanged();
                            mRecyclerView.setAdapter(mMembersAdapter);
                        }
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.members,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setBackgroundColor(Color.WHITE);
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setTextColor(Color.BLACK);
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(Color.GRAY);

        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        ArrayList<GroupMember> groupMemberUsers = new ArrayList<>();
        for (GroupMember groupMemberUser : mGroupMemberUsers){
            String name = groupMemberUser.getMemberUsername().toLowerCase();
            if (name.contains(newText)){
                groupMemberUsers.add(groupMemberUser);
            }
        }
        mMembersAdapter.setFilter(groupMemberUsers);
        return true;
    }
}
