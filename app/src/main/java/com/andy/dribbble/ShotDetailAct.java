package com.andy.dribbble;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.andy.dribbble.beans.ShotInfo;
import com.bumptech.glide.Glide;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_detail, menu);
        return true;
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mShotInfo != null) {
            toolbar.setTitle(mShotInfo.getTitle());
            Glide.with(this).load(mShotInfo.getImages().getNormal()).into(imageView);
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
