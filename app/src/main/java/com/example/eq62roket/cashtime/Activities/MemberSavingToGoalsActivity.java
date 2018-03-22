package com.example.eq62roket.cashtime.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

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
                Toast.makeText(MemberSavingToGoalsActivity.this, "Goal " + membersGoals.getGoal() + " Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

        prepareMembersGoalsData();
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
            String name = membersGoals.getName().toLowerCase();
            String goal = membersGoals.getGoal().toLowerCase();
            String amount = membersGoals.getAmount().toLowerCase();
            String date = membersGoals.getDate().toLowerCase();
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

    private void prepareMembersGoalsData() {
        MembersGoals membersGoals = new MembersGoals("Otim Tony", "Buy 5 hives", "500000", "12/2/2019");
        membersGoalsList.add(membersGoals);

        membersGoals = new MembersGoals("Nimukama Probuse", "Buy a Cow", "703000", "02/06/2019");
        membersGoalsList.add(membersGoals);

        membersGoals = new MembersGoals("Muguya Ivan", "Buy 10 axes", "3000", "02/06/2018");
        membersGoalsList.add(membersGoals);

        membersGoals = new MembersGoals("Nimukama Probuse", "Buy 2 Hives", "500000", "02/01/2019");
        membersGoalsList.add(membersGoals);

        membersGoals = new MembersGoals("Rik Linssen", "Buy Casava stems", "300000", "11/05/2019");
        membersGoalsList.add(membersGoals);

        membersGoals = new MembersGoals("Nimukama Probuse", "Buy 2 hoes", "13000", "04/08/2019");
        membersGoalsList.add(membersGoals);

        membersGoals = new MembersGoals("Nimukama Probuse", "Buy a Cow", "703000", "02/06/2019");
        membersGoalsList.add(membersGoals);

        membersGoals = new MembersGoals("Nimukama Probuse", "Buy a Cow", "703000", "02/06/2019");
        membersGoalsList.add(membersGoals);
        mAdapter.notifyDataSetChanged();
    }
}
