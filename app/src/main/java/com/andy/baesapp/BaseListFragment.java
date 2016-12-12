package com.andy.baesapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.andy.baesapp.contract.BaseListContract;

/**
 * Created by andy on 2016/12/11.
 */
public class BaseListFragment extends BaseFragment implements BaseListContract.View {

    protected View mRootLayout;
    protected SwipeRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected ViewGroup mFooter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootLayout = inflater.inflate(R.layout.frag_base_list, container, false);
        mRefreshLayout = (SwipeRefreshLayout) mRootLayout;
        mRecyclerView = (RecyclerView) mRootLayout.findViewById(R.id.recycler_view);
        return mRootLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BaseListFragment.this.onRefresh();
            }
        });
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        if (mRefreshLayout.isRefreshing() == refreshing) {
            return;
        }
        mRefreshLayout.setRefreshing(refreshing);
    }

    /**
     * 触发下拉刷新操作
     */
    protected void onRefresh() {

    }

    public View getRootView() {
        return mRootLayout;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
