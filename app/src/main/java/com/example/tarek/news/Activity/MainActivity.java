package com.example.tarek.news.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tarek.news.R;
import com.example.tarek.news.fragment.ArabicFragment;
import com.example.tarek.news.fragment.BusinessFragment;
import com.example.tarek.news.fragment.EgyptFragment;
import com.example.tarek.news.fragment.FootBallFragment;
import com.example.tarek.news.fragment.HomeFragment;
import com.example.tarek.news.fragment.ScienceFragment;
import com.example.tarek.news.fragment.SectionPageAdapter;
import com.example.tarek.news.fragment.TechFragment;
import com.example.tarek.news.fragment.WorldFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SectionPageAdapter sectionPageAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {


            sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
            viewPager = (ViewPager) findViewById(R.id.container);
            setSectionPageAdapter(viewPager);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
        } catch (Exception e) {


        }
    }

    private void setSectionPageAdapter(ViewPager viewPager) {
        try {


            SectionPageAdapter pageAdapter = new SectionPageAdapter(getSupportFragmentManager());

            pageAdapter.addFragment(new HomeFragment(), "Home");
            pageAdapter.addFragment(new WorldFragment(), "World News");

            pageAdapter.addFragment(new EgyptFragment(), "Egypt News");
            pageAdapter.addFragment(new ArabicFragment(), "Arabic News");

            pageAdapter.addFragment(new FootBallFragment(), "FootBall");
            pageAdapter.addFragment(new ScienceFragment(), "Science");
            pageAdapter.addFragment(new TechFragment(), "Technology");
            pageAdapter.addFragment(new BusinessFragment(), "Business");

            viewPager.setAdapter(pageAdapter);
        } catch (Exception e) {

        }
    }


}