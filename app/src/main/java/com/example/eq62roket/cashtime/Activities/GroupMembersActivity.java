package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.eq62roket.cashtime.Models.Members;
import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.MembersAdapter;

import java.util.ArrayList;
import java.util.List;

public class GroupMembersActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private List<Members> membersList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MembersAdapter mAdapter;
    private FloatingActionButton addMember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        addMember = (FloatingActionButton) findViewById(R.id.addNewMember);

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addMemberIntent = new Intent(GroupMembersActivity.this, AddNewMemberActivity.class);
                startActivity(addMemberIntent);
            }
        });

        mAdapter = new MembersAdapter(membersList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        prepareMemberData();
    }

    private void prepareMemberData() {
        Members member = new Members("Otim Tony", "0703457234");
        membersList.add(member);

        member = new Members("Nimukama Probuse", "0756212342");
        membersList.add(member);

        member = new Members("Muguya Ivan", "0733242874");
        membersList.add(member);

        member = new Members("Sytske Groenewald", "0756762368");
        membersList.add(member);

        member = new Members("Rik Linssen", "0716215342");
        membersList.add(member);

        member = new Members("Patricia Dekker", "0756212342");
        membersList.add(member);


        mAdapter.notifyDataSetChanged();
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
        ArrayList<Members> newList = new ArrayList<>();
        for (Members members : membersList){
            String name = members.getName().toLowerCase();
            if (name.contains(newText)){
                newList.add(members);
            }
        }
        mAdapter.setFilter(newList);
        return true;
    }
}
