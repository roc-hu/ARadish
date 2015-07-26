package com.hcp.aradish.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hcp.aradish.BaseActivity;
import com.hcp.aradish.R;
import com.hcp.aradish.adapter.Home_FragmentPagerAdapter;


public class Home_Activity extends BaseActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ViewPager mViewPager;
    private FloatingActionButton mFabtn;
    private TabLayout mTabLayout;

    private Home_FragmentPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initData();
    }

    private void initView() {
        mToolbar = findView(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout =findView(R.id.drawer_layout);
        mNavigationView =  findView(R.id.nav_view);
        mViewPager = findView(R.id.viewpager);
        mFabtn = findView(R.id.fab);
        mTabLayout =findView(R.id.tabs);

        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(navigationViewListener);
        }
        mFabtn.setOnClickListener(this);
    }

    private void initData() {
        if (mViewPager != null) {
            adapter = new Home_FragmentPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new Home_Fragment(), "目录一");
            adapter.addFragment(new Home_Fragment(), "目录二");
            adapter.addFragment(new Home_Fragment(), "目录三");
            mViewPager.setAdapter(adapter);

            mTabLayout.setupWithViewPager(mViewPager);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                Snackbar.make(v, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
        }
    }

    /**
     * 侧滑菜选中事件
     */
    private NavigationView.OnNavigationItemSelectedListener navigationViewListener=new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawers();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_detail_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
