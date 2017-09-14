/*
 * Copyright (C) 2017 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.andy.dribbble.presenter;

import com.andy.dribbble.api.UserInfoService;
import com.andy.dribbble.beans.ShotInfo;
import com.andy.dribbble.common_utils.ToastUtil;
import com.andy.dribbble.contract.UserShotsContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lixn on 2017/9/14.
 */

public class UserShotsPresenter implements UserShotsContract.Presenter {

    private UserShotsContract.View mView;

    public UserShotsPresenter(UserShotsContract.View view) {
        this.mView = view;
    }

    @Override
    public void updateData(int page, int userId) {
        mView.showRefreshView(true);
        UserInfoService.getUserShots(page, userId, new Callback<List<ShotInfo>>() {
            @Override
            public void onResponse(Call<List<ShotInfo>> call, Response<List<ShotInfo>> response) {
                mView.showRefreshView(false);
                if (response.isSuccessful()) {
                    mView.refreshView(response.body());
                } else {
                    ToastUtil.show(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ShotInfo>> call, Throwable t) {
                mView.showRefreshView(false);
                ToastUtil.show(t.getMessage());
            }
        });
    }

    @Override
    public void loadMoreData(int page, int userId) {
        mView.showLoadMoreView(true);
        UserInfoService.getUserShots(page, userId, new Callback<List<ShotInfo>>() {
            @Override
            public void onResponse(Call<List<ShotInfo>> call, Response<List<ShotInfo>> response) {
                mView.showLoadMoreView(false);
                if (response.isSuccessful()) {
                    mView.refreshMoreView(response.body());
                } else {
                    ToastUtil.show(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ShotInfo>> call, Throwable t) {
                mView.showLoadMoreView(false);
                ToastUtil.show(t.getMessage());
            }
        });
    }
}
