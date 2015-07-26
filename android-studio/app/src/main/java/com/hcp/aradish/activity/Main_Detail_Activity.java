package com.hcp.aradish.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hcp.aradish.BaseActivity;
import com.hcp.aradish.R;


public class Main_Detail_Activity extends BaseActivity {

    Toolbar mToolbar;
    CollapsingToolbarLayout mCollapsingToolbar;
    ImageView mIvTop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);
        initView();
        initData();
    }
    private void initView() {
        mToolbar = findView(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbar =findView(R.id.collapsing_toolbar);

        mIvTop = findView(R.id.backdrop);
    }

    private void initData() {
        mCollapsingToolbar.setTitle("Main_Detail_Activity");

        Glide.with(this).load(R.mipmap.aradish_dribs).centerCrop().into(mIvTop);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_detail_actions, menu);
        return true;
    }
}
