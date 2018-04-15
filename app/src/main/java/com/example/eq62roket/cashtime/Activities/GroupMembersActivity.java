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
import android.widget.TextView;

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
    private TextView emptyView;
    private MembersAdapter mMembersAdapter;
    private FloatingActionButton fabAddGroupMember;
    private String groupLocalUniqueID;
    private String groupCentreName;
    private String groupLocation;
    private String nameOfGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);

        Intent groupDetailIntent = getIntent();
        nameOfGroup = groupDetailIntent.getStringExtra("groupName");
        groupLocalUniqueID = groupDetailIntent.getStringExtra("groupLocalUniqueID");
        groupCentreName = groupDetailIntent.getStringExtra("groupCentreName");
        groupLocation = groupDetailIntent.getStringExtra("groupLocation");
        final String groupMemberCount = groupDetailIntent.getStringExtra("groupMemberCount");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(nameOfGroup);
        actionBar.setHomeButtonEnabled(true);

        fabAddGroupMember = (FloatingActionButton)findViewById(R.id.addNewGroupMember);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        emptyView = (TextView) findViewById(R.id.empty_view);

        fabAddGroupMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupMembersActivity.this, AddNewMemberActivity.class);
                intent.putExtra("groupLocalUniqueID", groupLocalUniqueID);
                startActivity(intent);
                finish();
            }
        });

        new ParseGroupHelper(GroupMembersActivity.this)
                .getGroupMembersFromParseDb(groupLocalUniqueID, new OnReturnedGroupMemberListener() {
                    @Override
                    public void onResponse(List<GroupMember> userList) {
                        if (userList.isEmpty()){
                            mRecyclerView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                        }else {
                            emptyView.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);

                            mGroupMemberUsers = userList;
                            mMembersAdapter = new MembersAdapter(userList, new MembersAdapter.OnGroupMemberClickListener() {
                                @Override
                                public void onGroupMemberClick(GroupMember groupMemberUser) {
                                    Intent editGroupMemberIntent = new Intent(GroupMembersActivity.this, EditGroupMemberActivity.class);
                                    editGroupMemberIntent.putExtra("groupMemberName", groupMemberUser.getMemberUsername());
                                    editGroupMemberIntent.putExtra("groupMemberParseId", groupMemberUser.getMemberParseId());
                                    editGroupMemberIntent.putExtra("groupMemberGroupId", groupMemberUser.getMemberGroupLocalUniqueId());
                                    editGroupMemberIntent.putExtra("groupMemberCount", groupMemberCount);
                                    startActivity(editGroupMemberIntent);
                                    finish();
                                }
                            });
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            mRecyclerView.setLayoutManager(mLayoutManager);
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.addItemDecoration(new DividerItemDecoration(GroupMembersActivity.this, LinearLayoutManager.VERTICAL));
                            mMembersAdapter.notifyDataSetChanged();
                            mRecyclerView.setAdapter(mMembersAdapter);
                        }

                    }

                    @Override
                    public void onFailure(String error) {

                        Log.d(TAG, "onFailure: " + error);
                    }
                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.members, menu);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.editGroup:
                startEditGroupActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startEditGroupActivity(){
        Intent editGroupIntent = new Intent(GroupMembersActivity.this, EditGroupActivity.class);
        editGroupIntent.putExtra("groupLocalUniqueID", groupLocalUniqueID);
        editGroupIntent.putExtra("groupCentreName", groupCentreName);
        editGroupIntent.putExtra("groupLocation", groupLocation);
        editGroupIntent.putExtra("nameOfGroup", nameOfGroup);
        startActivity(editGroupIntent);
        finish();
    }
}
