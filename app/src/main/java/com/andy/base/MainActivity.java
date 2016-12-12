package com.andy.base;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.andy.base.commom_utils.ToastUtil;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private FrameLayout mFrameContent;
    private LinearLayout mFrameDrawer;
    private Button mCloseBtn;
    private ShotsListFrag mShotsListFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        ToastUtil.setContext(this);
        this.mCloseBtn = (Button) findViewById(R.id.btn);
        this.mFrameDrawer = (LinearLayout) findViewById(R.id.frame_drawer);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.mFrameContent = (FrameLayout) findViewById(R.id.frame_content);
        initData();
        initView();
    }

    private void initData() {
        mShotsListFrag = new ShotsListFrag();
    }
    private void initView() {
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_content, mShotsListFrag).commitAllowingStateLoss();
    }

}
