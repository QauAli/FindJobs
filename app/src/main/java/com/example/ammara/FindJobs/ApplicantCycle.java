package com.example.ammara.FindJobs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import Api.ImageLoader;
import Fragments.CoversationsFragment;
import Fragments.Jobs;
import Fragments.Wishlist;

public class ApplicantCycle extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView shown_name,shown_email;
    private ImageView profileImageView;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_cycle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSectionsPagerAdapter = new ApplicantCycle.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewPager.setOnTouchListener((v, event) -> true);


        SharedPreferences prefs = getSharedPreferences("credentials", MODE_PRIVATE);
        String name = prefs.getString("name", "No");
        String email = prefs.getString("email", "No");
        String imageName = prefs.getString("imageName", "No");

        View headerView = navigationView.getHeaderView(0);
        shown_email  = (TextView) headerView.findViewById(R.id.shown_email);
        shown_name  = (TextView) headerView.findViewById(R.id.shown_name);
        profileImageView = headerView.findViewById(R.id.imageView);
        ImageLoader.LoadImage(profileImageView,imageName,ApplicantCycle.this);

        shown_name.setText(name);
        shown_email.setText(email);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation_advertiser_cycle view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            // Handle the camera action
        } else if (id == R.id.nav_logout) {
            SharedPreferences.Editor editor = ApplicantCycle.this.getSharedPreferences("credentials", MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();
            Intent myIntent = new Intent(ApplicantCycle.this, Login.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ApplicantCycle.this.startActivity(myIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new Jobs();
                case 1:
                    return new Wishlist();
                case 2:
                    return new CoversationsFragment();
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_jobs:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_conversation_applicant:
                    mViewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_wishlist:
                    mViewPager.setCurrentItem(1);
                    return true;
            }
            return false;
        }
    };

}
