package com.andy.dribbble.api.net_interface;

import com.andy.dribbble.beans.Token;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by andy on 2017/1/9.
 */
public interface OauthInterface {

    @GET("https://dribbble.com/oauth/authorize")
    Call<Object> getAuth(
            @Query("client_id") String clientId,
            @Query(("redirect_uri")) String redirectUri,
            @Query("scope") String scope,
            @Query("state") String state
    );

    @FormUrlEncoded
    @POST("https://dribbble.com/oauth/token")
    Call<Token> getToken(
            @FieldMap()Map<String, String> params
            );
}
