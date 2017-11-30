/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.andy.dribbble.api.net_interface;

import com.andy.dribbble.beans.CommentInfo;
import com.andy.dribbble.beans.ShotInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lixn on 2016/12/8.
 */

public interface ShotsInterface {

    @GET("v1/shots")
    Call<List<ShotInfo>> getShotsList(
            @Query("access_token") String token,
            @Query("per_page") int pageSize,
            @Query("page") int page,
            @Query("list") String list,
            @Query("timeframe") String timeFrame,
            @Query("date") String date,
            @Query("sort") String sort
    );

    @GET("v1/shots/{shot_id}")
    Call<ShotInfo> getShotInfo(
            @Query("access_token") String token,
            @Query("shot_id") int shotId
    );

    @GET("v1/shots/{shot_id}/comments")
    Call<List<CommentInfo>> getCommentsList(
            @Query("access_token") String token,
            @Query("per_page") int pageSize,
            @Query("page") int page,
            @Query("shot_id") int shotId
    );

    @FormUrlEncoded
    @POST("v1/shots/{shot_id}/like")
    Call<Object> likeShot(
            @Path("shot_id") int shotId,
            @Field("access_token") String token
    );

    @GET("v1/shots/{shot_id}/like")
    Call<Object> checkLike(
            @Path("shot_id") int shotId,
            @Query("access_token") String token
    );

}
