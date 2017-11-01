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
import android.widget.LinearLayout;

import com.andy.dribbble.contract.BaseListContract;

/**
 * Created by lixn on 2017/8/17.
 */

public class CommonListFrag extends BaseFragment implements BaseListContract.View{

    protected LinearLayout mRootLayout;
    protected RecyclerView mRecyclerView;
    protected boolean mIsLoadingMore = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootLayout = (LinearLayout) inflater.inflate(R.layout.frag_common_list, container, false);
        mRecyclerView = (RecyclerView) mRootLayout.findViewById(R.id.recycler_view);
        return mRootLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        //因为外层有NestedScrollView，并且RecyclerView设置了setNestedScrollEnabled()为false，导致findLastVisiblePosition不准确
        /*
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                BaseListAdapter adapter = (BaseListAdapter) mRecyclerView.getAdapter();
                int itemCount = adapter.getItemCount();
                int last = manager.findLastVisibleItemPosition();
                if (!mIsLoadingMore && last > 0 && last == itemCount - 1) {
                    onLoadMore();
                    showLoadMoreView(true);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });*/
        //避免onLoad()方法执行时Adapter的底部加载UI还未初始化，导致不能显示加载UI，延迟执行该方法
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoad();
            }
        }, 100);
    }

    @Override
    public void showRefreshView(boolean refreshing) {
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter instanceof BaseListAdapter) {
            ((BaseListAdapter) adapter).showLoadingView(refreshing);
        }
    }

    @Override
    public void showLoadMoreView(boolean show) {
        mIsLoadingMore = show;
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter instanceof BaseListAdapter) {
            ((BaseListAdapter) adapter).showLoadingView(show);
        }
    }

    /**
     * 触发加载更多操作
     */
    protected void onLoadMore() {
        showLoadMoreView(true);
    }

    /**
     * 触发加载操作
     */
    protected void onLoad() {
        showRefreshView(true);
    }
}
