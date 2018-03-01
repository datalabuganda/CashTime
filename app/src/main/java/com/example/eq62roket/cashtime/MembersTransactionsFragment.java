package com.example.eq62roket.cashtime;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.eq62roket.cashtime.Models.GroupTransactions;
import com.example.eq62roket.cashtime.Models.MembersTransactions;
import com.example.eq62roket.cashtime.adapters.GroupGoals;
import com.example.eq62roket.cashtime.adapters.GroupGoalsAdapter;
import com.example.eq62roket.cashtime.adapters.MembersTransactionsAdapter;

import java.util.ArrayList;
import java.util.List;


public class MembersTransactionsFragment extends Fragment implements SearchView.OnQueryTextListener{
    private List<MembersTransactions> membersTransactionsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MembersTransactionsAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_members_transactions, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.members_transaction_recycler_view);

        mAdapter = new MembersTransactionsAdapter(membersTransactionsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        prepareMembersTransactionsData();

        return rootView;
    }

    private void prepareMembersTransactionsData() {
        MembersTransactions membersTransactions = new MembersTransactions("Otim Tony", "Buy 5 hives", "500000", "12/2/2019");
        membersTransactionsList.add(membersTransactions);

        membersTransactions = new MembersTransactions("Nimukama Probuse", "By a Cow", "703000", "02/06/2019");
        membersTransactionsList.add(membersTransactions);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.members_transactions, menu);

        MenuItem menuItem = menu.findItem(R.id.membersTransactionsSearch);

        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setBackgroundColor(Color.WHITE);
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setTextColor(Color.BLACK);
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(Color.GRAY);

        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        ArrayList<MembersTransactions> newList = new ArrayList<>();
        for (MembersTransactions membersTransactions : membersTransactionsList){
            String name = membersTransactions.getName().toLowerCase();
            String goal = membersTransactions.getGoal().toLowerCase();
            String amount = membersTransactions.getAmount().toLowerCase();
            String date = membersTransactions.getDate().toLowerCase();
            if (name.contains(newText)){
                newList.add(membersTransactions);
            }else if (goal.contains(newText)){
                newList.add(membersTransactions);
            }else if (amount.contains(newText)){
                newList.add(membersTransactions);
            }else if (date.contains(newText)){
                newList.add(membersTransactions);
            }
        }
        mAdapter.setFilter(newList);
        return true;
    }
}
