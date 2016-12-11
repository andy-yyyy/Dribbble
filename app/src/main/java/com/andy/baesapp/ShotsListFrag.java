package com.andy.baesapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.andy.baesapp.beans.ShotInfo;
import com.andy.baesapp.commom_utils.ScreenUtil;
import com.andy.baesapp.contract.ShotsListContract;

import java.util.List;

/**
 * Created by andy on 2016/12/11.
 */
public class ShotsListFrag extends BaseListFragment implements ShotsListContract.View {

    private ShotsListAdapter mAdapter;
    private ShotsListContract.Presenter mPresenter;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new ShotsListAdapter();
        mPresenter = new ShotsListPresenter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.updateData();
        mRefreshLayout.setProgressViewOffset(false, 0, ScreenUtil.dip2px(getActivity(), 24));
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void refreshView(List<ShotInfo> infoList) {
        mAdapter.updateData(infoList);
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        mPresenter.updateData();
    }
}
