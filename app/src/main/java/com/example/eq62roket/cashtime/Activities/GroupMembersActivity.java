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

import com.example.eq62roket.cashtime.Helper.ParseGroupHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupMemberListener;
import com.example.eq62roket.cashtime.Models.GroupMember;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.MembersAdapter;

import java.util.ArrayList;
import java.util.List;

public class GroupMembersActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private static final String TAG = "GroupMembersActivity";

    private List<GroupMember> mGroupMemberUsers = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MembersAdapter mMembersAdapter;
    FloatingActionButton fabAddGroupMember;

    private String groupParseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);

        Intent groupDetailIntent = getIntent();
        String nameOfGroup = groupDetailIntent.getStringExtra("groupName");
        groupParseId = groupDetailIntent.getStringExtra("groupParseId");
        final String groupMemberCount = groupDetailIntent.getStringExtra("groupMemberCount");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(nameOfGroup);
        actionBar.setHomeButtonEnabled(true);

        fabAddGroupMember = (FloatingActionButton)findViewById(R.id.addNewGroupMember);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        fabAddGroupMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupMembersActivity.this, AddNewMemberActivity.class);
                intent.putExtra("groupParseId", groupParseId);
                startActivity(intent);
                finish();
            }
        });

        new ParseGroupHelper(GroupMembersActivity.this)
                .getGroupMembersFromParseDb(groupParseId, new OnReturnedGroupMemberListener() {
                    @Override
                    public void onResponse(List<GroupMember> userList) {
                        mGroupMemberUsers = userList;
                        mMembersAdapter = new MembersAdapter(userList, new MembersAdapter.OnGroupMemberClickListener() {
                            @Override
                            public void onGroupMemberClick(GroupMember groupMemberUser) {
                                Intent editGroupMemberIntent = new Intent(GroupMembersActivity.this, EditGroupMemberActivity.class);
                                editGroupMemberIntent.putExtra("groupMemberName", groupMemberUser.getMemberUsername());
                                editGroupMemberIntent.putExtra("groupMemberParseId", groupMemberUser.getMemberParseId());
                                editGroupMemberIntent.putExtra("groupMemberGroupId", groupMemberUser.getMemberGroupId());
                                editGroupMemberIntent.putExtra("groupMemberCount", groupMemberCount);
                                Log.d(TAG, "onGroupMemberClick: " + groupMemberUser.getMemberGroupId());
                                startActivity(editGroupMemberIntent);
                                finish();
                            }
                        });
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                        mRecyclerView.addItemDecoration(new DividerItemDecoration(GroupMembersActivity.this, LinearLayoutManager.VERTICAL));
                        mRecyclerView.setAdapter(mMembersAdapter);

                    }

                    @Override
                    public void onFailure(String error) {

                        Log.d(TAG, "onFailure: " + error);
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
