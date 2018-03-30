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
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseGroupHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupsListener;
import com.example.eq62roket.cashtime.Models.Group;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupAdapter;

import java.util.List;

public class GroupsActivity extends AppCompatActivity {

    private static final String TAG = "GroupsActivity";

    private GroupAdapter mGroupAdapter;

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        new ParseGroupHelper(GroupsActivity.this).getAllGroupsFromParseDb(new OnReturnedGroupsListener() {
            @Override
            public void onResponse(List<Group> groupsList) {
                mGroupAdapter = new GroupAdapter(groupsList, new GroupAdapter.OnGroupClickListener() {
                    @Override
                    public void onGroupSelected(Group group) {
//                        Intent groupDetailsIntent = new Intent(GroupsActivity.this, GroupMembersActivity.class);
//                        groupDetailsIntent.putExtra("groupParseId", group.getGroupParseId());
//                        groupDetailsIntent.putExtra("groupName", group.getGroupName());
//                        startActivity(groupDetailsIntent);
//                        finish();
                        Toast.makeText(GroupsActivity.this, "This Feature is still under development", Toast.LENGTH_SHORT).show();

                    }
                });
                mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

                mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent createGroupIntent = new Intent(GroupsActivity.this, AddNewGroupActivity.class);
                        startActivity(createGroupIntent);
                        finish();
                    }
                });

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(mGroupAdapter);
            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG, "onFailure: " + error);

            }
        });

    }


}
