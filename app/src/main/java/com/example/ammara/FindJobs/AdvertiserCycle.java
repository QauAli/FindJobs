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

import RestfullServices.ImageLibrary;
import Fragments.AdvertisementF;
import Fragments.Applications;
import Fragments.Coversations;
import Fragments.Profiles;

public class AdvertiserCycle extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView shown_name,shown_email;
    private ImageView profileImageView;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Profiles profiles = new Profiles();
    private AdvertisementF advertisementF = new AdvertisementF();
    private Coversations coversations = new Coversations();
    private Applications applications = new Applications();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser_cycle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        SharedPreferences prefs = getSharedPreferences(Constants.file, MODE_PRIVATE);
        String name = prefs.getString("name", "No");
        String email = prefs.getString("email", "No");
        String imageName = prefs.getString("imageName", "No");

        View headerView = navigationView.getHeaderView(0);
        shown_email  = (TextView) headerView.findViewById(R.id.shown_email);
        shown_name  = (TextView) headerView.findViewById(R.id.shown_name);
        profileImageView = headerView.findViewById(R.id.imageView);
        ImageLibrary.LoadImage(profileImageView,imageName,AdvertiserCycle.this);
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

        if (id == R.id.nav_update_account) {
            Intent myIntent = new Intent(AdvertiserCycle.this, UpdateAdvertiser.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            AdvertiserCycle.this.startActivity(myIntent);

            // Handle the camera action
        }
        else if (id == R.id.nav_logout) {
            SharedPreferences.Editor editor = AdvertiserCycle.this.getSharedPreferences(Constants.file, MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();
            Intent myIntent = new Intent(AdvertiserCycle.this, Login.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            AdvertiserCycle.this.startActivity(myIntent);
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
                    return profiles;
                case 1:
                    return advertisementF;
                case 2:
                    return coversations;
                case 3:
                    return applications;
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_advertisement:
                    mViewPager.setCurrentItem(1);
                   // mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_conversation_advertiser:
                    mViewPager.setCurrentItem(2);
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_profiles:
                    mViewPager.setCurrentItem(0);
                   // mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_applications_advertiser:
                    mViewPager.setCurrentItem(3);
                    // mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

}
