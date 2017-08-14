package com.andy.dribbble;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.andy.base.beans.CommentInfo;
import com.andy.base.beans.ShotInfo;
import com.andy.base.contract.CommentsListContract;
import com.andy.base.contract.ShotsListContract;
import com.andy.base.presenter.CommentsListPresenter;
import com.andy.base.presenter.ShotsListPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 2016/12/11.
 */
public class ShotDetailAct extends BaseActivity implements CommentsListContract.View {

    public static final String SHOT_INFO = "shot_info";

    private RecyclerView mRecyclerView;
    private CommentsListContract.Presenter mPresenter;
    private CommentsListAdapter mAdapter;
    private ShotInfo mShotInfo;
    private int mPage = 1;
    private int mShotId;

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
        mAdapter = new CommentsListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mPresenter = new CommentsListPresenter(this);
        mPresenter.updateData(mPage, mShotId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SHOT_INFO, mShotInfo);
    }

    @Override
    public void refreshView(List<CommentInfo> infoList) {
        mAdapter.updateData(infoList);
    }

    @Override
    public void refreshMoreView(List<CommentInfo> moreList) {
        mAdapter.addData(moreList);
    }

    @Override
    public void showRefreshView(boolean refreshing) {
//        mPresenter.updateData(mPage, mShotId);
    }

    @Override
    public void showLoadMoreView(boolean show) {
//        mPresenter.loadMoreData(++mPage, mShotId);
    }

    private void initView() {
        initDetailHeader();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        /*mDetailContainer = (CardView) findViewById(R.id.detail_container);
        mCommentsContainer = (CardView) findViewById(R.id.comments__container);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mShotInfo != null) {
            ft.replace(R.id.detail_container, ShotDetailFrag.getInstance(mShotInfo));
            ft.replace(R.id.comments__container, CommentsListFrag.getInstance(mShotInfo.getId())).commitAllowingStateLoss();
        }*/
    }

    private void initDetailHeader() {
        TextView updateTime = (TextView) findViewById(R.id.update_time);
        TextView likesCount = (TextView) findViewById(R.id.likes_count);
        TextView commentsCount = (TextView) findViewById(R.id.comments_count);
        TextView viewsCount = (TextView) findViewById(R.id.views_count);
        TextView title = (TextView) findViewById(R.id.title);
        TextView description = (TextView) findViewById(R.id.description);
        TextView name = (TextView) findViewById(R.id.name);
        TextView userName = (TextView) findViewById(R.id.user_name);

        if (mShotInfo != null) {
            updateTime.setText(mShotInfo.getUpdateTime());
            likesCount.setText(getFormatString(R.string.likes_count, mShotInfo.getLikesCount()));
            commentsCount.setText(getFormatString(R.string.comments_count, mShotInfo.getCommentsCount()));
            viewsCount.setText(getFormatString(R.string.views_count, mShotInfo.getViewsCount()));
            title.setText(mShotInfo.getTitle());
            if (mShotInfo.getDescription() != null) {
                description.setText(Html.fromHtml(mShotInfo.getDescription()));
            }
            name.setText(mShotInfo.getUserInfo().getName());
            userName.setText(mShotInfo.getUserInfo().getUserName());
        }
    }

    private String getFormatString(int resId, Object obj) {
        return String.format(this.getResources().getString(resId), obj);
    }

    private void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mShotInfo = (ShotInfo) getIntent().getSerializableExtra(SHOT_INFO);
        } else {
            mShotInfo = (ShotInfo) savedInstanceState.get(SHOT_INFO);
        }
        mShotId = mShotInfo != null ? mShotInfo.getId() : 0;
    }

    List<String> getData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add("item"+(i+1));
        }
        return data;
    }
}
