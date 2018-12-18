package com.wizeline.wizelinemovieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.wizeline.wizelinemovieapp.components.AbstractAppCompatActivity;
import com.wizeline.wizelinemovieapp.nowplaying.view.NowPlayingFragment;
import com.wizeline.wizelinemovieapp.toprated.view.TopRatedFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AbstractAppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    BottomNavigationView navigation;

    private NowPlayingFragment fragment1 = new NowPlayingFragment();
    private TopRatedFragment fragment2 = new TopRatedFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0,true);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1,true);
                    return true;
            }
            return true;
        }
    };

    @Override
    public void onRetryInternet() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return fragment1;
                    case 1:
                        return fragment2;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        navigation.getMenu().getItem(i).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
