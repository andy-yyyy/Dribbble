package com.andy.dribbble;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andy.dribbble.adapter.BaseListAdapter;
import com.andy.dribbble.adapter.ShotsListAdapter;
import com.andy.dribbble.api.ShotsService;
import com.andy.dribbble.beans.ShotInfo;
import com.andy.dribbble.common_utils.ToastUtil;
import com.andy.dribbble.contract.ShotsListContract;
import com.andy.dribbble.local_beans.ShotListConfig;
import com.andy.dribbble.presenter.ShotsListPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andy on 2016/12/11.
 */
public class ShotsListFrag extends BaseListFragment implements ShotsListContract.View ,
        BaseListAdapter.OnItemClickListener, ShotsListAdapter.ActionListener {

    public static final String KEY_CONFIG = "config";
    private ShotsListAdapter mAdapter;
    private ShotsListContract.Presenter mPresenter;

    private ShotListConfig mConfig;
    private int mPage = 1;
    private String mListType;
    private String mTimeFrame;
    private String mTime;
    private String mSort;

    public static ShotsListFrag newInstance(ShotListConfig config) {
        ShotsListFrag frag = new ShotsListFrag();
        Bundle b = new Bundle();
        b.putSerializable(KEY_CONFIG, config);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_CONFIG, mConfig);
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
        CacheUtil.cacheShotList(getActivity(), infoList);
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

    @Override
    public void onItemClick(View itemView, int position) {
        ShotInfo shotInfo = mAdapter.getDataAtPosition(position);
        startActivity(ShotDetailAct.getIntent(getActivity(), shotInfo));
    }

    @Override
    public void onLikeClick(View view, int shotId) {
        ShotsService.likeShot(shotId, new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    ToastUtil.show("喜欢成功");
                } else {
                    ToastUtil.show("喜欢失败"+response.message());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                ToastUtil.show("喜欢失败啊"+t.getMessage());
            }
        });
    }

    private void initWithCache() {
        List<ShotInfo> shotList = CacheUtil.fetchCacheShotList(getContext());
        if (shotList != null) {
            mAdapter.updateData(shotList);
        }
    }

    private void initView() {
        mAdapter = new ShotsListAdapter(this);
        mAdapter.setActionListener(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new ShotsListPresenter(this);
        mPresenter.updateData(mPage, mListType, mTimeFrame, mTime, mSort);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initData(Bundle b) {
        if (b != null) {
            mConfig = (ShotListConfig) b.getSerializable(KEY_CONFIG);
        } else if (getArguments() != null) {
            mConfig = (ShotListConfig) getArguments().getSerializable(KEY_CONFIG);
        }
        if (mConfig != null) {
            mListType = mConfig.listType;
            mTimeFrame = mConfig.timeFrame;
            mTime = mConfig.date;
            mSort = mConfig.sort;
        }
    }
}
