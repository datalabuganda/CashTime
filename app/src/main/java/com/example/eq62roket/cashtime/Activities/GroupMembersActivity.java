package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
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
import com.example.eq62roket.cashtime.Helper.DatabaseHelper;
import com.example.eq62roket.cashtime.adapters.DatabaseAdapter;
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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        addMember = (FloatingActionButton) findViewById(R.id.addNewMember);

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addMemberIntent = new Intent(GroupMembersActivity.this, AddNewMemberActivity.class);
                startActivity(addMemberIntent);
            }
        });

        mAdapter = new MembersAdapter(this,membersList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

//        recyclerView.setAdapter(mAdapter);
        prepareMemberData();


//        prepareMemberData();
    }

    private void prepareMemberData(){
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        databaseAdapter.close();

        membersList.clear();
        Cursor cursor = databaseHelper.getAllMembers();
//        Cursor cursor=databaseAdapter.getAllGroupMembers();
        //loop through the data adding to arraylist
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String phone = cursor.getString(2);

            Members members = new Members(name,phone,id);
            membersList.add(members);
        }


        if(!(membersList.size()<1)){
            recyclerView.setAdapter(mAdapter);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        prepareMemberData();

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