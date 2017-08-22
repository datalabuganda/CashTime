package com.example.eq62roket.CashTime.activities;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.eq62roket.CashTime.Fragments.EducationFragment;
import com.example.eq62roket.CashTime.Fragments.HealthFragment;
import com.example.eq62roket.CashTime.Fragments.HomeNeedsFragment;
import com.example.eq62roket.CashTime.Fragments.OthersFragment;
import com.example.eq62roket.CashTime.Fragments.SavingsFragment;
import com.example.eq62roket.CashTime.Fragments.TransportFragment;
import com.example.eq62roket.CashTime.R;

import java.util.ArrayList;
import java.util.List;

public class TipsActivity extends AppCompatActivity {


    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useful_tips);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TransportFragment(), "Transport");
        adapter.addFragment(new SavingsFragment(), "Savings");
        adapter.addFragment(new HealthFragment(), "Health");
        adapter.addFragment(new EducationFragment(), "Education");
        adapter.addFragment(new HomeNeedsFragment(), "Home Needs");
        adapter.addFragment(new OthersFragment(), "Others");
        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager){
            super(manager);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position){
            return mFragmentList.get(position);
        }

        @Override
        public int getCount(){
            return mFragmentList.size();
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }
    }
}
