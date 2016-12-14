package com.andy.base;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.andy.base.commom_utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private RelativeLayout mFrameContent;
    private LinearLayout mFrameDrawer;
    private ViewPager mViewPager;
    private TabView mTab;
    private Button mCloseBtn;
    private ShotsListFrag mShotsListFrag;
    private List<ShotsListFrag> mFrags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        ToastUtil.setContext(this);
        getSupportActionBar().setElevation(0);
        this.mCloseBtn = (Button) findViewById(R.id.btn);
        this.mFrameDrawer = (LinearLayout) findViewById(R.id.frame_drawer);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.mFrameContent = (RelativeLayout) findViewById(R.id.frame_content);
        this.mViewPager = (ViewPager) findViewById(R.id.view_pager);
        this.mTab = (TabView) findViewById(R.id.tab_view);
        initData();
        initView();
    }

    private void initData() {
        mShotsListFrag = new ShotsListFrag();
        mFrags = new ArrayList<>();
        ShotsListFrag a = new ShotsListFrag();
        ShotsListFrag b = new ShotsListFrag();
        ShotsListFrag c = new ShotsListFrag();
        mFrags.add(a);
        mFrags.add(b);
        mFrags.add(c);
    }

    private void initView() {
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        mTab.addItem("Popular");
        mTab.addItem("Recent");
        mTab.addItem("Animated");
        mTab.setSelection(0);
        mViewPager.setAdapter(new ShotsPagerAdapter(getSupportFragmentManager(), mFrags));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTab.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
