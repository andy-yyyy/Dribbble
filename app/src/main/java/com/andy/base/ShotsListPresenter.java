package com.andy.base;

import com.andy.base.api.ShotsService;
import com.andy.base.beans.ShotInfo;
import com.andy.base.commom_utils.ToastUtil;
import com.andy.base.contract.ShotsListContract;

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
    public void updateData(int page, String list, String timeFrame, String time, String sort) {
        ShotsService.getShotsList(page, list, timeFrame, time, sort, new Callback<List<ShotInfo>>() {
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
