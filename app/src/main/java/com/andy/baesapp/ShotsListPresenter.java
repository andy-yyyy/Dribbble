package com.andy.baesapp;

import com.andy.baesapp.api.ShotsService;
import com.andy.baesapp.beans.ShotInfo;
import com.andy.baesapp.commom_utils.ToastUtil;
import com.andy.baesapp.contract.ShotsListContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andy on 2016/12/11.
 */
public class ShotsListPresenter implements ShotsListContract.Presenter {

    private ShotsListContract.View mView;
    public ShotsListPresenter(ShotsListContract.View view) {
        this.mView = view;
    }

    @Override
    public void updateData() {
        ShotsService.getShotsList(null, null, "2016-04-23", null, new Callback<List<ShotInfo>>() {
            @Override
            public void onResponse(Call<List<ShotInfo>> call, Response<List<ShotInfo>> response) {
                mView.setRefreshing(false);
                if (response.isSuccessful()) {
                    mView.refreshView(response.body());
                } else {
                    ToastUtil.show(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ShotInfo>> call, Throwable t) {
                mView.setRefreshing(false);
                ToastUtil.show(t.getMessage());
            }
        });
    }
}
