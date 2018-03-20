package com.andy.dribbble;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.andy.dribbble.beans.ShotInfo;
import com.andy.dribbble.view.ImageViewer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by andy on 2016/12/11.
 */
public class ShotDetailAct extends BaseActivity {

    public static final String SHOT_INFO = "shot_info";
    private ShotInfo mShotInfo;
    private CommentsListFrag mCommentsFrag;
    private ImageViewer mScaleImageView;

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return !mScaleImageView.handleBackEvent() && super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        final ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScaleImageView.show(ShotDetailAct.this, imageView);
            }
        });
        mScaleImageView = new ImageViewer(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mShotInfo != null) {
            mCommentsFrag = CommentsListFrag.getInstance(mShotInfo.getId());
            toolbar.setTitle(mShotInfo.getTitle());
            Glide.with(this).load(mShotInfo.getImages().getNormal()).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    Drawable d = resource.getCurrent();
                    imageView.setImageDrawable(d);
                    mScaleImageView.setImgDrawable(d);
                    Log.d(TAG, "src img width: "+d.getIntrinsicWidth()+"; height: "+d.getIntrinsicHeight());
                }
            });
            ft.replace(R.id.header_container, ShotDetailFrag.getInstance(mShotInfo));
            ft.replace(R.id.list_container, mCommentsFrag).commitAllowingStateLoss();
        }
        initScrollView();
    }

    private void initScrollView() {
        final NestedScrollView scrollView  = (NestedScrollView) findViewById(R.id.scroll_view);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()))  {
                    // 滑动到底部了，开始加载更多数据
                    if (mCommentsFrag != null) {
                        mCommentsFrag.onLoadMore();
                    }
                }
            }
        });
    }

    private void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mShotInfo = (ShotInfo) getIntent().getSerializableExtra(SHOT_INFO);
        } else {
            mShotInfo = (ShotInfo) savedInstanceState.get(SHOT_INFO);
        }
    }
}
