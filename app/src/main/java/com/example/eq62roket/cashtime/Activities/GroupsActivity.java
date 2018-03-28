package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Models.Group;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupAdapter;

import java.util.ArrayList;
import java.util.List;

public class GroupsActivity extends AppCompatActivity {

    private static final String TAG = "TipsActivity";

    List<Group> mGroupList = new ArrayList<>();
    private GroupAdapter mGroupAdapter;

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        mGroupAdapter = new GroupAdapter(mGroupList, new GroupAdapter.OnGroupClickListener() {
            @Override
            public void onGroupSelected(Group group) {
                Toast.makeText(GroupsActivity.this, "GoalName>>>" + group.getGroupName(), Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupsActivity.this, AddTipsActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mGroupAdapter);

        prepareGroupData();
    }

    private void prepareGroupData(){
       Group group = new Group();
       group.setGroupName("chwezi bee keepers");
       group.setDateCreated("29/03/2018");
       group.setGroupMemberCount(3);

       mGroupList.add(group);
       mGroupAdapter.notifyDataSetChanged();
    }
}
