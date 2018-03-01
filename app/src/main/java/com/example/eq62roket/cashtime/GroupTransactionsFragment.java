package com.example.eq62roket.cashtime;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
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
import com.example.eq62roket.cashtime.adapters.GroupTransactionsAdapter;
import com.example.eq62roket.cashtime.adapters.MembersTransactionsAdapter;

import java.util.ArrayList;
import java.util.List;


public class GroupTransactionsFragment extends Fragment implements SearchView.OnQueryTextListener{
    private List<GroupTransactions> groupTransactionsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GroupTransactionsAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_group_transactions, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.group_transaction_recycler_view);

        mAdapter = new GroupTransactionsAdapter(groupTransactionsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        prepareGroupTransactionsData();

        return rootView;
    }

    private void prepareGroupTransactionsData() {
        GroupTransactions groupTransactions = new GroupTransactions("Otim Tony", "Buy 5 hives", "500000", "12/2/2019");
        groupTransactionsList.add(groupTransactions);

        groupTransactions = new GroupTransactions("Nimukama Probuse", "Buy a Cow", "703000", "02/06/2019");
        groupTransactionsList.add(groupTransactions);

        groupTransactions = new GroupTransactions("Muguya Ivan", "Buy 10 axes", "3000", "02/06/2018");
        groupTransactionsList.add(groupTransactions);

        groupTransactions = new GroupTransactions("Nimukama Probuse", "Buy 2 Hives", "500000", "02/01/2019");
        groupTransactionsList.add(groupTransactions);

        groupTransactions = new GroupTransactions("Rik Linssen", "Buy Casava stems", "300000", "11/05/2019");
        groupTransactionsList.add(groupTransactions);

        groupTransactions = new GroupTransactions("Nimukama Probuse", "Buy 2 hoes", "13000", "04/08/2019");
        groupTransactionsList.add(groupTransactions);

        groupTransactions = new GroupTransactions("Nimukama Probuse", "Buy a Cow", "703000", "02/06/2019");
        groupTransactionsList.add(groupTransactions);

        groupTransactions = new GroupTransactions("Nimukama Probuse", "Buy a Cow", "703000", "02/06/2019");
        groupTransactionsList.add(groupTransactions);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.group_transactions, menu);

        MenuItem menuItem = menu.findItem(R.id.groupTransactionsSearch);

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
        ArrayList<GroupTransactions> newList = new ArrayList<>();
        for (GroupTransactions groupTransactions : groupTransactionsList){
            String name = groupTransactions.getName().toLowerCase();
            String goal = groupTransactions.getGoal().toLowerCase();
            String amount = groupTransactions.getAmount().toLowerCase();
            String date = groupTransactions.getDate().toLowerCase();
            if (name.contains(newText)){
                newList.add(groupTransactions);
            }else if (goal.contains(newText)){
                newList.add(groupTransactions);
            }else if (amount.contains(newText)){
                newList.add(groupTransactions);
            }else if (date.contains(newText)){
                newList.add(groupTransactions);
            }
        }
        mAdapter.setFilter(newList);
        return true;
    }
}