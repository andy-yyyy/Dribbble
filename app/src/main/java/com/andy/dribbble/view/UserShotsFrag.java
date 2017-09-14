/*
 * Copyright (C) 2017 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.andy.dribbble.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andy.dribbble.CommentsListFrag;
import com.andy.dribbble.CommonListFrag;
import com.andy.dribbble.ShotsListAdapter;
import com.andy.dribbble.beans.ShotInfo;
import com.andy.dribbble.contract.UserShotsContract;
import com.andy.dribbble.presenter.ShotsListPresenter;
import com.andy.dribbble.presenter.UserShotsPresenter;

import java.util.List;

/**
 * Created by lixn on 2017/9/12.
 */

public class UserShotsFrag extends CommonListFrag implements UserShotsContract.View {

    private static final String USER_ID = "user_id";

    private ShotsListAdapter mAdapter;
    private UserShotsPresenter mPresenter;

    private int mPage = 1;
    private int mUserId;

    public static UserShotsFrag newInstance(int userId) {
        UserShotsFrag frag = new UserShotsFrag();
        Bundle bundle = new Bundle();
        bundle.putInt(USER_ID, userId);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new ShotsListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setNestedScrollingEnabled(false);
        mPresenter = new UserShotsPresenter(this);
        mPresenter.updateData(mPage, mUserId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(USER_ID, mUserId);
    }

    @Override
    protected void onLoadMore() {
        super.onLoadMore();
        mPresenter.loadMoreData(++mPage, mUserId);
    }

    @Override
    public void refreshView(List<ShotInfo> infoList) {
        if (infoList != null) {
            mAdapter.updateData(infoList);
        }
    }

    @Override
    public void refreshMoreView(List<ShotInfo> moreList) {
        if (moreList != null) {
            mAdapter.addData(moreList);
        }
    }

    private void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mUserId = savedInstanceState.getInt(USER_ID);
        } else if (getArguments() != null) {
            mUserId = getArguments().getInt(USER_ID);
        }
    }
}
