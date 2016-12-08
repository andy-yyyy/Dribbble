/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.andy.baesapp.api;

import com.andy.baesapp.beans.GetShotsListResult;
import com.andy.baesapp.beans.ShotInfo;
import com.andy.baesapp.beans.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lixn on 2016/12/8.
 */

public interface ShotsService {

    @GET("v1/shots")
    Call<GetShotsListResult> getShotsList(@Query("list") String list,
                      @Query("timeframe") String timeFrame,
                      @Query("date") String date,
                      @Query("sort") String sort);

    @GET("v1/user")
    Call<User> getuserInfo(@Query("access_token") String token);

    @GET("v1/shots/471756")
    Call<ShotInfo> getShotInfo(@Query("access_token") String token);

}
