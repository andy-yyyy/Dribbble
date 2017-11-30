package com.andy.dribbble.api;

import com.andy.dribbble.api.net_interface.OauthInterface;
import com.andy.dribbble.beans.Token;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

/**
 * Created by andy on 2017/1/9.
 */
public class OauthService {

    private static final Class<OauthInterface> CLASS = OauthInterface.class;
    private static final String CLIENT_ID = ApiConstants.CLIENT_ID;
    private static final String CLIENT_SECRET = ApiConstants.CLIENT_SECRET;

    public static void getUserToken(String code, Callback<Token> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", CLIENT_ID);
        params.put("client_secret", CLIENT_SECRET);
        params.put("code", code);
        ApiUtil.getApi(CLASS).getToken(params).enqueue(callback);
    }
}
