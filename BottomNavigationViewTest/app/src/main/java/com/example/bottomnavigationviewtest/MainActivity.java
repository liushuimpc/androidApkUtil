package com.example.bottomnavigationviewtest;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener {

    private final static String TAG = "MainActivity";

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem prevMenuItem;

    private BottomNavigationView.OnNavigationItemSelectedListener
            onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    break;

                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    break;

                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    break;
            }

            return true;
        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener = new
            ViewPager.OnPageChangeListener() {

                @Override
                public void onPageScrolled(int position, float
                        positionOffset, int positionOffsetPixels) {
                    Log.i(TAG, "marco----onPageScrolled=" + position);
                }

                @Override
                public void onPageSelected(int position) {
                    Log.i(TAG, "marco----onPageSelected=" + position);
                    if (prevMenuItem != null) {
                        prevMenuItem.setChecked(false);
                    } else {
                        bottomNavigationView.getMenu().getItem(0)
                                .setChecked(false);
                    }

                    bottomNavigationView.getMenu().getItem(position)
                            .setChecked(true);
                    prevMenuItem = bottomNavigationView.getMenu().getItem
                            (position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    Log.i(TAG, "marco----onPageScrollStateChanged=" + state);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.addOnPageChangeListener(onPageChangeListener);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id
                .navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (onNavigationItemSelectedListener);

        viewPager.setAdapter(new FragmentPagerAdapter
                (getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return HomeFragment.newInstance();

                    case 1:
                        return ProfileFragment.newInstance();

                    case 2:
                        return SettingsFragment.newInstance();
                }

                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        if (savedInstanceState == null) {
//            loadHomeFragment();
        }
    }

/*
    private void loadHomeFragment() {

        HomeFragment fragment = HomeFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadProfileFragment() {

        ProfileFragment fragment = ProfileFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadSettingsFragment() {

        SettingsFragment fragment = SettingsFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }
*/

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i(TAG, "marco-----uri=" + uri);
    }
}
