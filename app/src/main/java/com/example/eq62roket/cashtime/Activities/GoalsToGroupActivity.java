package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.eq62roket.cashtime.Helper.ParseGroupHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupsListener;
import com.example.eq62roket.cashtime.Models.Group;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.GroupAdapter;

import java.util.List;

public class GoalsToGroupActivity extends AppCompatActivity {

    private static final String TAG = "GoalsToGroupActivity";

    private GroupAdapter mGroupAdapter;

    private RecyclerView mRecyclerView;
    private TextView emptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_to_group);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        emptyView = (TextView) findViewById(R.id.empty_view);

        new ParseGroupHelper(GoalsToGroupActivity.this).getAllGroupsFromParseDb(new OnReturnedGroupsListener() {
            @Override
            public void onResponse(List<Group> groupsList) {
                if (groupsList.isEmpty()){
                    mRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);

                    mGroupAdapter = new GroupAdapter(groupsList, new GroupAdapter.OnGroupClickListener() {
                        @Override
                        public void onGroupSelected(Group group) {
                        Intent groupDetailsIntent = new Intent(GoalsToGroupActivity.this, AddGroupGoalsActivity.class);
                        groupDetailsIntent.putExtra("groupLocalUniqueID", group.getLocalUniqueID());
                        groupDetailsIntent.putExtra("groupName", group.getGroupName());
//                        groupDetailsIntent.putExtra("groupCentreName", group.getGroupCentreName());
//                        groupDetailsIntent.putExtra("groupLocation", group.getLocationOfGroup());
//                        groupDetailsIntent.putExtra("groupMemberCount", String.valueOf(group.getGroupMemberCount()));
                        startActivity(groupDetailsIntent);
                        finish();

                        }
                    });
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setAdapter(mGroupAdapter);
                }
            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG, "onFailure: " + error);

            }
        });

    }


}
