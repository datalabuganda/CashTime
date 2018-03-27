package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.example.eq62roket.cashtime.Models.MembersGoals;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.MembersGoalsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MemberSavingToGoalsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private List<MembersGoals> membersGoalsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MembersGoalsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_saving_to_goals);

        recyclerView = (RecyclerView) findViewById(R.id.members_goals_recycler_view);

        mAdapter = new MembersGoalsAdapter(membersGoalsList, new MembersGoalsAdapter.OnMemberGoalClickListener() {
            @Override
            public void onMemberGoalClick(MembersGoals membersGoals) {
                // show Add Member Saving Form
                Intent intent = new Intent(MemberSavingToGoalsActivity.this, AddMemberSavingsActivity.class);
                intent.putExtra("goalName", membersGoals.getMemberGoalName());
                intent.putExtra("memberName",membersGoals.getMemberName());
                startActivity(intent);
                finish();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<MembersGoals> newList = new ArrayList<>();
        for (MembersGoals membersGoals : membersGoalsList){
            String name = membersGoals.getMemberName().toLowerCase();
            String goal = membersGoals.getMemberGoalName().toLowerCase();
            String amount = membersGoals.getMemberGoalAmount().toLowerCase();
            String date = membersGoals.getMemberGoalDueDate().toLowerCase();
            if (name.contains(newText)){
                newList.add(membersGoals);
            }else if (goal.contains(newText)){
                newList.add(membersGoals);
            }else if (amount.contains(newText)){
                newList.add(membersGoals);
            }else if (date.contains(newText)){
                newList.add(membersGoals);
            }
        }
        mAdapter.setFilter(newList);
        return true;
    }

}
