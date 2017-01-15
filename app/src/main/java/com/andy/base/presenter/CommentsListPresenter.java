/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.andy.base.presenter;

import com.andy.base.api.CommentsService;
import com.andy.base.beans.CommentInfo;
import com.andy.base.common_utils.ToastUtil;
import com.andy.base.contract.CommentsListContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lixn on 2016/12/28.
 */

public class CommentsListPresenter implements CommentsListContract.Presenter {

    private CommentsListContract.View mView;

    public CommentsListPresenter(CommentsListContract.View view) {
        this.mView = view;
    }

    @Override
    public void updateData(int page, int shotId) {
        CommentsService.getCommentsList(page, shotId, new Callback<List<CommentInfo>>() {
            @Override
            public void onResponse(Call<List<CommentInfo>> call, Response<List<CommentInfo>> response) {
                mView.showRefreshView(false);
                if (response.isSuccessful()) {
                    mView.refreshView(response.body());
                } else {
                    ToastUtil.show(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<CommentInfo>> call, Throwable t) {
                mView.showRefreshView(false);
                ToastUtil.show(t.getMessage());
            }
        });
    }

    @Override
    public void loadMoreData(int page, int shotId) {
        CommentsService.getCommentsList(page, shotId, new Callback<List<CommentInfo>>() {
            @Override
            public void onResponse(Call<List<CommentInfo>> call, Response<List<CommentInfo>> response) {
                mView.showLoadMoreView(false);
                if (response.isSuccessful()) {
                    mView.refreshMoreView(response.body());
                } else {
                    ToastUtil.show(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<CommentInfo>> call, Throwable t) {
                mView.showLoadMoreView(false);
                ToastUtil.show(t.getMessage());
            }
        });
    }
}
