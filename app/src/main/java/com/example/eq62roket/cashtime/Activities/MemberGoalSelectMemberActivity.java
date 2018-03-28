package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.MembersAdapter;

import java.util.ArrayList;
import java.util.List;

public class MemberGoalSelectMemberActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private List<User> mGroupMemberUsers = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MembersAdapter mMembersAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mMembersAdapter = new MembersAdapter(mGroupMemberUsers, new MembersAdapter.OnGroupMemberClickListener() {
            @Override
            public void onGroupMemberClick(User groupMemberUser) {
                Intent addMemberGoalIntent = new Intent(MemberGoalSelectMemberActivity.this, AddMembersGoalsActivity.class);
                addMemberGoalIntent.putExtra("groupMemberName", groupMemberUser.getUserName());
                startActivity(addMemberGoalIntent);
                finish();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mMembersAdapter);

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
        ArrayList<User> groupMemberUsers = new ArrayList<>();
        for (User groupMemberUser : mGroupMemberUsers){
            String name = groupMemberUser.getUserName().toLowerCase();
            if (name.contains(newText)){
                groupMemberUsers.add(groupMemberUser);
            }
        }
        mMembersAdapter.setFilter(groupMemberUsers);
        return true;
    }
}
