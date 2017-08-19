package com.andy.dribbble;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by andy on 2017/8/19.
 */

public class BaseMDActivity extends BaseActivity {

    protected Toolbar mToolBar;
    protected ImageView mImage;
    protected NestedScrollView mContentLayout;
    protected View mContentView;

    protected int getLayoutResource() {
        return R.layout.act_base_md;
    }

    protected int getContentLayout() {
        return -1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        initData(savedInstanceState);
        initView();
    }

    @Override
    public View findViewById(int id) {
        View view = super.findViewById(id);
        if (view != null) {
            return view;
        }
        return mContentView.findViewById(id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void initData(Bundle savedInstanceState) {

    }

    protected void initView() {
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        mImage = (ImageView) findViewById(R.id.image);
        mContentLayout = (NestedScrollView) findViewById(R.id.content_container);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        if (getContentLayout() != -1) {
            mContentView = LayoutInflater.from(this).inflate(getContentLayout(), mContentLayout, true);
        }
    }
}
