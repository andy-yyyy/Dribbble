package com.andy.dribbble;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.andy.dribbble.api.ApiUtil;
import com.andy.dribbble.common_utils.ToastUtil;
import com.andy.dribbble.view.LoadingButton;
import com.andy.dribbble.view.TabView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private RelativeLayout mFrameContent;
    private LinearLayout mFrameDrawer;
    private ViewPager mViewPager;
//    private TabView mTab;
    private Button mLoginBtn;
    private Toolbar mToolBar;
    private ImageView mImageView;

    private ShotsListFrag mShotsListFrag;
    private List<ShotsListFrag> mFrags;
    private boolean mChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        ToastUtil.setContext(this);
        initData();
        initTitleBar();
        initView();
        initDrawView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
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

    private void initData() {
        mShotsListFrag = new ShotsListFrag();
        mFrags = new ArrayList<>();
        ShotsListFrag a = ShotsListFrag.newInstance("Popular");
        ShotsListFrag b = ShotsListFrag.newInstance("Recent");
        ShotsListFrag c = ShotsListFrag.newInstance("Animated");
        mFrags.add(a);
        mFrags.add(b);
        mFrags.add(c);
    }

    private void initTitleBar() {
        this.mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        mToolBar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.mipmap.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView() {
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.mViewPager = (ViewPager) findViewById(R.id.view_pager);

        List<String> tabNames = new ArrayList<>();
        tabNames.add("Popular");
        tabNames.add("Recent");
        tabNames.add("Animated");
        TabLayout tab = (TabLayout) findViewById(R.id.tabs);
        tab.setTabTextColors(Color.WHITE, getResources().getColor(R.color.colorAccent));
        tab.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new ShotsPagerAdapter(getSupportFragmentManager(), mFrags, tabNames));
    }

    private void initDrawView() {
        this.mLoginBtn = (Button) findViewById(R.id.tv);
        this.mImageView = (ImageView) findViewById(R.id.iv);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(MainActivity.this, LoginAct.class));
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                if (ApiUtil.hasToken()) {
                    startActivity(UserInfoAct.getIntent(MainActivity.this, true));
                } else {
                    startActivity(LoginAct.getIntent(MainActivity.this));
                }
            }
        });

        final LoadingButton btn = (LoadingButton) findViewById(R.id.loading_btn);
        btn.setClickable(true);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.showLoading(true);
                btn.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn.showLoading(false);
                        if (mChecked) {
                            mChecked = false;
                            btn.toggle(false, "关注");
                        } else {
                            mChecked = true;
                            btn.toggle(true, "已关注");
                        }
                    }
                }, 2000);
            }
        });
    }
}
