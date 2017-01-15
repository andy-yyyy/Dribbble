package com.andy.base.api;

import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by andy on 2017/1/9.
 */
public interface UserInfoInterface {

    @PUT("v1/users/{user_id}/follow")
    Call<Object> follow(
            @Path("user_id") int userId,
            @Query("access_token") String token
    );

}
