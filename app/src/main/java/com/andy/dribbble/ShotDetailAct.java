package com.andy.dribbble;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.andy.dribbble.beans.ShotInfo;

/**
 * Created by andy on 2016/12/11.
 */
public class ShotDetailAct extends BaseActivity {

    public static final String SHOT_INFO = "shot_info";
    private ShotInfo mShotInfo;

    public static Intent getIntent(Context context, ShotInfo shotInfo) {
        Intent intent = new Intent(context, ShotDetailAct.class);
        intent.putExtra(SHOT_INFO, shotInfo);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_shot_detail);
        initData(savedInstanceState);
        initView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SHOT_INFO, mShotInfo);
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return true;
//    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mShotInfo != null) {
            toolbar.setTitle(mShotInfo.getTitle());
            ft.replace(R.id.header_container, ShotDetailFrag.getInstance(mShotInfo));
            ft.replace(R.id.list_container, CommentsListFrag.getInstance(mShotInfo.getId())).commitAllowingStateLoss();
        }
    }

    private void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mShotInfo = (ShotInfo) getIntent().getSerializableExtra(SHOT_INFO);
        } else {
            mShotInfo = (ShotInfo) savedInstanceState.get(SHOT_INFO);
        }
    }
}
