package com.example.eq62roket.CashTime.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eq62roket.CashTime.R;


public class HomeNeedsFragment extends Fragment {

    public HomeNeedsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_useful_tips, container, false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(R.string.home_needs_tip);
        return view;
    }

}
