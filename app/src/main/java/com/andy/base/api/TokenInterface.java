package com.andy.base.api;

import com.andy.base.beans.Token;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by andy on 2017/1/9.
 */
public interface TokenInterface {

    @GET("https://dribbble.com/oauth/authorize")
    Call<Object> getAuth(
            @Query("client_id") String clientId,
            @Query(("redirect_uri")) String redirectUri,
            @Query("scope") String scope,
            @Query("state") String state
    );

    @Headers({
            "Content-type:application/json"
    })
    @POST("https://dribbble.com/oauth/token")
    Call<Token> getToken(
            @Body JSONObject data
            );
}
