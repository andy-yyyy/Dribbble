package com.andy.dribbble.api;

import com.andy.dribbble.beans.Token;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

/**
 * Created by andy on 2017/1/9.
 */
public class OauthService {

    private static final Class<OauthInterface> CLASS = OauthInterface.class;
    private static final String TOKEN = ApiConstants.CLIENT_ACCESS_TOKEN;
    private static final String CLIENT_ID = ApiConstants.CLIENT_ID;
    private static final String CLIENT_SECRET = ApiConstants.CLIENT_SECRET;
    private static final String REDIRECT_URI = ApiConstants.REDIRECT_URI;
    private static final String SCOPE = "write";
    private static final String STATE = "fdierjel";

    public static void getAuth(Callback<Object> callback) {
        ApiUtil.getApi(CLASS).getAuth(CLIENT_ID, REDIRECT_URI, SCOPE, STATE).enqueue(callback);
    }

    public static void getUserToken(String code, Callback<Token> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", CLIENT_ID);
        params.put("client_secret", CLIENT_SECRET);
        params.put("code", code);
//        params.put("redirect_uri", REDIRECT_URI);
        ApiUtil.getApi(CLASS).getToken(params).enqueue(callback);
    }
}
