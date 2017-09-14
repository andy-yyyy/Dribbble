package com.andy.dribbble.api;

import com.andy.dribbble.beans.ShotInfo;
import com.andy.dribbble.beans.UserInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by andy on 2017/1/9.
 */
public interface UserInfoInterface {

    @GET("v1/user")
    Call<UserInfo> getUserInfo(
            @Query("access_token") String token
    );

    @PUT("v1/users/{user_id}/follow")
    Call<Object> follow(
            @Path("user_id") int userId,
            @Query("access_token") String token
    );

    @GET("v1/users/{user_id}/shots")
    Call<ShotInfo> getUserShots(
            @Query("access_token") String token,
            @Path("user_id") int userId
    );
}
