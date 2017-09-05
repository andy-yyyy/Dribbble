package com.andy.dribbble;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.andy.dribbble.api.ApiUtil;
import com.andy.dribbble.common_utils.ToastUtil;
import com.andy.dribbble.local_beans.ShotListConfig;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;

    private List<ShotsListFrag> mFrags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        ToastUtil.setContext(this);
        initData();
        initTitleBar();
        initDrawView();
        initView();
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
        mFrags = new ArrayList<>();
        ShotsListFrag a = ShotsListFrag.newInstance(new ShotListConfig.Builder().build());
        ShotsListFrag b = ShotsListFrag.newInstance(new ShotListConfig.Builder().sort("recent").build());
        ShotsListFrag c = ShotsListFrag.newInstance(new ShotListConfig.Builder().listType("animated").build());
        mFrags.add(a);
        mFrags.add(b);
        mFrags.add(c);
    }

    private void initTitleBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.mipmap.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView() {
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        List<String> tabNames = new ArrayList<>();
        tabNames.add("Popular");
        tabNames.add("Recent");
        tabNames.add("Animated");
        TabLayout tab = (TabLayout) findViewById(R.id.tabs);
        tab.setTabTextColors(Color.WHITE, getResources().getColor(R.color.colorAccent));
        tab.setupWithViewPager(viewPager);
        viewPager.setAdapter(new ShotsPagerAdapter(getSupportFragmentManager(), mFrags, tabNames));
    }

    private void initDrawView() {
        final NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        nav.getMenu().getItem(0).setChecked(true);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(!item.isChecked());
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        View header = nav.getHeaderView(0);
        if (header != null) {
            header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDrawerLayout.closeDrawers();
                    if (ApiUtil.hasToken()) {
                        startActivity(UserInfoAct.getIntent(MainActivity.this, true));
                    } else {
                        startActivity(LoginAct.getIntent(MainActivity.this));
                    }
                }
            });
        }
    }
}
