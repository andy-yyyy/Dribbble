package com.andy.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andy.base.beans.ShotInfo;
import com.andy.base.commom_utils.ScreenUtil;
import com.andy.base.contract.ShotsListContract;

import java.util.List;

/**
 * Created by andy on 2016/12/11.
 */
public class ShotsListFrag extends BaseListFragment implements ShotsListContract.View {

    private ShotsListAdapter mAdapter;
    private ShotsListContract.Presenter mPresenter;

    private int mPage;
    private String mListType;
    private String mTimeFrame;
    private String mTime;
    private String mSort;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new ShotsListAdapter(this);
        mPresenter = new ShotsListPresenter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.updateData(mPage, mListType, mTimeFrame, mTime, mSort);
        mRefreshLayout.setProgressViewOffset(false, 0, ScreenUtil.dip2px(getActivity(), 24));
        mRefreshLayout.setRefreshing(true);
        mAdapter.setOnItemClickListener(new ShotsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v) {
                startActivity(ShotDetailAct.getIntent(getActivity()));
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void refreshView(List<ShotInfo> infoList) {
        mAdapter.updateData(infoList);
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        mPresenter.updateData(++mPage, mListType, mTimeFrame, mTime, mSort);
    }
}
