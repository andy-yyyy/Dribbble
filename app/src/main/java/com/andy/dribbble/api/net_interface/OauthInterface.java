package com.andy.dribbble.api.net_interface;

import com.andy.dribbble.beans.Token;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by andy on 2017/1/9.
 */
public interface OauthInterface {
    @FormUrlEncoded
    @POST("https://dribbble.com/oauth/token")
    Call<Token> getToken(
            @FieldMap()Map<String, String> params
            );
}
