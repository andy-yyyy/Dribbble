/*
 * Copyright (C) 2017 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.andy.dribbble;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andy.dribbble.adapter.CommentsListAdapter;
import com.andy.dribbble.beans.CommentInfo;
import com.andy.dribbble.contract.CommentsListContract;
import com.andy.dribbble.presenter.CommentsListPresenter;

import java.util.List;

/**
 * Created by andy on 2016/12/28.
 */
public class TestCommentsListFrag extends BaseFragment implements CommentsListContract.View {

    public static final String SHOT_ID = "shot_id";
    private CommentsListAdapter mAdapter;
    private CommentsListContract.Presenter mPresenter;
    private int mPage = 1;
    private int mShotId;

    private RecyclerView mRecyclerView;

    public static TestCommentsListFrag getInstance(int shotId) {
        TestCommentsListFrag frag = new TestCommentsListFrag();
        Bundle bundle = new Bundle();
        bundle.putInt(SHOT_ID, shotId);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_comments_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new CommentsListAdapter(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setNestedScrollingEnabled(false);
        mPresenter = new CommentsListPresenter(this);
        mPresenter.updateData(mPage, mShotId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SHOT_ID, mShotId);
    }

    /*@Override
    protected void onLoadMore() {
        super.onLoadMore();
        mPresenter.loadMoreData(++mPage, mShotId);
    }*/

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

    }

    @Override
    public void showLoadMoreView(boolean show) {

    }

    private void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle arg = getArguments();
            if (arg != null) {
                mShotId = arg.getInt(SHOT_ID);
            }
        } else {
            savedInstanceState.get(SHOT_ID);
        }
    }
}
