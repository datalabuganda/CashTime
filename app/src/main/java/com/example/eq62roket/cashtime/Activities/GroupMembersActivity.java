package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.MembersAdapter;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class GroupMembersActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private List<User> mGroupMemberUsers = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MembersAdapter mMembersAdapter;
    FloatingActionButton fabAddGroupMember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);
        Parse.initialize(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);

        fabAddGroupMember = (FloatingActionButton)findViewById(R.id.addNewGroupMember);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mMembersAdapter = new MembersAdapter(mGroupMemberUsers, new MembersAdapter.OnGroupMemberClickListener() {
            @Override
            public void onGroupMemberClick(User groupMemberUser) {
//                Intent addMemberGoalIntent = new Intent(GroupMembersActivity.this, AddMembersGoalsActivity.class);
//                addMemberGoalIntent.putExtra("groupMemberName", groupMemberUser.getUserName());
//                startActivity(addMemberGoalIntent);
//                finish();
                Toast.makeText(GroupMembersActivity.this, "Edit Me", Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mMembersAdapter);

        fabAddGroupMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupMembersActivity.this, AddNewMemberActivity.class);
                startActivity(intent);
            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Members");
        query.getInBackground("nBVOTCFNso", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, com.parse.ParseException e) {
                if (e == null) {
                    String t = parseObject.getString("username");
                    Log.d("Username", " username" + t);

                } else {
                }
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
