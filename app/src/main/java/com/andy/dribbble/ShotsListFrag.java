package com.andy.dribbble;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andy.dribbble.beans.ShotInfo;
import com.andy.dribbble.contract.ShotsListContract;
import com.andy.dribbble.presenter.ShotsListPresenter;

import java.util.List;

/**
 * Created by andy on 2016/12/11.
 */
public class ShotsListFrag extends BaseListFragment implements ShotsListContract.View {

    public static final String KEY_TITLE = "title";
    private ShotsListAdapter mAdapter;
    private ShotsListContract.Presenter mPresenter;

    private int mPage = 1;
    private String mTitle;

    private String mListType;
    private String mTimeFrame;
    private String mTime;
    private String mSort;

    public static ShotsListFrag newInstance(String title) {
        ShotsListFrag frag = new ShotsListFrag();
        Bundle b = new Bundle();
        b.putString(KEY_TITLE, title);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TITLE, mTitle);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(savedInstanceState);
        initView();
        initWithCache();
    }

    @Override
    public void refreshView(List<ShotInfo> infoList) {
        mAdapter.updateData(infoList);
    }

    @Override
    public void refreshMoreView(List<ShotInfo> moreList) {
        mAdapter.addData(moreList);
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        mPresenter.updateData(mPage, mListType, mTimeFrame, mTime, mSort);
    }

    @Override
    protected void onLoadMore() {
        super.onLoadMore();
        mPresenter.loadMoreData(++mPage, mListType, mTimeFrame, mTime, mSort);
    }

    private void initWithCache() {
        List<ShotInfo> shotList = CacheUtil.fetchCacheShotList(getContext());
        if (shotList != null) {
            mAdapter.updateData(shotList);
        }
    }

    public String getTitle() {
        return mTitle;
    }

    private void initView() {
        mAdapter = new ShotsListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new ShotsListPresenter(this);
        mPresenter.updateData(mPage, mListType, mTimeFrame, mTime, mSort);
        mAdapter.setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                ShotInfo shotInfo = mAdapter.getDataAtPosition(position);
                startActivity(ShotDetailAct.getIntent(getActivity(), shotInfo));
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initData(Bundle b) {
        if (b != null) {
            mTitle = b.getString(KEY_TITLE);
        } else if (getArguments() != null) {
            mTitle = getArguments().getString(KEY_TITLE);
        }
    }
}
