package com.andy.base.api;

import com.andy.base.beans.Token;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by andy on 2017/1/9.
 */
public class TokenService {

    private static final Class<TokenInterface> CLASS = TokenInterface.class;
    private static final String TOKEN = ApiConstants.CLIENT_ACCESS_TOKEN;
    private static final String CLIENT_ID = ApiConstants.CLIENT_ID;
    private static final String CIENT_SECRET = ApiConstants.CLIENT_SECRET;
    private static final String REDIRECT_URI = "https:www.baidu.com";
    private static final String SCOPE = "write";
    private static final String STATE = "fdierjel";
    private static final String CODE = "b8bbda39a5b6bc0fc140ad51c64ae2029ecf782cd771b9e3d4df8e949658f339";

    public static void getAuth(Callback<Object> callback) {
        ApiUtil.getApi(CLASS).getAuth(CLIENT_ID, REDIRECT_URI, SCOPE, STATE).enqueue(callback);

    }

    public static void getToken(Callback<Token> callback) {
        ApiUtil.getApi(CLASS).getToken(CLIENT_ID, CIENT_SECRET, CODE, REDIRECT_URI).enqueue(callback);
    }
}
