package com.andy.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.andy.base.commom_utils.ToastUtil;
import com.andy.base.contract.BaseListContract;

/**
 * Created by andy on 2016/12/11.
 */
public class BaseListFragment extends BaseFragment implements BaseListContract.View {

    protected View mRootLayout;
    protected SwipeRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager mLayoutManager;
    protected ViewGroup mFooter;
    protected boolean isLoadingMore = false;

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
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BaseListFragment.this.onRefresh();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                BaseListAdapter adapter = (BaseListAdapter) mRecyclerView.getAdapter();
                int itemCount = adapter.getItemCount();
                int last = manager.findLastVisibleItemPosition();
                if (!isLoadingMore && last == itemCount - 1) {
                    onLoadMore();
                    showLoadMoreView(true);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    public void showRefreshView(boolean refreshing) {
        if (mRefreshLayout.isRefreshing() == refreshing) {
            return;
        }
        mRefreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void showLoadMoreView(boolean show) {
        isLoadingMore = show;
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter instanceof BaseListAdapter) {
            ((BaseListAdapter)adapter).setFooterViewEnabled(show);
        }
    }

    /**
     * 触发下拉刷新操作
     */
    protected void onRefresh() {

    }

    /**
     * 触发加载更多操作
     */
    protected void onLoadMore() {

    }

    public View getRootView() {
        return mRootLayout;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
